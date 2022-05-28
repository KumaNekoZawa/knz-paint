package knz.paint.view.plainpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    public static final int ACTION_LISTENER_ID = 2001;

    private List<ActionListener> listeners = new ArrayList<>();

    private BufferedImage image;

    public ImagePanel(File file) throws IOException {
        this(ImageIO.read(file));
    }

    public ImagePanel(BufferedImage image) {
        super();
        this.image = image;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (ActionListener listener : listeners) {
                    listener.actionPerformed(new ActionEvent(e, ACTION_LISTENER_ID, ImagePanel.class.getSimpleName()));
                }
            }
        });

        Dimension d = new Dimension(image.getWidth(), image.getHeight());
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
        setSize(d);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        final int width = getWidth();
        final int height = getHeight();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, width, height);
        g2d.drawImage(image, 0, 0, null);
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public Color getColorAt(int x, int y) {
        return new Color(image.getRGB(x, y), true);
    }

    public BufferedImage getImage() {
        return image;
    }

}
