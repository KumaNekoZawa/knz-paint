package knz.paint.model.tools;

public enum AirbrushType {

    NORMAL("Normal"),
    RANDOM_COLOR("Random color"),
    RANDOM_HUE("Random hue"),

    ;

    private String title;

    AirbrushType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
