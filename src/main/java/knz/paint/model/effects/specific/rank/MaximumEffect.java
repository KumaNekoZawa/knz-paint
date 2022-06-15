package knz.paint.model.effects.specific.rank;

public class MaximumEffect extends AbstractRankEffect {

    public MaximumEffect() {
        super("Maximum");
    }

    @Override
    protected int filter(int valueCount) {
        return valueCount > 0 ? valueCount - 1 : 0;
    }

}
