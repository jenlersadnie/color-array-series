package work.samoje.colors.modification.filter.filters;

import work.samoje.colors.ColorHelpers;

/**
 * Abstract filter instance which applies the filter with an intensity dictated
 * by the specified multiplier.
 *
 * @author Jennie Sadler
 */
public abstract class IntensityFilter implements Filter {
    private final double intensity;

    public IntensityFilter(final int multiplier, final int maxMultiplier) {
        this.intensity = (multiplier * 1.0) / maxMultiplier;
    }

    /**
     * Applies the filter intensity given the original color value and the
     * filtered color value.
     *
     * Does this with the following calculation:
     *
     * result = original(1 - intensity) + (filtered * intensity)
     *
     * @param original
     *            The original int value, before the filter is applied
     * @param filtered
     *            The filtered int value, to be applied by the configured
     *            intensity.
     * @return The result value, filtered to the correct intensity
     */
    protected int applyIntensity(final double original, final double filtered) {
        return ColorHelpers.boundByColorRange((original * (1.0 - intensity))
                + (filtered * intensity));
    }
}
