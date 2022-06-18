package knz.paint.model.effects.specific.area.custom;

import knz.paint.model.effects.parameter.IntegerArrayParameter;
import knz.paint.model.effects.parameter.Preset;
import knz.paint.model.effects.parameter.PresetParameter;

public class Custom3x3Effect extends AbstractCustomEffect {

    public Custom3x3Effect() {
        super("Custom 3×3 effect", 1, new PresetParameter(
            new String[] { "Auto scale", "Matrix" },
            new Preset("Default", true, new int[] {
                 0,  0,  0,
                 0,  1,  0,
                 0,  0,  0 }),
            new Preset("Diamond blur", true, new int[] {
                 0,  1,  0,
                 1,  1,  1,
                 0,  1,  0 }),
            new Preset("Box blur", true, new int[] {
                 1,  1,  1,
                 1,  1,  1,
                 1,  1,  1 }),
            new Preset("Gaussian blur", true, new int[] {
                 1,  2,  1,
                 2,  4,  2,
                 1,  2,  1 }),
            new Preset("Diamond sharp", false, new int[] {
                 0, -1,  0,
                -1,  5, -1,
                 0, -1,  0 }),
            new Preset("Box sharp", false, new int[] {
                -1, -1, -1,
                -1,  9, -1,
                -1, -1, -1 }),
            new Preset("Gaussian sharp", false, new int[] {
                -1, -2, -1,
                -2, 13, -2,
                -1, -2, -1 }),
            new Preset("Laplacian", false, new int[] {
                 0, -1,  0,
                -1,  4, -1,
                 0, -1,  0 }),
            new Preset("Emboss", true, new int[] {
                 0,  1,  0,
                 0,  0,  0,
                 0, -1,  0 })
        ), new IntegerArrayParameter("Matrix", 3, 3, new int[] {
                 0,  0,  0,
                 0,  1,  0,
                 0,  0,  0 }));
    }

}
