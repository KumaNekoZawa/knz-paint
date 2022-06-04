package knz.paint.model.tools;

import knz.paint.model.tools.specific.AbstractTool;
import knz.paint.model.tools.specific.AirbrushTool;
import knz.paint.model.tools.specific.BezierCurveTool;
import knz.paint.model.tools.specific.ChangeImageSizeTool;
import knz.paint.model.tools.specific.EllipseTool;
import knz.paint.model.tools.specific.FloodFillTool;
import knz.paint.model.tools.specific.LineTool;
import knz.paint.model.tools.specific.PickColorTool;
import knz.paint.model.tools.specific.PolygonFreeFormTool;
import knz.paint.model.tools.specific.PolygonTool;
import knz.paint.model.tools.specific.PolylineFreeFormTool;
import knz.paint.model.tools.specific.PolylineTool;
import knz.paint.model.tools.specific.RectangleTool;
import knz.paint.model.tools.specific.RoundedRectangleTool;
import knz.paint.model.tools.specific.SelectFreeFormTool;
import knz.paint.model.tools.specific.SelectPolygonTool;
import knz.paint.model.tools.specific.SelectRectangleTool;

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
