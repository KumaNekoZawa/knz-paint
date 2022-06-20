# Always check these first
* go through all Checkstyle warnings/infos

# Bugs & cosmetic changes
* transparency support
    * clear selection could use transparent
    * any tool that uses setRGB() might need to be adjusted to use drawLine()
* consistent usage of various tools
    * use keyboard keys !? or double click & right click !?
        * 'A': Apply (polygon will be applied to image and the operation is done)
        * 'C': Cancel (cancel operation and nothing will be applied)
* "Grayscale" effect sliders are not precise
* don't block UI while applying effects, make it work in all cases!
    * basically update the subimage when necessary!
* some Listeners are in the View, some are in the Model; fix this
* when using the ColorPickerWindow the user should be able to select a color from the image

# Enhancements
* redraw icons to match the style from 90s Paint/Photoshop/etc. but still be self-drawn and usable
* "Selection" tools: be able to move around selected content
* https://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
* "Flood fill" tool with pattern
* add menu icons
* the user should be able to mask every effect with a grayscale bitmap
    * this way "radial" versions of effects become easily possible etc.

# Features
* view
    * show histogram
    * show grid!?
* add more tools
    * arrow tool?
    * triangle tool?
    * clone stamp tool? (as in IrfanPaint and Photoshop)
    * measure tool? (measures distances)
    * magnetic lasso tool? (as in Photoshop)
    * magic wand tool? (as in Photoshop)
    * "Map editor" tool to draw images that snap to a 16x16 (or whatever) grid
* add more effects
    * replace color A with color B effect (with threshold)
    * black & white effect with threshold depended on x,y coords
    * "bone distort" effect
    * delftware effect
    * composite edge detection effect
        * Extract brightness
        * (Adjust background color using flood fill if black)
        * Gamma correction 2.0~7.0
        * Threshold ~0.66
        * Laplacian 3x3
        * Negate brightness
        * Minimum (rank) effect
* some effects that change the canvas size should be specially implemented
    * "Resize" function
    * "Rotate left/right" functions
    * "Change canvas size" function
    * "Auto-crop" function
* multi language ui support
* preset brushes (not just heads/sizes but also effects on different colors)
    * pencil
    * erasor
    * highlighter
    * calligraphy
    * etc...
* create palette from image feature
* history feature
    * stored as plain text
    * can be saved/loaded
    * can implement back/restore
    * can be shown in a history view
    * every tool has two methods: parseStr, formatStr
    * some menu points need entries too
    * new/open reset the history
    * history tree!?
