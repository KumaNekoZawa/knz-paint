package knz.paint.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AboutWindow extends JDialog {

    public AboutWindow(MainWindow parentElement) {
        super(parentElement, "About...", true);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel labelTop = new JLabel(parentElement.getTitle());
        labelTop.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTop.setFont(labelTop.getFont().deriveFont(32f));
        panel.add(labelTop);
        panel.add(Box.createVerticalStrut(10));
        JLabel labelBottom = new JLabel("©2022 熊猫沢");
        labelBottom.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelBottom.setFont(labelTop.getFont().deriveFont(24f));
        panel.add(labelBottom);
        panel.add(Box.createVerticalStrut(10));
        JButton buttonOkay = new JButton("Okay");
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
