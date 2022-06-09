package knz.paint.model.tools.specific;

import java.awt.Color;
import java.awt.Graphics2D;

import knz.paint.model.tools.MouseInfo;

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
    public boolean doesChangeImage() {
        return false;
    }

    @Override
    public boolean doesChangeToolState() {
        return true;
    }

    @Override
    public void mousePressed(Graphics2D graphics2d, MouseInfo e) {
        super.mousePressed(graphics2d, e);
        final Color c = new Color(imageState.getImage().getRGB(x, y), true);
        if (e.isLeftButton()) {
            toolState.setColorPrimary(c);
        } else if (e.isRightButton()) {
            toolState.setColorSecondary(c);
        }
    }

}
