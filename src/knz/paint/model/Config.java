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

    private Properties properties = new Properties();

    private String lookAndFeel = null;
    private Color backgroundColor = Color.GRAY;

    private int colorBarSize = 16 * 3;
    private List<File> colorPaletteFiles = new ArrayList<>();

    private boolean toolsAirbrushUseTimer = true;
    private int toolsAirbrushTickDelay = 100;
    private int toolsAirbrushPixelsPerTick = 10;

    private static final Config config = new Config();

    private Config() {
        super();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
            lookAndFeel = properties.getProperty("look-and-feel");
            backgroundColor = parseColor(properties.getProperty("background-color"));
            colorBarSize = Integer.parseInt(properties.getProperty("color-bar-size"));
            for (int i = 1; i <= MAX_NUMBER_OF_COLOR_PALETTES; i++) {
                String filename = properties.getProperty("color-palette" + i);
                if (filename != null && !filename.isEmpty()) {
                    colorPaletteFiles.add(new File(filename));
                }
            }
            toolsAirbrushUseTimer = Boolean.parseBoolean(properties.getProperty("tools.airbrush.use-timer"));
            toolsAirbrushTickDelay = Integer.parseInt(properties.getProperty("tools.airbrush.tick-delay"));
            toolsAirbrushPixelsPerTick = Integer.parseInt(properties.getProperty("tools.airbrush.pixels-per-tick"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Color parseColor(String s) {
        String[] parts = s.split(",", -1);
        if (parts.length == 1) {
            return new Color(Integer.parseInt(parts[0]),
                             Integer.parseInt(parts[0]),
                             Integer.parseInt(parts[0]));
        } else if (parts.length == 3) {
            return new Color(Integer.parseInt(parts[0]),
                             Integer.parseInt(parts[1]),
                             Integer.parseInt(parts[2]));
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
        return config;
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

    public boolean getToolsAirbrushUseTimer() {
        return toolsAirbrushUseTimer;
    }

    public int getToolsAirbrushTickDelay() {
        return toolsAirbrushTickDelay;
    }

    public int getToolsAirbrushPixelsPerTick() {
        return toolsAirbrushPixelsPerTick;
    }

}
