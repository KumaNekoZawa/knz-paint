package knz.paint.view.mainwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import knz.paint.model.Config;
import knz.paint.model.ImageState;
import knz.paint.model.effects.EffectState;
import knz.paint.model.effects.parameter.AbstractParameter;
import knz.paint.model.effects.specific.AbstractEffect;
import knz.paint.model.effects.specific.area.OilPaintingEffect;
import knz.paint.model.effects.specific.area.RankEffect;
import knz.paint.model.effects.specific.area.custom.Custom3x3Effect;
import knz.paint.model.effects.specific.area.custom.Custom5x5Effect;
import knz.paint.model.effects.specific.graphics.TadaEffect;
import knz.paint.model.effects.specific.hsba.AdjustChannelsHSBAEffect;
import knz.paint.model.effects.specific.hsba.ExtractAlphaEffect;
import knz.paint.model.effects.specific.hsba.ExtractBrightnessEffect;
import knz.paint.model.effects.specific.hsba.ExtractSaturationEffect;
import knz.paint.model.effects.specific.hsba.MapRangeHSBAEffect;
import knz.paint.model.effects.specific.hsba.MixColorHSBAEffect;
import knz.paint.model.effects.specific.hsba.NegateHSBAEffect;
import knz.paint.model.effects.specific.hsba.NoiseHSBAEffect;
import knz.paint.model.effects.specific.hsba.NormalizeHSBAEffect;
import knz.paint.model.effects.specific.hsba.QuantizeHSBAEffect;
import knz.paint.model.effects.specific.hsba.SolarizeHSBAEffect;
import knz.paint.model.effects.specific.hsba.colorblindness.AchromatopsiaEffect;
import knz.paint.model.effects.specific.hsba.colorblindness.DeuteranomalyEffect;
import knz.paint.model.effects.specific.hsba.colorblindness.DeuteranopiaEffect;
import knz.paint.model.effects.specific.hsba.colorblindness.ProtanomalyEffect;
import knz.paint.model.effects.specific.hsba.colorblindness.ProtanopiaEffect;
import knz.paint.model.effects.specific.hsba.colorblindness.TritanomalyEffect;
import knz.paint.model.effects.specific.hsba.colorblindness.TritanopiaEffect;
import knz.paint.model.effects.specific.hsba.gray.BlackWhiteEffect;
import knz.paint.model.effects.specific.hsba.gray.ThresholdEffect;
import knz.paint.model.effects.specific.rgba.AdjustChannelsRGBAEffect;
import knz.paint.model.effects.specific.rgba.AdjustContrastEffect;
import knz.paint.model.effects.specific.rgba.AdjustGammaEffect;
import knz.paint.model.effects.specific.rgba.BitShiftEffect;
import knz.paint.model.effects.specific.rgba.ExtractChannelsRGBAEffect;
import knz.paint.model.effects.specific.rgba.GrayscaleEffect;
import knz.paint.model.effects.specific.rgba.MapRangeRGBAEffect;
import knz.paint.model.effects.specific.rgba.MixChannelsRGBAEffect;
import knz.paint.model.effects.specific.rgba.MixColorRGBAEffect;
import knz.paint.model.effects.specific.rgba.NegateRGBAEffect;
import knz.paint.model.effects.specific.rgba.NoiseRGBAEffect;
import knz.paint.model.effects.specific.rgba.NormalizeRGBAEffect;
import knz.paint.model.effects.specific.rgba.QuantizeRGBAEffect;
import knz.paint.model.effects.specific.rgba.SolarizeRGBAEffect;
import knz.paint.model.effects.specific.rgba.SwapRedBlueEffect;
import knz.paint.model.effects.specific.xy.AdjustCartesianEffect;
import knz.paint.model.effects.specific.xy.DistortionEffect;
import knz.paint.model.effects.specific.xy.ExplosionEffect;
import knz.paint.model.effects.specific.xy.FlipEffect;
import knz.paint.model.effects.specific.xy.FunhouseMirrorEffect;
import knz.paint.model.effects.specific.xy.MirrorEffect;
import knz.paint.model.effects.specific.xy.MosaicEffect;
import knz.paint.model.effects.specific.xy.ShearSlicingEffect;
import knz.paint.model.effects.specific.xy.StainedGlassEffect;
import knz.paint.model.effects.specific.xy.ZoomEffect;
import knz.paint.model.effects.specific.xy.polar.AdjustPolarEffect;
import knz.paint.model.effects.specific.xy.polar.PolarMirrorEffect;
import knz.paint.model.effects.specific.xy.polar.PolarMosaicEffect;
import knz.paint.model.effects.specific.xy.polar.RotationEffect;
import knz.paint.model.effects.specific.xy.polar.SwirlEffect;
import knz.paint.model.effects.specific.xycolor.BorderEffect;
import knz.paint.model.effects.specific.xycolor.GridEffect;
import knz.paint.model.effects.specific.xycolor.SaltPepperEffect;
import knz.paint.model.tools.AirbrushType;
import knz.paint.model.tools.FillStyle;
import knz.paint.model.tools.StrokeDash;
import knz.paint.model.tools.Tool;
import knz.paint.model.tools.ToolState;
import knz.paint.model.tools.specific.AbstractTool;
import knz.paint.view.aboutwindow.AboutWindow;
import knz.paint.view.colorpickerwindow.ColorPickerEvent;
import knz.paint.view.colorpickerwindow.ColorPickerListener;
import knz.paint.view.colorpickerwindow.ColorPickerWindow;
import knz.paint.view.effectwindow.EffectWindow;
import knz.paint.view.plainpanels.PalettePanel;

public class MainWindow extends JFrame {

    private static final String TITLE = "熊猫沢ペイント";

    private static final File FILE_BACKUP = new File("backup.png");

    private static final FileFilter FILTER_BMP = createFileFilter("Microsoft Windows Bitmap", "bmp", "dib");
    private static final FileFilter FILTER_GIF = createFileFilter("Graphics Interchange Format", "gif");
    private static final FileFilter FILTER_JPG = createFileFilter("Joint Photographic Experts Group", "jpg", "jpeg");
    private static final FileFilter FILTER_PNG = createFileFilter("Portable Network Graphics", "png");

    private static final AbstractEffect[] EFFECTS = {
        /* xy/polar */
        new AdjustPolarEffect(),
        new PolarMirrorEffect(),
        new PolarMosaicEffect(),
        new RotationEffect(),
        new SwirlEffect(),
        /* xy */
        new AdjustCartesianEffect(),
        new DistortionEffect(),
        new ExplosionEffect(),
        new FlipEffect(),
        new FunhouseMirrorEffect(),
        new MirrorEffect(),
        new MosaicEffect(),
        new ShearSlicingEffect(),
        new StainedGlassEffect(),
        new ZoomEffect(),
        /* xycolor */
        new BorderEffect(),
        new GridEffect(),
        new SaltPepperEffect(),
        /* rgba */
        new AdjustChannelsRGBAEffect(),
        new AdjustContrastEffect(),
        new AdjustGammaEffect(),
        new BitShiftEffect(),
        new ExtractChannelsRGBAEffect(),
        new GrayscaleEffect(),
        new MapRangeRGBAEffect(),
        new MixChannelsRGBAEffect(),
        new MixColorRGBAEffect(),
        new NegateRGBAEffect(),
        new NoiseRGBAEffect(),
        new NormalizeRGBAEffect(),
        new QuantizeRGBAEffect(),
        new SolarizeRGBAEffect(),
        new SwapRedBlueEffect(),
        /* hsba/colorblindness */
        new AchromatopsiaEffect(),
        new DeuteranomalyEffect(),
        new DeuteranopiaEffect(),
        new ProtanomalyEffect(),
        new ProtanopiaEffect(),
        new TritanomalyEffect(),
        new TritanopiaEffect(),
        /* hsba/gray */
        new BlackWhiteEffect(),
        new ThresholdEffect(),
        /* hsba */
        new AdjustChannelsHSBAEffect(),
        new ExtractAlphaEffect(),
        new ExtractBrightnessEffect(),
        new ExtractSaturationEffect(),
        new MapRangeHSBAEffect(),
        new MixColorHSBAEffect(),
        new NegateHSBAEffect(),
        new NoiseHSBAEffect(),
        new NormalizeHSBAEffect(),
        new QuantizeHSBAEffect(),
        new SolarizeHSBAEffect(),
        /* area */
        new Custom3x3Effect(),
        new Custom5x5Effect(),
        new OilPaintingEffect(),
        new RankEffect(),
        /* graphics */
        new TadaEffect(),
    };

    private ColorPickerWindow colorPickerWindow = null;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuFileLoad = new JMenuItem("Load...");
    private JMenuItem menuFileSave = new JMenuItem("Save...");
    private JMenuItem menuFileAbout = new JMenuItem("About...");
    private JMenuItem menuFileQuit = new JMenuItem("Quit");

    private JMenu menuEdit = new JMenu("Edit");
    private JMenuItem menuEditSelectNone = new JMenuItem("Select none");
    private JMenuItem menuEditSelectAll = new JMenuItem("Select all");
    private JMenuItem menuEditCut = new JMenuItem("Cut");
    private JMenuItem menuEditCopy = new JMenuItem("Copy");
    private JMenuItem menuEditPaste = new JMenuItem("Paste");
    private JMenuItem menuEditClearSelection = new JMenuItem("Clear selection");
    private JMenuItem menuEditCropToSelection = new JMenuItem("Crop to selection");

    private JMenu menuView = new JMenu("View");
    private JCheckBoxMenuItem menuViewToolBar = new JCheckBoxMenuItem("Tool bar");
    private JCheckBoxMenuItem menuViewColorBar = new JCheckBoxMenuItem("Color bar");
    private JCheckBoxMenuItem menuViewStatusBar = new JCheckBoxMenuItem("Status bar");
    private JMenu menuViewZoom = new JMenu("Zoom");
    private JMenuItem menuViewZoomIn = new JMenuItem("Zoom in");
    private JMenuItem menuViewZoomOut = new JMenuItem("Zoom out");
    private List<JMenuItem> menuViewZoomLevels = new ArrayList<>();
    private JMenuItem menuViewPackAndCenter = new JMenuItem("Pack & center");

    private JMenu menuTools = new JMenu("Tools");
    private JMenuItem menuToolsColorPicker = new JMenuItem("Color picker...");
    private JMenu menuToolsFillStyle = new JMenu("Fill style");
    private JMenu menuToolsStrokeWidth = new JMenu("Stroke width");
    private JMenu menuToolsStrokeDash = new JMenu("Stroke dash");
    private JMenu menuToolsRoundedRectangle = new JMenu("Rounded rectangle");
    private JMenu menuToolsRoundedRectangleArcWidth  = new JMenu("Arc width");
    private JMenu menuToolsRoundedRectangleArcHeight = new JMenu("Arc height");
    private JMenu menuToolsAirbrush = new JMenu("Airbrush");
    private JMenu menuToolsAirbrushType = new JMenu("Type");
    private JMenu menuToolsAirbrushSize = new JMenu("Size");

    private JMenu menuEffects = new JMenu("Effects");
    private Map<String, JMenu> packageToMenu = new HashMap<>();

    private JScrollPane scrollPane;
    private MainPanel mainPanel;
    private JToolBar toolBar = new JToolBar();
    private JToolBar colorBar = new JToolBar();
    private JLabel statusBar = new JLabel();

    private ToolState toolState = new ToolState();
    private EffectState effectState = new EffectState();

    private File lastPath;

    private int statusBarWidth;
    private int statusBarHeight;
    private int statusBarCurrentX;
    private int statusBarCurrentY;
    private int statusBarCurrentRGBA;

    public MainWindow() {
        super(TITLE);

        for (final Tool tool : Tool.values()) {
            tool.getToolObject().setToolState(toolState);
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quit();
            }
        });

        menuFileLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });
        menuFileLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuFile.add(menuFileLoad);
        menuFileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        menuFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuFile.add(menuFileSave);
        menuFile.addSeparator();
        menuFileAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutWindow(MainWindow.this);
            }
        });
        menuFileAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        menuFile.add(menuFileAbout);
        menuFileQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        menuFileQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        menuFile.add(menuFileQuit);
        menuBar.add(menuFile);

        menuEditSelectNone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.selectNone();
            }
        });
        menuEditSelectNone.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
        menuEdit.add(menuEditSelectNone);
        menuEditSelectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.selectAll();
            }
        });
        menuEditSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        menuEdit.add(menuEditSelectAll);
        menuEdit.addSeparator();
        menuEditCut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.cut();
                mainPanel.getImageState().setChangedTillLastSave(true);
            }
        });
        menuEditCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuEdit.add(menuEditCut);
        menuEditCopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.copy();
            }
        });
        menuEditCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        menuEdit.add(menuEditCopy);
        menuEditPaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.paste();
            }
        });
        menuEditPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        menuEdit.add(menuEditPaste);
        menuEdit.addSeparator();
        menuEditClearSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.clearSelection();
                mainPanel.getImageState().setChangedTillLastSave(true);
            }
        });
        menuEditClearSelection.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        menuEdit.add(menuEditClearSelection);
        menuEditCropToSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.cropToSelection();
                mainPanel.getImageState().setChangedTillLastSave(true);
            }
        });
        menuEditCropToSelection.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        menuEdit.add(menuEditCropToSelection);
        menuBar.add(menuEdit);

        menuViewToolBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolBar.setVisible(menuViewToolBar.isSelected());
            }
        });
        menuViewToolBar.setSelected(true);
        menuView.add(menuViewToolBar);
        menuViewColorBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorBar.setVisible(menuViewColorBar.isSelected());
            }
        });
        menuViewColorBar.setSelected(true);
        menuView.add(menuViewColorBar);
        menuViewStatusBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusBar.setVisible(menuViewStatusBar.isSelected());
            }
        });
        menuViewStatusBar.setSelected(true);
        menuView.add(menuViewStatusBar);
        menuView.addSeparator();
        menuViewZoomIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPanel.getImageState().increaseZoomLevel()) {
                    updateZoomLevel();
                }
            }
        });
        menuViewZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0));
        menuViewZoom.add(menuViewZoomIn);
        menuViewZoomOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPanel.getImageState().decreaseZoomLevel()) {
                    updateZoomLevel();
                }
            }
        });
        menuViewZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0));
        menuViewZoom.add(menuViewZoomOut);
        menuViewZoom.addSeparator();
        final ButtonGroup bgZoomLevels = new ButtonGroup();
        for (int zoomLevel = 0; zoomLevel < ImageState.ZOOM_LEVELS; zoomLevel++) {
            final int zoomLevelFinal = zoomLevel;
            final int zoomDivisor = ImageState.ZOOM_DIVISORS[zoomLevel];
            final int zoomFactor  = ImageState.ZOOM_FACTORS[zoomLevel];
            final JRadioButtonMenuItem menuViewZoomLevel = new JRadioButtonMenuItem(
                zoomDivisor == 1 && zoomFactor == 1 ? "100 %" : ((zoomFactor > 1 ? "×" + zoomFactor : "") + (zoomDivisor > 1 ? "/" + zoomDivisor : ""))
            );
            menuViewZoomLevel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPanel.getImageState().setZoomLevel(zoomLevelFinal);
                    updateZoomLevel();
                }
            });
            if (zoomLevel == ImageState.DEFAULT_ZOOM_LEVEL) {
                menuViewZoomLevel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.CTRL_MASK));
                menuViewZoomLevel.setSelected(true);
            }
            menuViewZoomLevels.add(menuViewZoomLevel);
            bgZoomLevels.add(menuViewZoomLevel);
            menuViewZoom.add(menuViewZoomLevel);
        }
        menuView.add(menuViewZoom);
        menuView.addSeparator();
        menuViewPackAndCenter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.NORMAL);
                pack();
                setLocationRelativeTo(null);
            }
        });
        menuViewPackAndCenter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        menuView.add(menuViewPackAndCenter);
        menuBar.add(menuView);

        menuToolsColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (colorPickerWindow == null || !colorPickerWindow.isDisplayable()) {
                    if (colorPickerWindow != null) {
                        colorPickerWindow.dispose();
                    }
                    colorPickerWindow = new ColorPickerWindow(MainWindow.this, 2);
                    colorPickerWindow.setColorLeft(toolState.getColorPrimary());
                    colorPickerWindow.setColorRight(toolState.getColorSecondary());
                    colorPickerWindow.addColorPickerListener(new ColorPickerListener() {
                        @Override
                        public void colorChangedLeft(ColorPickerEvent e) {
                            toolState.setColorPrimary(e.getColor());
                        }

                        @Override
                        public void colorChangedRight(ColorPickerEvent e) {
                            toolState.setColorSecondary(e.getColor());
                        }
                    });
                }
            }
        });
        menuTools.add(menuToolsColorPicker);
        final ButtonGroup bgFillStyle = new ButtonGroup();
        for (final FillStyle fillStyle : FillStyle.values()) {
            final JRadioButtonMenuItem menuToolsFillStyleChild = new JRadioButtonMenuItem(fillStyle.getTitle());
            menuToolsFillStyleChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setFillStyle(fillStyle);
                }
            });
            if (fillStyle == ToolState.DEFAULT_FILL_STYLE) {
                menuToolsFillStyleChild.setSelected(true);
            }
            bgFillStyle.add(menuToolsFillStyleChild);
            menuToolsFillStyle.add(menuToolsFillStyleChild);
        }
        menuTools.add(menuToolsFillStyle);
        final ButtonGroup bgStrokeWidth = new ButtonGroup();
        for (final int strokeWidth : ToolState.STOKE_WIDTHS) {
            final JRadioButtonMenuItem menuToolsStrokeWidthChild = new JRadioButtonMenuItem(strokeWidth + " px");
            menuToolsStrokeWidthChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setStrokeWidth(strokeWidth);
                }
            });
            if (strokeWidth == ToolState.DEFAULT_STROKE_WIDTH) {
                menuToolsStrokeWidthChild.setSelected(true);
            }
            bgStrokeWidth.add(menuToolsStrokeWidthChild);
            menuToolsStrokeWidth.add(menuToolsStrokeWidthChild);
        }
        menuTools.add(menuToolsStrokeWidth);
        final ButtonGroup bgStrokeDash = new ButtonGroup();
        for (final StrokeDash strokeDash : StrokeDash.values()) {
            final JRadioButtonMenuItem menuToolsStrokeDashChild = new JRadioButtonMenuItem(strokeDash.getTitle());
            menuToolsStrokeDashChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setStrokeDash(strokeDash);
                }
            });
            if (strokeDash == ToolState.DEFAULT_STROKE_DASH) {
                menuToolsStrokeDashChild.setSelected(true);
            }
            bgStrokeDash.add(menuToolsStrokeDashChild);
            menuToolsStrokeDash.add(menuToolsStrokeDashChild);
        }
        menuTools.add(menuToolsStrokeDash);
        menuTools.addSeparator();
        final ButtonGroup bgRoundedRectangleArcWidth = new ButtonGroup();
        for (final int arcWidth : ToolState.ROUNDED_RECTANGLE_RADII) {
            final JRadioButtonMenuItem menuToolsRoundedRectangleArcWidthChild = new JRadioButtonMenuItem(arcWidth + " px");
            menuToolsRoundedRectangleArcWidthChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setRoundedRectangleArcWidth(arcWidth);
                }
            });
            if (arcWidth == ToolState.DEFAULT_ROUNDED_RECTANGLE_ARC_WIDTH) {
                menuToolsRoundedRectangleArcWidthChild.setSelected(true);
            }
            bgRoundedRectangleArcWidth.add(menuToolsRoundedRectangleArcWidthChild);
            menuToolsRoundedRectangleArcWidth.add(menuToolsRoundedRectangleArcWidthChild);
        }
        menuToolsRoundedRectangle.add(menuToolsRoundedRectangleArcWidth);
        final ButtonGroup bgRoundedRectangleArcHeight = new ButtonGroup();
        for (final int arcHeight : ToolState.ROUNDED_RECTANGLE_RADII) {
            final JRadioButtonMenuItem menuToolsRoundedRectangleArcHeightChild = new JRadioButtonMenuItem(arcHeight + " px");
            menuToolsRoundedRectangleArcHeightChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setRoundedRectangleArcHeight(arcHeight);
                }
            });
            if (arcHeight == ToolState.DEFAULT_ROUNDED_RECTANGLE_ARC_HEIGHT) {
                menuToolsRoundedRectangleArcHeightChild.setSelected(true);
            }
            bgRoundedRectangleArcHeight.add(menuToolsRoundedRectangleArcHeightChild);
            menuToolsRoundedRectangleArcHeight.add(menuToolsRoundedRectangleArcHeightChild);
        }
        menuToolsRoundedRectangle.add(menuToolsRoundedRectangleArcHeight);
        menuTools.add(menuToolsRoundedRectangle);
        final ButtonGroup bgAirbrushType = new ButtonGroup();
        for (final AirbrushType airbrushType : AirbrushType.values()) {
            final JRadioButtonMenuItem menuToolsAirbrushTypeChild = new JRadioButtonMenuItem(airbrushType.getTitle());
            menuToolsAirbrushTypeChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setAirbrushType(airbrushType);
                }
            });
            if (airbrushType == ToolState.DEFAULT_AIRBRUSH_TYPE) {
                menuToolsAirbrushTypeChild.setSelected(true);
            }
            bgAirbrushType.add(menuToolsAirbrushTypeChild);
            menuToolsAirbrushType.add(menuToolsAirbrushTypeChild);
        }
        menuToolsAirbrush.add(menuToolsAirbrushType);
        final ButtonGroup bgAirbrushSize = new ButtonGroup();
        for (final int airbrushSize : ToolState.AIRBRUSH_SIZES) {
            final JRadioButtonMenuItem menuToolsAirbrushSizeChild = new JRadioButtonMenuItem(airbrushSize + " px");
            menuToolsAirbrushSizeChild.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setAirbrushSize(airbrushSize);
                }
            });
            if (airbrushSize == ToolState.DEFAULT_AIRBRUSH_SIZE) {
                menuToolsAirbrushSizeChild.setSelected(true);
            }
            bgAirbrushSize.add(menuToolsAirbrushSizeChild);
            menuToolsAirbrushSize.add(menuToolsAirbrushSizeChild);
        }
        menuToolsAirbrush.add(menuToolsAirbrushSize);
        menuTools.add(menuToolsAirbrush);
        menuBar.add(menuTools);

        packageToMenu.clear();
        packageToMenu.put("", menuEffects);
        for (final AbstractEffect effect : EFFECTS) {
            final String title = effect.getFinalName() + (effect.getParameters().isEmpty() ? "" : "...");
            final JMenuItem menuEffectsEffect = new JMenuItem(title);
            menuEffectsEffect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final ImageState imageState = mainPanel.getImageState();
                    final BufferedImage image = imageState.getImage();
                    for (final AbstractParameter parameter : effect.getParameters()) {
                        parameter.reset(image.getWidth(), image.getHeight());
                    }
                    if (effect.getParameters().isEmpty()) {
                        imageState.setImage(effect.apply(imageState.getImage()));
                        imageState.setChangedTillLastSave(true);
                        mainPanel.repaint();
                    } else {
                        new EffectWindow(MainWindow.this, scrollPane, mainPanel, effect, title);
                    }
                }
            });
            makeSureMenuExists(effect.getParentName()).add(menuEffectsEffect);
        }
        menuBar.add(menuEffects);

        setJMenuBar(menuBar);

        setLayout(new BorderLayout());

        toolBar.setLayout(new GridBagLayout());
        toolBar.setOrientation(SwingConstants.VERTICAL);
        toolBar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                if (e.getPropertyName().equals("orientation")) {
                    toolBar.removeAll();
                    addToolBarButtons();
                }
            }
        });
        addToolBarButtons();
        add(toolBar, BorderLayout.LINE_START);

        mainPanel = new MainPanel(toolState, effectState);
        mainPanel.addMainPanelListener(new MainPanelListener() {
            @Override
            public void mouseMoved(MainPanelMouseMovedEvent e) {
                statusBarCurrentX = e.getX();
                statusBarCurrentY = e.getY();
                statusBarCurrentRGBA = e.getRGBA();
                updateStatusBar();
            }

            @Override
            public void sizeChanged(MainPanelSizeChangedEvent e) {
                statusBarWidth = e.getWidth();
                statusBarHeight = e.getHeight();
                updateStatusBar();
            }

            @Override
            public void toolStateChanged(MainPanelToolStateChangedEvent e) {
                updateChildWindows();
            }
        });
        scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        colorBar.setOrientation(SwingConstants.VERTICAL);
        final int colorBarSize = Config.getConfig().getColorBarSize();
        final List<File> colorPaletteFiles = Config.getConfig().getColorPaletteFiles();
        for (int i = 0; i < colorPaletteFiles.size(); i++) {
            final PalettePanel palettePanel = new PalettePanel(colorBarSize, colorPaletteFiles.get(i));
            palettePanel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final MouseEvent source = (MouseEvent) e.getSource();
                    final int x = source.getX();
                    final int y = source.getY();
                    final Color c = palettePanel.getColorAt(x, y);
                    if (SwingUtilities.isLeftMouseButton(source)) {
                        toolState.setColorPrimary(c);
                    } else if (SwingUtilities.isRightMouseButton(source)) {
                        toolState.setColorSecondary(c);
                    }
                    updateChildWindows();
                }
            });
            colorBar.add(palettePanel);
        }
        add(colorBar, BorderLayout.LINE_END);

        statusBar.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        updateStatusBar();
        add(statusBar, BorderLayout.PAGE_END);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        final URL iconURL = Thread.currentThread().getContextClassLoader().getResource("icon.png");
        if (iconURL != null) {
            setIconImage(new ImageIcon(iconURL).getImage());
        }

        if (FILE_BACKUP.isFile()) {
            try {
                mainPanel.getImageState().setImage(ImageIO.read(FILE_BACKUP));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mainPanel.getImageState().newImage(
                Config.getConfig().getNewImageWidth(),
                Config.getConfig().getNewImageHeight(),
                Config.getConfig().getNewImageColor()
            );
        }
        mainPanel.updatePanelSize();
        mainPanel.repaint();

        setSize(Config.getConfig().getMainWindowWidth(),
                Config.getConfig().getMainWindowHeight());
        switch (Config.getConfig().getMainWindowMode()) {
        case NORMAL:
            /* empty */
            break;
        case PACKED:
            pack();
            break;
        case MAXIMIZED:
            setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
            break;
        default:
            throw new AssertionError();
        }
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void updateZoomLevel() {
        menuViewZoomLevels.get(mainPanel.getImageState().getZoomLevel()).setSelected(true);
        mainPanel.updatePanelSize();
        mainPanel.repaint();
    }

    private JMenu makeSureMenuExists(String fullpath) {
        JMenu result = menuEffects;
        int i = 0;
        int index;
        do {
            index = fullpath.indexOf(".", i);
            final String subpath = index < 0 ? fullpath : fullpath.substring(0, index);
            if (!packageToMenu.containsKey(subpath)) {
                final String subname = subpath.substring(subpath.lastIndexOf(".") + 1);
                final JMenu newMenu = new JMenu(subname);
                packageToMenu.put(subpath, newMenu);
                result.add(newMenu);
            }
            result = packageToMenu.get(subpath);
            if (index >= 0) {
                i = index + 1;
            }
        } while (index >= 0);
        return result;
    }

    private void addToolBarButtons() {
        final int numberOfTools = Tool.values().length;
        final int orient = toolBar.getOrientation();
        final GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        for (final Tool tool : Tool.values()) {
            final AbstractTool toolObject = tool.getToolObject();
            final String title = toolObject.getName();
            final String icon = toolObject.getIcon();
            JButton button = null;
            if (icon != null && !icon.isEmpty()) {
                final URL iconURL = Thread.currentThread().getContextClassLoader().getResource(icon);
                if (iconURL != null) {
                    button = new JButton(new ImageIcon(iconURL));
                }
            }
            if (button == null) {
                button = new JButton(title.substring(0, 2));
                button.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            }
            button.setToolTipText(title);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toolState.setSelectedTool(tool);
                }
            });
            final int j = tool.ordinal();
            switch (orient) {
            case JToolBar.VERTICAL:
                c.gridx = j % 2;
                c.gridy = j / 2;
                break;
            case JToolBar.HORIZONTAL:
                c.gridx = j / 2;
                c.gridy = j % 2;
                break;
            default:
                throw new AssertionError();
            }
            if ((numberOfTools % 2 == 0 && j == numberOfTools - 2) || j == numberOfTools - 1) {
                switch (orient) {
                case JToolBar.VERTICAL:
                    c.weightx = 0;
                    c.weighty = 1;
                    break;
                case JToolBar.HORIZONTAL:
                    c.weightx = 1;
                    c.weighty = 0;
                    break;
                default:
                    throw new AssertionError();
                }
            } else {
                c.weightx = 0;
                c.weighty = 0;
            }
            toolBar.add(button, c);
        }
    }

    private void updateChildWindows() {
        if (colorPickerWindow != null) {
            colorPickerWindow.setColorLeft(toolState.getColorPrimary());
            colorPickerWindow.setColorRight(toolState.getColorSecondary());
        }
    }

    private void updateStatusBar() {
        String text = "";
        text += "Size: " + statusBarWidth + " × " + statusBarHeight;
        text += " | ";
        text += "Current: (" + statusBarCurrentX + ", " + statusBarCurrentY + ") = 0x" + String.format("%08X", statusBarCurrentRGBA);
        statusBar.setText(text);
    }

    private void load() {
        final JFileChooser fileChooser = new JFileChooser(lastPath);
        addFilters(fileChooser);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            final File file = fileChooser.getSelectedFile();
            if (file.isFile()) {
                try {
                    final ImageState imageState = mainPanel.getImageState();
                    imageState.setImage(ImageIO.read(file));
                    imageState.setChangedTillLastSave(false);
                    mainPanel.updatePanelSize();
                    mainPanel.repaint();
                    lastPath = file.getParentFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void save() {
        final JFileChooser fileChooser = new JFileChooser(lastPath);
        fileChooser.setAcceptAllFileFilterUsed(false);
        addFilters(fileChooser);
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            final File file = fileChooser.getSelectedFile();
            final FileFilter fileFilter = fileChooser.getFileFilter();
            final String formatString;
            final boolean supportsAlpha;
            if (fileFilter == FILTER_BMP) {
                formatString = "BMP";
                supportsAlpha = false;
            } else if (fileFilter == FILTER_GIF) {
                formatString = "GIF";
                supportsAlpha = true;
            } else if (fileFilter == FILTER_JPG) {
                formatString = "JPG";
                supportsAlpha = false;
            } else if (fileFilter == FILTER_PNG) {
                formatString = "PNG";
                supportsAlpha = true;
            } else {
                throw new AssertionError();
            }
            final ImageState imageState = mainPanel.getImageState();
            final BufferedImage image = supportsAlpha
                ? imageState.getImage()
                : imageState.getImageWithoutAlpha(toolState.getColorSecondary());
            if (file.exists()) {
                file.delete();
            }
            try {
                ImageIO.write(image, formatString, file);
                imageState.setChangedTillLastSave(false);
                lastPath = file.getParentFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addFilters(JFileChooser fileChooser) {
        fileChooser.addChoosableFileFilter(FILTER_PNG);
        fileChooser.addChoosableFileFilter(FILTER_BMP);
        fileChooser.addChoosableFileFilter(FILTER_GIF);
        fileChooser.addChoosableFileFilter(FILTER_JPG);
    }

    private static FileFilter createFileFilter(String name, String... extensions) {
        return new FileFilter() {
            @Override
            public String getDescription() {
                return name + " (*." + String.join("; *.", extensions) + ")";
            }

            @Override
            public boolean accept(File file) {
                final String lc = file.getName().toLowerCase();
                return (file.isFile() && Arrays.stream(extensions).anyMatch(extension -> lc.endsWith("." + extension))) || file.isDirectory();
            }
        };
    }

    private void quit() {
        final ImageState imageState = mainPanel.getImageState();
        if (imageState.hasChangedTillLastSave()) {
            if (FILE_BACKUP.exists()) {
                FILE_BACKUP.delete();
            }
            try {
                ImageIO.write(imageState.getImage(), "PNG", FILE_BACKUP);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (colorPickerWindow != null) {
            colorPickerWindow.dispose();
        }
        dispose();
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

}
