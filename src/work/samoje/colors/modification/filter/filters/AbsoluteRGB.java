package work.samoje.colors.modification.filter.filters;

import work.samoje.colors.ColorHelpers;

/**
 * {@link IntensityFilter} which, at full intensity, pushes the RGB values of a
 * pixel to their nearest absolutes (0 or 255).
 *
 * @author Jennie Sadler
 *
 */
public class AbsoluteRGB extends RGBValueWiseFilter {
    public AbsoluteRGB(final int multiplier, final int maxMultiplier) {
        super(multiplier, maxMultiplier);
    }

    @Override
    protected int filter(final int value) {
        return applyIntensity(value,
                ColorHelpers.getNearestAbsoluteValue(value));
    }
}