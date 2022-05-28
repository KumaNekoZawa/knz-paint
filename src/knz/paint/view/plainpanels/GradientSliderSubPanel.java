package knz.paint.view.plainpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import javax.swing.JPanel;

import knz.paint.model.Config;

public class GradientSliderSubPanel extends JPanel {

    public static final int ACTION_LISTENER_ID = 2003;

    private List<ActionListener> listeners = new ArrayList<>();

    private BiFunction<Color, Integer, Color> colorFunction;

    private Color baseColor = null;
    private int selectedValue = 0;

    private Polygon polygon = new Polygon();

    public GradientSliderSubPanel(BiFunction<Color, Integer, Color> colorFunction) {
        super();
        this.colorFunction = colorFunction;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                GradientSliderSubPanel.this.update(e);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                GradientSliderSubPanel.this.update(e);
            }
        });

        Dimension d = new Dimension(50, 256);
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
        setSize(d);
    }

    private void update(MouseEvent e) {
        final int y = e.getY();
        if (!(0 <= y && y < 256)) {
            return;
        }
        selectedValue = 255 - e.getY();
        repaint(); // only needed if used stand-alone
        for (ActionListener listener : listeners) {
            listener.actionPerformed(new ActionEvent(e, ACTION_LISTENER_ID, GradientSliderSubPanel.class.getSimpleName()));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        final int width = getWidth();
        final int height = getHeight();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Config.getConfig().getBackgroundColor());
        g2d.fillRect(0, 0, width, height);
        for (int y = 0; y < 256; y++) {
            final int value = 255 - y;
            g2d.setColor(baseColor == null || value == selectedValue ? Color.BLACK : colorFunction.apply(baseColor, value));
            g2d.drawLine(0, y, width, y);
        }
        final int y = 255 - selectedValue;

        /* left-hand arrow */
        polygon.addPoint(0, y - 5);
        polygon.addPoint(5, y);
        polygon.addPoint(0, y + 5);
        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(polygon);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(polygon);
        polygon.reset();

        /* right-hand arrow */
        polygon.addPoint(width - 1, y - 5);
        polygon.addPoint(width - 6, y);
        polygon.addPoint(width - 1, y + 5);
        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(polygon);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(polygon);
        polygon.reset();
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    public int getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(int selectedValue) {
        this.selectedValue = selectedValue;
    }

}
