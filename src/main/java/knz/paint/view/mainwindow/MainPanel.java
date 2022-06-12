package knz.paint.view.mainwindow;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import knz.paint.model.Config;
import knz.paint.model.ImageMode;
import knz.paint.model.ImageState;
import knz.paint.model.effects.EffectState;
import knz.paint.model.effects.parameter.AbstractParameter;
import knz.paint.model.effects.parameter.PointListParameter;
import knz.paint.model.effects.parameter.PointParameter;
import knz.paint.model.tools.MouseInfo;
import knz.paint.model.tools.Tool;
import knz.paint.model.tools.ToolState;
import knz.paint.model.tools.specific.AbstractTool;
import knz.paint.model.tools.specific.selection.AbstractSelectionTool;
import knz.paint.model.tools.specific.selection.SelectRectangleTool;
import knz.paint.view.TransferableImage;

public class MainPanel extends JPanel {

    private static final int POINT_SIZE = 9;
    private static final Stroke DEFAULT_STROKE = new BasicStroke(1f);

    private static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();

    private List<MainPanelListener> listeners = new ArrayList<>();

    private Timer timer = new Timer(40, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
            toolObject.timerEvent(imageState.getGraphics2D());
            postToolEvent(toolObject);
        }
    });

    private ImageState imageState = new ImageState();
    private ToolState toolState;
    private EffectState effectState;

    public MainPanel(ToolState toolState, EffectState effectState) {
        super();
        this.toolState = toolState;
        this.effectState = effectState;

        for (final Tool tool : Tool.values()) {
            tool.getToolObject().setImageState(imageState);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                switch (imageState.getImageMode()) {
                case TOOL:
                    final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
                    setCursor(toolObject.getCursor());
                    break;
                default:
                    setCursor(AbstractTool.CURSOR_DEFAULT);
                    break;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(AbstractTool.CURSOR_DEFAULT);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                switch (imageState.getImageMode()) {
                case TOOL:
                    final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
                    toolObject.mousePressed(imageState.getGraphics2D(), getMouseInfo(e));
                    postToolEvent(toolObject);
                    if (toolObject.usesTimer()) {
                        timer.start();
                    }
                    break;
                case EFFECT:
                    final int x = imageState.fromUserToImage(e.getX());
                    final int y = imageState.fromUserToImage(e.getY());
                    effectState.setSelectedParameter(null);
                    effectState.setSelectedPoint(null);
                    for (final AbstractParameter parameter : effectState.getCurrentParameters()) {
                        if (parameter instanceof PointParameter) {
                            final PointParameter pointParameter = (PointParameter) parameter;
                            final Point point = pointParameter.getValue();
                            if (doesMouseHitPoint(parameter, point, x, y)) {
                                break;
                            }
                        } else if (parameter instanceof PointListParameter) {
                            final PointListParameter pointListParameter = (PointListParameter) parameter;
                            for (final Point point : pointListParameter.getValue()) {
                                if (doesMouseHitPoint(parameter, point, x, y)) {
                                    break;
                                }
                            }
                        }
                    }
                    effectEvent(x, y);
                    break;
                default:
                }
            }

            private boolean doesMouseHitPoint(AbstractParameter parameter, Point point, int x, int y) {
                final int d = imageState.fromUserToImage(POINT_SIZE / 2);
                if (point.x - d <= x && x <= point.x + d
                 && point.y - d <= y && y <= point.y + d) {
                    effectState.setSelectedParameter(parameter);
                    effectState.setSelectedPoint(point);
                    return true;
                }
                return false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                switch (imageState.getImageMode()) {
                case TOOL:
                    final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
                    if (toolObject.usesTimer()) {
                        timer.stop();
                    }
                    toolObject.mouseReleased(imageState.getGraphics2D(), getMouseInfo(e));
                    postToolEvent(toolObject);
                    if (toolObject.doesChangeImage()) {
                        imageState.setChangedTillLastSave(true);
                    }
                    break;
                case EFFECT:
                    final int x = imageState.fromUserToImage(e.getX());
                    final int y = imageState.fromUserToImage(e.getY());
                    effectEvent(x, y);
                    break;
                default:
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                final int x = imageState.fromUserToImage(e.getX());
                final int y = imageState.fromUserToImage(e.getY());
                switch (imageState.getImageMode()) {
                case TOOL:
                    final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
                    toolObject.mouseDragged(imageState.getGraphics2D(), getMouseInfo(e));
                    postToolEvent(toolObject);
                    break;
                case EFFECT:
                    effectEvent(x, y);
                    break;
                default:
                }
                updateStatusBar(x, y);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                final int x = imageState.fromUserToImage(e.getX());
                final int y = imageState.fromUserToImage(e.getY());
                updateStatusBar(x, y);
            }

            private void updateStatusBar(int x, int y) {
                final BufferedImage image = imageState.getImageMode() == ImageMode.EFFECT
                    ? imageState.getImageEffect()
                    : imageState.getImage();
                if (0 <= x && x < image.getWidth()
                 && 0 <= y && y < image.getHeight()) {
                    final int rgba = image.getRGB(x, y);
                    for (final MainPanelListener listener : listeners) {
                        listener.mouseMoved(new MainPanelMouseMovedEvent(x, y, rgba));
                    }
                }
            }
        });

        setBackground(Config.getConfig().getBackgroundColor());
    }

    private MouseInfo getMouseInfo(MouseEvent e) {
        return new MouseInfo(
            e.getX(),
            e.getY(),
            SwingUtilities.isLeftMouseButton(e),
            SwingUtilities.isRightMouseButton(e)
        );
    }

    private void postToolEvent(AbstractTool toolObject) {
        if (toolObject.doesChangeImageSize()) {
            updatePanelSize();
        }
        MainPanel.this.repaint();
        if (toolObject.doesChangeToolState()) {
            for (final MainPanelListener listener : listeners) {
                listener.toolStateChanged(new MainPanelToolStateChangedEvent());
            }
        }
    }

    private void effectEvent(int x, int y) {
        final AbstractParameter selectedParameter = effectState.getSelectedParameter();
        final Point selectedPoint = effectState.getSelectedPoint();
        if (selectedParameter != null && selectedPoint != null) {
            selectedPoint.x = x;
            selectedPoint.y = y;
            if (selectedParameter instanceof PointParameter) {
                final PointParameter pointParameter = (PointParameter) selectedParameter;
                pointParameter.firePointChangedEvent();
            } else if (selectedParameter instanceof PointListParameter) {
                final PointListParameter pointListParameter = (PointListParameter) selectedParameter;
                pointListParameter.firePointChangedEvent(selectedPoint);
            } else {
                throw new AssertionError();
            }
            MainPanel.this.repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        final Graphics2D graphics2d = (Graphics2D) g;
        final BufferedImage image = imageState.getImage();
        final int imageWidth  = image.getWidth();
        final int imageHeight = image.getHeight();
        final BufferedImage imageEffect = imageState.getImageEffect();
        switch (imageState.getImageMode()) {
        case EFFECT:
            final int imageEffectX = imageState.getImageEffectX();
            final int imageEffectY = imageState.getImageEffectY();
            final int imageEffectWidth  = imageEffect.getWidth();
            final int imageEffectHeight = imageEffect.getHeight();
            graphics2d.drawImage(imageEffect,
                imageState.fromImageToUser(imageEffectX),
                imageState.fromImageToUser(imageEffectY),
                imageState.fromImageToUser(imageEffectWidth),
                imageState.fromImageToUser(imageEffectHeight),
                null);
            break;
        default:
            graphics2d.drawImage(image,
                0,
                0,
                imageState.fromImageToUser(imageWidth),
                imageState.fromImageToUser(imageHeight),
                null);
            break;
        }
        final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
        if (toolObject.doesChangeCanvas()) {
            final BufferedImage canvas = new BufferedImage(imageWidth, imageHeight, image.getType());
            toolObject.paint(canvas.createGraphics());
            graphics2d.drawImage(canvas,
                0,
                0,
                imageState.fromImageToUser(imageWidth),
                imageState.fromImageToUser(imageHeight),
                null);
        }
        if (imageState.getImageMode() == ImageMode.EFFECT) {
            for (final AbstractParameter parameter : effectState.getCurrentParameters()) {
                if (parameter instanceof PointParameter) {
                    final PointParameter pointParameter = (PointParameter) parameter;
                    final Point point = pointParameter.getValue();
                    drawPoint(graphics2d, imageState.fromImageToUser(point.x), imageState.fromImageToUser(point.y));
                } else if (parameter instanceof PointListParameter) {
                    final PointListParameter pointListParameter = (PointListParameter) parameter;
                    for (final Point point : pointListParameter.getValue()) {
                        drawPoint(graphics2d, imageState.fromImageToUser(point.x), imageState.fromImageToUser(point.y));
                    }
                }
            }
        }
    }

    private void drawPoint(Graphics2D graphics2d, int x, int y) {
        final int l = x - POINT_SIZE / 2;
        final int t = y - POINT_SIZE / 2;
        final int s = POINT_SIZE - 1;
        graphics2d.setStroke(DEFAULT_STROKE);
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillOval(l, t, s, s);
        graphics2d.setColor(Color.BLACK);
        graphics2d.drawOval(l, t, s, s);
        graphics2d.drawLine(x, y, x, y);
    }

    public void addMainPanelListener(MainPanelListener listener) {
        listeners.add(listener);
    }

    public void updatePanelSize() {
        final BufferedImage image = imageState.getImage();
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final Dimension d = new Dimension(
            imageState.fromImageToUser(width),
            imageState.fromImageToUser(height)
        );
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
        setSize(d);
        for (final MainPanelListener listener : listeners) {
            listener.sizeChanged(new MainPanelSizeChangedEvent(width, height));
        }
    }

    public ImageState getImageState() {
        return imageState;
    }

    public ToolState getToolState() {
        return toolState;
    }

    public EffectState getEffectState() {
        return effectState;
    }

    /* Edit */

    public void selectNone() {
        final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
        if (!(toolObject instanceof AbstractSelectionTool)) {
            /* No selection tool is active, so nothing is selected right now! */
            return;
        }
        final AbstractSelectionTool ast = (AbstractSelectionTool) toolObject;
        ast.selectNone();
        repaint();
    }

    public void selectAll() {
        toolState.setSelectedTool(Tool.SELECT_RECTANGLE);
        final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
        if (!(toolObject instanceof SelectRectangleTool)) {
            /* We just selected this tool!! */
            return;
        }
        final SelectRectangleTool srt = (SelectRectangleTool) toolObject;
        srt.selectAll();
        repaint();
    }

    public void cut() {
        final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
        if (!(toolObject instanceof SelectRectangleTool)) {
            /* You have to select a rectangle first! */
            return;
        }
        final SelectRectangleTool srt = (SelectRectangleTool) toolObject;
        CLIPBOARD.setContents(new TransferableImage(srt.getSubimage()), null);
        srt.clearSelection();
        repaint();
    }

    public void copy() {
        final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
        if (!(toolObject instanceof SelectRectangleTool)) {
            /* You have to select a rectangle first! */
            return;
        }
        final SelectRectangleTool srt = (SelectRectangleTool) toolObject;
        CLIPBOARD.setContents(new TransferableImage(srt.getSubimage()), null);
    }

    public void paste() {
        final Transferable t = CLIPBOARD.getContents(null);
        if (t == null || !t.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            /* Your clipboard is empty! */
            return;
        }
        try {
            final Image imageNew = (Image) t.getTransferData(DataFlavor.imageFlavor);
            imageState.getGraphics2D().drawImage(imageNew, 0, 0, null);
            toolState.setSelectedTool(Tool.SELECT_RECTANGLE);
            final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
            if (!(toolObject instanceof SelectRectangleTool)) {
                /* We just selected this tool!! */
                return;
            }
            final SelectRectangleTool srt = (SelectRectangleTool) toolObject;
            srt.selectRectangle(0, 0, imageNew.getWidth(null), imageNew.getHeight(null));
            repaint();
        } catch (IOException | UnsupportedFlavorException e) {
            e.printStackTrace();
        }
    }

    public void clearSelection() {
        final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
        if (!(toolObject instanceof AbstractSelectionTool)) {
            /* You have to select a region first! */
            return;
        }
        final AbstractSelectionTool ast = (AbstractSelectionTool) toolObject;
        ast.clearSelection();
        repaint();
    }

    public void cropToSelection() {
        final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
        if (!(toolObject instanceof SelectRectangleTool)) {
            /* You have to select a rectangle first! */
            return;
        }
        final SelectRectangleTool srt = (SelectRectangleTool) toolObject;
        srt.cropToSelection();
        updatePanelSize();
        repaint();
    }

}
