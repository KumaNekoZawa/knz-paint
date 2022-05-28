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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import knz.paint.model.effects.AbstractParameter;
import knz.paint.model.effects.BooleanParameter;
import knz.paint.model.effects.DoubleParameter;
import knz.paint.model.effects.Effect;
import knz.paint.model.effects.IntegerParameter;
import knz.paint.model.effects.PresetParameter;

public class EffectWindow extends JDialog {

    public EffectWindow(MainWindow parent, JScrollPane scrollPane, MainPanel mainPanel, Effect effect, String title) {
        super(parent, title, true);

        for (AbstractParameter parameter : effect.getParameters()) {
            parameter.reset();
        }

        final BufferedImage image = mainPanel.getImage();
        final Rectangle rect = scrollPane.getViewport().getViewRect();
        rect.x /= mainPanel.getZoomFactor();
        rect.y /= mainPanel.getZoomFactor();
        rect.width /= mainPanel.getZoomFactor();
        rect.height /= mainPanel.getZoomFactor();
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
            if (parameter instanceof PresetParameter) {
                PresetParameter presetParameter = (PresetParameter) parameter;
                JComboBox<String> comboBox = new JComboBox<>(presetParameter.getPresetNames());
                comboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // FIXME this doesn't update the UI elements
                        final String[] parameterNames = presetParameter.getParameterNames();
                        final Object[] values = presetParameter.getPresets().get(comboBox.getSelectedIndex()).getValues();
                        for (int i = 0; i < parameterNames.length; i++) {
                            final String parameterName = parameterNames[i];
                            final Object value = values[i];
                            boolean found = false;
                            for (AbstractParameter parameterToSet : effect.getParameters()) {
                                if (parameterToSet.getName().equals(parameterName)) {
                                    if (parameterToSet instanceof BooleanParameter) {
                                        ((BooleanParameter) parameterToSet).setValue((boolean) value);
                                    } else if (parameterToSet instanceof IntegerParameter) {
                                        ((IntegerParameter) parameterToSet).setValue((int) value);
                                    } else if (parameterToSet instanceof DoubleParameter) {
                                        ((DoubleParameter) parameterToSet).setValue((double) value);
                                    } else {
                                        throw new AssertionError();
                                    }
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                System.err.println("Could not find parameter: " + parameterName);
                            }
                        }
                        mainPanel.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);
                    }
                });
                add(comboBox, c);
                c.gridy++;
            } else if (parameter instanceof BooleanParameter) {
                BooleanParameter booleanParameter = (BooleanParameter) parameter;
                JCheckBox checkBox = new JCheckBox(parameter.getName());
                checkBox.setSelected(booleanParameter.getDef());
                checkBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        booleanParameter.setValue(checkBox.isSelected());
                        mainPanel.setImageTemp(effect.apply(imageTemp), imageTempX, imageTempY);
                    }
                });
                add(checkBox, c);
                c.gridy++;
            } else {
                JLabel label = new JLabel(parameter.getLabelText());
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
                add(label, c);
                c.gridy++;
                if (parameter instanceof IntegerParameter) {
                    IntegerParameter integerParameter = (IntegerParameter) parameter;
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
                    add(slider, c);
                    c.gridy++;
                } else if (parameter instanceof DoubleParameter) {
                    DoubleParameter doubleParameter = (DoubleParameter) parameter;
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
