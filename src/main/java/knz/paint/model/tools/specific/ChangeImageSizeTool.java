package knz.paint.model.tools.specific;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import knz.paint.model.tools.MouseInfo;

public class ChangeImageSizeTool extends AbstractTool {

    @Override
    public String getName() {
        return "Change image size";
    }

    @Override
    public String getIcon() {
        return "cis.png";
    }

    @Override
    public boolean doesChangeImageSize() {
        return true;
    }

    @Override
    public void mousePressed(Graphics2D graphics2d, MouseInfo e) {
        super.mousePressed(graphics2d, e);
        changeSize();
    }

    @Override
    public void mouseDragged(Graphics2D graphics2d, MouseInfo e) {
        super.mouseDragged(graphics2d, e);
        changeSize();
    }

    @Override
    public void mouseReleased(Graphics2D graphics2d, MouseInfo e) {
        super.mouseReleased(graphics2d, e);
        changeSize();
    }

    private void changeSize() {
        if (x <= 0 || y <= 0) {
            return;
        }
        final BufferedImage image = imageState.getImage();
        final int xOld = image.getWidth();
        final int yOld = image.getHeight();
        final BufferedImage imageNew = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics2dNew = imageNew.createGraphics();
        if (x > xOld || y > yOld) {
            graphics2dNew.setColor(toolState.getColorSecondary());
            if (y > yOld) {
                graphics2dNew.fillRect(0, yOld, xOld, y);
            }
            if (x > xOld) {
                graphics2dNew.fillRect(xOld, 0, x, y);
            }
        }
        graphics2dNew.drawImage(image, 0, 0, null);
        graphics2dNew.dispose();
        imageState.setImage(imageNew);
    }

}
