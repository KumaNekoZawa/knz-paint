package knz.paint.model.effects.hsba;

public class ExtractBrightnessEffect extends HSBAEffect {

    public ExtractBrightnessEffect() {
        super("Extract brightness");
    }

    @Override
    public void filter() {
        h = 0;
        s = 0;
    }

}
