package knz.paint.view.colorpickerwindow;

import java.awt.Color;

public class ColorPickerEvent {

    private Color color;

    public ColorPickerEvent(Color color) {
        super();
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}
