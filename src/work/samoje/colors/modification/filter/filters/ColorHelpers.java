package work.samoje.colors.modification.filter.filters;

public class ColorHelpers {
    public static final double MAX_COLOR_VAL = 255.0;

    public static int getNearestAbsoluteValue(final int value) {
        return (value > (MAX_COLOR_VAL / 2)) ? (int) MAX_COLOR_VAL : 0;
    }

}
