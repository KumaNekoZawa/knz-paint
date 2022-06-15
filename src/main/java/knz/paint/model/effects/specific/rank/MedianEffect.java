package knz.paint.model.effects.specific.rank;

public class MedianEffect extends AbstractRankEffect {

    public MedianEffect() {
        super("Median");
    }

    @Override
    protected int filter(int valueCount) {
        return valueCount / 2;
    }

}
