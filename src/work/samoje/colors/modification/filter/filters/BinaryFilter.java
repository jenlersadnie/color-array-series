package work.samoje.colors.modification.filter.filters;

import java.awt.Color;

/**
 * {@link IntensityFilter} which, at full intensity, produces a binary version
 * of the image. Does this by averaging all RGB values at a given point and
 * mapping it to the nearest absolute color value (0 or 255).
 *
 * @author Jennie Sadler
 */
public class BinaryFilter extends IntensityFilter {
    public BinaryFilter(final int multiplier, final int maxMultiplier) {
        super(multiplier, maxMultiplier);
    }

    @Override
    public Color filter(final Color original) {
        final int filteredValue = getBinaryValueFor(original);
        return new Color(applyIntensity(original.getRed(), filteredValue),
                applyIntensity(original.getGreen(), filteredValue),
                applyIntensity(original.getBlue(), filteredValue));
    }

    private int getBinaryValueFor(final Color a) {
        return ColorHelpers
                .getNearestAbsoluteValue((a.getRed() + a.getGreen() + a
                        .getBlue()) / 3);
    }
}