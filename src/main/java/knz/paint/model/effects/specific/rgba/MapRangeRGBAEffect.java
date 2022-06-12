package knz.paint.model.effects.specific.rgba;

import java.awt.Color;

import knz.paint.model.effects.parameter.ColorParameter;

public class MapRangeRGBAEffect extends AbstractRGBAEffect {

    private static final Color COLOR_FROM = new Color(0x00, 0x00, 0x00, 0x00);
    private static final Color COLOR_TO   = new Color(0xFF, 0xFF, 0xFF, 0xFF);

    private ColorParameter paramFromDark   = new ColorParameter("From dark",   COLOR_FROM);
    private ColorParameter paramFromBright = new ColorParameter("From bright", COLOR_TO);
    private ColorParameter paramToDark     = new ColorParameter("To dark",     COLOR_FROM);
    private ColorParameter paramToBright   = new ColorParameter("To bright",   COLOR_TO);

    public MapRangeRGBAEffect() {
        super("Map range", true, true);
        this.parameters.add(paramFromDark);
        this.parameters.add(paramFromBright);
        this.parameters.add(paramToDark);
        this.parameters.add(paramToBright);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final Color fromDark   = paramFromDark.getValue();
        final Color fromBright = paramFromBright.getValue();
        final Color toDark     = paramToDark.getValue();
        final Color toBright   = paramToBright.getValue();
        out_r = (toBright.getRed()   - toDark.getRed())   * (in_r - fromDark.getRed())   / Math.max(1, fromBright.getRed()   - fromDark.getRed())   + toDark.getRed();
        out_g = (toBright.getGreen() - toDark.getGreen()) * (in_g - fromDark.getGreen()) / Math.max(1, fromBright.getGreen() - fromDark.getGreen()) + toDark.getGreen();
        out_b = (toBright.getBlue()  - toDark.getBlue())  * (in_b - fromDark.getBlue())  / Math.max(1, fromBright.getBlue()  - fromDark.getBlue())  + toDark.getBlue();
        out_a = (toBright.getAlpha() - toDark.getAlpha()) * (in_a - fromDark.getAlpha()) / Math.max(1, fromBright.getAlpha() - fromDark.getAlpha()) + toDark.getAlpha();
    }

}
