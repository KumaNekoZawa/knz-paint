package knz.paint.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ColorPanel extends JPanel {

    private static final int SIZE = 75;

    private BufferedImage image;

    public ColorPanel(MainPanel mainPanel) {
        super();
        Dimension d = new Dimension(SIZE, SIZE);
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
        setSize(d);
        image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                image.setRGB(x, y, Color.HSBtoRGB(x / (float) SIZE, 1f - y / (float) SIZE, 1f));
            }
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final int x = e.getX();
                final int y = e.getY();
                mainPanel.setColor(new Color(image.getRGB(x, y)));
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null);
    }

}
