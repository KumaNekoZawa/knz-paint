package knz.paint.view.plainpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import knz.paint.model.Config;

public class GradientSliderPanel extends JPanel {

    public static final int ACTION_LISTENER_ID = 2004;

    private List<ActionListener> listeners = new ArrayList<>();

    private JLabel label = new JLabel();
    private GradientSliderSubPanel gradientSliderSubPanel;
    private JTextField textField = new JTextField();

    private Function<Color, Integer> valueFunction;
    private BiFunction<Color, Integer, Color> colorFunction;

    public GradientSliderPanel(String labelStr, Function<Color, Integer> valueFunction, BiFunction<Color, Integer, Color> colorFunction) {
        super();
        this.valueFunction = valueFunction;
        this.colorFunction = colorFunction;

        gradientSliderSubPanel = new GradientSliderSubPanel(colorFunction);
        gradientSliderSubPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ActionListener listener : listeners) {
                    listener.actionPerformed(new ActionEvent(e, ACTION_LISTENER_ID, GradientSliderPanel.class.getSimpleName()));
                }
            }
        });

        label.setText(labelStr);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                return ((JTextField) input).getText().matches("^[0-9A-Fa-f][0-9A-Fa-f]?$");
            }
        });
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                gradientSliderSubPanel.setSelectedValue(Integer.parseInt(textField.getText(), 16));
                for (ActionListener listener : listeners) {
                    listener.actionPerformed(new ActionEvent(e, ACTION_LISTENER_ID, GradientSliderPanel.class.getSimpleName()));
                }
            }
        });
        setLayout(new BorderLayout());
        add(label, BorderLayout.PAGE_START);
        add(gradientSliderSubPanel, BorderLayout.CENTER);
        add(textField, BorderLayout.PAGE_END);

        setBackground(Config.getConfig().getBackgroundColor());
    }

    @Override
    public void repaint() {
        super.repaint();
        if (gradientSliderSubPanel != null) {
            gradientSliderSubPanel.repaint();
        }
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void update(Color color) {
        gradientSliderSubPanel.setBaseColor(color);
        gradientSliderSubPanel.setSelectedValue(valueFunction.apply(color));
        textField.setText(String.format("%02X", gradientSliderSubPanel.getSelectedValue()));
    }

    public int getSelectedValue() {
        return gradientSliderSubPanel.getSelectedValue();
    }

    public Function<Color, Integer> getValueFunction() {
        return valueFunction;
    }

    public BiFunction<Color, Integer, Color> getColorFunction() {
        return colorFunction;
    }

}
