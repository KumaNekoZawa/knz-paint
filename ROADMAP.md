# Always check these first
* go through all Checkstyle warnings/infos

# Bugs & cosmetic changes
* some Listeners are in the View, some are in the Model; fix this
* block UI while applying effects
* "Grayscale" effect sliders are not precise
* transparency support
    * clear selection could use transparent
    * any tool that uses setRGB() might need to be adjusted to use drawLine()
* consistent usage of various tools
    * use keyboard keys !? or double click & right click !?
        * 'A': Apply (polygon will be applied to image and the operation is done)
        * 'C': Cancel (cancel operation and nothing will be applied)

# Enhancements
* redraw icons to match the style from 90s Paint/Photoshop/etc. but still be self-drawn and usable
* "Flood fill" tool with pattern
* add menu icons
* "Selection" tools: be able to move around selected content
* https://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html

# Features
* actual "Resize" function, "Rotate left/right" functions and "Extend canvas" function
* "Map editor" tool to draw images that snap to a 16x16 (or whatever) grid
* a lot more effects! (see hidden effects.txt file)
* more tools!
    * arrow tool?
    * triangle tool?
    * clone stamp tool? (as in IrfanPaint and Photoshop)
    * measure tool? (measures distances)
    * magnetic lasso tool? (as in Photoshop)
    * magic wand tool? (as in Photoshop)
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

# ... and beyond!
* merge with all other paint and viewer software I've written in the past!
    * (but make uconvert a separate cli program written in C/C++)
* look at other programs
    * coreldraw
    * gimp
    * imagej
    * irfanview
    * paint
    * photofiltre
    * photoshop
    * pinta
    * xnview
    * my old java image viewer project
