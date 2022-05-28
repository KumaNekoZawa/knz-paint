package knz.paint.model.effects;

public class GrayscaleEffect extends RGBAEffect {

    public GrayscaleEffect() {
        super("Grayscale");
    }

    @Override
    public void filter() {
        final int gray = (r + g + b) / 3;
        r = gray;
        g = gray;
        b = gray;
    }

}
