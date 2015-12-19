package work.samoje.colors.modification.combiner.combiners;

/**
 * Combines colors by taking the RGB value-wise average, and scaling the value
 * by the configured multiplier.
 *
 * @author Jennie Sadler
 */
public class RGBScaleAvg extends RGBValueWiseCombiner {
    private static final double MULTIPLIER_CONSTANT = 0.25;
    private static final double MULTIPLIER_SCALE = 0.5;

    private final double colorValueMuliplier;

    public RGBScaleAvg(final double multiplier, final double multiplierScale) {
        this.colorValueMuliplier = MULTIPLIER_CONSTANT
                + (multiplier * MULTIPLIER_SCALE) / multiplierScale;
    }

    @Override
    protected int combine(final int left, final int right) {
        return boundByColorRange(Math.floor((left + right) * colorValueMuliplier));
    }
}