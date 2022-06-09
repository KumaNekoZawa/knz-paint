package knz.paint.model.tools;

public class MouseInfo {

    private int x, y;
    private boolean leftButton, rightButton;

    public MouseInfo(int x, int y, boolean leftButton, boolean rightButton) {
        super();
        this.x = x;
        this.y = y;
        this.leftButton = leftButton;
        this.rightButton = rightButton;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLeftButton() {
        return leftButton;
    }

    public boolean isRightButton() {
        return rightButton;
    }

}
