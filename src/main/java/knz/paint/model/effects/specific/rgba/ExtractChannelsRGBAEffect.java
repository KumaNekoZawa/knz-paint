package knz.paint.model.effects.specific.rgba;

import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

public class ExtractChannelsRGBAEffect extends AbstractRGBAEffect {

    private PresetParameter paramPresets = new PresetParameter(
        new String[] { "Affect red", "Affect green", "Affect blue", "Affect alpha" },
        new Preset("Default",        true,  true,  true, false),
        new Preset("Extract red",   false,  true,  true,  true),
        new Preset("Extract green",  true, false,  true,  true),
        new Preset("Extract blue",   true,  true, false,  true),
        new Preset("Remove alpha",  false, false, false,  true)
    );

    public ExtractChannelsRGBAEffect() {
        super("Extract channels", true, true);
        this.parameters.add(paramPresets);
    }

    @Override
    protected void filter(int in_r, int in_g, int in_b, int in_a) {
        out_r = 0x00;
        out_g = 0x00;
        out_b = 0x00;
        out_a = 0xFF;
    }

}
