package knz.paint.model.effects;

import java.awt.Point;
import java.util.List;

import knz.paint.model.effects.parameter.AbstractParameter;

/* Corresponds to the "Effects" menu in the MainWindow. */
public class EffectState {

    private List<AbstractParameter> currentParameters;
    private AbstractParameter selectedParameter;
    private Point selectedPoint;

    public EffectState() {
        super();
    }

    public List<AbstractParameter> getCurrentParameters() {
        return currentParameters;
    }

    public void setCurrentParameters(List<AbstractParameter> currentParameters) {
        this.currentParameters = currentParameters;
    }

    public AbstractParameter getSelectedParameter() {
        return selectedParameter;
    }

    public void setSelectedParameter(AbstractParameter selectedParameter) {
        this.selectedParameter = selectedParameter;
    }

    public Point getSelectedPoint() {
        return selectedPoint;
    }

    public void setSelectedPoint(Point selectedPoint) {
        this.selectedPoint = selectedPoint;
    }

}
