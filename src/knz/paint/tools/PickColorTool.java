package knz.paint.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class PickColorTool extends AbstractTool {

    @Override
    public String getName() {
        return "Pick color";
    }

    @Override
    public String getIcon() {
        return "tool_4.png";
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        mainPanel.setColorPrimary(new Color(mainPanel.getImage().getRGB(x, y), true));
    }

}
