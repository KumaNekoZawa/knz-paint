package knz.paint.view.effectwindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
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

import knz.paint.model.ImageState;
import knz.paint.model.effects.EffectState;
import knz.paint.model.effects.parameter.AbstractLabeledParameter;
import knz.paint.model.effects.parameter.AbstractParameter;
import knz.paint.model.effects.parameter.BooleanParameter;
import knz.paint.model.effects.parameter.BorderFillStrategy;
import knz.paint.model.effects.parameter.BorderFillStrategyParameter;
import knz.paint.model.effects.parameter.ColorParameter;
import knz.paint.model.effects.parameter.DoubleParameter;
import knz.paint.model.effects.parameter.IntegerParameter;
import knz.paint.model.effects.parameter.PointEvent;
import knz.paint.model.effects.parameter.PointListParameter;
import knz.paint.model.effects.parameter.PointListener;
import knz.paint.model.effects.parameter.PointParameter;
import knz.paint.model.effects.parameter.PresetParameter;
import knz.paint.model.effects.specific.AbstractEffect;
import knz.paint.view.colorpickerwindow.ColorPickerEvent;
import knz.paint.view.colorpickerwindow.ColorPickerListener;
import knz.paint.view.colorpickerwindow.ColorPickerWindow;
import knz.paint.view.mainwindow.MainPanel;
import knz.paint.view.plainpanels.ColorPanel;
import knz.paint.view.plainpanels.ColorPanelEvent;
import knz.paint.view.plainpanels.ColorPanelListener;

public class EffectWindow extends JDialog {

    private Map<String, AbstractParameter> parameters = new HashMap<>();
    private Map<String, JComponent> parameterElements = new HashMap<>();

    public EffectWindow(Window parent, JScrollPane scrollPane, MainPanel mainPanel, AbstractEffect effect, String title) {
        super(parent, title);

        final ImageState  imageState  = mainPanel.getImageState();
        final EffectState effectState = mainPanel.getEffectState();
        effectState.setCurrentParameters(effect.getParameters());
        for (final AbstractParameter parameter : effect.getParameters()) {
            parameters.put(parameter.getName(), parameter);
        }

        final BufferedImage image = imageState.getImage();
        final Rectangle rect = scrollPane.getViewport().getViewRect();
        rect.x      = imageState.fromUserToImage(rect.x);
        rect.y      = imageState.fromUserToImage(rect.y);
        rect.width  = imageState.fromUserToImage(rect.width);
        rect.height = imageState.fromUserToImage(rect.height);
        final int imageEffectX = Math.max(0, Math.min(rect.x, image.getWidth()  - 1));
        final int imageEffectY = Math.max(0, Math.min(rect.y, image.getHeight() - 1));
        final int imageEffectWidth  = Math.min(rect.x + rect.width,  rect.x + image.getWidth())  - rect.x;
        final int imageEffectHeight = Math.min(rect.y + rect.height, rect.y + image.getHeight()) - rect.y;
        final BufferedImage imageEffect = image.getSubimage(imageEffectX, imageEffectY, imageEffectWidth, imageEffectHeight);
        /* initially draw the image with the effect once */
        imageState.setImageEffect(effect.apply(imageEffect), imageEffectX, imageEffectY);
        mainPanel.repaint();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                imageState.setImageEffectReset();
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
            if (parameter instanceof AbstractLabeledParameter) {
                final AbstractLabeledParameter labeledParameter = (AbstractLabeledParameter) parameter;
                final JLabel label = new JLabel(labeledParameter.getLabelText());
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
                            label.setText(labeledParameter.getLabelText());
                            imageState.setImageEffect(effect.apply(imageEffect), imageEffectX, imageEffectY);
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
                            label.setText(labeledParameter.getLabelText());
                            imageState.setImageEffect(effect.apply(imageEffect), imageEffectX, imageEffectY);
                            mainPanel.repaint();
                        }
                    });
                    parameterElements.put(parameterName, slider);
                    add(slider, c);
                    c.gridy++;
                } else if (parameter instanceof ColorParameter) {
                    final ColorParameter colorParameter = (ColorParameter) parameter;
                    final ColorPanel colorPanel = new ColorPanel();
                    final Dimension d = new Dimension(40, 40);
                    colorPanel.setMaximumSize(d);
                    colorPanel.setMinimumSize(d);
                    colorPanel.setSize(d);
                    colorPanel.setPreferredSize(d);
                    colorPanel.setColor(colorParameter.getDef());
                    colorPanel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            final ColorPickerWindow colorPickerWindow = new ColorPickerWindow(EffectWindow.this, 1);
                            colorPickerWindow.setColorLeft(colorPanel.getColor());
                            colorPickerWindow.addColorPickerListener(new ColorPickerListener() {
                                @Override
                                public void colorChangedLeft(ColorPickerEvent e) {
                                    final Color color = e.getColor();
                                    colorPanel.setColor(color);
                                    colorPanel.repaint();
                                }

                                @Override
                                public void colorChangedRight(ColorPickerEvent e) {
                                    throw new AssertionError();
                                }
                            });
                        }
                    });
                    colorPanel.addColorPanelListener(new ColorPanelListener() {
                        @Override
                        public void colorChanged(ColorPanelEvent e) {
                            final Color color = e.getColor();
                            colorParameter.setValue(color);
                            label.setText(labeledParameter.getLabelText());
                            imageState.setImageEffect(effect.apply(imageEffect), imageEffectX, imageEffectY);
                            mainPanel.repaint();
                        }
                    });
                    parameterElements.put(parameterName, colorPanel);
                    add(colorPanel, c);
                    c.gridy++;
                } else if (parameter instanceof PointParameter) {
                    final PointParameter pointParameter = (PointParameter) parameter;
                    pointParameter.addPointListener(new PointListener() {
                        @Override
                        public void pointChanged(PointEvent e) {
                            label.setText(labeledParameter.getLabelText());
                            imageState.setImageEffect(effect.apply(imageEffect), imageEffectX, imageEffectY);
                            mainPanel.repaint();
                        }
                    });
                } else if (parameter instanceof PointListParameter) {
                    final PointListParameter pointListParameter = (PointListParameter) parameter;
                    pointListParameter.addPointListener(new PointListener() {
                        @Override
                        public void pointChanged(PointEvent e) {
                            // FIXME should not change unless + or - are pressed
                            label.setText(labeledParameter.getLabelText());
                            imageState.setImageEffect(effect.apply(imageEffect), imageEffectX, imageEffectY);
                            mainPanel.repaint();
                        }
                    });
                    // FIXME maybe show an + and an - button?
                } else {
                    throw new AssertionError();
                }
            } else if (parameter instanceof PresetParameter) {
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
                                final double doubleValue = value instanceof Integer ? (double) (int) value : (double) value;
                                doubleParameterToSet.setValue(doubleValue);
                                slider.setValue((int) (doubleParameterToSet.getResolution() * doubleValue));
                            } else if (parameterToSet instanceof ColorParameter && parameterToSetElement instanceof ColorPanel) {
                                final ColorParameter colorParameterToSet = (ColorParameter) parameterToSet;
                                final ColorPanel colorPanel = (ColorPanel) parameterToSetElement;
                                final Color colorValue = (Color) value;
                                colorParameterToSet.setValue(colorValue);
                                colorPanel.setColor(colorValue);
                                colorPanel.repaint();
                            } else {
                                throw new AssertionError();
                            }
                        }
                        imageState.setImageEffect(effect.apply(imageEffect), imageEffectX, imageEffectY);
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
                        imageState.setImageEffect(effect.apply(imageEffect), imageEffectX, imageEffectY);
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
                        imageState.setImageEffect(effect.apply(imageEffect), imageEffectX, imageEffectY);
                        mainPanel.repaint();
                    }
                });
                parameterElements.put(parameterName, checkBox);
                add(checkBox, c);
                c.gridy++;
            }
        }
        final JButton buttonOkay = new JButton("Okay");
        buttonOkay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageState.setImageEffectReset();
                imageState.setImage(effect.apply(image));
                imageState.setChangedTillLastSave(true);
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
