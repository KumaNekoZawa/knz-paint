package knz.paint.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class MainWindow extends JFrame {

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuFileNew = new JMenuItem("New");
    private JMenuItem menuFileOpen = new JMenuItem("Open...");
    private JMenuItem menuFileSave = new JMenuItem("Save...");

    private JToolBar toolBar = new JToolBar();
    private JToolBar colorBar = new JToolBar();
    private MainPanel mainPanel = new MainPanel();

    public MainWindow() {
        super("熊猫沢ペイント");
        menuFile.add(menuFileNew);
        menuFileNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.clearImage();
                mainPanel.repaint();
            }
        });
        menuFile.add(menuFileOpen);
        menuFileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (file.isFile()) {
                        try {
                            mainPanel.openImage(ImageIO.read(file));
                            mainPanel.repaint();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        menuFile.add(menuFileSave);
        menuFileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
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
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
        setLayout(new BorderLayout());
        for (MainPanel.Tool tool : MainPanel.Tool.values()) {
            JButton button = new JButton(tool.toString().toLowerCase());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setTool(tool);
                }
            });
            toolBar.add(button);
        }
        add(toolBar, BorderLayout.PAGE_START);
        add(new JScrollPane(mainPanel), BorderLayout.CENTER);
        colorBar.add(new ColorPanel(mainPanel));
        add(colorBar, BorderLayout.PAGE_END);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
