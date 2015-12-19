package work.samoje.colors.modification.combiner.combiners;

import java.awt.Color;

/**
 * Abstract implementation of {@link ColorCombiner} for any combiner which acts
 * by merging respective RGB values of the two parent colors using a single
 * combine method.
 *
 * @author Jennie Sadler
 *
 */
public abstract class RGBValueWiseCombiner implements ColorCombiner {
    protected static final double MAX_COLOR_VAL = 255.0;

    @Override
    public Color combine(final Color parentLeft, final Color parentRight) {
        return new Color(combine(parentLeft.getRed(), parentRight.getRed()),
                combine(parentLeft.getGreen(), parentRight.getGreen()),
                combine(parentLeft.getBlue(), parentRight.getBlue()));
    }

    /**
     * Helper method. Returns the integer value nearest the given double value
     * that is in the range [0, 255].
     *
     * @param value
     *            Any double value
     * @return The integer value nearest to the provided in [0, 255]. May be
     *         equal to the given value.
     */
    protected int boundByColorRange(final double value) {
        return Math.max(0, (int) Math.min(MAX_COLOR_VAL, value));
    }

    /**
     * Helper method. Returns the double average of the two integers.
     */
    protected double avg(final int left, final int right) {
        return (left + right) / 2.0;
    }

    protected abstract int combine(int left, int right);
}