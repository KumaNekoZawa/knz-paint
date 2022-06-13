package knz.paint.model.effects.specific.rgba;

import java.awt.Color;

import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.ColorParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

public class MapRangeRGBAEffect extends AbstractRGBAEffect {

    private static final Color COLOR_FROM = new Color(0x00, 0x00, 0x00, 0x00);
    private static final Color COLOR_TO   = new Color(0xFF, 0xFF, 0xFF, 0xFF);

    private PresetParameter paramPresets = new PresetParameter(
        new String[] {
            "Affect red",
            "Affect green",
            "Affect blue",
            "Affect alpha",
            "From dark",
            "From bright",
            "To dark",
            "To bright",
            "Affect range only",
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
            false
        ),
        new Preset(
            "9b25afb9",
            true,
            true,
            true,
            false,
            new Color(0x68, 0x00, 0x07, 0xFF),
            new Color(0xFF, 0x2C, 0x70, 0xFF),
            new Color(0xD8, 0xB2, 0x83, 0xFF),
            new Color(0xE9, 0xC6, 0x9E, 0xFF),
            true
        )
    );
    private ColorParameter   paramFromDark        = new ColorParameter("From dark",   COLOR_FROM);
    private ColorParameter   paramFromBright      = new ColorParameter("From bright", COLOR_TO);
    private ColorParameter   paramToDark          = new ColorParameter("To dark",     COLOR_FROM);
    private ColorParameter   paramToBright        = new ColorParameter("To bright",   COLOR_TO);
    private BooleanParameter paramAffectRangeOnly = new BooleanParameter("Affect range only", false);

    public MapRangeRGBAEffect() {
        super("Map range", true, true);
        this.parameters.add(paramPresets);
        this.parameters.add(paramFromDark);
        this.parameters.add(paramFromBright);
        this.parameters.add(paramToDark);
        this.parameters.add(paramToBright);
        this.parameters.add(paramAffectRangeOnly);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        final Color   fromDark        = paramFromDark.getValue();
        final Color   fromBright      = paramFromBright.getValue();
        final Color   toDark          = paramToDark.getValue();
        final Color   toBright        = paramToBright.getValue();
        final boolean affectRangeOnly = paramAffectRangeOnly.getValue();
        if (!affectRangeOnly
            || ((fromDark.getRed()   <= in_r && in_r <= fromBright.getRed())
             && (fromDark.getGreen() <= in_g && in_g <= fromBright.getGreen())
             && (fromDark.getBlue()  <= in_b && in_b <= fromBright.getBlue())
             && (fromDark.getAlpha() <= in_a && in_a <= fromBright.getAlpha()))) {
            out_r = (toBright.getRed()   - toDark.getRed())   * (in_r - fromDark.getRed())   / Math.max(1, fromBright.getRed()   - fromDark.getRed())   + toDark.getRed();
            out_g = (toBright.getGreen() - toDark.getGreen()) * (in_g - fromDark.getGreen()) / Math.max(1, fromBright.getGreen() - fromDark.getGreen()) + toDark.getGreen();
            out_b = (toBright.getBlue()  - toDark.getBlue())  * (in_b - fromDark.getBlue())  / Math.max(1, fromBright.getBlue()  - fromDark.getBlue())  + toDark.getBlue();
            out_a = (toBright.getAlpha() - toDark.getAlpha()) * (in_a - fromDark.getAlpha()) / Math.max(1, fromBright.getAlpha() - fromDark.getAlpha()) + toDark.getAlpha();
        } else {
            out_r = in_r;
            out_g = in_g;
            out_b = in_b;
            out_a = in_a;
        }
    }

}
