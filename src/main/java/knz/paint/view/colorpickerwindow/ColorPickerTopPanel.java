package knz.paint.view.colorpickerwindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

import knz.paint.view.plainpanels.ColorPanel;

public class ColorPickerTopPanel extends JPanel {

    private List<ColorPickerTopListener> listeners = new ArrayList<>();

    private ColorPanel colorPanelLeft;
    private JButton buttonSwapColors;
    private ColorPanel colorPanelRight;

    private int numberOfColorsToChoose;

    public ColorPickerTopPanel(int numberOfColorsToChoose) {
        super();
        if (!(1 <= numberOfColorsToChoose && numberOfColorsToChoose <= 2)) {
            throw new IllegalArgumentException();
        }
        this.numberOfColorsToChoose = numberOfColorsToChoose;

        final Dimension d = new Dimension(40, 40);

        if (numberOfColorsToChoose >= 1) {
            colorPanelLeft = new ColorPanel();
            colorPanelLeft.setMaximumSize(d);
            colorPanelLeft.setMinimumSize(d);
            colorPanelLeft.setPreferredSize(d);
            colorPanelLeft.setSize(d);
            colorPanelLeft.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (final ColorPickerTopListener listener : listeners) {
                        listener.clickedLeft(new ColorPickerTopEvent(e));
                    }
                }
            });
        }
        if (numberOfColorsToChoose >= 2) {
            buttonSwapColors = new JButton("Swap");
            buttonSwapColors.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (final ColorPickerTopListener listener : listeners) {
                        listener.clickedSwap(new ColorPickerTopEvent(e));
                    }
                }
            });
            colorPanelRight = new ColorPanel();
            colorPanelRight.setMaximumSize(d);
            colorPanelRight.setMinimumSize(d);
            colorPanelRight.setPreferredSize(d);
            colorPanelRight.setSize(d);
            colorPanelRight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (final ColorPickerTopListener listener : listeners) {
                        listener.clickedRight(new ColorPickerTopEvent(e));
                    }
                }
            });
        }

        setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        if (numberOfColorsToChoose >= 1) {
            c.gridx = 0;
            c.weightx = 1;
            c.weighty = 1;
            add(colorPanelLeft, c);
        }
        if (numberOfColorsToChoose >= 2) {
            c.gridx = 1;
            c.weightx = 0;
            c.weighty = 0;
            add(buttonSwapColors, c);
            c.gridx = 2;
            c.weightx = 1;
            c.weighty = 1;
            add(colorPanelRight, c);
        }
    }

    @Override
    public void repaint() {
        super.repaint();
        if (numberOfColorsToChoose >= 1 && colorPanelLeft != null) {
            colorPanelLeft.repaint();
        }
        if (numberOfColorsToChoose >= 2 && colorPanelRight != null) {
            colorPanelRight.repaint();
        }
    }

    public void addColorPickerTopListener(ColorPickerTopListener listener) {
        listeners.add(listener);
    }

    public void swapColors() {
        final Color colorLeftOld  = colorPanelLeft.getColor();
        final Color colorRightOld = colorPanelRight.getColor();
        colorPanelLeft.setColor(colorRightOld);
        colorPanelRight.setColor(colorLeftOld);
    }

    public Color getColorLeft() {
        return colorPanelLeft.getColor();
    }

    public void setColorLeft(Color color) {
        colorPanelLeft.setColor(color);
    }

    public Color getColorRight() {
        return colorPanelRight.getColor();
    }

    public void setColorRight(Color color) {
        colorPanelRight.setColor(color);
    }

    public void setHighlightLeft(boolean highlightLeft) {
        colorPanelLeft.setHighlighted(highlightLeft);
    }

    public void setHighlightRight(boolean highlightRight) {
        colorPanelRight.setHighlighted(highlightRight);
    }

}
