package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class FloodFillTool extends AbstractTool {

    private static class Pair {
        public int x, y;

        Pair(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }
    }

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
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        image = mainPanel.getImage();
        rgbFrom = image.getRGB(x, y);
        rgbTo = mainPanel.getColorPrimary().getRGB();
        if (rgbFrom == rgbTo) {
            return;
        }
        floodFill(x, y);
    }

    private void floodFill(int x, int y) {
        Stack<Pair> s = new Stack<>();
        s.push(new Pair(x, y));
        while (!s.isEmpty()) {
            Pair p = s.pop();
            x = p.x;
            y = p.y;
            int lx = x;
            while (inside(lx - 1, y)) {
                image.setRGB(lx - 1, y, rgbTo);
                lx--;
            }
            while (inside(x, y)) {
                image.setRGB(x, y, rgbTo);
                x++;
            }
            scan(s, lx, x - 1, y + 1);
            scan(s, lx, x - 1, y - 1);
        }
    }

    private void scan(Stack<Pair> s, int lx, int rx, int y) {
        boolean added = false;
        for (int x = lx; x <= rx; x++) {
            if (!inside(x, y)) {
                added = false;
            } else if (!added) {
                s.push(new Pair(x, y));
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
