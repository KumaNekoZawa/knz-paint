package knz.paint.tools;

import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import knz.paint.view.MainPanel;

public class FloodFillTool extends AbstractTool {

    private BufferedImage mask = null;
    private int toBeReplacedRGB = 0;

    public FloodFillTool(MainPanel mainPanel) {
        super(mainPanel);
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        BufferedImage image = mainPanel.getImage();
        mask = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        toBeReplacedRGB = image.getRGB(x, y);
        floodFill(x, y);
        g2d.drawImage(mask, 0, 0, null);
        mask = null;
        toBeReplacedRGB = 0;
    }

    // FIXME this produces StackOverflowErrors
    private void floodFill(int lx, int ly) {
        final int replaceWithRGB = mainPanel.getColorPrimary().getRGB();
        BufferedImage image = mainPanel.getImage();
        mask.setRGB(lx, ly, replaceWithRGB);
        /* top pixel */
        if (ly - 1 >= 0) {
            if (image.getRGB(lx, ly - 1) == toBeReplacedRGB
             && mask.getRGB(lx, ly - 1) != replaceWithRGB) {
                floodFill(lx, ly - 1);
            }
        }
        /* right pixel */
        if (lx + 1 < image.getWidth()) {
            if (image.getRGB(lx + 1, ly) == toBeReplacedRGB
             && mask.getRGB(lx + 1, ly) != replaceWithRGB) {
                floodFill(lx + 1, ly);
            }
        }
        /* bottom pixel */
        if (ly + 1 < image.getHeight()) {
            if (image.getRGB(lx, ly + 1) == toBeReplacedRGB
             && mask.getRGB(lx, ly + 1) != replaceWithRGB) {
                floodFill(lx, ly + 1);
            }
        }
        /* left pixel */
        if (lx - 1 >= 0) {
            if (image.getRGB(lx - 1, ly) == toBeReplacedRGB
             && mask.getRGB(lx - 1, ly) != replaceWithRGB) {
                floodFill(lx - 1, ly);
            }
        }
    }

}
