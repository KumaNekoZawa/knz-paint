package knz.paint.tools;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import knz.paint.model.ImageState;
import knz.paint.model.ToolState;

public abstract class AbstractTool {

    public static final Cursor CURSOR_DEFAULT = new Cursor(Cursor.DEFAULT_CURSOR);

    protected ToolState toolState;
    protected ImageState imageState;

    protected int startX = 0, startY = 0;
    protected int x = 0, y = 0;
    protected boolean mousePressed = false;

    public final void setToolState(ToolState toolState) {
        this.toolState = toolState;
    }

    public final void setImageState(ImageState imageState) {
        this.imageState = imageState;
    }

    public String getName() {
        return "";
    }

    public String getIcon() {
        return "";
    }

    public Cursor getCursor() {
        return CURSOR_DEFAULT;
    }

    /* Whether the image changes.
       Typically selection tools do not change the image,
       while most other tools will change the image.
     */
    public boolean doesChangeImage() {
        return true;
    }

    /* Whether the size of the image changes.
       Typically the "change image size" tool will be the only tool to do so.
     */
    public boolean doesChangeImageSize() {
        return false;
    }

    /* Whether the canvas layer will need to be painted and shown.
       Typically tools whose progress can be canceled (lines, polygons, rectangles, etc) change the canvas,
       while tools that take effect immediately (airbrush, flood fill, pick color, etc) do not change the canvas.
     */
    public boolean doesChangeCanvas() {
        return false;
    }

    /* Whether the tool state is affected by this tool.
       Typically these are tools like the "pick color" tool.
     */
    public boolean doesChangeToolState() {
        return false;
    }

    /* Whether the tool uses a timer.
       Typically these are tools like the "airbrush" tool.
     */
    public boolean usesTimer() {
        return false;
    }

    public void mousePressed(Graphics2D graphics2d, MouseEvent e) {
        updateXY(e);
        startX = x;
        startY = y;
        mousePressed = true;
    }

    public void mouseDragged(Graphics2D graphics2d, MouseEvent e) {
        updateXY(e);
    }

    public void mouseReleased(Graphics2D graphics2d, MouseEvent e) {
        mousePressed = false;
        updateXY(e);
    }

    private void updateXY(MouseEvent e) {
        x = imageState.getZoomDivisor() * e.getX() / imageState.getZoomFactor();
        y = imageState.getZoomDivisor() * e.getY() / imageState.getZoomFactor();
    }

    public void timerEvent(Graphics2D graphics2d, ActionEvent e) {
    }

    public void paint(Graphics2D graphics2d) {
    }

}
