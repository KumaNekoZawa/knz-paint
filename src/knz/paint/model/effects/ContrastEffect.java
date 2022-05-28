package knz.paint.model.effects;

public class ContrastEffect extends RGBAEffect {

    private EffectParameter factor = new EffectParameter("Factor", 0d, 1d, 2d);

    public ContrastEffect() {
        super("Contrast");
        this.parameters.add(factor);
    }

    @Override
    public void filter() {
        final double f = factor.getValue();
        r = (int) (f * (r - 0x80)) + 0x80;
        g = (int) (f * (g - 0x80)) + 0x80;
        b = (int) (f * (b - 0x80)) + 0x80;
    }

}
