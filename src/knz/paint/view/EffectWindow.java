package knz.paint.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import knz.paint.model.effects.AbstractEffect;
import knz.paint.model.effects.AbstractParameter;
import knz.paint.model.effects.BooleanParameter;
import knz.paint.model.effects.DoubleParameter;
import knz.paint.model.effects.IntegerParameter;
import knz.paint.model.effects.PresetParameter;

public class EffectWindow extends JDialog {

    private Map<String, AbstractParameter> parameters = new HashMap<>();
    private Map<String, JComponent> parameterElements = new HashMap<>();

    public EffectWindow(MainWindow parent, JScrollPane scrollPane, MainPanel mainPanel, AbstractEffect effect, String title) {
        super(parent, title, true);

        for (AbstractParameter parameter : effect.getParameters()) {
            parameter.reset();
            parameters.put(parameter.getName(), parameter);
        }

        final BufferedImage image = mainPanel.getImage();
        final Rectangle rect = scrollPane.getViewport().getViewRect();
        final int zoomDivisor = mainPanel.getZoomDivisor();
        final int zoomFactor = mainPanel.getZoomFactor();
        rect.x      = zoomDivisor * rect.x      / zoomFactor;
        rect.y      = zoomDivisor * rect.y      / zoomFactor;
        rect.width  = zoomDivisor * rect.width  / zoomFactor;
        rect.height = zoomDivisor * rect.height / zoomFactor;
        final int imageTempX = Math.max(0, Math.min(rect.x, image.getWidth() - 1));
        final int imageTempY = Math.max(0, Math.min(rect.y, image.getHeight() - 1));
        final int imageTempWidth  = Math.min(rect.x + rect.width,  rect.x + image.getWidth())  - rect.x;
        final int imageTempHeight = Math.min(rect.y + rect.height, rect.y + image.getHeight()) - rect.y;
        BufferedImage imageTemp = image.getSubimage(imageTempX, imageTempY, imageTempWidth, imageTempHeight);
        /* initially draw the image with the effect once */
        mainPanel.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainPanel.setImageTempReset(true);
                EffectWindow.this.dispose();
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        for (AbstractParameter parameter : effect.getParameters()) {
            final String parameterName = parameter.getName();
            if (parameter instanceof PresetParameter) {
                final PresetParameter presetParameter = (PresetParameter) parameter;
                JComboBox<String> comboBox = new JComboBox<>(presetParameter.getPresetNames());
                comboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final String[] parameterToSetNames = presetParameter.getParameterNames();
                        final Object[] values = presetParameter.getPresets().get(comboBox.getSelectedIndex()).getValues();
                        for (int i = 0; i < parameterToSetNames.length; i++) {
                            final String parameterToSetName = parameterToSetNames[i];
                            final Object value = values[i];
                            final AbstractParameter parameterToSet = parameters.get(parameterToSetName);
                            final JComponent parameterToSetElement = parameterElements.get(parameterToSetName);
                            if (parameterToSet instanceof BooleanParameter && parameterToSetElement instanceof JCheckBox) {
                                final BooleanParameter booleanParameterToSet = (BooleanParameter) parameterToSet;
                                final JCheckBox checkBox = (JCheckBox) parameterToSetElement;
                                final boolean booleanValue = (boolean) value;
                                booleanParameterToSet.setValue(booleanValue);
                                checkBox.setSelected(booleanValue);
                            } else if (parameterToSet instanceof IntegerParameter && parameterToSetElement instanceof JSlider) {
                                final IntegerParameter integerParameterToSet = (IntegerParameter) parameterToSet;
                                final JSlider slider = (JSlider) parameterToSetElement;
                                final int intValue = (int) value;
                                integerParameterToSet.setValue(intValue);
                                slider.setValue(intValue);
                            } else if (parameterToSet instanceof DoubleParameter && parameterToSetElement instanceof JSlider) {
                                final DoubleParameter doubleParameterToSet = (DoubleParameter) parameterToSet;
                                final JSlider slider = (JSlider) parameterToSetElement;
                                final double doubleValue = (double) value;
                                doubleParameterToSet.setValue(doubleValue);
                                slider.setValue((int) (doubleParameterToSet.getResolution() * doubleValue));
                            } else {
                                throw new AssertionError();
                            }
                        }
                        mainPanel.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);
                    }
                });
                parameterElements.put(parameterName, comboBox);
                add(comboBox, c);
                c.gridy++;
            } else if (parameter instanceof BooleanParameter) {
                final BooleanParameter booleanParameter = (BooleanParameter) parameter;
                JCheckBox checkBox = new JCheckBox(parameterName);
                checkBox.setSelected(booleanParameter.getDef());
                checkBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        booleanParameter.setValue(checkBox.isSelected());
                        mainPanel.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);
                    }
                });
                parameterElements.put(parameterName, checkBox);
                add(checkBox, c);
                c.gridy++;
            } else {
                JLabel label = new JLabel(parameter.getLabelText());
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                add(label, c);
                c.gridy++;
                if (parameter instanceof IntegerParameter) {
                    final IntegerParameter integerParameter = (IntegerParameter) parameter;
                    JSlider slider = new JSlider(JSlider.HORIZONTAL,
                        integerParameter.getMin(),
                        integerParameter.getMax(),
                        integerParameter.getDef());
                    slider.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            integerParameter.setValue(slider.getValue());
                            label.setText(parameter.getLabelText());
                            mainPanel.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);
                        }
                    });
                    parameterElements.put(parameterName, slider);
                    add(slider, c);
                    c.gridy++;
                } else if (parameter instanceof DoubleParameter) {
                    final DoubleParameter doubleParameter = (DoubleParameter) parameter;
                    JSlider slider = new JSlider(JSlider.HORIZONTAL,
                        (int) (doubleParameter.getResolution() * doubleParameter.getMin()),
                        (int) (doubleParameter.getResolution() * doubleParameter.getMax()),
                        (int) (doubleParameter.getResolution() * doubleParameter.getDef()));
                    slider.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            doubleParameter.setValue(slider.getValue() / doubleParameter.getResolution());
                            label.setText(parameter.getLabelText());
                            mainPanel.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);
                        }
                    });
                    parameterElements.put(parameterName, slider);
                    add(slider, c);
                    c.gridy++;
                } else {
                    throw new AssertionError();
                }
            }
        }
        JButton buttonOkay = new JButton("Okay");
        buttonOkay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setImageTempReset(false);
                mainPanel.setImage(effect.apply(image));
                EffectWindow.this.dispose();
            }
        });
        add(buttonOkay, c);

        setAlwaysOnTop(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
