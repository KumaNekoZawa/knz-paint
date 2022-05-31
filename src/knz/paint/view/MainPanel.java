package knz.paint.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import knz.paint.model.Config;
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

    public enum StrokeDash {
        NORMAL("Normal"),
        DOTS("Dots"),
        DASHES("Dashes"),
        DASHES_AND_DOTS("Dashes and dots"),
        DASHES_DASHES_AND_DOTS("Dashes, dashes and dots"),
        DASHES_DOTS_AND_DOTS("Dashes, dots and dots"),

        ;

        private String title;

        StrokeDash(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    public enum AirbrushType {
        NORMAL("Normal"),
        RANDOM_COLOR("Random color"),
        RANDOM_HUE("Random hue"),

        ;

        private String title;

        AirbrushType(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    private static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();

    private MainWindow parentElement;

    private BufferedImage image;
    private Graphics2D g2d;

    // temporary image that is being drawn when previewing effects
    private BufferedImage imageTemp;
    private int imageTempX, imageTempY;

    // View
    private int zoomDivisor = 1;
    private int zoomFactor = 1;
    // Options
    private Tool selectedTool = Tool.POLYLINE_FREE_FORM;
    private Color colorPrimary = Color.BLACK;
    private Color colorSecondary = Color.WHITE;
    private FillStyle fillStyle = FillStyle.NONE;
    private int strokeWidth = 1;
    private StrokeDash strokeDash = StrokeDash.NORMAL;
    private Stroke stroke = null;
    private int roundedRectangleArcWidth = 20;
    private int roundedRectangleArcHeight = 20;
    private AirbrushType airbrushType = AirbrushType.NORMAL;
    private int airbrushSize = 15;

    public MainPanel() {
        super();
        updateStroke();
        for (Tool tool : Tool.values()) {
            tool.getToolObject().setMainPanel(this);
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(selectedTool.getToolObject().getCursor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(AbstractTool.CURSOR_DEFAULT);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectedTool.getToolObject().mousePressed(g2d, e);
                MainPanel.this.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedTool.getToolObject().mouseReleased(g2d, e);
                MainPanel.this.repaint();
                if (selectedTool.getToolObject().doesChangeImage()) {
                    MainPanel.this.parentElement.changedTillLastSave();
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updateStatusBar(e);
                selectedTool.getToolObject().mouseDragged(g2d, e);
                MainPanel.this.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                updateStatusBar(e);
            }

            private void updateStatusBar(MouseEvent e) {
                final int x = zoomDivisor * e.getX() / zoomFactor;
                final int y = zoomDivisor * e.getY() / zoomFactor;
                if (0 <= x && x < image.getWidth()
                 && 0 <= y && y < image.getHeight()) {
                    parentElement.updateStatusBarCurrentPixel(x, y, image.getRGB(x, y));
                }
            }
        });
        setBackground(Config.getConfig().getBackgroundColor());
    }

    public MainWindow getParentElement() {
        return parentElement;
    }

    public void setParentElement(MainWindow parentElement) {
        this.parentElement = parentElement;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        if (imageTemp == null) {
            final int imageWidth = image.getWidth();
            final int imageHeight = image.getHeight();
            g2d.drawImage(image,
                          0,
                          0,
                          zoomFactor * imageWidth  / zoomDivisor,
                          zoomFactor * imageHeight / zoomDivisor,
                          null);
            AbstractTool toolObject = selectedTool.getToolObject();
            if (toolObject.needsRepaint()) {
                BufferedImage canvas = new BufferedImage(imageWidth, imageHeight, image.getType());
                toolObject.paint(canvas.createGraphics());
                g2d.drawImage(canvas,
                              0,
                              0,
                              zoomFactor * imageWidth  / zoomDivisor,
                              zoomFactor * imageHeight / zoomDivisor,
                              null);
            }
        } else {
            final int imageTempWidth = imageTemp.getWidth();
            final int imageTempHeight = imageTemp.getHeight();
            g2d.drawImage(imageTemp,
                          zoomFactor * imageTempX      / zoomDivisor,
                          zoomFactor * imageTempY      / zoomDivisor,
                          zoomFactor * imageTempWidth  / zoomDivisor,
                          zoomFactor * imageTempHeight / zoomDivisor,
                          null);
        }
    }

    // File

    public BufferedImage getImage() {
        return image;
    }

    public Graphics2D getG2D() {
        return g2d;
    }

    public void setImage(BufferedImage image) {
        if (image == null || image.getType() != BufferedImage.TYPE_INT_ARGB) {
            throw new IllegalArgumentException();
        }
        setImageAndGraphics(image, image.createGraphics());
    }

    public void setImageAndGraphics(BufferedImage imageNew, Graphics2D g2dNew) {
        if (g2d != null) {
            g2d.dispose();
        }
        image = imageNew;
        g2d = g2dNew;
        updatePanelSize();
        repaint();
    }

    public void newImage(int width, int height, Color fillColor) {
        BufferedImage imageNew = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dNew = imageNew.createGraphics();
        if (fillColor != null) {
            g2dNew.setColor(fillColor);
            g2dNew.fillRect(0, 0, width, height);
        }
        setImageAndGraphics(imageNew, g2dNew);
    }

    public BufferedImage getImageWithoutAlpha() {
        final int width = image.getWidth();
        final int height = image.getHeight();
        BufferedImage imageNew = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2dNew = imageNew.createGraphics();
        g2dNew.setColor(colorSecondary);
        g2dNew.fillRect(0, 0, width, height);
        g2dNew.drawImage(image, 0, 0, null);
        g2dNew.dispose();
        return imageNew;
    }

    public void setImageWithOrWithoutAlpha(BufferedImage imageNew) {
        if (imageNew.getType() == BufferedImage.TYPE_INT_ARGB) {
            setImage(imageNew);
        } else {
            BufferedImage imageNewer = new BufferedImage(imageNew.getWidth(), imageNew.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2dNewer = imageNewer.createGraphics();
            g2dNewer.drawImage(imageNew, 0, 0, null);
            g2dNewer.dispose();
            setImage(imageNewer);
        }
    }

    public void setImageTemp(BufferedImage imageTempNew, int imageTempNewX, int imageTempNewY) {
        imageTemp = imageTempNew;
        imageTempX = imageTempNewX;
        imageTempY = imageTempNewY;
        repaint();
    }

    public void setImageTempReset(boolean repaint) {
        imageTemp = null;
        imageTempX = 0;
        imageTempY = 0;
        if (repaint) {
            repaint();
        }
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
            srt.selectAll();
            repaint();
        } else {
            throw new AssertionError();
        }
    }

    public void cut() {
        AbstractTool toolObject = selectedTool.getToolObject();
        if (toolObject instanceof SelectRectangleTool) {
            SelectRectangleTool srt = (SelectRectangleTool) toolObject;
            CLIPBOARD.setContents(new TransferableImage(srt.getSubimage()), null);
            srt.clearSelection();
            repaint();
        } else {
            JOptionPane.showMessageDialog(parentElement, "You have to select a rectangle first!", parentElement.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void copy() {
        AbstractTool toolObject = selectedTool.getToolObject();
        if (toolObject instanceof SelectRectangleTool) {
            SelectRectangleTool srt = (SelectRectangleTool) toolObject;
            CLIPBOARD.setContents(new TransferableImage(srt.getSubimage()), null);
        } else {
            JOptionPane.showMessageDialog(parentElement, "You have to select a rectangle first!", parentElement.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void paste() {
        Transferable t = CLIPBOARD.getContents(null);
        if (t != null && t.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            try {
                Image imageNew = (Image) t.getTransferData(DataFlavor.imageFlavor);
                g2d.drawImage(imageNew, 0, 0, null);
                selectedTool = Tool.SELECT_RECTANGLE;
                AbstractTool toolObject = selectedTool.getToolObject();
                if (toolObject instanceof SelectRectangleTool) {
                    SelectRectangleTool srt = (SelectRectangleTool) toolObject;
                    srt.selectRectangle(0, 0, imageNew.getWidth(null), imageNew.getHeight(null));
                } else {
                    throw new AssertionError();
                }
                repaint();
            } catch (IOException | UnsupportedFlavorException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(parentElement, "Your clipboard is empty!", parentElement.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearSelection() {
        AbstractTool toolObject = selectedTool.getToolObject();
        if (toolObject instanceof AbstractSelectionTool) {
            AbstractSelectionTool ast = (AbstractSelectionTool) toolObject;
            ast.clearSelection();
            repaint();
        } else {
            JOptionPane.showMessageDialog(parentElement, "You have to select a region first!", parentElement.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cropToSelection() {
        AbstractTool toolObject = selectedTool.getToolObject();
        if (toolObject instanceof SelectRectangleTool) {
            SelectRectangleTool srt = (SelectRectangleTool) toolObject;
            srt.cropToSelection();
            repaint();
        } else {
            JOptionPane.showMessageDialog(parentElement, "You have to select a rectangle first!", parentElement.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    // View

    public int getZoomDivisor() {
        return zoomDivisor;
    }

    public int getZoomFactor() {
        return zoomFactor;
    }

    public void setZoom(int zoomDivisor, int zoomFactor) {
        this.zoomDivisor = zoomDivisor;
        this.zoomFactor = zoomFactor;
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
        final int cap = BasicStroke.CAP_ROUND;
        final int join = BasicStroke.JOIN_ROUND;
        if (strokeDash == StrokeDash.NORMAL) {
            stroke = new BasicStroke(width, cap, join);
        } else {
            float[] dash;
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

    // Misc

    private void updatePanelSize() {
        final int width = image.getWidth();
        final int height = image.getHeight();
        Dimension d = new Dimension(zoomFactor * width / zoomDivisor, zoomFactor * height / zoomDivisor);
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
        setSize(d);
        parentElement.updateStatusBarSize(width, height);
    }

}
