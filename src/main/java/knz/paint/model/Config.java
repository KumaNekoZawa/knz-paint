package knz.paint.model;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Config {

    private static final int MAX_NUMBER_OF_COLOR_PALETTES = 25;

    private static final Config CONFIG = new Config();

    private Properties properties = new Properties();

    private boolean printLookAndFeelInfo = false;
    private String lookAndFeel = null;
    private Color backgroundColor = Color.GRAY;

    private int colorBarSize = 16 * 3;
    private List<File> colorPaletteFiles = new ArrayList<>();

    private int mainWindowWidth  = 800;
    private int mainWindowHeight = 600;
    private WindowMode mainWindowMode = WindowMode.PACKED;

    private int newImageWidth  = 400;
    private int newImageHeight = 300;
    private Color newImageColor = Color.WHITE;

    private boolean toolsAirbrushUseTimer = true;
    private int toolsAirbrushPixelsPerTick = 10;

    private Config() {
        super();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
            printLookAndFeelInfo = Boolean.parseBoolean(properties.getProperty("print-look-and-feel-info"));
            lookAndFeel = properties.getProperty("look-and-feel");
            backgroundColor = parseColor(properties.getProperty("background-color"));
            colorBarSize = Integer.parseInt(properties.getProperty("color-bar-size"));
            for (int i = 1; i <= MAX_NUMBER_OF_COLOR_PALETTES; i++) {
                final String filename = properties.getProperty("color-palette" + i);
                if (filename != null && !filename.isEmpty()) {
                    colorPaletteFiles.add(new File(filename));
                }
            }
            mainWindowWidth  = Integer.parseInt(properties.getProperty("main-window.width"));
            mainWindowHeight = Integer.parseInt(properties.getProperty("main-window.height"));
            mainWindowMode = WindowMode.valueOf(properties.getProperty("main-window.mode").toUpperCase());
            newImageWidth  = Integer.parseInt(properties.getProperty("new-image.width"));
            newImageHeight = Integer.parseInt(properties.getProperty("new-image.height"));
            newImageColor = parseColor(properties.getProperty("new-image.color"));
            toolsAirbrushUseTimer = Boolean.parseBoolean(properties.getProperty("tools.airbrush.use-timer"));
            toolsAirbrushPixelsPerTick = Integer.parseInt(properties.getProperty("tools.airbrush.pixels-per-tick"));
        } catch (IOException | NullPointerException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static Color parseColor(String s) {
        final String[] parts = s.split(",", -1);
        if (parts.length == 1) {
            return new Color(Integer.parseInt(parts[0]),
                             Integer.parseInt(parts[0]),
                             Integer.parseInt(parts[0]),
                             0xFF);
        } else if (parts.length == 3) {
            return new Color(Integer.parseInt(parts[0]),
                             Integer.parseInt(parts[1]),
                             Integer.parseInt(parts[2]),
                             0xFF);
        } else if (parts.length == 4) {
            return new Color(Integer.parseInt(parts[0]),
                             Integer.parseInt(parts[1]),
                             Integer.parseInt(parts[2]),
                             Integer.parseInt(parts[3]));
        } else {
            return Color.BLACK;
        }
    }

    public static Config getConfig() {
        return CONFIG;
    }

    public boolean printLookAndFeelInfo() {
        return printLookAndFeelInfo;
    }

    public String getLookAndFeel() {
        return lookAndFeel;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public int getColorBarSize() {
        return colorBarSize;
    }

    public List<File> getColorPaletteFiles() {
        return colorPaletteFiles;
    }

    public int getMainWindowWidth() {
        return mainWindowWidth;
    }

    public int getMainWindowHeight() {
        return mainWindowHeight;
    }

    public WindowMode getMainWindowMode() {
        return mainWindowMode;
    }

    public int getNewImageWidth() {
        return newImageWidth;
    }

    public int getNewImageHeight() {
        return newImageHeight;
    }

    public Color getNewImageColor() {
        return newImageColor;
    }

    public boolean getToolsAirbrushUseTimer() {
        return toolsAirbrushUseTimer;
    }

    public int getToolsAirbrushPixelsPerTick() {
        return toolsAirbrushPixelsPerTick;
    }

}
