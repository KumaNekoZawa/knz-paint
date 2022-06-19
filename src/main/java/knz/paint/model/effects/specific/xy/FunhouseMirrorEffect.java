package knz.paint.model.effects.specific.xy;

import knz.paint.model.effects.parameter.DoubleParameter;

/* see: Beyond Photography: The Digital Darkroom p.56-57 */
public class FunhouseMirrorEffect extends AbstractXYEffect {

    private DoubleParameter paramFactorXArg = new DoubleParameter("Factor x argument", "°", -10, 0, 10);
    private DoubleParameter paramFactorXRes = new DoubleParameter("Factor x result", 1, 1, 1000);
    private DoubleParameter paramFactorYArg = new DoubleParameter("Factor y argument", "°", -10, 0, 10);
    private DoubleParameter paramFactorYRes = new DoubleParameter("Factor y result", 1, 1, 1000);

    public FunhouseMirrorEffect() {
        super("Funhouse mirror");
        this.parameters.add(paramFactorXArg);
        this.parameters.add(paramFactorXRes);
        this.parameters.add(paramFactorYArg);
        this.parameters.add(paramFactorYRes);
    }

    @Override
    protected void filter(int width, int height, int toX, int toY) {
        final double factorXArg = paramFactorXArg.getValue() * Math.PI / 180;
        final double factorXRes = paramFactorXRes.getValue();
        final double factorYArg = paramFactorYArg.getValue() * Math.PI / 180;
        final double factorYRes = paramFactorYRes.getValue();
        fromX = (int) (factorXRes * Math.sin(factorXArg * toX)) + toX;
        fromY = (int) (factorYRes * Math.sin(factorYArg * toY)) + toY;
    }

}
