package knz.paint.view;

public class MainPanelSizeChangedEvent {

    private int width, height;

    public MainPanelSizeChangedEvent(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
