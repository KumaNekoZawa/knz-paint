package knz.paint.model.effects.specific.positional.polar;

import knz.paint.model.effects.parameter.BorderFillStrategy;

/* see: Beyond Photography: The Digital Darkroom p.60-61 */
public class FisheyeLensEffect extends AbstractPolarEffect {

    public FisheyeLensEffect() {
        super("Fisheye lens", BorderFillStrategy.FILL_TRANSPARENT);
    }

    @Override
    protected void filter(double toR, double toA) {
        fromR = toR * toR;
        fromA = toA;
    }

}
