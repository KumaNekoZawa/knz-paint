package knz.paint.model;

public enum FillStyle {

    NONE("No fill"),
    PRIMARY("Primary color"),
    SECONDARY("Secondary color"),

    ;

    private String title;

    FillStyle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
