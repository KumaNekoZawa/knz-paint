package knz.paint.model.effects.specific.hsba;

import java.awt.Color;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.ColorParameter;

public class MapRangeHSBAEffect extends AbstractHSBAEffect {

    private static final Color COLOR_FROM = new Color( Color.HSBtoRGB(0,       0, 0) & 0xFFFFFF,               true);
    private static final Color COLOR_TO   = new Color((Color.HSBtoRGB(0.9995f, 1, 1) & 0xFFFFFF) | 0xFF000000, true);

    private BooleanParameter paramAffectH = new BooleanParameter("Affect hue",        false);
    private BooleanParameter paramAffectS = new BooleanParameter("Affect saturation", false);
    private BooleanParameter paramAffectB = new BooleanParameter("Affect brightness", false);
    private BooleanParameter paramAffectA = new BooleanParameter("Affect alpha",      false);
    private ColorParameter paramFromDark   = new ColorParameter("From dark",   COLOR_FROM);
    private ColorParameter paramFromBright = new ColorParameter("From bright", COLOR_TO);
    private ColorParameter paramToDark     = new ColorParameter("To dark",     COLOR_FROM);
    private ColorParameter paramToBright   = new ColorParameter("To bright",   COLOR_TO);

    public MapRangeHSBAEffect() {
        super("Map range");
        this.parameters.add(paramAffectH);
        this.parameters.add(paramAffectS);
        this.parameters.add(paramAffectB);
        this.parameters.add(paramAffectA);
        this.parameters.add(paramFromDark);
        this.parameters.add(paramFromBright);
        this.parameters.add(paramToDark);
        this.parameters.add(paramToBright);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final boolean affectH = paramAffectH.getValue();
        final boolean affectS = paramAffectS.getValue();
        final boolean affectB = paramAffectB.getValue();
        final boolean affectA = paramAffectA.getValue();
        final Color fromDark   = paramFromDark.getValue();
        final Color fromBright = paramFromBright.getValue();
        final Color toDark     = paramToDark.getValue();
        final Color toBright   = paramToBright.getValue();
        final float[] fromDarkHSB   = Color.RGBtoHSB(fromDark.getRed(),   fromDark.getGreen(),   fromDark.getBlue(),   null);
        final float[] fromBrightHSB = Color.RGBtoHSB(fromBright.getRed(), fromBright.getGreen(), fromBright.getBlue(), null);
        final float[] toDarkHSB     = Color.RGBtoHSB(toDark.getRed(),     toDark.getGreen(),     toDark.getBlue(),     null);
        final float[] toBrightHSB   = Color.RGBtoHSB(toBright.getRed(),   toBright.getGreen(),   toBright.getBlue(),   null);
        out_h = affectH ? (toBrightHSB[0] - toDarkHSB[0]) * (in_h - fromDarkHSB[0]) / Math.max(0.01f, fromBrightHSB[0] - fromDarkHSB[0]) + toDarkHSB[0] : in_h;
        out_s = affectS ? (toBrightHSB[1] - toDarkHSB[1]) * (in_s - fromDarkHSB[1]) / Math.max(0.01f, fromBrightHSB[1] - fromDarkHSB[1]) + toDarkHSB[1] : in_s;
        out_b = affectB ? (toBrightHSB[2] - toDarkHSB[2]) * (in_b - fromDarkHSB[2]) / Math.max(0.01f, fromBrightHSB[2] - fromDarkHSB[2]) + toDarkHSB[2] : in_b;
        out_a = affectA ? (toBright.getAlpha() - toDark.getAlpha()) * (in_a - fromDark.getAlpha()) / Math.max(1, fromBright.getAlpha() - fromDark.getAlpha()) + toDark.getAlpha() : in_a;
    }

}
