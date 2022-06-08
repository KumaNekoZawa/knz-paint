package knz.paint.model.effects.parameter;

import java.util.Arrays;

public enum BorderFillStrategy {

    FILL_TRANSPARENT("Fill transparent"),
    FILL_BLACK("Fill with black"),
    FILL_GRAY("Fill with gray"),
    FILL_WHITE("Fill with white"),
    EXTEND_EDGES("Extend edges"),
    ROLLOVER("Rollover"),

    ;

    private String name;

    BorderFillStrategy(String name) {
        this.name = name;
    }

    public static String[] getNames() {
        return Arrays.stream(values()).map(BorderFillStrategy::getName).toArray(String[]::new);
    }

    public String getName() {
        return name;
    }

}
