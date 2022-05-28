package knz.paint.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.filechooser.FileFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

    private static final int[] ZOOM_LEVELS = { 1, 2, 4, 8 };

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuFileNew = new JMenuItem("New");
    private JMenuItem menuFileOpen = new JMenuItem("Open...");
    // TODO Save/Save as distinction (!?)
    private JMenuItem menuFileSave = new JMenuItem("Save...");
    // TODO ---
    // TODO Quit (Ctrl+Q)

    private JMenu menuEdit = new JMenu("Edit");
    // TODO Select all (Ctrl+A)
    // TODO Select none
    // TODO Invert selection
    // ---
    // TODO Crop to selection
    private JMenuItem menuEditClearSelection = new JMenuItem("Clear selection");
    // TODO ---
    // TODO Cut (Ctrl+X)
    // TODO Copy (Ctrl+C)
    // TODO Paste (Ctrl+V)
    // TODO Clear clipboard (!?)

    private JMenu menuView = new JMenu("View");
    private JMenu menuViewZoom = new JMenu("Zoom");

    private JMenu menuOptions = new JMenu("Options");
    private JMenuItem menuOptionsSwapColors = new JMenuItem("Swap colors");
    private JMenu menuOptionsFill = new JMenu("Fill style");

    private MainPanel mainPanel = new MainPanel();
    private JToolBar toolBar = new JToolBar();
    private JToolBar colorBar = new JToolBar();
    private JLabel statusBar = new JLabel();

    private int statusBarWidth = 0;
    private int statusBarHeight = 0;
    private int statusBarCurrentX = 0;
    private int statusBarCurrentY = 0;
    private int statusBarCurrentRGBA = 0;

    public MainWindow() {
        super("熊猫沢ペイント");
        mainPanel.setParent(this);

        menuFileNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // FIXME ask user
                mainPanel.newImage(400, 300, Color.WHITE);
            }
        });
        menuFile.add(menuFileNew);
        menuFileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (file.isFile()) {
                        try {
                            mainPanel.openImage(ImageIO.read(file));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        menuFile.add(menuFileOpen);
        menuFileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public String getDescription() {
                        return "Portable Network Graphics (*.png)";
                    }

                    @Override
                    public boolean accept(File f) {
                        if (f.isFile()) {
                            return f.getName().toLowerCase().endsWith(".png");
                        } else if (f.isDirectory()) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                if (fc.showSaveDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (file.exists()) {
                        System.err.println("Can't overwrite files!");
                        return;
                    }
                    try {
                        ImageIO.write(mainPanel.getImage(), "PNG", file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        menuFile.add(menuFileSave);
        menuBar.add(menuFile);

        menuEditClearSelection.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        menuEditClearSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.clearSelection();
            }
        });
        menuEdit.add(menuEditClearSelection);
        menuBar.add(menuEdit);

        ButtonGroup bgZoomLevels = new ButtonGroup();
        for (int zoomLevel : ZOOM_LEVELS) {
            JRadioButtonMenuItem menuViewZoomLevel = new JRadioButtonMenuItem("×" + zoomLevel);
            menuViewZoomLevel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setZoomLevel(zoomLevel);
                }
            });
            if (zoomLevel == 1) {
                menuViewZoomLevel.setSelected(true);
            }
            bgZoomLevels.add(menuViewZoomLevel);
            menuViewZoom.add(menuViewZoomLevel);
        }
        menuView.add(menuViewZoom);
        menuBar.add(menuView);

        menuOptionsSwapColors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.swapColors();
            }
        });
        menuOptions.add(menuOptionsSwapColors);
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
        for (int i = 0; i < 7; i++) {
            colorBar.add(new ColorPanel(mainPanel, i));
        }
        add(colorBar, BorderLayout.LINE_END);

        add(statusBar, BorderLayout.PAGE_END);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        mainPanel.newImage(400, 300, Color.WHITE);
    }

    private void addToolBarButtons(JToolBar toolBar) {
        final int numberOfTools = MainPanel.Tool.values().length;
        final int orient = toolBar.getOrientation();
        Insets insetsFirstRow = new Insets(orient == JToolBar.VERTICAL   ? 10 : 0,
                                           orient == JToolBar.HORIZONTAL ? 10 : 0,
                                           0, 0);
        Insets insetsNo = new Insets(0, 0, 0, 0);
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        for (MainPanel.Tool tool : MainPanel.Tool.values()) {
            String title = tool.getTitle();
            String icon = tool.getIcon();
            JButton button = icon.isEmpty()
                ? new JButton(title)
                : new JButton(new ImageIcon("icons" + File.separator + icon));
            button.setToolTipText(title);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setTool(tool);
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
            }
            c.insets = j == 0 || j == 1 ? insetsFirstRow : insetsNo;
            if (j == numberOfTools - 2
             || j == numberOfTools - 1) {
                switch (orient) {
                case JToolBar.VERTICAL:
                    c.weightx = 0;
                    c.weighty = 1;
                    break;
                case JToolBar.HORIZONTAL:
                    c.weightx = 1;
                    c.weighty = 0;
                    break;
                }
            } else {
                c.weightx = 0;
                c.weighty = 0;
            }
            toolBar.add(button, c);
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
        statusBar.setText("Size: " + statusBarWidth + " × " + statusBarHeight + "; " +
                          "Current: (" + statusBarCurrentX + ", " + statusBarCurrentY + ") = 0x" + String.format("%08X", statusBarCurrentRGBA));
    }

}
