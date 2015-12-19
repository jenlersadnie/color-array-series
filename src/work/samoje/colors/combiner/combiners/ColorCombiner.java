package work.samoje.colors.combiner.combiners;

import java.awt.Color;

/**
 * Combines colors into one.
 *
 * @author Jennie Sadler
 */
/*
 * TODO: Introduce combine(Color firstParent, Color... parents) method to
 * support combinations of arbitrary amounts of colors.
 */
public interface ColorCombiner {

    /**
     * Given the two {@link Color}s, returns the one {@link Color}.
     *
     * @param parentLeft
     * @param parentRight
     * @return
     */
    public Color combine(final Color parentLeft, final Color parentRight);
}