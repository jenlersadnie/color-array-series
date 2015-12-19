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
    @Override
    public Color combine(final Color parentLeft, final Color parentRight) {
        return new Color(combine(parentLeft.getRed(), parentRight.getRed()),
                combine(parentLeft.getGreen(), parentRight.getGreen()),
                combine(parentLeft.getBlue(), parentRight.getBlue()));
    }

    /**
     * Helper method. Returns the double average of the two integers.
     */
    protected double avg(final int left, final int right) {
        return (left + right) / 2.0;
    }

    protected abstract int combine(int left, int right);
}