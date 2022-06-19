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
    private ColorParameter   paramFromDark        = new ColorParameter("From dark",   COLOR_FROM);
    private ColorParameter   paramFromBright      = new ColorParameter("From bright", COLOR_TO);
    private ColorParameter   paramToDark          = new ColorParameter("To dark",     COLOR_FROM);
    private ColorParameter   paramToBright        = new ColorParameter("To bright",   COLOR_TO);
    private BooleanParameter paramAffectRangeOnly = new BooleanParameter("Affect range only",   false);
    private BooleanParameter paramAffectLowSatBr  = new BooleanParameter("Affect low sat./br.", false);

    private float[] hsbFromDark, hsbFromBright;
    private float[] hsbToDark,   hsbToBright;
    private float minH, maxH;
    private float minS, maxS;
    private float minB, maxB;
    private int   minA, maxA;

    public MapRangeHSBAEffect() {
        super("Map range", true, true);
        this.parameters.add(paramPresets);
        this.parameters.add(paramFromDark);
        this.parameters.add(paramFromBright);
        this.parameters.add(paramToDark);
        this.parameters.add(paramToBright);
        this.parameters.add(paramAffectRangeOnly);
        this.parameters.add(paramAffectLowSatBr);
    }

    @Override
    protected void applyHead() {
        final Color   fromDark        = paramFromDark.getValue();
        final Color   fromBright      = paramFromBright.getValue();
        final Color   toDark          = paramToDark.getValue();
        final Color   toBright        = paramToBright.getValue();
        hsbFromDark   = Color.RGBtoHSB(fromDark.getRed(),   fromDark.getGreen(),   fromDark.getBlue(),   null);
        hsbFromBright = Color.RGBtoHSB(fromBright.getRed(), fromBright.getGreen(), fromBright.getBlue(), null);
        hsbToDark     = Color.RGBtoHSB(toDark.getRed(),     toDark.getGreen(),     toDark.getBlue(),     null);
        hsbToBright   = Color.RGBtoHSB(toBright.getRed(),   toBright.getGreen(),   toBright.getBlue(),   null);
        minH = Math.min(hsbFromDark[0], hsbFromBright[0]);
        maxH = Math.max(hsbFromDark[0], hsbFromBright[0]);
        minS = Math.min(hsbFromDark[1], hsbFromBright[1]);
        maxS = Math.max(hsbFromDark[1], hsbFromBright[1]);
        minB = hsbFromDark[2];
        maxB = hsbFromBright[2];
        minA = Math.min(fromDark.getAlpha(), fromBright.getAlpha());
        maxA = Math.max(fromDark.getAlpha(), fromBright.getAlpha());
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final Color   toDark          = paramToDark.getValue();
        final Color   toBright        = paramToBright.getValue();
        final boolean affectRangeOnly = paramAffectRangeOnly.getValue();
        final boolean affectLowSatBr  = paramAffectLowSatBr.getValue();
        if ((!affectRangeOnly
             || (minH <= in_h && in_h <= maxH
              && minS <= in_s && in_s <= maxS
              && minB <= in_b && in_b <= maxB
              && minA <= in_a && in_a <= maxA))
         && (!affectLowSatBr || (in_s >= 0.01f && in_b >= 0.01f))) {
            out_h = (hsbToBright[0]      - hsbToDark[0])      * (in_h - minH) / Math.max(0.01f, maxH - minH) + hsbToDark[0];
            out_s = (hsbToBright[1]      - hsbToDark[1])      * (in_s - minS) / Math.max(0.01f, maxS - minS) + hsbToDark[1];
            out_b = (hsbToBright[2]      - hsbToDark[2])      * (in_b - minB) / Math.max(0.01f, maxB - minB) + hsbToDark[2];
            out_a = (toBright.getAlpha() - toDark.getAlpha()) * (in_a - minA) / Math.max(1,     maxA - minA) + toDark.getAlpha();
        } else {
            out_h = in_h;
            out_s = in_s;
            out_b = in_b;
            out_a = in_a;
        }
    }

}
