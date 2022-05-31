package knz.paint.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import knz.paint.model.Config;
import knz.paint.model.effects.AbstractEffect;
import knz.paint.model.effects.hsba.AdjustHSBAEffect;
import knz.paint.model.effects.hsba.ExtractBrightnessEffect;
import knz.paint.model.effects.hsba.ExtractSaturationEffect;
import knz.paint.model.effects.hsba.SaltPepperEffect;
import knz.paint.model.effects.hsba.gray.BlackWhiteEffect;
import knz.paint.model.effects.positional.ExplosionEffect;
import knz.paint.model.effects.positional.FlipEffect;
import knz.paint.model.effects.positional.MosaicEffect;
import knz.paint.model.effects.positional.PolarCoordinatesEffect;
import knz.paint.model.effects.positional.RotationEffect;
import knz.paint.model.effects.positional.StainedGlassEffect;
import knz.paint.model.effects.rgba.AdjustContrastEffect;
import knz.paint.model.effects.rgba.AdjustGammaEffect;
import knz.paint.model.effects.rgba.AdjustRGBAEffect;
import knz.paint.model.effects.rgba.AlphaAsGrayEffect;
import knz.paint.model.effects.rgba.BitShiftEffect;
import knz.paint.model.effects.rgba.ExtractRGBAEffect;
import knz.paint.model.effects.rgba.GrayscaleEffect;
import knz.paint.model.effects.rgba.NegateEffect;
import knz.paint.model.effects.rgba.NoiseEffect;
import knz.paint.model.effects.rgba.NormalizeEffect;
import knz.paint.model.effects.rgba.ReduceContrastEffect;
import knz.paint.model.effects.rgba.SepiaEffect;
import knz.paint.model.effects.rgba.SolarizationEffect;
import knz.paint.tools.AbstractTool;
import knz.paint.view.colorpicker.ColorPickerWindow;
import knz.paint.view.plainpanels.PalettePanel;

public class MainWindow extends JFrame {

    private static final String TITLE = "熊猫沢ペイント";

    private static final FileFilter FILTER_BMP = createFileFilter("Microsoft Windows Bitmap", "bmp", "dib");
    private static final FileFilter FILTER_GIF = createFileFilter("Graphics Interchange Format", "gif");
    private static final FileFilter FILTER_JPG = createFileFilter("Joint Photographic Experts Group", "jpg", "jpeg");
    private static final FileFilter FILTER_PNG = createFileFilter("Portable Network Graphics", "png");

    private static final int[] ZOOM_DIVISORS = { 32, 16, 8, 4, 2, 1, 1, 1, 1,  1,  1 };
    private static final int[] ZOOM_FACTORS  = {  1,  1, 1, 1, 1, 1, 2, 4, 8, 16, 32 };
    private static final int ZOOM_LEVELS = ZOOM_FACTORS.length;
    private static final int ZOOM_DEFAULT_LEVEL = 5;

    private static final int[] STOKE_WIDTHS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 25, 30, 40, 50 };
    private static final int[] ROUNDED_RECTANGLE_RADII = { 5, 10, 15, 20, 25, 50, 75, 100 };
    private static final int[] AIRBRUSH_SIZES = { 5, 10, 15, 20, 25 };

    private static final AbstractEffect[] EFFECTS = {
        new ExplosionEffect(),
        new FlipEffect(),
        new MosaicEffect(),
        new PolarCoordinatesEffect(),
        new RotationEffect(),
        new StainedGlassEffect(),
        null,
        new AdjustContrastEffect(),
        new AdjustGammaEffect(),
        new AdjustRGBAEffect(),
        new AlphaAsGrayEffect(),
        new BitShiftEffect(),
        new ExtractRGBAEffect(),
        new GrayscaleEffect(),
        new NegateEffect(),
        new NoiseEffect(),
        new NormalizeEffect(),
        new ReduceContrastEffect(),
        new SepiaEffect(),
        new SolarizationEffect(),
        null,
        new AdjustHSBAEffect(),
        new ExtractBrightnessEffect(),
        new ExtractSaturationEffect(),
        new SaltPepperEffect(),
        null,
        new BlackWhiteEffect(),
    };

    private ColorPickerWindow colorPickerWindow = null;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuFileLoad = new JMenuItem("Load...");
    private JMenuItem menuFileSave = new JMenuItem("Save...");
    private JMenuItem menuFileAbout = new JMenuItem("About...");
    private JMenuItem menuFileQuit = new JMenuItem("Quit");

    private JMenu menuEdit = new JMenu("Edit");
    private JMenuItem menuEditSelectNone = new JMenuItem("Select none");
    private JMenuItem menuEditSelectAll = new JMenuItem("Select all");
    private JMenuItem menuEditCut = new JMenuItem("Cut");
    private JMenuItem menuEditCopy = new JMenuItem("Copy");
    private JMenuItem menuEditPaste = new JMenuItem("Paste");
    private JMenuItem menuEditClearSelection = new JMenuItem("Clear selection");
    private JMenuItem menuEditCropToSelection = new JMenuItem("Crop to selection");

    private JMenu menuView = new JMenu("View");
    private JCheckBoxMenuItem menuViewToolBar = new JCheckBoxMenuItem("Tool bar");
    private JCheckBoxMenuItem menuViewColorBar = new JCheckBoxMenuItem("Color bar");
    private JCheckBoxMenuItem menuViewStatusBar = new JCheckBoxMenuItem("Status bar");
    private JMenu menuViewZoom = new JMenu("Zoom");
    private JMenuItem menuViewZoomIn = new JMenuItem("Zoom in");
    private JMenuItem menuViewZoomOut = new JMenuItem("Zoom out");
    private List<JMenuItem> menuViewZoomLevels = new ArrayList<>();

    private JMenu menuOptions = new JMenu("Options");
    private JMenuItem menuOptionsColorPicker = new JMenuItem("Color picker...");
    private JMenu menuOptionsFillStyle = new JMenu("Fill style");
    private JMenu menuOptionsStrokeWidth = new JMenu("Stroke width");
    private JMenu menuOptionsStrokeDash = new JMenu("Stroke dash");
    private JMenu menuOptionsRoundedRectangle = new JMenu("Rounded rectangle");
    private JMenu menuOptionsRoundedRectangleArcWidth = new JMenu("Arc width");
    private JMenu menuOptionsRoundedRectangleArcHeight = new JMenu("Arc height");
    private JMenu menuOptionsAirbrush = new JMenu("Airbrush");
    private JMenu menuOptionsAirbrushType = new JMenu("Type");
    private JMenu menuOptionsAirbrushSize = new JMenu("Size");

    private JMenu menuEffects = new JMenu("Effects");

    private JScrollPane scrollPane;
    private MainPanel mainPanel = new MainPanel();
    private JToolBar toolBar = new JToolBar();
    private JToolBar colorBar = new JToolBar();
    private JLabel statusBar = new JLabel();

    private File lastPath = null;

    private int zoomLevel = ZOOM_DEFAULT_LEVEL;

    private int statusBarWidth = 0;
    private int statusBarHeight = 0;
    private int statusBarCurrentX = 0;
    private int statusBarCurrentY = 0;
    private int statusBarCurrentRGBA = 0;

    private boolean changedTillLastSave = false;

    public MainWindow() {
        super(TITLE);
        mainPanel.setParentElement(this);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quit();
            }
        });

        menuFileLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });
        menuFileLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuFile.add(menuFileLoad);
        menuFileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        menuFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuFile.add(menuFileSave);
        menuFile.addSeparator();
        menuFileAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutWindow(MainWindow.this);
            }
        });
        menuFileAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        menuFile.add(menuFileAbout);
        menuFileQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        menuFileQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        menuFile.add(menuFileQuit);
        menuBar.add(menuFile);

        menuEditSelectNone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.selectNone();
            }
        });
        menuEditSelectNone.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
        menuEdit.add(menuEditSelectNone);
        menuEditSelectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.selectAll();
            }
        });
        menuEditSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        menuEdit.add(menuEditSelectAll);
        menuEdit.addSeparator();
        menuEditCut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.cut();
                changedTillLastSave = true;
            }
        });
        menuEditCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuEdit.add(menuEditCut);
        menuEditCopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.copy();
            }
        });
        menuEditCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        menuEdit.add(menuEditCopy);
        menuEditPaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.paste();
            }
        });
        menuEditPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        menuEdit.add(menuEditPaste);
        menuEdit.addSeparator();
        menuEditClearSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.clearSelection();
                changedTillLastSave = true;
            }
        });
        menuEditClearSelection.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        menuEdit.add(menuEditClearSelection);
        menuEditCropToSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.cropToSelection();
                changedTillLastSave = true;
            }
        });
        menuEditCropToSelection.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        menuEdit.add(menuEditCropToSelection);
        menuBar.add(menuEdit);

        menuViewToolBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolBar.setVisible(menuViewToolBar.isSelected());
            }
        });
        menuViewToolBar.setSelected(true);
        menuView.add(menuViewToolBar);
        menuViewColorBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorBar.setVisible(menuViewColorBar.isSelected());
            }
        });
        menuViewColorBar.setSelected(true);
        menuView.add(menuViewColorBar);
        menuViewStatusBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusBar.setVisible(menuViewStatusBar.isSelected());
            }
        });
        menuViewStatusBar.setSelected(true);
        menuView.add(menuViewStatusBar);
        menuView.addSeparator();
        menuViewZoomIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (zoomLevel < ZOOM_LEVELS - 1) {
                    zoomLevel++;
                    menuViewZoomLevels.get(zoomLevel).setSelected(true);
                    mainPanel.setZoom(ZOOM_DIVISORS[zoomLevel], ZOOM_FACTORS[zoomLevel]);
                }
            }
        });
        menuViewZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0));
        menuViewZoom.add(menuViewZoomIn);
        menuViewZoomOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (zoomLevel > 0) {
                    zoomLevel--;
                    menuViewZoomLevels.get(zoomLevel).setSelected(true);
                    mainPanel.setZoom(ZOOM_DIVISORS[zoomLevel], ZOOM_FACTORS[zoomLevel]);
                }
            }
        });
        menuViewZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0));
        menuViewZoom.add(menuViewZoomOut);
        menuViewZoom.addSeparator();
        ButtonGroup bgZoomLevels = new ButtonGroup();
        for (int i = 0; i < ZOOM_LEVELS; i++) {
            final int zoomLevelFinal = i;
            final int zoomDivisor = ZOOM_DIVISORS[zoomLevelFinal];
            final int zoomFactor = ZOOM_FACTORS[zoomLevelFinal];
            JRadioButtonMenuItem menuViewZoomLevel = new JRadioButtonMenuItem(
                zoomDivisor == 1 && zoomFactor == 1 ? "100 %" : ((zoomFactor > 1 ? "×" + zoomFactor : "") + (zoomDivisor > 1 ? "/" + zoomDivisor : ""))
            );
            menuViewZoomLevel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    zoomLevel = zoomLevelFinal;
                    mainPanel.setZoom(zoomDivisor, zoomFactor);
                }
            });
            if (zoomLevelFinal == ZOOM_DEFAULT_LEVEL) {
                menuViewZoomLevel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.CTRL_MASK));
                menuViewZoomLevel.setSelected(true);
            }
            menuViewZoomLevels.add(menuViewZoomLevel);
            bgZoomLevels.add(menuViewZoomLevel);
            menuViewZoom.add(menuViewZoomLevel);
        }
        menuView.add(menuViewZoom);
        menuBar.add(menuView);

        menuOptionsColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (colorPickerWindow == null || !colorPickerWindow.isDisplayable()) {
                    if (colorPickerWindow != null) {
                        colorPickerWindow.dispose();
                    }
                    colorPickerWindow = new ColorPickerWindow(MainWindow.this);
                }
            }
        });
        menuOptions.add(menuOptionsColorPicker);
        ButtonGroup bgFillStyle = new ButtonGroup();
        for (MainPanel.FillStyle fillStyle : MainPanel.FillStyle.values()) {
            JRadioButtonMenuItem menuOptionsFillStyleChild = new JRadioButtonMenuItem(fillStyle.getTitle());
            menuOptionsFillStyleChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setFillStyle(fillStyle);
                }
            });
            if (fillStyle == MainPanel.FillStyle.NONE) {
                menuOptionsFillStyleChild.setSelected(true);
            }
            bgFillStyle.add(menuOptionsFillStyleChild);
            menuOptionsFillStyle.add(menuOptionsFillStyleChild);
        }
        menuOptions.add(menuOptionsFillStyle);
        ButtonGroup bgStrokeWidth = new ButtonGroup();
        for (int strokeWidth : STOKE_WIDTHS) {
            JRadioButtonMenuItem menuOptionsStrokeWidthChild = new JRadioButtonMenuItem(strokeWidth + " px");
            menuOptionsStrokeWidthChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setStrokeWidth(strokeWidth);
                }
            });
            if (strokeWidth == 1) {
                menuOptionsStrokeWidthChild.setSelected(true);
            }
            bgStrokeWidth.add(menuOptionsStrokeWidthChild);
            menuOptionsStrokeWidth.add(menuOptionsStrokeWidthChild);
        }
        menuOptions.add(menuOptionsStrokeWidth);
        ButtonGroup bgStrokeDash = new ButtonGroup();
        for (MainPanel.StrokeDash strokeDash : MainPanel.StrokeDash.values()) {
            JRadioButtonMenuItem menuOptionsStrokeDashChild = new JRadioButtonMenuItem(strokeDash.getTitle());
            menuOptionsStrokeDashChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setStrokeDash(strokeDash);
                }
            });
            if (strokeDash == MainPanel.StrokeDash.NORMAL) {
                menuOptionsStrokeDashChild.setSelected(true);
            }
            bgStrokeDash.add(menuOptionsStrokeDashChild);
            menuOptionsStrokeDash.add(menuOptionsStrokeDashChild);
        }
        menuOptions.add(menuOptionsStrokeDash);
        menuOptions.addSeparator();
        ButtonGroup bgRoundedRectangleArcWidth = new ButtonGroup();
        for (int arcWidth : ROUNDED_RECTANGLE_RADII) {
            JRadioButtonMenuItem menuOptionsRoundedRectangleArcWidthChild = new JRadioButtonMenuItem(arcWidth + " px");
            menuOptionsRoundedRectangleArcWidthChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setRoundedRectangleArcWidth(arcWidth);
                }
            });
            if (arcWidth == 20) {
                menuOptionsRoundedRectangleArcWidthChild.setSelected(true);
            }
            bgRoundedRectangleArcWidth.add(menuOptionsRoundedRectangleArcWidthChild);
            menuOptionsRoundedRectangleArcWidth.add(menuOptionsRoundedRectangleArcWidthChild);
        }
        menuOptionsRoundedRectangle.add(menuOptionsRoundedRectangleArcWidth);
        ButtonGroup bgRoundedRectangleArcHeight = new ButtonGroup();
        for (int arcHeight : ROUNDED_RECTANGLE_RADII) {
            JRadioButtonMenuItem menuOptionsRoundedRectangleArcHeightChild = new JRadioButtonMenuItem(arcHeight + " px");
            menuOptionsRoundedRectangleArcHeightChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setRoundedRectangleArcHeight(arcHeight);
                }
            });
            if (arcHeight == 20) {
                menuOptionsRoundedRectangleArcHeightChild.setSelected(true);
            }
            bgRoundedRectangleArcHeight.add(menuOptionsRoundedRectangleArcHeightChild);
            menuOptionsRoundedRectangleArcHeight.add(menuOptionsRoundedRectangleArcHeightChild);
        }
        menuOptionsRoundedRectangle.add(menuOptionsRoundedRectangleArcHeight);
        menuOptions.add(menuOptionsRoundedRectangle);
        ButtonGroup bgAirbrushType = new ButtonGroup();
        for (MainPanel.AirbrushType airbrushType : MainPanel.AirbrushType.values()) {
            JRadioButtonMenuItem menuOptionsAirbrushTypeChild = new JRadioButtonMenuItem(airbrushType.getTitle());
            menuOptionsAirbrushTypeChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setAirbrushType(airbrushType);
                }
            });
            if (airbrushType == MainPanel.AirbrushType.NORMAL) {
                menuOptionsAirbrushTypeChild.setSelected(true);
            }
            bgAirbrushType.add(menuOptionsAirbrushTypeChild);
            menuOptionsAirbrushType.add(menuOptionsAirbrushTypeChild);
        }
        menuOptionsAirbrush.add(menuOptionsAirbrushType);
        ButtonGroup bgAirbrushSize = new ButtonGroup();
        for (int airbrushSize : AIRBRUSH_SIZES) {
            JRadioButtonMenuItem menuOptionsAirbrushSizeChild = new JRadioButtonMenuItem(airbrushSize + " px");
            menuOptionsAirbrushSizeChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setAirbrushSize(airbrushSize);
                }
            });
            if (airbrushSize == 15) {
                menuOptionsAirbrushSizeChild.setSelected(true);
            }
            bgAirbrushSize.add(menuOptionsAirbrushSizeChild);
            menuOptionsAirbrushSize.add(menuOptionsAirbrushSizeChild);
        }
        menuOptionsAirbrush.add(menuOptionsAirbrushSize);
        menuOptions.add(menuOptionsAirbrush);
        menuBar.add(menuOptions);

        for (AbstractEffect effect : EFFECTS) {
            if (effect == null) {
                menuEffects.addSeparator();
                continue;
            }
            final String title = effect.getName() + (effect.getParameters().isEmpty() ? "" : "...");
            JMenuItem menuEffectsEffect = new JMenuItem(title);
            menuEffectsEffect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (effect.getParameters().isEmpty()) {
                        mainPanel.setImage(effect.apply(mainPanel.getImage()));
                    } else {
                        new EffectWindow(MainWindow.this, scrollPane, mainPanel, effect, title);
                    }
                }
            });
            menuEffects.add(menuEffectsEffect);
        }
        menuBar.add(menuEffects);

        setJMenuBar(menuBar);

        setLayout(new BorderLayout());

        toolBar.setLayout(new GridBagLayout());
        toolBar.setOrientation(SwingConstants.VERTICAL);
        toolBar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                if (e.getPropertyName().equals("orientation")) {
                    toolBar.removeAll();
                    addToolBarButtons();
                }
            }
        });
        addToolBarButtons();
        add(toolBar, BorderLayout.LINE_START);

        scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        colorBar.setOrientation(SwingConstants.VERTICAL);
        final int colorBarSize = Config.getConfig().getColorBarSize();
        List<File> colorPaletteFiles = Config.getConfig().getColorPaletteFiles();
        for (int i = 0; i < colorPaletteFiles.size(); i++) {
            PalettePanel palettePanel = new PalettePanel(colorBarSize, colorPaletteFiles.get(i));
            palettePanel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final MouseEvent source = (MouseEvent) e.getSource();
                    final int x = source.getX();
                    final int y = source.getY();
                    final Color c = palettePanel.getColorAt(x, y);
                    if (SwingUtilities.isLeftMouseButton(source)) {
                        mainPanel.setColorPrimary(c);
                    } else if (SwingUtilities.isRightMouseButton(source)) {
                        mainPanel.setColorSecondary(c);
                    }
                    updateChildWindows();
                }
            });
            colorBar.add(palettePanel);
        }
        add(colorBar, BorderLayout.LINE_END);

        add(statusBar, BorderLayout.PAGE_END);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(new ImageIcon("icons" + File.separator + "icon.png").getImage());
        setSize(Config.getConfig().getMainWindowWidth(),
                Config.getConfig().getMainWindowHeight());
        setLocationRelativeTo(null);
        mainPanel.newImage(Config.getConfig().getNewImageWidth(),
                           Config.getConfig().getNewImageHeight(),
                           Config.getConfig().getNewImageColor());

        setVisible(true);
    }

    private void addToolBarButtons() {
        final int numberOfTools = MainPanel.Tool.values().length;
        final int orient = toolBar.getOrientation();
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        for (MainPanel.Tool tool : MainPanel.Tool.values()) {
            AbstractTool toolObject = tool.getToolObject();
            String title = toolObject.getName();
            String icon = toolObject.getIcon();
            JButton button = icon.isEmpty()
                ? new JButton(title.substring(0, 2))
                : new JButton(new ImageIcon("icons" + File.separator + icon));
            button.setToolTipText(title);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setSelectedTool(tool);
                }
            });
            final int j = tool.ordinal();
            switch (orient) {
            case JToolBar.VERTICAL:
                c.gridx = j % 2;
                c.gridy = j / 2;
                break;
            case JToolBar.HORIZONTAL:
                c.gridx = j / 2;
                c.gridy = j % 2;
                break;
            default:
                throw new AssertionError();
            }
            if ((numberOfTools % 2 == 0 && j == numberOfTools - 2) || j == numberOfTools - 1) {
                switch (orient) {
                case JToolBar.VERTICAL:
                    c.weightx = 0;
                    c.weighty = 1;
                    break;
                case JToolBar.HORIZONTAL:
                    c.weightx = 1;
                    c.weighty = 0;
                    break;
                default:
                    throw new AssertionError();
                }
            } else {
                c.weightx = 0;
                c.weighty = 0;
            }
            toolBar.add(button, c);
        }
    }

    public void updateChildWindows() {
        if (colorPickerWindow != null) {
            colorPickerWindow.updateAll();
        }
    }

    public void updateStatusBarSize(int width, int height) {
        statusBarWidth = width;
        statusBarHeight = height;
        updateStatusBar();
    }

    public void updateStatusBarCurrentPixel(int x, int y, int rgba) {
        statusBarCurrentX = x;
        statusBarCurrentY = y;
        statusBarCurrentRGBA = rgba;
        updateStatusBar();
    }

    private void updateStatusBar() {
        String text = "";
        text += "Size: " + statusBarWidth + " × " + statusBarHeight;
        text += " | ";
        text += "Current: (" + statusBarCurrentX + ", " + statusBarCurrentY + ") = 0x" + String.format("%08X", statusBarCurrentRGBA);
        statusBar.setText(text);
    }

    public void changedTillLastSave() {
        this.changedTillLastSave = true;
    }

    private void load() {
        JFileChooser fc = new JFileChooser(lastPath);
        addFilters(fc);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (file.isFile()) {
                try {
                    mainPanel.setImageWithOrWithoutAlpha(ImageIO.read(file));
                    changedTillLastSave = false;
                    lastPath = file.getParentFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void save() {
        JFileChooser fc = new JFileChooser(lastPath);
        fc.setAcceptAllFileFilterUsed(false);
        addFilters(fc);
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            FileFilter ff = fc.getFileFilter();
            String formatString;
            boolean supportsAlpha;
            if (ff == FILTER_BMP) {
                formatString = "BMP";
                supportsAlpha = false;
            } else if (ff == FILTER_GIF) {
                formatString = "GIF";
                supportsAlpha = true;
            } else if (ff == FILTER_JPG) {
                formatString = "JPG";
                supportsAlpha = false;
            } else if (ff == FILTER_PNG) {
                formatString = "PNG";
                supportsAlpha = true;
            } else {
                throw new AssertionError();
            }
            BufferedImage image = supportsAlpha
                ? mainPanel.getImage()
                : mainPanel.getImageWithoutAlpha();
            if (file.exists()) {
                file.delete();
            }
            try {
                ImageIO.write(image, formatString, file);
                changedTillLastSave = false;
                lastPath = file.getParentFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addFilters(JFileChooser fc) {
        fc.addChoosableFileFilter(FILTER_PNG);
        fc.addChoosableFileFilter(FILTER_BMP);
        fc.addChoosableFileFilter(FILTER_GIF);
        fc.addChoosableFileFilter(FILTER_JPG);
    }

    private static FileFilter createFileFilter(String name, String... extensions) {
        return new FileFilter() {
            @Override
            public String getDescription() {
                return name + " (*." + String.join("; *.", extensions) + ")";
            }

            @Override
            public boolean accept(File f) {
                final String lc = f.getName().toLowerCase();
                return (f.isFile() && Arrays.stream(extensions).anyMatch(extension -> lc.endsWith("." + extension))) || f.isDirectory();
            }
        };
    }

    private void quit() {
        if (changedTillLastSave) {
            switch (JOptionPane.showConfirmDialog(this, "Do you want to save your image first?", getTitle(), JOptionPane.YES_NO_CANCEL_OPTION)) {
            case JOptionPane.YES_OPTION:
                save();
                actuallyQuit();
                break;
            case JOptionPane.NO_OPTION:
                actuallyQuit();
                break;
            case JOptionPane.CANCEL_OPTION:
            default:
                /* empty */
                break;
            }
        } else {
            actuallyQuit();
        }
    }

    private void actuallyQuit() {
        if (colorPickerWindow != null) {
            colorPickerWindow.dispose();
        }
        dispose();
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

}
