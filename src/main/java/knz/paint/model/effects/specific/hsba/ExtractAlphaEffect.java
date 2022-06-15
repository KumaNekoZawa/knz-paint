package knz.paint.model.effects.specific.hsba;

public class ExtractAlphaEffect extends AbstractHSBAEffect {

    public ExtractAlphaEffect() {
        super("Extract alpha", false, true);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        out_h = 0;
        out_s = 0;
        out_b = in_a / (float) 0xFF;
        out_a = 0xFF;
    }

}
