package work.samoje.colors.modification.filter.filters;

import java.awt.Color;

import work.samoje.colors.modification.ColorModifier;

/**
 * Filters a color to produce another.
 *
 * @author Jennie Sadler
 */
public interface Filter extends ColorModifier {
    public Color filter(Color a);
}
