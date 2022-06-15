package knz.paint.model.effects.specific.rank;

public class MinimumEffect extends AbstractRankEffect {

    public MinimumEffect() {
        super("Minimum");
    }

    @Override
    protected int filter(int valueCount) {
        return 0;
    }

}
