package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ChangeCanvasSizeTool extends AbstractTool {

    @Override
    public String getName() {
        return "Change canvas size";
    }

    @Override
    public String getIcon() {
        return "ccs.png";
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        changeSize();
    }

    @Override
    public void mouseDragged(Graphics2D g2d, MouseEvent e) {
        super.mouseDragged(g2d, e);
        changeSize();
    }

    @Override
    public void mouseReleased(Graphics2D g2d, MouseEvent e) {
        super.mouseReleased(g2d, e);
        changeSize();
    }

    private void changeSize() {
        if (x <= 0 || y <= 0) {
            return;
        }
        BufferedImage image = mainPanel.getImage();
        final int xOld = image.getWidth();
        final int yOld = image.getHeight();
        BufferedImage imageNew = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imageNew.createGraphics();
        if (x > xOld || y > yOld) {
            g2d.setColor(mainPanel.getColorSecondary());
            if (y > yOld) {
                g2d.fillRect(0, yOld, xOld, y);
            }
            if (x > xOld) {
                g2d.fillRect(xOld, 0, x, y);
            }
        }
        g2d.drawImage(image, 0, 0, null);
        mainPanel.setImageAndGraphics(imageNew, g2d);
    }

}
