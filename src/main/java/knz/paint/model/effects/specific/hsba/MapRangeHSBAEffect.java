package knz.paint.model.effects.specific.hsba;

import java.awt.Color;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.ColorParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

public class MapRangeHSBAEffect extends AbstractHSBAEffect {

    private static final Color COLOR_FROM = new Color( Color.HSBtoRGB(0,       0, 0) & 0xFFFFFF,               true);
    private static final Color COLOR_TO   = new Color((Color.HSBtoRGB(0.9995f, 1, 1) & 0xFFFFFF) | 0xFF000000, true);

    private PresetParameter paramPresets = new PresetParameter(
        new String[] {
            "Affect hue",
            "Affect saturation",
            "Affect brightness",
            "Affect alpha",
            "From dark",
            "From bright",
            "To dark",
            "To bright",
            "Affect range only",
            "Affect low sat./br.",
        },
        new Preset(
            "Default",
            false,
            false,
            false,
            false,
            COLOR_FROM,
            COLOR_TO,
            COLOR_FROM,
            COLOR_TO,
            false,
            false
        ),
        new Preset(
            "9b25afb9",
            true,
            true,
            false,
            false,
            new Color(0x68, 0x00, 0x07, 0xFF),
            new Color(0xFF, 0x2C, 0x70, 0xFF),
            new Color(0xD8, 0xB2, 0x83, 0xFF),
            new Color(0xE9, 0xC6, 0x9E, 0xFF),
            true,
            false
        )
    );
    private BooleanParameter paramAffectH         = new BooleanParameter("Affect hue",        false);
    private BooleanParameter paramAffectS         = new BooleanParameter("Affect saturation", false);
    private BooleanParameter paramAffectB         = new BooleanParameter("Affect brightness", false);
    private BooleanParameter paramAffectA         = new BooleanParameter("Affect alpha",      false);
    private ColorParameter   paramFromDark        = new ColorParameter("From dark",   COLOR_FROM);
    private ColorParameter   paramFromBright      = new ColorParameter("From bright", COLOR_TO);
    private ColorParameter   paramToDark          = new ColorParameter("To dark",     COLOR_FROM);
    private ColorParameter   paramToBright        = new ColorParameter("To bright",   COLOR_TO);
    private BooleanParameter paramAffectRangeOnly = new BooleanParameter("Affect range only",   false);
    private BooleanParameter paramAffectLowSatBr  = new BooleanParameter("Affect low sat./br.", false);

    public MapRangeHSBAEffect() {
        super("Map range");
        this.parameters.add(paramPresets);
        this.parameters.add(paramAffectH);
        this.parameters.add(paramAffectS);
        this.parameters.add(paramAffectB);
        this.parameters.add(paramAffectA);
        this.parameters.add(paramFromDark);
        this.parameters.add(paramFromBright);
        this.parameters.add(paramToDark);
        this.parameters.add(paramToBright);
        this.parameters.add(paramAffectRangeOnly);
        this.parameters.add(paramAffectLowSatBr);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final boolean affectH         = paramAffectH.getValue();
        final boolean affectS         = paramAffectS.getValue();
        final boolean affectB         = paramAffectB.getValue();
        final boolean affectA         = paramAffectA.getValue();
        final Color   fromDark        = paramFromDark.getValue();
        final Color   fromBright      = paramFromBright.getValue();
        final Color   toDark          = paramToDark.getValue();
        final Color   toBright        = paramToBright.getValue();
        final boolean affectRangeOnly = paramAffectRangeOnly.getValue();
        final boolean affectLowSatBr  = paramAffectLowSatBr.getValue();
        final float[] fromDarkHSB   = Color.RGBtoHSB(fromDark.getRed(),   fromDark.getGreen(),   fromDark.getBlue(),   null);
        final float[] fromBrightHSB = Color.RGBtoHSB(fromBright.getRed(), fromBright.getGreen(), fromBright.getBlue(), null);
        final float[] toDarkHSB     = Color.RGBtoHSB(toDark.getRed(),     toDark.getGreen(),     toDark.getBlue(),     null);
        final float[] toBrightHSB   = Color.RGBtoHSB(toBright.getRed(),   toBright.getGreen(),   toBright.getBlue(),   null);
        final float minH = Math.min(fromDarkHSB[0], fromBrightHSB[0]);
        final float maxH = Math.max(fromDarkHSB[0], fromBrightHSB[0]);
        final float minS = Math.min(fromDarkHSB[1], fromBrightHSB[1]);
        final float maxS = Math.max(fromDarkHSB[1], fromBrightHSB[1]);
        final float minB = fromDarkHSB[2];
        final float maxB = fromBrightHSB[2];
        final int   minA = Math.min(fromDark.getAlpha(), fromBright.getAlpha());
        final int   maxA = Math.max(fromDark.getAlpha(), fromBright.getAlpha());
        if ((!affectRangeOnly
             || ((!affectH || (minH <= in_h && in_h <= maxH))
              && (!affectS || (minS <= in_s && in_s <= maxS))
              && (!affectB || (minB <= in_b && in_b <= maxB))
              && (!affectA || (minA <= in_a && in_a <= maxA))))
         && (!affectLowSatBr || (in_s >= 0.01f && in_b >= 0.01f))) {
            out_h = affectH ? (toBrightHSB[0]      - toDarkHSB[0])      * (in_h - minH) / Math.max(0.01f, maxH - minH) + toDarkHSB[0]      : in_h;
            out_s = affectS ? (toBrightHSB[1]      - toDarkHSB[1])      * (in_s - minS) / Math.max(0.01f, maxS - minS) + toDarkHSB[1]      : in_s;
            out_b = affectB ? (toBrightHSB[2]      - toDarkHSB[2])      * (in_b - minB) / Math.max(0.01f, maxB - minB) + toDarkHSB[2]      : in_b;
            out_a = affectA ? (toBright.getAlpha() - toDark.getAlpha()) * (in_a - minA) / Math.max(1,     maxA - minA) + toDark.getAlpha() : in_a;
        } else {
            out_h = in_h;
            out_s = in_s;
            out_b = in_b;
            out_a = in_a;
        }
    }

}
