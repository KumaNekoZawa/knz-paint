package knz.paint.model;

public enum StrokeDash {

    NORMAL("Normal"),
    DOTS("Dots"),
    DASHES("Dashes"),
    DASHES_AND_DOTS("Dashes and dots"),
    DASHES_DASHES_AND_DOTS("Dashes, dashes and dots"),
    DASHES_DOTS_AND_DOTS("Dashes, dots and dots"),

    ;

    private String title;

    StrokeDash(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
