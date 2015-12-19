package work.samoje.colors.combiner.combiners;

import java.util.Random;

/**
 * When combining two colors, randomly chooses each RGB value from one of the
 * parents.
 *
 * @author Jennie Sadler
 *
 */
public class RGBChooser extends RGBValueWiseCombiner {
    private final Random rand = new Random();

    @Override
    protected int combine(final int left, final int right) {
        return rand.nextBoolean() ? left : right;
    }
}
