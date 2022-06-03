package knz.paint.model;

import knz.paint.tools.AbstractTool;
import knz.paint.tools.AirbrushTool;
import knz.paint.tools.BezierCurveTool;
import knz.paint.tools.ChangeImageSizeTool;
import knz.paint.tools.EllipseTool;
import knz.paint.tools.FloodFillTool;
import knz.paint.tools.LineTool;
import knz.paint.tools.PickColorTool;
import knz.paint.tools.PolygonFreeFormTool;
import knz.paint.tools.PolygonTool;
import knz.paint.tools.PolylineFreeFormTool;
import knz.paint.tools.PolylineTool;
import knz.paint.tools.RectangleTool;
import knz.paint.tools.RoundedRectangleTool;
import knz.paint.tools.SelectFreeFormTool;
import knz.paint.tools.SelectPolygonTool;
import knz.paint.tools.SelectRectangleTool;

public enum Tool {

    POLYLINE_FREE_FORM(new PolylineFreeFormTool()),
    POLYLINE(new PolylineTool()),
    LINE(new LineTool()),
    BEZIER_CURVE(new BezierCurveTool()),
    POLYGON_FREE_FORM(new PolygonFreeFormTool()),
    POLYGON(new PolygonTool()),
    ELLIPSE(new EllipseTool()),
    RECTANGLE(new RectangleTool()),
    ROUNDED_RECTANGLE(new RoundedRectangleTool()),
    SELECT_FREE_FORM(new SelectFreeFormTool()),
    SELECT_POLYGON(new SelectPolygonTool()),
    SELECT_RECTANGLE(new SelectRectangleTool()),
    AIRBRUSH(new AirbrushTool()),
    FLOOD_FILL(new FloodFillTool()),
    PICK_COLOR(new PickColorTool()),
    CHANGE_IMAGE_SIZE(new ChangeImageSizeTool()),

    ;

    private AbstractTool toolObject;

    Tool(AbstractTool toolObject) {
        this.toolObject = toolObject;
    }

    public AbstractTool getToolObject() {
        return toolObject;
    }

}
