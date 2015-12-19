package work.samoje.colors.modification.combiner.combiners;

/**
 * Combines colors by taking the modulus of the sum of each respective RGB
 * value. Modulo is determined by the multiplier.
 *
 * @author Jennie Sadler
 *
 */
public class RGBSumMod extends RGBValueWiseCombiner {
    private final int modulus;

    public RGBSumMod(final double modifier, final double modifierScale) {
        this.modulus = Math.max(1,
                (int) ((modifier * MAX_COLOR_VAL) / modifierScale));
    }

    @Override
    protected int combine(final int left, final int right) {
        return (left + right) % modulus;
    }
}