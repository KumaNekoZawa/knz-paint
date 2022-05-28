package knz.paint.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class FloodFillTool extends AbstractTool {

    private static class Quad {
        public int x1, x2, y, dy;

        Quad(int x1, int x2, int y, int dy) {
            super();
            this.x1 = x1;
            this.x2 = x2;
            this.y = y;
            this.dy = dy;
        }
    }

    private int rgbToBeReplaced = 0;
    private int rgbReplaceWith = 0;

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
        BufferedImage image = mainPanel.getImage();
        rgbToBeReplaced = image.getRGB(x, y);
        rgbReplaceWith = mainPanel.getColorPrimary().getRGB();
        floodFill(x, y);
    }

    // FIXME this algo doesn't work yet
    private void floodFill(int x, int y) {
        if (!inside(x, y)) {
            return;
        }
        BufferedImage image = mainPanel.getImage();
        Stack<Quad> s = new Stack<>();
        s.push(new Quad(x, x, y, 1));
        s.push(new Quad(x, x, y - 1, -1));
        while (!s.isEmpty()) {
            Quad q = s.pop();
            int x1 = q.x1;
            int x2 = q.x2;
            int yb = q.y;
            int dy = q.dy;
            x = x1;
            if (inside(x, yb)) {
                while (inside(x - 1, yb)) {
                    image.setRGB(x - 1, yb, rgbReplaceWith);
                    x--;
                }
            }
            if (x < x1) {
                s.push(new Quad(x, x1 - 1, yb - dy, -dy));
            }
            while (x1 <= x2) {
                while (inside(x1, yb)) {
                    image.setRGB(x1, yb, rgbReplaceWith);
                    x1++;
                }
                s.push(new Quad(x, x1 - 1, yb + dy, dy));
                if (x1 - 1 > x2) {
                    s.push(new Quad(x2 + 1, x1 - 1, yb - dy, -dy));
                }
                x1++;
                while (x1 < x2 && !inside(x1, yb)) {
                    x1++;
                }
                x = x1;
            }
        }
    }

    private boolean inside(int x, int y) {
        BufferedImage image = mainPanel.getImage();
        if (!(0 <= x && x < image.getWidth()
           && 0 <= y && y < image.getHeight())) {
            return false;
        }
        return image.getRGB(x, y) == rgbToBeReplaced;
    }

}
