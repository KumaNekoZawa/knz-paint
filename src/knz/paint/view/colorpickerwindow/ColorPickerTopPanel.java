package knz.paint.view.colorpickerwindow;

import java.awt.Color;
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

    private ColorPanel colorPanelLeft = new ColorPanel();
    private JButton buttonSwapColors = new JButton("Swap");
    private ColorPanel colorPanelRight = new ColorPanel();

    public ColorPickerTopPanel() {
        super();
        colorPanelLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (final ColorPickerTopListener listener : listeners) {
                    listener.clickedLeft(new ColorPickerTopEvent(e));
                }
            }
        });
        buttonSwapColors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (final ColorPickerTopListener listener : listeners) {
                    listener.clickedSwap(new ColorPickerTopEvent(e));
                }
            }
        });
        colorPanelRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (final ColorPickerTopListener listener : listeners) {
                    listener.clickedRight(new ColorPickerTopEvent(e));
                }
            }
        });

        setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        add(colorPanelLeft, c);
        c.gridx = 1;
        c.weightx = 0;
        c.weighty = 0;
        add(buttonSwapColors, c);
        c.gridx = 2;
        c.weightx = 1;
        c.weighty = 1;
        add(colorPanelRight, c);
    }

    @Override
    public void repaint() {
        super.repaint();
        if (colorPanelLeft != null) {
            colorPanelLeft.repaint();
        }
        if (colorPanelRight != null) {
            colorPanelRight.repaint();
        }
    }

    public void addColorPickerTopListener(ColorPickerTopListener listener) {
        listeners.add(listener);
    }

    public void setColorLeft(Color color) {
        colorPanelLeft.setColor(color);
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
