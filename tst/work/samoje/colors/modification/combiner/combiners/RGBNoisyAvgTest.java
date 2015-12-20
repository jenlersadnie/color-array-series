package work.samoje.colors.modification.combiner.combiners;

import java.awt.Color;
import java.util.Random;

import org.junit.Test;

public class RGBNoisyAvgTest {
    Random rand = new Random();

    /**
     * Generates high confidence through a large number of trials that it will
     * not try to generate an exceptional Color instance.
     */
    @Test
    public void test_combine_sanityTest() {
        final int maxMultiplier = 100;
        for (double multiplier = 0.5; multiplier <= maxMultiplier; multiplier++) {
            final RGBNoisyAvg underTest = new RGBNoisyAvg(multiplier,
                    maxMultiplier);
            for (int times = 0; times < 10000; times++) {
                final Color parentLeft = new Color(rand.nextInt(256 * 3));
                final Color parentRight = new Color(rand.nextInt(256 * 3));
                underTest.combine(parentLeft, parentRight);
            }
        }
    }
}
