package knz.paint.model.effects.specific.xy.polar;

import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.DoubleParameter;

/* see: Beyond Photography: The Digital Darkroom p.44-45 */
public class SwirlEffect extends AbstractPolarEffect {

    private DoubleParameter paramFactor = new DoubleParameter("Factor", -10, 0, 10);

    public SwirlEffect() {
        super("Swirl", BorderFillStrategy.FILL_TRANSPARENT);
        this.parameters.add(paramFactor);
    }

    @Override
    protected void filter(double toR, double toA) {
        final double factor = paramFactor.getValue();
        fromR = toR;
        fromA = toA + factor * toR;
    }

}
