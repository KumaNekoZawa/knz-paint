package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

import knz.paint.model.IntegerPair;

public class FloodFillTool extends AbstractTool {

    private BufferedImage image;
    private int rgbFrom = 0;
    private int rgbTo = 0;

    @Override
    public String getName() {
        return "Flood fill";
    }

    @Override
    public String getIcon() {
        return "tool_3.png";
    }

    @Override
    public void mousePressed(Graphics2D graphics2d, MouseEvent e) {
        super.mousePressed(graphics2d, e);
        image = imageState.getImage();
        if (!(0 <= x && x < image.getWidth()
           && 0 <= y && y < image.getHeight())) {
            return;
        }
        rgbFrom = image.getRGB(x, y);
        rgbTo = toolState.getColorPrimary().getRGB();
        if (rgbFrom == rgbTo) {
            return;
        }
        floodFill(x, y);
    }

    private void floodFill(int x, int y) {
        final Stack<IntegerPair> s = new Stack<>();
        s.push(new IntegerPair(x, y));
        while (!s.isEmpty()) {
            final IntegerPair p = s.pop();
            final int px = p.getX();
            final int py = p.getY();
            int lx = px;
            while (inside(lx - 1, py)) {
                image.setRGB(--lx, py, rgbTo);
            }
            int rx = px;
            while (inside(rx, py)) {
                image.setRGB(rx++, py, rgbTo);
            }
            scan(s, lx, rx - 1, py + 1);
            scan(s, lx, rx - 1, py - 1);
        }
    }

    private void scan(Stack<IntegerPair> s, int lx, int rx, int y) {
        boolean added = false;
        for (int x = lx; x <= rx; x++) {
            if (!inside(x, y)) {
                added = false;
            } else if (!added) {
                s.push(new IntegerPair(x, y));
                added = true;
            }
        }
    }

    private boolean inside(int x, int y) {
        if (!(0 <= x && x < image.getWidth()
           && 0 <= y && y < image.getHeight())) {
            return false;
        }
        return image.getRGB(x, y) == rgbFrom;
    }

}
