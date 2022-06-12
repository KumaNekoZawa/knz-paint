package knz.paint.model.effects.parameter;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PointParameter extends AbstractLabeledParameter {

    private List<PointListener> listeners = new ArrayList<>();

    private double defX, defY;
    private Point value = new Point(0, 0);

    public PointParameter(String name, double defX, double defY) {
        super(name);
        this.defX = defX;
        this.defY = defY;
    }

    @Override
    public void reset(int width, int height) {
        this.value.x = (int) (defX * width);
        this.value.y = (int) (defY * height);
    }

    @Override
    public String getLabelText() {
        return getName() + ": (" + value.x + ", " + value.y + ")";
    }

    public void addPointListener(PointListener listener) {
        this.listeners.add(listener);
    }

    public void firePointChangedEvent() {
        for (final PointListener listener : listeners) {
            listener.pointChanged(new PointEvent(value));
        }
    }

    public double getDefX() {
        return defX;
    }

    public double getDefY() {
        return defY;
    }

    public Point getValue() {
        return value;
    }

    public void setValue(Point value) {
        this.value = value;
    }

}
