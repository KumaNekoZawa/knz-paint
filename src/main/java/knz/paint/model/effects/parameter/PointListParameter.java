package knz.paint.model.effects.parameter;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PointListParameter extends AbstractLabeledParameter {

    private List<PointListener> listeners = new ArrayList<>();

    private List<Double> defX, defY;
    private List<Point> value;

    public PointListParameter(String name, List<Double> defX, List<Double> defY) {
        super(name);
        this.defX = defX;
        this.defY = defY;
    }

    @Override
    public void reset(int width, int height) {
        this.value.clear();
        for (int i = 0; i < defX.size(); i++) {
            this.value.add(new Point(
                (int) (defX.get(i) * width),
                (int) (defY.get(i) * height)
            ));
        }
    }

    @Override
    public String getLabelText() {
        return getName() + ": " + value.size() + " points";
    }

    public void addPointListener(PointListener listener) {
        this.listeners.add(listener);
    }

    public void firePointChangedEvent(Point point) {
        for (final PointListener listener : listeners) {
            listener.pointChanged(new PointEvent(point));
        }
    }

    public List<Double> getDefX() {
        return defX;
    }

    public List<Double> getDefY() {
        return defY;
    }

    public List<Point> getValue() {
        return value;
    }

}
