package work.samoje.colors;

/**
 * Static methods to help with common concerns when working with colors.
 *
 * @author Jennie Sadler
 *
 */
public class ColorHelpers {
    public static final double MAX_COLOR_VAL = 255.0;

    /**
     * Returns the integer value nearest the given double that is in the set {0,
     * 255}.
     *
     * @param value
     * @return
     */
    public static int getNearestAbsoluteValue(final double value) {
        return (value > (MAX_COLOR_VAL / 2)) ? (int) MAX_COLOR_VAL : 0;
    }

    /**
     * Returns the integer value nearest the given double value that is in the
     * range [0, 255].
     *
     * @param value
     *            Any double value
     * @return The integer value nearest to the provided in [0, 255]. May be
     *         equal to the given value.
     */
    public static int boundByColorRange(final double value) {
        return Math.max(0, (int) Math.min(ColorHelpers.MAX_COLOR_VAL, value));
    }
}
