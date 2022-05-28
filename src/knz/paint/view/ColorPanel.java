package knz.paint.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ColorPanel extends JPanel {

    private static final int SIZE = 75;

    private BufferedImage image;

    public ColorPanel(MainPanel mainPanel, int index) {
        super();
        if (!(0 <= index && index < 7)) {
            throw new IllegalArgumentException();
        }
        updatePanelSize();
        image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if (index == 0) {
                    final double lx = 2. * x / (double) SIZE - 1.;
                    final double ly = 1. - 2. * y / (double) SIZE;
                    final float h = (float) (0.5 * Math.atan2(lx, ly) / Math.PI);
                    final float s = (float) Math.min(Math.sqrt(lx * lx + ly * ly), 1.);
                    final float b = 1f;
                    image.setRGB(x, y, Color.HSBtoRGB(h, s, b));
                } else if (index == 1) {
                    final float h = 0f;
                    final float s = 0f;
                    final float b = x / (float) SIZE;
                    image.setRGB(x, y, Color.HSBtoRGB(h, s, b));
                } else {
                    final int[] v = new int[] { 0, 64, 128, 192, 255 };
                    final int r = v[y / (SIZE / 5)];
                    final int g = v[x / (SIZE / 5)];
                    final int b = v[index - 2];
                    image.setRGB(x, y, new Color(r, g, b).getRGB());
                }
            }
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final int x = e.getX();
                final int y = e.getY();
                final Color c = new Color(image.getRGB(x, y));
                if (SwingUtilities.isLeftMouseButton(e)) {
                    mainPanel.setColorPrimary(c);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    mainPanel.setColorSecondary(c);
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null);
    }

    private void updatePanelSize() {
        Dimension d = new Dimension(SIZE, SIZE);
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
        setSize(d);
    }

}
