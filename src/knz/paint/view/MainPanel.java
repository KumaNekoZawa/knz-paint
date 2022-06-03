package knz.paint.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import javax.swing.Timer;

import knz.paint.model.Config;
import knz.paint.model.ImageState;
import knz.paint.model.Tool;
import knz.paint.model.ToolState;
import knz.paint.tools.AbstractSelectionTool;
import knz.paint.tools.AbstractTool;
import knz.paint.tools.SelectRectangleTool;

public class MainPanel extends JPanel {

    private static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();

    private List<MainPanelListener> listeners = new ArrayList<>();

    private Timer timer = new Timer(40, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
            toolObject.timerEvent(imageState.getGraphics2D(), e);
            postEvent(toolObject);
        }
    });

    private ToolState toolState;
    private ImageState imageState = new ImageState();

    public MainPanel(ToolState toolState) {
        super();
        this.toolState = toolState;

        for (final Tool tool : Tool.values()) {
            tool.getToolObject().setImageState(imageState);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
                setCursor(toolObject.getCursor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(AbstractTool.CURSOR_DEFAULT);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
                toolObject.mousePressed(imageState.getGraphics2D(), e);
                postEvent(toolObject);
                if (toolObject.usesTimer()) {
                    timer.start();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
                if (toolObject.usesTimer()) {
                    timer.stop();
                }
                toolObject.mouseReleased(imageState.getGraphics2D(), e);
                postEvent(toolObject);
                if (toolObject.doesChangeImage()) {
                    imageState.setChangedTillLastSave(true);
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
                toolObject.mouseDragged(imageState.getGraphics2D(), e);
                postEvent(toolObject);
                updateStatusBar(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                updateStatusBar(e);
            }

            private void updateStatusBar(MouseEvent e) {
                final BufferedImage image = imageState.getImage();
                final int zoomDivisor = imageState.getZoomDivisor();
                final int zoomFactor  = imageState.getZoomFactor();
                final int x = zoomDivisor * e.getX() / zoomFactor;
                final int y = zoomDivisor * e.getY() / zoomFactor;
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

    private void postEvent(AbstractTool toolObject) {
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        final Graphics2D graphics2d = (Graphics2D) g;
        final BufferedImage imageTemp = imageState.getImageTemp();
        final int zoomFactor  = imageState.getZoomFactor();
        final int zoomDivisor = imageState.getZoomDivisor();
        if (imageTemp == null) {
            final BufferedImage image = imageState.getImage();
            final int imageWidth  = image.getWidth();
            final int imageHeight = image.getHeight();
            graphics2d.drawImage(image,
                0,
                0,
                zoomFactor * imageWidth  / zoomDivisor,
                zoomFactor * imageHeight / zoomDivisor,
                null);
            final AbstractTool toolObject = toolState.getSelectedTool().getToolObject();
            if (toolObject.doesChangeCanvas()) {
                final BufferedImage canvas = new BufferedImage(imageWidth, imageHeight, image.getType());
                toolObject.paint(canvas.createGraphics());
                graphics2d.drawImage(canvas,
                    0,
                    0,
                    zoomFactor * imageWidth  / zoomDivisor,
                    zoomFactor * imageHeight / zoomDivisor,
                    null);
            }
        } else {
            final int imageTempX = imageState.getImageTempX();
            final int imageTempY = imageState.getImageTempY();
            final int imageTempWidth  = imageTemp.getWidth();
            final int imageTempHeight = imageTemp.getHeight();
            graphics2d.drawImage(imageTemp,
                zoomFactor * imageTempX      / zoomDivisor,
                zoomFactor * imageTempY      / zoomDivisor,
                zoomFactor * imageTempWidth  / zoomDivisor,
                zoomFactor * imageTempHeight / zoomDivisor,
                null);
        }
    }

    public void addMainPanelListener(MainPanelListener listener) {
        listeners.add(listener);
    }

    public void updatePanelSize() {
        final BufferedImage image = imageState.getImage();
        final int zoomFactor  = imageState.getZoomFactor();
        final int zoomDivisor = imageState.getZoomDivisor();
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final Dimension d = new Dimension(
            zoomFactor * width  / zoomDivisor,
            zoomFactor * height / zoomDivisor
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
