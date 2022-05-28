package knz.paint.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import knz.paint.tools.AbstractSelectionTool;
import knz.paint.tools.AbstractTool;
import knz.paint.tools.AirbrushTool;
import knz.paint.tools.BezierCurveTool;
import knz.paint.tools.ChangeCanvasSizeTool;
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

public class MainPanel extends JPanel {

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
        CHANGE_CANVAS_SIZE(new ChangeCanvasSizeTool()),

        ;

        private AbstractTool toolObject;

        Tool(AbstractTool toolObject) {
            this.toolObject = toolObject;
        }

        public AbstractTool getToolObject() {
            return toolObject;
        }
    }

    public enum FillStyle {
        NONE("No fill"),
        PRIMARY("Primary color"),
        SECONDARY("Secondary color"),

        ;

        private String title;

        FillStyle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    private static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();

    private MainWindow parent;

    private BufferedImage image;
    private Graphics2D g2d;

    // View
    private int zoomLevel = 1;
    // Options
    private Tool selectedTool = Tool.POLYLINE_FREE_FORM;
    private Color colorPrimary = Color.BLACK;
    private Color colorSecondary = Color.WHITE;
    private FillStyle fillStyle = FillStyle.NONE;

    public MainPanel() {
        super();
        for (Tool tool : Tool.values()) {
            tool.getToolObject().setMainPanel(this);
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedTool.getToolObject().mousePressed(g2d, e);
                MainPanel.this.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedTool.getToolObject().mouseReleased(g2d, e);
                MainPanel.this.repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                selectedTool.getToolObject().mouseDragged(g2d, e);
                MainPanel.this.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                final int x = e.getX() / zoomLevel;
                final int y = e.getY() / zoomLevel;
                if (0 <= x && x < image.getWidth()
                 && 0 <= y && y < image.getHeight()) {
                    parent.updateStatusBarCurrentPixel(x, y, image.getRGB(x, y));
                }
            }
        });
    }

    public void setParent(MainWindow parent) {
        this.parent = parent;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.drawImage(image, 0, 0, zoomLevel * image.getWidth(), zoomLevel * image.getHeight(), null);
        AbstractTool toolObject = selectedTool.getToolObject();
        if (toolObject.needsRepaint()) {
            BufferedImage canvas = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            toolObject.paint(canvas.createGraphics());
            g2d.drawImage(canvas, 0, 0, zoomLevel * canvas.getWidth(), zoomLevel * canvas.getHeight(), null);
        }
    }

    // File

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        setImageAndGraphics(image, image.createGraphics());
    }

    public void setImageAndGraphics(BufferedImage image, Graphics2D g2d) {
        if (this.g2d != null) {
            this.g2d.dispose();
        }
        this.image = image;
        this.g2d = g2d;
        updatePanelSize();
        repaint();
    }

    public void newImage(int width, int height, Color fillColor) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        if (fillColor != null) {
            g2d.setColor(fillColor);
            g2d.fillRect(0, 0, width, height);
        }
        setImageAndGraphics(image, g2d);
    }

    // Edit

    public void selectNone() {
        AbstractTool toolObject = selectedTool.getToolObject();
        if (toolObject instanceof AbstractSelectionTool) {
            AbstractSelectionTool ast = (AbstractSelectionTool) toolObject;
            ast.selectNone();
            repaint();
        }
    }

    public void selectAll() {
        selectedTool = Tool.SELECT_RECTANGLE;
        AbstractTool toolObject = selectedTool.getToolObject();
        if (toolObject instanceof SelectRectangleTool) {
            SelectRectangleTool srt = (SelectRectangleTool) toolObject;
            srt.selectAll(image.getWidth(), image.getHeight());
            repaint();
        } else {
            throw new AssertionError();
        }
    }

    public void cropToSelection() {
        AbstractTool toolObject = selectedTool.getToolObject();
        if (toolObject instanceof SelectRectangleTool) {
            SelectRectangleTool srt = (SelectRectangleTool) toolObject;
            srt.cropToSelection();
            repaint();
        } else {
            JOptionPane.showMessageDialog(parent, "You have to select a rectangle first!", parent.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearSelection() {
        AbstractTool toolObject = selectedTool.getToolObject();
        if (toolObject instanceof AbstractSelectionTool) {
            AbstractSelectionTool ast = (AbstractSelectionTool) toolObject;
            ast.clearSelection(g2d);
            repaint();
        } else {
            JOptionPane.showMessageDialog(parent, "You have to select a region first!", parent.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cut() {
        AbstractTool toolObject = selectedTool.getToolObject();
        if (toolObject instanceof SelectRectangleTool) {
            SelectRectangleTool srt = (SelectRectangleTool) toolObject;
            CLIPBOARD.setContents(new TransferableImage(srt.getSubimage()), null);
            srt.clearSelection(g2d);
            repaint();
        } else {
            JOptionPane.showMessageDialog(parent, "You have to select a rectangle first!", parent.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void copy() {
        AbstractTool toolObject = selectedTool.getToolObject();
        if (toolObject instanceof SelectRectangleTool) {
            SelectRectangleTool srt = (SelectRectangleTool) toolObject;
            CLIPBOARD.setContents(new TransferableImage(srt.getSubimage()), null);
        } else {
            JOptionPane.showMessageDialog(parent, "You have to select a rectangle first!", parent.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    // View

    public int getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
        updatePanelSize();
        repaint();
    }

    // Options

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

    // Misc

    private void updatePanelSize() {
        final int width = image.getWidth();
        final int height = image.getHeight();
        Dimension d = new Dimension(zoomLevel * width, zoomLevel * height);
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
        setSize(d);
        parent.updateStatusBarSize(width, height);
    }

}
