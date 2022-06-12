package knz.paint.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageState {

    public static final int[] ZOOM_DIVISORS = { 32, 16, 8, 4, 2, 1, 1, 1, 1,  1,  1 };
    public static final int[] ZOOM_FACTORS  = {  1,  1, 1, 1, 1, 1, 2, 4, 8, 16, 32 };
    public static final int ZOOM_LEVELS = ZOOM_FACTORS.length;
    public static final int DEFAULT_ZOOM_LEVEL = 5;

    private BufferedImage image;
    private Graphics2D graphics2d;

    /* Temporary image that is being drawn when previewing effects. */
    private BufferedImage imageEffect;
    private int imageEffectX, imageEffectY;

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
        final Graphics2D graphics2dNew = imageNew.createGraphics();
        graphics2dNew.setColor(backgroundColor);
        graphics2dNew.fillRect(0, 0, width, height);
        graphics2dNew.drawImage(image, 0, 0, null);
        graphics2dNew.dispose();
        return imageNew;
    }

    public Graphics2D getGraphics2D() {
        return graphics2d;
    }

    public void setImage(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException();
        }
        if (graphics2d != null) {
            graphics2d.dispose();
        }
        if (image.getType() == BufferedImage.TYPE_INT_ARGB) {
            this.image = image;
        } else {
            final BufferedImage imageNew = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            final Graphics2D graphics2dNew = imageNew.createGraphics();
            graphics2dNew.drawImage(image, 0, 0, null);
            graphics2dNew.dispose();
            this.image = imageNew;
        }
        graphics2d = this.image.createGraphics();
    }

    public void newImage(int width, int height, Color backgroundColor) {
        final BufferedImage imageNew = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        if (backgroundColor != null) {
            final Graphics2D graphics2dNew = imageNew.createGraphics();
            graphics2dNew.setColor(backgroundColor);
            graphics2dNew.fillRect(0, 0, width, height);
            graphics2dNew.dispose();
        }
        setImage(imageNew);
    }

    public BufferedImage getImageEffect() {
        return imageEffect;
    }

    public int getImageEffectX() {
        return imageEffectX;
    }

    public int getImageEffectY() {
        return imageEffectY;
    }

    public void setImageEffect(BufferedImage imageEffectNew, int imageEffectNewX, int imageEffectNewY) {
        imageMode = ImageMode.EFFECT;
        imageEffect = imageEffectNew;
        imageEffectX = imageEffectNewX;
        imageEffectY = imageEffectNewY;
    }

    public void setImageEffectReset() {
        imageMode = ImageMode.TOOL;
        imageEffect = null;
        imageEffectX = 0;
        imageEffectY = 0;
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
