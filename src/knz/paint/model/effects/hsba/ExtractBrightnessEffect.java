package knz.paint.model.effects.hsba;

public class ExtractBrightnessEffect extends HSBAEffect {

    public ExtractBrightnessEffect() {
        super("Extract brightness");
    }

    @Override
    public void filter(int x, int y, float in_h, float in_s, float in_b, int in_a) {
        out_h = 0;
        out_s = 0;
    }

}
