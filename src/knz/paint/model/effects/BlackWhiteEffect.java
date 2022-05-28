package knz.paint.model.effects;

public class BlackWhiteEffect extends RGBAEffect {

    public BlackWhiteEffect() {
        super("Black & White");
    }

    @Override
    public void filter() {
        // FIXME also support a fixed threshold version of this filter
        final int gray = ((r + g + b) / 3) / 255. < Math.random() ? 0 : 255;
        r = gray;
        g = gray;
        b = gray;
    }

}
