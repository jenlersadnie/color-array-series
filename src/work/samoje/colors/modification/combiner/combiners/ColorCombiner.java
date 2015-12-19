package work.samoje.colors.modification.combiner.combiners;

import java.awt.Color;

import work.samoje.colors.ColorModifier;

/**
 * Combines colors into one.
 *
 * @author Jennie Sadler
 */
/*
 * TODO: Introduce combine(Color firstParent, Color... parents) method to
 * support combinations of arbitrary amounts of colors.
 */
public interface ColorCombiner extends ColorModifier {

    /**
     * Given the two {@link Color}s, returns the one {@link Color}.
     *
     * @param parentLeft
     * @param parentRight
     * @return
     */
    public Color combine(final Color parentLeft, final Color parentRight);
}