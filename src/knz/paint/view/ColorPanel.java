package knz.paint.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ColorPanel extends JPanel {

    private BufferedImage image;

    public ColorPanel(MainPanel mainPanel, int size, File paletteFile) {
        super();
        if (mainPanel == null || size <= 1 || paletteFile == null || !paletteFile.isFile()) {
            throw new IllegalArgumentException();
        }
        updatePanelSize(size);
        String name = paletteFile.getName();
        String ext = name.contains(".") ? name.substring(name.lastIndexOf(".")).toLowerCase() : "";
        switch (ext) {
        case ".gif":
        case ".jpeg":
        case ".jpg":
        case ".png":
            try {
                image = ImageIO.read(paletteFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case ".txt":
            List<String[]> colors = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(paletteFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    colors.add(line.split(";"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!colors.isEmpty()) {
                final int countX = colors.stream().mapToInt(c -> c.length).max().orElse(1);
                final int countY = colors.size();
                image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                for (int y = 0; y < size; y++) {
                    for (int x = 0; x < size; x++) {
                        final int lx = Math.min(x / (size / countX), countX - 1);
                        final int ly = Math.min(y / (size / countY), countY - 1);
                        String color = colors.get(ly)[lx];
                        switch (color.length()) {
                        case 6: {
                            /* RGB */
                            final int r = Integer.parseInt(color.substring(0, 2), 16);
                            final int g = Integer.parseInt(color.substring(2, 4), 16);
                            final int b = Integer.parseInt(color.substring(4, 6), 16);
                            image.setRGB(x, y, new Color(r, g, b).getRGB());
                        }   break;
                        case 8: {
                            /* ARGB */
                            final int a = Integer.parseInt(color.substring(0, 2), 16);
                            final int r = Integer.parseInt(color.substring(2, 4), 16);
                            final int g = Integer.parseInt(color.substring(4, 6), 16);
                            final int b = Integer.parseInt(color.substring(6, 8), 16);
                            image.setRGB(x, y, new Color(r, g, b, a).getRGB());
                        }   break;
                        default:
                            System.err.println("Unknown color format: " + color);
                        }
                    }
                }
            }
            break;
        default:
            System.out.println("Unknown file extension: " + name);
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final int x = e.getX();
                final int y = e.getY();
                final Color c = new Color(image.getRGB(x, y), true);
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

    private void updatePanelSize(int size) {
        Dimension d = new Dimension(size, size);
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
        setSize(d);
    }

}