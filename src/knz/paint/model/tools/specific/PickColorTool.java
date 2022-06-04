package knz.paint.model.tools.specific;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

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
    public void mousePressed(Graphics2D graphics2d, MouseEvent e) {
        super.mousePressed(graphics2d, e);
        final Color c = new Color(imageState.getImage().getRGB(x, y), true);
        if (SwingUtilities.isLeftMouseButton(e)) {
            toolState.setColorPrimary(c);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            toolState.setColorSecondary(c);
        }
    }

}
