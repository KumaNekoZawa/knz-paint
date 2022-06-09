package knz.paint.view.colorpickerwindow;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

import knz.paint.model.tools.ToolState;
import knz.paint.view.plainpanels.GradientSliderPanel;

public class ColorPickerWindow extends JFrame {

    private enum ColorPickerMode {
        PRIMARY,
        SECONDARY,
    }

    private ColorPickerTopPanel colorPickerTopPanel = new ColorPickerTopPanel();
    private GradientSliderPanel[] gradientSliderPanels = new GradientSliderPanel[] {
        new GradientSliderPanel("Gray",
            c -> (c.getRed() + c.getGreen() + c.getBlue()) / 3,
            (c, y) -> new Color(y, y, y, c.getAlpha())),
        new GradientSliderPanel("Red",
            c -> c.getRed(),
            (c, y) -> new Color(y, c.getGreen(), c.getBlue(), c.getAlpha())),
        new GradientSliderPanel("Green",
            c -> c.getGreen(),
            (c, y) -> new Color(c.getRed(), y, c.getBlue(), c.getAlpha())),
        new GradientSliderPanel("Blue",
            c -> c.getBlue(),
            (c, y) -> new Color(c.getRed(), c.getGreen(), y, c.getAlpha())),
        new GradientSliderPanel("Hue",
            c -> {
                final int r = c.getRed();
                final int g = c.getGreen();
                final int b = c.getBlue();
                final float[] hsb = Color.RGBtoHSB(r, g, b, null);
                return (int) (0xFF * hsb[0]);
            },
            (c, y) -> {
                final int r = c.getRed();
                final int g = c.getGreen();
                final int b = c.getBlue();
                final int a = c.getAlpha();
                final float[] hsb = Color.RGBtoHSB(r, g, b, null);
                final float hsb_h = y / 255f;
                final int rgb = Color.HSBtoRGB(hsb_h, hsb[1], hsb[2]);
                final int rgba = (a << 24) | (rgb & 0xFFFFFF);
                return new Color(rgba, true);
            }),
        new GradientSliderPanel("Sat.",
            c -> {
                final int r = c.getRed();
                final int g = c.getGreen();
                final int b = c.getBlue();
                final float[] hsb = Color.RGBtoHSB(r, g, b, null);
                return (int) (0xFF * hsb[1]);
            },
            (c, y) -> {
                final int r = c.getRed();
                final int g = c.getGreen();
                final int b = c.getBlue();
                final int a = c.getAlpha();
                final float[] hsb = Color.RGBtoHSB(r, g, b, null);
                final float hsb_s = y / 255f;
                final int rgb = Color.HSBtoRGB(hsb[0], hsb_s, hsb[2]);
                final int rgba = (a << 24) | (rgb & 0xFFFFFF);
                return new Color(rgba, true);
            }),
        new GradientSliderPanel("Br.",
            c -> {
                final int r = c.getRed();
                final int g = c.getGreen();
                final int b = c.getBlue();
                final float[] hsb = Color.RGBtoHSB(r, g, b, null);
                return (int) (0xFF * hsb[2]);
            },
            (c, y) -> {
                final int r = c.getRed();
                final int g = c.getGreen();
                final int b = c.getBlue();
                final int a = c.getAlpha();
                final float[] hsb = Color.RGBtoHSB(r, g, b, null);
                final float hsb_b = y / 255f;
                final int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb_b);
                final int rgba = (a << 24) | (rgb & 0xFFFFFF);
                return new Color(rgba, true);
            }),
        new GradientSliderPanel("Alpha",
            c -> c.getAlpha(),
            (c, y) -> new Color(c.getRed(), c.getGreen(), c.getBlue(), y)),
    };

    private ColorPickerMode mode = ColorPickerMode.PRIMARY;

    private ToolState toolState;

    public ColorPickerWindow(ToolState toolState) {
        super("Color picker...");
        this.toolState = toolState;

        colorPickerTopPanel.addColorPickerTopListener(new ColorPickerTopListener() {
            @Override
            public void clickedLeft(ColorPickerTopEvent e) {
                mode = ColorPickerMode.PRIMARY;
                updateAll();
            }

            @Override
            public void clickedSwap(ColorPickerTopEvent e) {
                toolState.swapColors();
                updateAll();
            }

            @Override
            public void clickedRight(ColorPickerTopEvent e) {
                mode = ColorPickerMode.SECONDARY;
                updateAll();
            }
        });

        setLayout(new GridBagLayout());

        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = gradientSliderPanels.length;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        add(colorPickerTopPanel, c);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        for (final GradientSliderPanel gradientSliderPanel : gradientSliderPanels) {
            gradientSliderPanel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final Color colorOld;
                    switch (mode) {
                    case PRIMARY:
                        colorOld = toolState.getColorPrimary();
                        break;
                    case SECONDARY:
                        colorOld = toolState.getColorSecondary();
                        break;
                    default:
                        throw new AssertionError();
                    }
                    final Color colorNew = gradientSliderPanel.getColorFunction().apply(colorOld, gradientSliderPanel.getSelectedValue());
                    switch (mode) {
                    case PRIMARY:
                        toolState.setColorPrimary(colorNew);
                        break;
                    case SECONDARY:
                        toolState.setColorSecondary(colorNew);
                        break;
                    default:
                        throw new AssertionError();
                    }
                    updateAll();
                }
            });
            add(gradientSliderPanel, c);
            c.gridx++;
        }

        setAlwaysOnTop(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        updateAll();

        setVisible(true);
    }

    public void updateAll() {
        final Color color;
        switch (mode) {
        case PRIMARY:
            color = toolState.getColorPrimary();
            colorPickerTopPanel.setHighlightLeft(true);
            colorPickerTopPanel.setHighlightRight(false);
            break;
        case SECONDARY:
            color = toolState.getColorSecondary();
            colorPickerTopPanel.setHighlightLeft(false);
            colorPickerTopPanel.setHighlightRight(true);
            break;
        default:
            throw new AssertionError();
        }
        colorPickerTopPanel.setColorLeft(toolState.getColorPrimary());
        colorPickerTopPanel.setColorRight(toolState.getColorSecondary());
        colorPickerTopPanel.repaint();
        for (final GradientSliderPanel gradientSliderPanel : gradientSliderPanels) {
            gradientSliderPanel.update(color);
            gradientSliderPanel.repaint();
        }
    }

}
