package knz.paint.view.colorpickerwindow;

import java.awt.event.ActionEvent;

public class ColorPickerTopEvent {

    private ActionEvent source;

    public ColorPickerTopEvent(ActionEvent source) {
        super();
        this.source = source;
    }

    public ActionEvent getSource() {
        return source;
    }

}
