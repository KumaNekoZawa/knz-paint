package knz.paint.view.mainwindow;

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
import knz.paint.model.ImageState;
import knz.paint.model.effects.specific.AbstractEffect;
import knz.paint.model.effects.specific.BentleyEffect;
import knz.paint.model.effects.specific.PolarBentleyEffect;
import knz.paint.model.effects.specific.graphics.TadaEffect;
import knz.paint.model.effects.specific.hsba.AdjustHSBAEffect;
import knz.paint.model.effects.specific.hsba.ExtractBrightnessEffect;
import knz.paint.model.effects.specific.hsba.ExtractSaturationEffect;
import knz.paint.model.effects.specific.hsba.SaltPepperEffect;
import knz.paint.model.effects.specific.hsba.gray.BlackWhiteEffect;
import knz.paint.model.effects.specific.positional.AdjustCartesianEffect;
import knz.paint.model.effects.specific.positional.ExplosionEffect;
import knz.paint.model.effects.specific.positional.FlipEffect;
import knz.paint.model.effects.specific.positional.MirrorEffect;
import knz.paint.model.effects.specific.positional.MosaicEffect;
import knz.paint.model.effects.specific.positional.ShearSlicingEffect;
import knz.paint.model.effects.specific.positional.StainedGlassEffect;
import knz.paint.model.effects.specific.positional.ZoomEffect;
import knz.paint.model.effects.specific.positional.polar.AdjustPolarEffect;
import knz.paint.model.effects.specific.positional.polar.PolarMosaicEffect;
import knz.paint.model.effects.specific.positional.polar.RotationEffect;
import knz.paint.model.effects.specific.positional.polar.SwirlEffect;
import knz.paint.model.effects.specific.rgba.AdjustContrastEffect;
import knz.paint.model.effects.specific.rgba.AdjustGammaEffect;
import knz.paint.model.effects.specific.rgba.AdjustRGBAEffect;
import knz.paint.model.effects.specific.rgba.AlphaAsGrayEffect;
import knz.paint.model.effects.specific.rgba.BitShiftEffect;
import knz.paint.model.effects.specific.rgba.ExtractRGBAEffect;
import knz.paint.model.effects.specific.rgba.GrayscaleEffect;
import knz.paint.model.effects.specific.rgba.NegateEffect;
import knz.paint.model.effects.specific.rgba.NoiseEffect;
import knz.paint.model.effects.specific.rgba.NormalizationEffect;
import knz.paint.model.effects.specific.rgba.ReduceContrastEffect;
import knz.paint.model.effects.specific.rgba.SepiaEffect;
import knz.paint.model.effects.specific.rgba.SolarizationEffect;
import knz.paint.model.tools.AirbrushType;
import knz.paint.model.tools.FillStyle;
import knz.paint.model.tools.StrokeDash;
import knz.paint.model.tools.Tool;
import knz.paint.model.tools.ToolState;
import knz.paint.model.tools.specific.AbstractTool;
import knz.paint.view.aboutwindow.AboutWindow;
import knz.paint.view.colorpickerwindow.ColorPickerWindow;
import knz.paint.view.effectwindow.EffectWindow;
import knz.paint.view.plainpanels.PalettePanel;

public class MainWindow extends JFrame {

    private static final String TITLE = "熊猫沢ペイント";

    private static final FileFilter FILTER_BMP = createFileFilter("Microsoft Windows Bitmap", "bmp", "dib");
    private static final FileFilter FILTER_GIF = createFileFilter("Graphics Interchange Format", "gif");
    private static final FileFilter FILTER_JPG = createFileFilter("Joint Photographic Experts Group", "jpg", "jpeg");
    private static final FileFilter FILTER_PNG = createFileFilter("Portable Network Graphics", "png");

    private static final AbstractEffect[] EFFECTS = {
        /* positional */
        new AdjustCartesianEffect(),
        new ExplosionEffect(),
        new FlipEffect(),
        new MirrorEffect(),
        new MosaicEffect(),
        new ShearSlicingEffect(),
        new StainedGlassEffect(),
        new ZoomEffect(),
        null,
        /* positional/polar */
        new AdjustPolarEffect(),
        new PolarMosaicEffect(),
        new RotationEffect(),
        new SwirlEffect(),
        null,
        /* rgba */
        new AdjustContrastEffect(),
        new AdjustGammaEffect(),
        new AdjustRGBAEffect(),
        new AlphaAsGrayEffect(),
        new BitShiftEffect(),
        new ExtractRGBAEffect(),
        new GrayscaleEffect(),
        new NegateEffect(),
        new NoiseEffect(),
        new NormalizationEffect(),
        new ReduceContrastEffect(),
        new SepiaEffect(),
        new SolarizationEffect(),
        null,
        /* hsba */
        new AdjustHSBAEffect(),
        new ExtractBrightnessEffect(),
        new ExtractSaturationEffect(),
        new SaltPepperEffect(),
        null,
        /* hsba/gray */
        new BlackWhiteEffect(),
        null,
        /* misc. */
        new BentleyEffect(),
        new PolarBentleyEffect(),
        null,
        /* graphics */
        new TadaEffect(),
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
    private JMenuItem menuViewPackAndCenter = new JMenuItem("Pack & center");

    private JMenu menuOptions = new JMenu("Options");
    private JMenuItem menuOptionsColorPicker = new JMenuItem("Color picker...");
    private JMenu menuOptionsFillStyle = new JMenu("Fill style");
    private JMenu menuOptionsStrokeWidth = new JMenu("Stroke width");
    private JMenu menuOptionsStrokeDash = new JMenu("Stroke dash");
    private JMenu menuOptionsRoundedRectangle = new JMenu("Rounded rectangle");
    private JMenu menuOptionsRoundedRectangleArcWidth  = new JMenu("Arc width");
    private JMenu menuOptionsRoundedRectangleArcHeight = new JMenu("Arc height");
    private JMenu menuOptionsAirbrush = new JMenu("Airbrush");
    private JMenu menuOptionsAirbrushType = new JMenu("Type");
    private JMenu menuOptionsAirbrushSize = new JMenu("Size");

    private JMenu menuEffects = new JMenu("Effects");

    private JScrollPane scrollPane;
    private MainPanel mainPanel;
    private JToolBar toolBar = new JToolBar();
    private JToolBar colorBar = new JToolBar();
    private JLabel statusBar = new JLabel();

    private ToolState toolState = new ToolState();

    private File lastPath;

    private int statusBarWidth;
    private int statusBarHeight;
    private int statusBarCurrentX;
    private int statusBarCurrentY;
    private int statusBarCurrentRGBA;

    public MainWindow() {
        super(TITLE);

        for (final Tool tool : Tool.values()) {
            tool.getToolObject().setToolState(toolState);
        }

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
                mainPanel.getImageState().setChangedTillLastSave(true);
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
                mainPanel.getImageState().setChangedTillLastSave(true);
            }
        });
        menuEditClearSelection.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        menuEdit.add(menuEditClearSelection);
        menuEditCropToSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.cropToSelection();
                mainPanel.getImageState().setChangedTillLastSave(true);
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
                if (mainPanel.getImageState().increaseZoomLevel()) {
                    updateZoomLevel();
                }
            }
        });
        menuViewZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0));
        menuViewZoom.add(menuViewZoomIn);
        menuViewZoomOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPanel.getImageState().decreaseZoomLevel()) {
                    updateZoomLevel();
                }
            }
        });
        menuViewZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0));
        menuViewZoom.add(menuViewZoomOut);
        menuViewZoom.addSeparator();
        final ButtonGroup bgZoomLevels = new ButtonGroup();
        for (int zoomLevel = 0; zoomLevel < ImageState.ZOOM_LEVELS; zoomLevel++) {
            final int zoomLevelFinal = zoomLevel;
            final int zoomDivisor = ImageState.ZOOM_DIVISORS[zoomLevel];
            final int zoomFactor  = ImageState.ZOOM_FACTORS[zoomLevel];
            final JRadioButtonMenuItem menuViewZoomLevel = new JRadioButtonMenuItem(
                zoomDivisor == 1 && zoomFactor == 1 ? "100 %" : ((zoomFactor > 1 ? "×" + zoomFactor : "") + (zoomDivisor > 1 ? "/" + zoomDivisor : ""))
            );
            menuViewZoomLevel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.getImageState().setZoomLevel(zoomLevelFinal);
                    updateZoomLevel();
                }
            });
            if (zoomLevel == ImageState.DEFAULT_ZOOM_LEVEL) {
                menuViewZoomLevel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.CTRL_MASK));
                menuViewZoomLevel.setSelected(true);
            }
            menuViewZoomLevels.add(menuViewZoomLevel);
            bgZoomLevels.add(menuViewZoomLevel);
            menuViewZoom.add(menuViewZoomLevel);
        }
        menuView.add(menuViewZoom);
        menuView.addSeparator();
        menuViewPackAndCenter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pack();
                setLocationRelativeTo(null);
            }
        });
        menuView.add(menuViewPackAndCenter);
        menuBar.add(menuView);

        menuOptionsColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (colorPickerWindow == null || !colorPickerWindow.isDisplayable()) {
                    if (colorPickerWindow != null) {
                        colorPickerWindow.dispose();
                    }
                    colorPickerWindow = new ColorPickerWindow(toolState);
                }
            }
        });
        menuOptions.add(menuOptionsColorPicker);
        final ButtonGroup bgFillStyle = new ButtonGroup();
        for (final FillStyle fillStyle : FillStyle.values()) {
            final JRadioButtonMenuItem menuOptionsFillStyleChild = new JRadioButtonMenuItem(fillStyle.getTitle());
            menuOptionsFillStyleChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setFillStyle(fillStyle);
                }
            });
            if (fillStyle == ToolState.DEFAULT_FILL_STYLE) {
                menuOptionsFillStyleChild.setSelected(true);
            }
            bgFillStyle.add(menuOptionsFillStyleChild);
            menuOptionsFillStyle.add(menuOptionsFillStyleChild);
        }
        menuOptions.add(menuOptionsFillStyle);
        final ButtonGroup bgStrokeWidth = new ButtonGroup();
        for (final int strokeWidth : ToolState.STOKE_WIDTHS) {
            final JRadioButtonMenuItem menuOptionsStrokeWidthChild = new JRadioButtonMenuItem(strokeWidth + " px");
            menuOptionsStrokeWidthChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setStrokeWidth(strokeWidth);
                }
            });
            if (strokeWidth == ToolState.DEFAULT_STROKE_WIDTH) {
                menuOptionsStrokeWidthChild.setSelected(true);
            }
            bgStrokeWidth.add(menuOptionsStrokeWidthChild);
            menuOptionsStrokeWidth.add(menuOptionsStrokeWidthChild);
        }
        menuOptions.add(menuOptionsStrokeWidth);
        final ButtonGroup bgStrokeDash = new ButtonGroup();
        for (final StrokeDash strokeDash : StrokeDash.values()) {
            final JRadioButtonMenuItem menuOptionsStrokeDashChild = new JRadioButtonMenuItem(strokeDash.getTitle());
            menuOptionsStrokeDashChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setStrokeDash(strokeDash);
                }
            });
            if (strokeDash == ToolState.DEFAULT_STROKE_DASH) {
                menuOptionsStrokeDashChild.setSelected(true);
            }
            bgStrokeDash.add(menuOptionsStrokeDashChild);
            menuOptionsStrokeDash.add(menuOptionsStrokeDashChild);
        }
        menuOptions.add(menuOptionsStrokeDash);
        menuOptions.addSeparator();
        final ButtonGroup bgRoundedRectangleArcWidth = new ButtonGroup();
        for (final int arcWidth : ToolState.ROUNDED_RECTANGLE_RADII) {
            final JRadioButtonMenuItem menuOptionsRoundedRectangleArcWidthChild = new JRadioButtonMenuItem(arcWidth + " px");
            menuOptionsRoundedRectangleArcWidthChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setRoundedRectangleArcWidth(arcWidth);
                }
            });
            if (arcWidth == ToolState.DEFAULT_ROUNDED_RECTANGLE_ARC_WIDTH) {
                menuOptionsRoundedRectangleArcWidthChild.setSelected(true);
            }
            bgRoundedRectangleArcWidth.add(menuOptionsRoundedRectangleArcWidthChild);
            menuOptionsRoundedRectangleArcWidth.add(menuOptionsRoundedRectangleArcWidthChild);
        }
        menuOptionsRoundedRectangle.add(menuOptionsRoundedRectangleArcWidth);
        final ButtonGroup bgRoundedRectangleArcHeight = new ButtonGroup();
        for (final int arcHeight : ToolState.ROUNDED_RECTANGLE_RADII) {
            final JRadioButtonMenuItem menuOptionsRoundedRectangleArcHeightChild = new JRadioButtonMenuItem(arcHeight + " px");
            menuOptionsRoundedRectangleArcHeightChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setRoundedRectangleArcHeight(arcHeight);
                }
            });
            if (arcHeight == ToolState.DEFAULT_ROUNDED_RECTANGLE_ARC_HEIGHT) {
                menuOptionsRoundedRectangleArcHeightChild.setSelected(true);
            }
            bgRoundedRectangleArcHeight.add(menuOptionsRoundedRectangleArcHeightChild);
            menuOptionsRoundedRectangleArcHeight.add(menuOptionsRoundedRectangleArcHeightChild);
        }
        menuOptionsRoundedRectangle.add(menuOptionsRoundedRectangleArcHeight);
        menuOptions.add(menuOptionsRoundedRectangle);
        final ButtonGroup bgAirbrushType = new ButtonGroup();
        for (final AirbrushType airbrushType : AirbrushType.values()) {
            final JRadioButtonMenuItem menuOptionsAirbrushTypeChild = new JRadioButtonMenuItem(airbrushType.getTitle());
            menuOptionsAirbrushTypeChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setAirbrushType(airbrushType);
                }
            });
            if (airbrushType == ToolState.DEFAULT_AIRBRUSH_TYPE) {
                menuOptionsAirbrushTypeChild.setSelected(true);
            }
            bgAirbrushType.add(menuOptionsAirbrushTypeChild);
            menuOptionsAirbrushType.add(menuOptionsAirbrushTypeChild);
        }
        menuOptionsAirbrush.add(menuOptionsAirbrushType);
        final ButtonGroup bgAirbrushSize = new ButtonGroup();
        for (final int airbrushSize : ToolState.AIRBRUSH_SIZES) {
            final JRadioButtonMenuItem menuOptionsAirbrushSizeChild = new JRadioButtonMenuItem(airbrushSize + " px");
            menuOptionsAirbrushSizeChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setAirbrushSize(airbrushSize);
                }
            });
            if (airbrushSize == ToolState.DEFAULT_AIRBRUSH_SIZE) {
                menuOptionsAirbrushSizeChild.setSelected(true);
            }
            bgAirbrushSize.add(menuOptionsAirbrushSizeChild);
            menuOptionsAirbrushSize.add(menuOptionsAirbrushSizeChild);
        }
        menuOptionsAirbrush.add(menuOptionsAirbrushSize);
        menuOptions.add(menuOptionsAirbrush);
        menuBar.add(menuOptions);

        for (final AbstractEffect effect : EFFECTS) {
            if (effect == null) {
                menuEffects.addSeparator();
                continue;
            }
            final String title = effect.getName() + (effect.getParameters().isEmpty() ? "" : "...");
            final JMenuItem menuEffectsEffect = new JMenuItem(title);
            menuEffectsEffect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (effect.getParameters().isEmpty()) {
                        final ImageState imageState = mainPanel.getImageState();
                        imageState.setImage(effect.apply(imageState.getImage()));
                        mainPanel.repaint();
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

        mainPanel = new MainPanel(toolState);
        mainPanel.addMainPanelListener(new MainPanelListener() {
            @Override
            public void mouseMoved(MainPanelMouseMovedEvent e) {
                statusBarCurrentX = e.getX();
                statusBarCurrentY = e.getY();
                statusBarCurrentRGBA = e.getRGBA();
                updateStatusBar();
            }

            @Override
            public void sizeChanged(MainPanelSizeChangedEvent e) {
                statusBarWidth = e.getWidth();
                statusBarHeight = e.getHeight();
                updateStatusBar();
            }

            @Override
            public void toolStateChanged(MainPanelToolStateChangedEvent e) {
                updateChildWindows();
            }
        });
        scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        colorBar.setOrientation(SwingConstants.VERTICAL);
        final int colorBarSize = Config.getConfig().getColorBarSize();
        final List<File> colorPaletteFiles = Config.getConfig().getColorPaletteFiles();
        for (int i = 0; i < colorPaletteFiles.size(); i++) {
            final PalettePanel palettePanel = new PalettePanel(colorBarSize, colorPaletteFiles.get(i));
            palettePanel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final MouseEvent source = (MouseEvent) e.getSource();
                    final int x = source.getX();
                    final int y = source.getY();
                    final Color c = palettePanel.getColorAt(x, y);
                    if (SwingUtilities.isLeftMouseButton(source)) {
                        toolState.setColorPrimary(c);
                    } else if (SwingUtilities.isRightMouseButton(source)) {
                        toolState.setColorSecondary(c);
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
        mainPanel.getImageState().newImage(
            Config.getConfig().getNewImageWidth(),
            Config.getConfig().getNewImageHeight(),
            Config.getConfig().getNewImageColor()
        );

        setVisible(true);
    }

    private void updateZoomLevel() {
        menuViewZoomLevels.get(mainPanel.getImageState().getZoomLevel()).setSelected(true);
        mainPanel.updatePanelSize();
        mainPanel.repaint();
    }

    private void addToolBarButtons() {
        final int numberOfTools = Tool.values().length;
        final int orient = toolBar.getOrientation();
        final GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        for (final Tool tool : Tool.values()) {
            final AbstractTool toolObject = tool.getToolObject();
            final String title = toolObject.getName();
            final String icon = toolObject.getIcon();
            final JButton button = icon.isEmpty()
                ? new JButton(title.substring(0, 2))
                : new JButton(new ImageIcon("icons" + File.separator + icon));
            button.setToolTipText(title);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setSelectedTool(tool);
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

    private void updateChildWindows() {
        if (colorPickerWindow != null) {
            colorPickerWindow.updateAll();
        }
    }

    private void updateStatusBar() {
        String text = "";
        text += "Size: " + statusBarWidth + " × " + statusBarHeight;
        text += " | ";
        text += "Current: (" + statusBarCurrentX + ", " + statusBarCurrentY + ") = 0x" + String.format("%08X", statusBarCurrentRGBA);
        statusBar.setText(text);
    }

    private void load() {
        final JFileChooser fileChooser = new JFileChooser(lastPath);
        addFilters(fileChooser);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            final File file = fileChooser.getSelectedFile();
            if (file.isFile()) {
                try {
                    final ImageState imageState = mainPanel.getImageState();
                    imageState.setImage(ImageIO.read(file));
                    imageState.setChangedTillLastSave(false);
                    mainPanel.updatePanelSize();
                    mainPanel.repaint();
                    lastPath = file.getParentFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void save() {
        final JFileChooser fileChooser = new JFileChooser(lastPath);
        fileChooser.setAcceptAllFileFilterUsed(false);
        addFilters(fileChooser);
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            final File file = fileChooser.getSelectedFile();
            final FileFilter fileFilter = fileChooser.getFileFilter();
            final String formatString;
            final boolean supportsAlpha;
            if (fileFilter == FILTER_BMP) {
                formatString = "BMP";
                supportsAlpha = false;
            } else if (fileFilter == FILTER_GIF) {
                formatString = "GIF";
                supportsAlpha = true;
            } else if (fileFilter == FILTER_JPG) {
                formatString = "JPG";
                supportsAlpha = false;
            } else if (fileFilter == FILTER_PNG) {
                formatString = "PNG";
                supportsAlpha = true;
            } else {
                throw new AssertionError();
            }
            final ImageState imageState = mainPanel.getImageState();
            final BufferedImage image = supportsAlpha
                ? imageState.getImage()
                : imageState.getImageWithoutAlpha(toolState.getColorSecondary());
            if (file.exists()) {
                file.delete();
            }
            try {
                ImageIO.write(image, formatString, file);
                imageState.setChangedTillLastSave(false);
                lastPath = file.getParentFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addFilters(JFileChooser fileChooser) {
        fileChooser.addChoosableFileFilter(FILTER_PNG);
        fileChooser.addChoosableFileFilter(FILTER_BMP);
        fileChooser.addChoosableFileFilter(FILTER_GIF);
        fileChooser.addChoosableFileFilter(FILTER_JPG);
    }

    private static FileFilter createFileFilter(String name, String... extensions) {
        return new FileFilter() {
            @Override
            public String getDescription() {
                return name + " (*." + String.join("; *.", extensions) + ")";
            }

            @Override
            public boolean accept(File file) {
                final String lc = file.getName().toLowerCase();
                return (file.isFile() && Arrays.stream(extensions).anyMatch(extension -> lc.endsWith("." + extension))) || file.isDirectory();
            }
        };
    }

    private void quit() {
        if (mainPanel.getImageState().hasChangedTillLastSave()) {
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
