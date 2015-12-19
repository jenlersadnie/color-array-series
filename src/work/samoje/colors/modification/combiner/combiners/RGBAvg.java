package work.samoje.colors.modification.combiner.combiners;

/**
 * Combines colors by taking the RGB value-wise average.
 *
 * @author Jennie Sadler
 */
public class RGBAvg extends RGBValueWiseCombiner {
    @Override
    public int combine(final int left, final int right) {
        return (int) ((left + right) / 2.0);
    }
}