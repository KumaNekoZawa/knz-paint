package knz.paint.tools;

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
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        final Color c = new Color(mainPanel.getImage().getRGB(x, y), true);
        if (SwingUtilities.isLeftMouseButton(e)) {
            mainPanel.setColorPrimary(c);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            mainPanel.setColorSecondary(c);
        }
        mainPanel.getParentElement().updateChildWindows();
    }

}
