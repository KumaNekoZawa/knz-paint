package knz.paint.view.effectwindow;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import knz.paint.model.ImageState;
import knz.paint.model.effects.parameter.AbstractParameter;
import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.BorderFillStrategyParameter;
import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.IntegerParameter;
import knz.paint.model.effects.parameter.PresetParameter;
import knz.paint.model.effects.specific.AbstractEffect;
import knz.paint.view.mainwindow.MainPanel;

public class EffectWindow extends JDialog {

    private Map<String, AbstractParameter> parameters = new HashMap<>();
    private Map<String, JComponent> parameterElements = new HashMap<>();

    public EffectWindow(JFrame parent, JScrollPane scrollPane, MainPanel mainPanel, AbstractEffect effect, String title) {
        super(parent, title, true);

        for (final AbstractParameter parameter : effect.getParameters()) {
            parameter.reset();
            parameters.put(parameter.getName(), parameter);
        }

        final ImageState imageState = mainPanel.getImageState();
        final BufferedImage image = imageState.getImage();
        final Rectangle rect = scrollPane.getViewport().getViewRect();
        final int zoomDivisor = imageState.getZoomDivisor();
        final int zoomFactor  = imageState.getZoomFactor();
        rect.x      = zoomDivisor * rect.x      / zoomFactor;
        rect.y      = zoomDivisor * rect.y      / zoomFactor;
        rect.width  = zoomDivisor * rect.width  / zoomFactor;
        rect.height = zoomDivisor * rect.height / zoomFactor;
        final int imageTempX = Math.max(0, Math.min(rect.x, image.getWidth()  - 1));
        final int imageTempY = Math.max(0, Math.min(rect.y, image.getHeight() - 1));
        final int imageTempWidth  = Math.min(rect.x + rect.width,  rect.x + image.getWidth())  - rect.x;
        final int imageTempHeight = Math.min(rect.y + rect.height, rect.y + image.getHeight()) - rect.y;
        final BufferedImage imageTemp = image.getSubimage(imageTempX, imageTempY, imageTempWidth, imageTempHeight);
        /* initially draw the image with the effect once */
        imageState.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);
        mainPanel.repaint();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                imageState.setImageTempReset();
                mainPanel.repaint();
                EffectWindow.this.dispose();
            }
        });

        setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        for (final AbstractParameter parameter : effect.getParameters()) {
            final String parameterName = parameter.getName();
            if (parameter instanceof PresetParameter) {
                final PresetParameter presetParameter = (PresetParameter) parameter;
                final JComboBox<String> comboBox = new JComboBox<>(presetParameter.getPresetNames());
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
                            if (parameterToSet instanceof BorderFillStrategyParameter && parameterToSetElement instanceof JComboBox) {
                                final BorderFillStrategyParameter borderFillStrategyParameterToSet = (BorderFillStrategyParameter) parameterToSet;
                                final JComboBox<?> comboBox = (JComboBox<?>) parameterToSetElement;
                                final BorderFillStrategy borderFillStrategyValue = (BorderFillStrategy) value;
                                borderFillStrategyParameterToSet.setValue(borderFillStrategyValue);
                                comboBox.setSelectedIndex(borderFillStrategyValue.ordinal());
                            } else if (parameterToSet instanceof BooleanParameter && parameterToSetElement instanceof JCheckBox) {
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
                        imageState.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);
                        mainPanel.repaint();
                    }
                });
                parameterElements.put(parameterName, comboBox);
                add(comboBox, c);
                c.gridy++;
            } else if (parameter instanceof BorderFillStrategyParameter) {
                final BorderFillStrategyParameter borderFillStrategyParameter = (BorderFillStrategyParameter) parameter;
                final JComboBox<String> comboBox = new JComboBox<>(BorderFillStrategy.getNames());
                comboBox.setSelectedIndex(borderFillStrategyParameter.getDef().ordinal());
                comboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        borderFillStrategyParameter.setValue(BorderFillStrategy.values()[comboBox.getSelectedIndex()]);
                        imageState.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);
                        mainPanel.repaint();
                    }
                });
                parameterElements.put(parameterName, comboBox);
                add(comboBox, c);
                c.gridy++;
            } else if (parameter instanceof BooleanParameter) {
                final BooleanParameter booleanParameter = (BooleanParameter) parameter;
                final JCheckBox checkBox = new JCheckBox(parameterName);
                checkBox.setSelected(booleanParameter.getDef());
                checkBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        booleanParameter.setValue(checkBox.isSelected());
                        imageState.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);
                        mainPanel.repaint();
                    }
                });
                parameterElements.put(parameterName, checkBox);
                add(checkBox, c);
                c.gridy++;
            } else {
                final JLabel label = new JLabel(parameter.getLabelText());
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                add(label, c);
                c.gridy++;
                if (parameter instanceof IntegerParameter) {
                    final IntegerParameter integerParameter = (IntegerParameter) parameter;
                    final JSlider slider = new JSlider(JSlider.HORIZONTAL,
                        integerParameter.getMin(),
                        integerParameter.getMax(),
                        integerParameter.getDef()
                    );
                    slider.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            integerParameter.setValue(slider.getValue());
                            label.setText(parameter.getLabelText());
                            imageState.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);
                            mainPanel.repaint();
                        }
                    });
                    parameterElements.put(parameterName, slider);
                    add(slider, c);
                    c.gridy++;
                } else if (parameter instanceof DoubleParameter) {
                    final DoubleParameter doubleParameter = (DoubleParameter) parameter;
                    final JSlider slider = new JSlider(JSlider.HORIZONTAL,
                        (int) (doubleParameter.getResolution() * doubleParameter.getMin()),
                        (int) (doubleParameter.getResolution() * doubleParameter.getMax()),
                        (int) (doubleParameter.getResolution() * doubleParameter.getDef())
                    );
                    slider.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            doubleParameter.setValue(slider.getValue() / doubleParameter.getResolution());
                            label.setText(parameter.getLabelText());
                            imageState.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);
                            mainPanel.repaint();
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
        final JButton buttonOkay = new JButton("Okay");
        buttonOkay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageState.setImageTempReset();
                imageState.setImage(effect.apply(image));
                mainPanel.repaint();
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
