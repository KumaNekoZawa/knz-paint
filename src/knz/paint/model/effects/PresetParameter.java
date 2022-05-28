package knz.paint.model.effects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PresetParameter extends AbstractParameter {

    private String[] parameterNames;
    private List<Preset> presets = new ArrayList<>();

    public PresetParameter(String[] parameterNames, Preset... presets) {
        this(parameterNames, Arrays.asList(presets));
    }

    public PresetParameter(String[] parameterNames, List<Preset> presets) {
        super("Presets");
        this.parameterNames = parameterNames;
        this.presets = presets;
    }

    @Override
    public void reset() {
        /* empty */
    }

    @Override
    public String getLabelText() {
        return getName();
    }

    public String[] getPresetNames() {
        return presets.stream().map(preset -> preset.getName()).toArray(String[]::new);
    }

    public String[] getParameterNames() {
        return parameterNames;
    }

    public List<Preset> getPresets() {
        return presets;
    }

}
