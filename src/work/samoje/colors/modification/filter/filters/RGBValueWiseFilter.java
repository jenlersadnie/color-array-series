package work.samoje.colors.modification.filter.filters;

import java.awt.Color;

/**
 * Abstract implementation of {@link Filter} for any filter which acts by
 * filtering respective RGB values of the color using a single filter method.
 *
 * @author Jennie Sadler
 *
 */
public abstract class RGBValueWiseFilter extends IntensityFilter {
    public RGBValueWiseFilter(final int multiplier, final int maxMultiplier) {
        super(multiplier, maxMultiplier);
    }

    @Override
    public Color filter(final Color color) {
        return new Color(filter(color.getRed()), filter(color.getGreen()),
                filter(color.getBlue()));
    }

    protected abstract int filter(int value);
}