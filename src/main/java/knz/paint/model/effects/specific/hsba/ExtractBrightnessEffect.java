package knz.paint.model.effects.specific.hsba;

public class ExtractBrightnessEffect extends AbstractHSBAEffect {

    public ExtractBrightnessEffect() {
        super("Extract brightness");
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        out_h = 0;
        out_s = 0;
    }

}
