package knz.paint.model.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

/* Corresponds to the "Options" menu in the MainWindow. */
public class ToolState {

    public static final int[] STOKE_WIDTHS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 25, 30, 40, 50 };
    public static final int[] ROUNDED_RECTANGLE_RADII = { 5, 10, 15, 20, 25, 50, 75, 100 };
    public static final int[] AIRBRUSH_SIZES = { 5, 10, 15, 20, 25 };

    public static final Tool DEFAULT_TOOL = Tool.POLYLINE_FREE_FORM;
    public static final Color DEFAULT_COLOR_PRIMARY = Color.BLACK;
    public static final Color DEFAULT_COLOR_SECONDARY = Color.WHITE;
    public static final FillStyle DEFAULT_FILL_STYLE = FillStyle.NONE;
    public static final int DEFAULT_STROKE_WIDTH = 1;
    public static final StrokeDash DEFAULT_STROKE_DASH = StrokeDash.NORMAL;
    public static final int DEFAULT_ROUNDED_RECTANGLE_ARC_WIDTH  = 20;
    public static final int DEFAULT_ROUNDED_RECTANGLE_ARC_HEIGHT = 20;
    public static final AirbrushType DEFAULT_AIRBRUSH_TYPE = AirbrushType.NORMAL;
    public static final int DEFAULT_AIRBRUSH_SIZE = 15;

    private Tool selectedTool = DEFAULT_TOOL;
    private Color colorPrimary = DEFAULT_COLOR_PRIMARY;
    private Color colorSecondary = DEFAULT_COLOR_SECONDARY;
    private FillStyle fillStyle = DEFAULT_FILL_STYLE;
    private int strokeWidth = DEFAULT_STROKE_WIDTH;
    private StrokeDash strokeDash = DEFAULT_STROKE_DASH;
    private Stroke stroke;
    private int roundedRectangleArcWidth  = DEFAULT_ROUNDED_RECTANGLE_ARC_WIDTH;
    private int roundedRectangleArcHeight = DEFAULT_ROUNDED_RECTANGLE_ARC_HEIGHT;
    private AirbrushType airbrushType = DEFAULT_AIRBRUSH_TYPE;
    private int airbrushSize = DEFAULT_AIRBRUSH_SIZE;

    public ToolState() {
        super();
        updateStroke();
    }

    public Tool getSelectedTool() {
        return selectedTool;
    }

    public void setSelectedTool(Tool selectedTool) {
        this.selectedTool = selectedTool;
    }

    public Color getColorPrimary() {
        return colorPrimary;
    }

    public void setColorPrimary(Color colorPrimary) {
        this.colorPrimary = colorPrimary;
    }

    public Color getColorSecondary() {
        return colorSecondary;
    }

    public void setColorSecondary(Color colorSecondary) {
        this.colorSecondary = colorSecondary;
    }

    public void swapColors() {
        final Color colorPrimaryOld = colorPrimary;
        final Color colorSecondaryOld = colorSecondary;
        colorPrimary = colorSecondaryOld;
        colorSecondary = colorPrimaryOld;
    }

    public FillStyle getFillStyle() {
        return fillStyle;
    }

    public void setFillStyle(FillStyle fillStyle) {
        this.fillStyle = fillStyle;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        updateStroke();
    }

    public StrokeDash getStrokeDash() {
        return strokeDash;
    }

    public void setStrokeDash(StrokeDash strokeDash) {
        this.strokeDash = strokeDash;
        updateStroke();
    }

    public Stroke getStroke() {
        return stroke;
    }

    private void updateStroke() {
        final float width = (float) strokeWidth;
        final int cap  = BasicStroke.CAP_ROUND;
        final int join = BasicStroke.JOIN_ROUND;
        if (strokeDash == StrokeDash.NORMAL) {
            stroke = new BasicStroke(width, cap, join);
        } else {
            final float[] dash;
            switch (strokeDash) {
            case NORMAL:
                throw new AssertionError();
            case DOTS:
                dash = new float[] { strokeWidth, 2f * strokeWidth };
                break;
            case DASHES:
                dash = new float[] { 4f * strokeWidth };
                break;
            case DASHES_AND_DOTS:
                dash = new float[] { 4f * strokeWidth, 2f * strokeWidth, strokeWidth, 2f * strokeWidth };
                break;
            case DASHES_DASHES_AND_DOTS:
                dash = new float[] { 4f * strokeWidth, 2f * strokeWidth, 4f * strokeWidth, 2f * strokeWidth, strokeWidth, 2f * strokeWidth };
                break;
            case DASHES_DOTS_AND_DOTS:
                dash = new float[] { 4f * strokeWidth, 2f * strokeWidth, strokeWidth, 2f * strokeWidth, strokeWidth, 2f * strokeWidth };
                break;
            default:
                throw new AssertionError();
            }
            stroke = new BasicStroke(width, cap, join, 0f, dash, 0f);
        }
    }

    public int getRoundedRectangleArcWidth() {
        return roundedRectangleArcWidth;
    }

    public void setRoundedRectangleArcWidth(int roundedRectangleArcWidth) {
        this.roundedRectangleArcWidth = roundedRectangleArcWidth;
    }

    public int getRoundedRectangleArcHeight() {
        return roundedRectangleArcHeight;
    }

    public void setRoundedRectangleArcHeight(int roundedRectangleArcHeight) {
        this.roundedRectangleArcHeight = roundedRectangleArcHeight;
    }

    public AirbrushType getAirbrushType() {
        return airbrushType;
    }

    public void setAirbrushType(AirbrushType airbrushType) {
        this.airbrushType = airbrushType;
    }

    public int getAirbrushSize() {
        return airbrushSize;
    }

    public void setAirbrushSize(int airbrushSize) {
        this.airbrushSize = airbrushSize;
    }

}
