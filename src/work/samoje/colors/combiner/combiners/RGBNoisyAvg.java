package work.samoje.colors.combiner.combiners;

import java.util.Random;

/**
 * Combines colors by taking the RGB value-wise average, and adding in some noise.
 *
 * @author Jennie Sadler
 */
public class RGBNoisyAvg extends RGBValueWiseCombiner {
    private final Random rand = new Random();
    private final double colorNoiseMultiplier;

    public RGBNoisyAvg(final double multiplier, final double maxMultiplier) {
        this.colorNoiseMultiplier = (multiplier * MAX_COLOR_VAL) / maxMultiplier;
    }

    @Override
    protected int combine(final int left, final int right) {
        return boundByColorRange(avg(left, right + noise()));
    }

    private int noise() {
        final int plusOrMinusValue = Math.max(1, (int) colorNoiseMultiplier);
        return (rand.nextInt(plusOrMinusValue * 2) - plusOrMinusValue);
    }
}