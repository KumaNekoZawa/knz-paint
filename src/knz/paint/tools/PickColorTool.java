package knz.paint.tools;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;

import knz.paint.view.MainPanel;

public class PickColorTool extends AbstractTool {

    public PickColorTool(MainPanel mainPanel) {
        super(mainPanel);
    }

    @Override
    public void mousePressed(Graphics2D g2d, MouseEvent e) {
        super.mousePressed(g2d, e);
        mainPanel.setColorPrimary(new Color(mainPanel.getImage().getRGB(x, y)));
    }

}
