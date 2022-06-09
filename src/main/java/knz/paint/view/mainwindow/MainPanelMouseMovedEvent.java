package knz.paint.view.mainwindow;

public class MainPanelMouseMovedEvent {

    private int x, y, rgba;

    public MainPanelMouseMovedEvent(int x, int y, int rgba) {
        super();
        this.x = x;
        this.y = y;
        this.rgba = rgba;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRGBA() {
        return rgba;
    }

}
