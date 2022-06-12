package knz.paint.model.effects.specific.hsba;

import knz.paint.model.effects.parameter.BooleanParameter;

public class NegateHSBAEffect extends AbstractHSBAEffect {

    private BooleanParameter paramAffectH = new BooleanParameter("Affect hue",        false);
    private BooleanParameter paramAffectS = new BooleanParameter("Affect saturation", false);
    private BooleanParameter paramAffectB = new BooleanParameter("Affect brightness", false);
    private BooleanParameter paramAffectA = new BooleanParameter("Affect alpha",      false);

    public NegateHSBAEffect() {
        super("Negate HSBA");
        this.parameters.add(paramAffectH);
        this.parameters.add(paramAffectS);
        this.parameters.add(paramAffectB);
        this.parameters.add(paramAffectA);
    }

    @Override
    protected void filter(float in_h, float in_s, float in_b, int in_a) {
        final boolean affectH = paramAffectH.getValue();
        final boolean affectS = paramAffectS.getValue();
        final boolean affectB = paramAffectB.getValue();
        final boolean affectA = paramAffectA.getValue();
        out_h = affectH ?    1 - in_h : in_h;
        out_s = affectS ?    1 - in_s : in_s;
        out_b = affectB ?    1 - in_b : in_b;
        out_a = affectA ? 0xFF - in_a : in_a;
    }

}
