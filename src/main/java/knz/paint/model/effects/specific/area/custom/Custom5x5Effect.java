package knz.paint.model.effects.specific.area.custom;

import knz.paint.model.effects.parameter.IntegerArrayParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

public class Custom5x5Effect extends AbstractCustomEffect {

    public Custom5x5Effect() {
        super("Custom 5×5 effect", 2, new PresetParameter(
            new String[] { "Auto scale", "Matrix" },
            new Preset("Default", true, new int[] {
                  0,   0,   0,   0,   0,
                  0,   0,   0,   0,   0,
                  0,   0,   1,   0,   0,
                  0,   0,   0,   0,   0,
                  0,   0,   0,   0,   0 }),
            new Preset("Diamond blur", true, new int[] {
                  0,   0,   1,   0,   0,
                  0,   1,   1,   1,   0,
                  1,   1,   1,   1,   1,
                  0,   1,   1,   1,   0,
                  0,   0,   1,   0,   0 }),
            new Preset("Box blur", true, new int[] {
                  1,   1,   1,   1,   1,
                  1,   1,   1,   1,   1,
                  1,   1,   1,   1,   1,
                  1,   1,   1,   1,   1,
                  1,   1,   1,   1,   1 }),
            new Preset("Gaussian blur", true, new int[] {
                  1,   4,   7,   4,   1,
                  4,  16,  26,  16,   4,
                  7,  26,  41,  26,   7,
                  4,  16,  26,  16,   4,
                  1,   4,   7,   4,   1 }),
            new Preset("Diamond sharp", false, new int[] {
                  0,   0,  -1,   0,   0,
                  0,  -1,  -1,  -1,   0,
                 -1,  -1,  13,  -1,  -1,
                  0,  -1,  -1,  -1,   0,
                  0,   0,  -1,   0,   0 }),
            new Preset("Box sharp", false, new int[] {
                 -1,  -1,  -1,  -1,  -1,
                 -1,  -1,  -1,  -1,  -1,
                 -1,  -1,  25,  -1,  -1,
                 -1,  -1,  -1,  -1,  -1,
                 -1,  -1,  -1,  -1,  -1 }),
            new Preset("Gaussian sharp", false, new int[] {
                 -1,  -4,  -7,  -4,  -1,
                 -4, -16, -26, -16,  -4,
                 -7, -26, 233, -26,  -7,
                 -4, -16, -26, -16,  -4,
                 -1,  -4,  -7,  -4,  -1 }),
            new Preset("Laplacian", false, new int[] {
                  0,   0,  -1,   0,   0,
                  0,  -1,  -2,  -1,   0,
                 -1,  -2,  16,  -2,  -1,
                  0,  -1,  -2,  -1,   0,
                  0,   0,  -1,   0,   0 }),
            new Preset("Emboss", true, new int[] {
                  0,   0,   1,   0,   0,
                  0,   0,   0,   0,   0,
                  0,   0,   0,   0,   0,
                  0,   0,   0,   0,   0,
                  0,   0,  -1,   0,   0 })
        ), new IntegerArrayParameter("Matrix", 5, 5, new int[] {
                  0,   0,   0,   0,   0,
                  0,   0,   0,   0,   0,
                  0,   0,   1,   0,   0,
                  0,   0,   0,   0,   0,
                  0,   0,   0,   0,   0 }));
    }

}
