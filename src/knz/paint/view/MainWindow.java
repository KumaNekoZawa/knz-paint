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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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

import knz.paint.tools.AbstractTool;
import knz.paint.view.colorpicker.ColorPickerWindow;
import knz.paint.view.plainpanels.PalettePanel;

public class MainWindow extends JFrame {

    private static final FileFilter FILTER_BMP = createFileFilter("Microsoft Windows Bitmap", "bmp", "dib");
    private static final FileFilter FILTER_GIF = createFileFilter("Graphics Interchange Format", "gif");
    private static final FileFilter FILTER_JPG = createFileFilter("Joint Photographic Experts Group", "jpg", "jpeg");
    private static final FileFilter FILTER_PNG = createFileFilter("Portable Network Graphics", "png");

    private static final int[] ZOOM_FACTORS   = { 1, 2, 4, 8, 16, 32 };
    private static final int[] ZOOM_SHORTCUTS = { KeyEvent.VK_0,
                                                  KeyEvent.VK_1,
                                                  KeyEvent.VK_2,
                                                  KeyEvent.VK_3,
                                                  KeyEvent.VK_4,
                                                  KeyEvent.VK_5 };

    private static final int MAX_NUMBER_OF_COLOR_PALETTES = 25;

    private Properties properties = new Properties();
    private int colorBarSize = 16 * 3;
    private List<File> colorPaletteFiles = new ArrayList<>();

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
    private JMenu menuOptionsFill = new JMenu("Fill style");

    private MainPanel mainPanel = new MainPanel();
    private JToolBar toolBar = new JToolBar();
    private JToolBar colorBar = new JToolBar();
    private JLabel statusBar = new JLabel();

    private File lastPath = null;

    private int zoomLevel = 0;

    private int statusBarWidth = 0;
    private int statusBarHeight = 0;
    private int statusBarCurrentX = 0;
    private int statusBarCurrentY = 0;
    private int statusBarCurrentRGBA = 0;

    private boolean changedTillLastSave = false;

    public MainWindow() {
        super("熊猫沢ペイント");
        mainPanel.setParentElement(this);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quit();
            }
        });

        try (InputStream is = new FileInputStream("config.properties")) {
            properties.load(is);
            colorBarSize = Integer.parseInt(properties.getProperty("colorbarsize"));
            for (int i = 1; i <= MAX_NUMBER_OF_COLOR_PALETTES; i++) {
                String filename = properties.getProperty("colorpalette" + i);
                if (filename != null && !filename.isEmpty()) {
                    colorPaletteFiles.add(new File(filename));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                if (zoomLevel < ZOOM_FACTORS.length - 1) {
                    zoomLevel++;
                    menuViewZoomLevels.get(zoomLevel).setSelected(true);
                    final int zoomFactor = ZOOM_FACTORS[zoomLevel];
                    mainPanel.setZoomFactor(zoomFactor);
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
                    final int zoomFactor = ZOOM_FACTORS[zoomLevel];
                    mainPanel.setZoomFactor(zoomFactor);
                }
            }
        });
        menuViewZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0));
        menuViewZoom.add(menuViewZoomOut);
        menuViewZoom.addSeparator();
        ButtonGroup bgZoomLevels = new ButtonGroup();
        for (int zoomLevel = 0; zoomLevel < ZOOM_FACTORS.length; zoomLevel++) {
            final int zoomFactor = ZOOM_FACTORS[zoomLevel];
            final int zoomShortcut = ZOOM_SHORTCUTS[zoomLevel];
            JRadioButtonMenuItem menuViewZoomLevel = new JRadioButtonMenuItem("×" + zoomFactor);
            menuViewZoomLevel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setZoomFactor(zoomFactor);
                }
            });
            menuViewZoomLevel.setAccelerator(KeyStroke.getKeyStroke(zoomShortcut, ActionEvent.CTRL_MASK));
            if (zoomFactor == 1) {
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
            JRadioButtonMenuItem menuOptionsFillChild = new JRadioButtonMenuItem(fillStyle.getTitle());
            menuOptionsFillChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setFillStyle(fillStyle);
                }
            });
            if (fillStyle == MainPanel.FillStyle.NONE) {
                menuOptionsFillChild.setSelected(true);
            }
            bgFillStyle.add(menuOptionsFillChild);
            menuOptionsFill.add(menuOptionsFillChild);
        }
        menuOptions.add(menuOptionsFill);
        menuBar.add(menuOptions);
        setJMenuBar(menuBar);

        setLayout(new BorderLayout());

        toolBar.setLayout(new GridBagLayout());
        toolBar.setOrientation(SwingConstants.VERTICAL);
        toolBar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                if (e.getPropertyName().equals("orientation")) {
                    toolBar.removeAll();
                    addToolBarButtons(toolBar);
                }
            }
        });
        addToolBarButtons(toolBar);
        add(toolBar, BorderLayout.LINE_START);

        add(new JScrollPane(mainPanel), BorderLayout.CENTER);

        colorBar.setOrientation(SwingConstants.VERTICAL);
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
        setSize(800, 600);
        setLocationRelativeTo(null);
        mainPanel.newImage(400, 300, Color.WHITE);

        setVisible(true);
    }

    private void addToolBarButtons(JToolBar toolBar) {
        final int numberOfTools = MainPanel.Tool.values().length;
        final int orient = toolBar.getOrientation();
        /* XXX Look & Feel
        Insets insetsFirstRow = new Insets(orient == JToolBar.VERTICAL   ? 10 : 0,
                                           orient == JToolBar.HORIZONTAL ? 10 : 0,
                                           0, 0);
        Insets insetsNo = new Insets(0, 0, 0, 0);
        */
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
            //c.insets = j == 0 || j == 1 ? insetsFirstRow : insetsNo;
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
                    mainPanel.setImage(ImageIO.read(file));
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
                if (f.isFile()) {
                    final String lc = f.getName().toLowerCase();
                    for (String extension : extensions) {
                        if (lc.endsWith("." + extension)) {
                            return true;
                        }
                    }
                    return false;
                } else if (f.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
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
