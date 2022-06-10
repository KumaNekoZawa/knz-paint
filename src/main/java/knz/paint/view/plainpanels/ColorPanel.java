package knz.paint.view.plainpanels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import knz.paint.model.Config;

public class ColorPanel extends JPanel {

    public static final int ACTION_LISTENER_ID = 2000;

    private List<ActionListener>     listenersAction     = new ArrayList<>();
    private List<ColorPanelListener> listenersColorPanel = new ArrayList<>();

    private Color color = null;
    private boolean highlighted = false;

    public ColorPanel() {
        super();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (final ActionListener listener : listenersAction) {
                    listener.actionPerformed(new ActionEvent(e, ACTION_LISTENER_ID, ColorPanel.class.getSimpleName()));
                }
            }
        });
        setBackground(Config.getConfig().getBackgroundColor());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        final int width  = getWidth();
        final int height = getHeight();
        final Graphics2D graphics2d = (Graphics2D) g;
        if (color != null) {
            graphics2d.setColor(color);
            graphics2d.fillRect(0, 0, width, height);
        }
        if (highlighted) {
            graphics2d.setColor(Color.BLACK);
            graphics2d.setStroke(new BasicStroke(3f));
            graphics2d.drawRect(1, 1, width - 2, height - 2);
        }
    }

    public void addActionListener(ActionListener listener) {
        listenersAction.add(listener);
    }

    public void addColorPanelListener(ColorPanelListener listener) {
        listenersColorPanel.add(listener);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        for (final ColorPanelListener listener : listenersColorPanel) {
            listener.colorChanged(new ColorPanelEvent(color));
        }
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

}
