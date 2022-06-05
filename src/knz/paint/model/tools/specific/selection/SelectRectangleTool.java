package knz.paint.model.tools.specific.selection;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class SelectRectangleTool extends AbstractSelectionTool {

    @Override
    public String getName() {
        return "Select rectangle";
    }

    @Override
    public String getIcon() {
        return "tool_1.png";
    }

    @Override
    public void mousePressed(Graphics2D graphics2d, MouseEvent e) {
        super.mousePressed(graphics2d, e);
        polygon.reset();
    }

    @Override
    public void mouseDragged(Graphics2D graphics2d, MouseEvent e) {
        super.mouseDragged(graphics2d, e);
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
    public void paint(Graphics2D graphics2d) {
        super.paint(graphics2d);
        drawSelection(graphics2d, false);
    }

    public void selectAll() {
        final BufferedImage image = imageState.getImage();
        final int minX = 0;
        final int minY = 0;
        final int maxX = image.getWidth()  - 1;
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
        final int maxX = x + width  - 1;
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
        final int width  = maxX - minX + 1;
        final int height = maxY - minY + 1;
        final Graphics2D graphics2d = imageState.getGraphics2D();
        graphics2d.setColor(toolState.getColorSecondary());
        graphics2d.fillRect(minX, minY, width, height);
        polygon.reset();
    }

    public void cropToSelection() {
        if (polygon.npoints != 4) {
            throw new AssertionError();
        }
        final BufferedImage subimage = getSubimage();
        polygon.reset();
        imageState.setImage(subimage);
    }

    public BufferedImage getSubimage() {
        if (polygon.npoints != 4) {
            throw new AssertionError();
        }
        final int minX = polygon.xpoints[0];
        final int minY = polygon.ypoints[0];
        final int maxX = polygon.xpoints[2];
        final int maxY = polygon.ypoints[2];
        final int width  = maxX - minX + 1;
        final int height = maxY - minY + 1;
        /* we need a copy here, because .getSubimage() will use the same data as the original image */
        final BufferedImage image = imageState.getImage();
        final BufferedImage imageNew = new BufferedImage(width, height, image.getType());
        final Graphics2D graphics2dNew = imageNew.createGraphics();
        graphics2dNew.drawImage(image.getSubimage(minX, minY, width, height), 0, 0, null);
        graphics2dNew.dispose();
        return imageNew;
    }

}
