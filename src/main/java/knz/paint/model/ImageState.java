package knz.paint.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/* Corresponds to the "View" menu in the MainWindow. */
public class ImageState {

    public static final int[] ZOOM_DIVISORS = { 32, 16, 8, 4, 2, 1, 1, 1, 1,  1,  1 };
    public static final int[] ZOOM_FACTORS  = {  1,  1, 1, 1, 1, 1, 2, 4, 8, 16, 32 };
    public static final int ZOOM_LEVELS = ZOOM_FACTORS.length;
    public static final int DEFAULT_ZOOM_LEVEL = 5;

    private BufferedImage image;
    private Graphics2D graphics2D;

    /* Temporary image that is being drawn when previewing effects. */
    private BufferedImage imageTemp;
    private int imageTempX, imageTempY;

    private ImageMode imageMode = ImageMode.TOOL;
    private boolean changedTillLastSave = false;

    /* View */
    private int zoomLevel = DEFAULT_ZOOM_LEVEL;

    public ImageState() {
        super();
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getImageWithoutAlpha(Color backgroundColor) {
        final int width  = image.getWidth();
        final int height = image.getHeight();
        final BufferedImage imageNew = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2DNew = imageNew.createGraphics();
        graphics2DNew.setColor(backgroundColor);
        graphics2DNew.fillRect(0, 0, width, height);
        graphics2DNew.drawImage(image, 0, 0, null);
        graphics2DNew.dispose();
        return imageNew;
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    public void setImage(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException();
        }
        if (graphics2D != null) {
            graphics2D.dispose();
        }
        if (image.getType() == BufferedImage.TYPE_INT_ARGB) {
            this.image = image;
        } else {
            final BufferedImage imageNew = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            final Graphics2D graphics2DNew = imageNew.createGraphics();
            graphics2DNew.drawImage(image, 0, 0, null);
            graphics2DNew.dispose();
            this.image = imageNew;
        }
        graphics2D = this.image.createGraphics();
    }

    public void newImage(int width, int height, Color backgroundColor) {
        final BufferedImage imageNew = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        if (backgroundColor != null) {
            final Graphics2D graphics2DNew = imageNew.createGraphics();
            graphics2DNew.setColor(backgroundColor);
            graphics2DNew.fillRect(0, 0, width, height);
            graphics2DNew.dispose();
        }
        setImage(imageNew);
    }

    public BufferedImage getImageTemp() {
        return imageTemp;
    }

    public int getImageTempX() {
        return imageTempX;
    }

    public int getImageTempY() {
        return imageTempY;
    }

    public void setImageTemp(BufferedImage imageTempNew, int imageTempNewX, int imageTempNewY) {
        imageMode = ImageMode.EFFECT;
        imageTemp = imageTempNew;
        imageTempX = imageTempNewX;
        imageTempY = imageTempNewY;
    }

    public void setImageTempReset() {
        imageMode = ImageMode.TOOL;
        imageTemp = null;
        imageTempX = 0;
        imageTempY = 0;
    }

    public ImageMode getImageMode() {
        return imageMode;
    }

    public void setImageMode(ImageMode imageMode) {
        this.imageMode = imageMode;
    }

    public boolean hasChangedTillLastSave() {
        return changedTillLastSave;
    }

    public void setChangedTillLastSave(boolean changedTillLastSave) {
        this.changedTillLastSave = changedTillLastSave;
    }

    /* View */

    public int getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public boolean increaseZoomLevel() {
        if (zoomLevel < ZOOM_LEVELS - 1) {
            zoomLevel++;
            return true;
        }
        return false;
    }

    public boolean decreaseZoomLevel() {
        if (zoomLevel > 0) {
            zoomLevel--;
            return true;
        }
        return false;
    }

    public int fromUserToImage(int coord) {
        return coord * ZOOM_DIVISORS[zoomLevel] / ZOOM_FACTORS[zoomLevel];
    }

    public int fromImageToUser(int coord) {
        return coord * ZOOM_FACTORS[zoomLevel] / ZOOM_DIVISORS[zoomLevel];
    }

}