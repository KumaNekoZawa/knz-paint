package knz.paint.model.effects.specific.hsba.colorblindness;

import java.util.HashMap;
import java.util.Map;

import knz.paint.model.effects.specific.hsba.AbstractHSBAEffect;

public abstract class AbstractColorBlindnessEffect extends AbstractHSBAEffect {

    private Map<Integer, Integer> mapHues         = new HashMap<>();
    private Map<Integer, Integer> mapSaturations  = new HashMap<>();
    private Map<Integer, Integer> mapBrightnesses = new HashMap<>();

    public AbstractColorBlindnessEffect(String name, int[] hues, int[] saturations, int[] brightnesses) {
        super("Color blindness." + name, true, false);
        for (int hue = 0; hue < 360; hue++) {
            if (hues != null) {
                mapHues.put(hue, hues[hue]);
            }
            if (saturations != null) {
                mapSaturations.put(hue, saturations[hue]);
            }
            if (brightnesses != null) {
                mapBrightnesses.put(hue, brightnesses[hue]);
            }
        }
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        if (in_s < 0.01f || in_b < 0.01f) {
            out_h = in_h;
            out_s = in_s;
            out_b = in_b;
        } else {
            final int key = (int) (in_h * 360);
            out_h =                       ((int)         mapHues.getOrDefault(key, 0)) / (float) 0xFF;
            out_s = Math.min(in_s, in_s * ((int)  mapSaturations.getOrDefault(key, 0)) / (float) 0xFF);
            out_b = Math.min(in_b, in_b * ((int) mapBrightnesses.getOrDefault(key, 0)) / (float) 0xFF);
        }
        out_a = in_a;
    }

}
