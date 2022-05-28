package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class SelectRectangleTool extends AbstractSelectionTool {

    // FIXME don't use Polygon in this class

    @Override
    public String getName() {
        return "Select rectangle";
    }

    @Override
    public String getIcon() {
        return "tool_1.png";
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        polygon.reset();
    }

    @Override
    public void mouseDragged(Graphics2D g2d, MouseEvent e) {
        super.mouseDragged(g2d, e);
        final int minX = Math.min(startX, x);
        final int minY = Math.min(startY, y);
        final int maxX = Math.max(startX, x);
        final int maxY = Math.max(startY, y);
        polygon.reset();
        polygon.addPoint(minX, minY);
        polygon.addPoint(maxX, minY);
        polygon.addPoint(maxX, maxY);
        polygon.addPoint(minX, maxY);
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paint(g2d);
        drawSelection(g2d, false);
    }

    public void selectAll() {
        BufferedImage image = mainPanel.getImage();
        final int minX = 0;
        final int minY = 0;
        final int maxX = image.getWidth() - 1;
        final int maxY = image.getHeight() - 1;
        polygon.reset();
        polygon.addPoint(minX, minY);
        polygon.addPoint(maxX, minY);
        polygon.addPoint(maxX, maxY);
        polygon.addPoint(minX, maxY);
    }

    public void selectRectangle(int x, int y, int width, int height) {
        final int minX = x;
        final int minY = y;
        final int maxX = x + width - 1;
        final int maxY = y + height - 1;
        polygon.reset();
        polygon.addPoint(minX, minY);
        polygon.addPoint(maxX, minY);
        polygon.addPoint(maxX, maxY);
        polygon.addPoint(minX, maxY);
    }

    @Override
    public void clearSelection() {
        if (polygon.npoints != 4) {
            throw new AssertionError();
        }
        final int minX = polygon.xpoints[0];
        final int minY = polygon.ypoints[0];
        final int maxX = polygon.xpoints[2];
        final int maxY = polygon.ypoints[2];
        final int width = maxX - minX + 1;
        final int height = maxY - minY + 1;
        Graphics2D g2d = mainPanel.getG2D();
        g2d.setColor(mainPanel.getColorSecondary());
        g2d.fillRect(minX, minY, width, height);
        polygon.reset();
    }

    public void cropToSelection() {
        if (polygon.npoints != 4) {
            throw new AssertionError();
        }
        BufferedImage subimage = getSubimage();
        polygon.reset();
        mainPanel.setImage(subimage);
    }

    public BufferedImage getSubimage() {
        if (polygon.npoints != 4) {
            throw new AssertionError();
        }
        final int minX = polygon.xpoints[0];
        final int minY = polygon.ypoints[0];
        final int maxX = polygon.xpoints[2];
        final int maxY = polygon.ypoints[2];
        final int width = maxX - minX + 1;
        final int height = maxY - minY + 1;
        /* we need a copy here, because .getSubimage() will use the same data as the original image */
        BufferedImage image = mainPanel.getImage();
        BufferedImage subimage = new BufferedImage(width, height, image.getType());
        Graphics2D subimageG2D = subimage.createGraphics();
        subimageG2D.drawImage(image.getSubimage(minX, minY, width, height), 0, 0, null);
        subimageG2D.dispose();
        return subimage;
    }

}
