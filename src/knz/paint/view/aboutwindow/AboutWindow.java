package knz.paint.view.aboutwindow;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AboutWindow extends JDialog {

    private static final String TITLE = "About...";
    private static final String COPYRIGHT = "©2022 熊猫沢";

    public AboutWindow(JFrame parentElement) {
        super(parentElement, TITLE, true);

        final JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        final JLabel labelTop = new JLabel(parentElement.getTitle());
        labelTop.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTop.setFont(labelTop.getFont().deriveFont(32f));
        panel.add(labelTop);
        panel.add(Box.createVerticalStrut(10));
        final JLabel labelBottom = new JLabel(COPYRIGHT);
        labelBottom.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelBottom.setFont(labelTop.getFont().deriveFont(24f));
        panel.add(labelBottom);
        panel.add(Box.createVerticalStrut(10));
        final JButton buttonOkay = new JButton("Okay");
        buttonOkay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutWindow.this.dispose();
            }
        });
        buttonOkay.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(buttonOkay);
        add(panel);

        setAlwaysOnTop(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
