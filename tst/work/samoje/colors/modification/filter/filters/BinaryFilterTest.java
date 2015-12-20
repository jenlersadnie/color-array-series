package work.samoje.colors.modification.filter.filters;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

public class BinaryFilterTest {
    @Test
    public void test_filter_absolutesAreUnchanged() {
        final int maxMultiplier = 100;
        for (int multiplier = 0; multiplier <= maxMultiplier; multiplier++) {
            final BinaryFilter underTest = new BinaryFilter(multiplier,
                    maxMultiplier);
            assertEquals(Color.WHITE, underTest.filter(Color.WHITE));
            assertEquals(Color.BLACK, underTest.filter(Color.BLACK));
        }
    }

    /**
     * Tests a sampling of the color spectrum for {@link BinaryFilter} behavior.
     * If you modify this test to look at all possible colors, it takes about 10
     * seconds to run.
     */
    @Test
    public void test_filter_testRoughColorSpectru() {
        final BinaryFilter underTest = new BinaryFilter(1, 1);
        for (int r = 0; r < 256; r += 5) {
            for (int g = 0; g < 256; g += 7) {
                for (int b = 0; b < 256; b += 11) {
                    final Color color = new Color(r, g, b);
                    if ((r + g + b) > (255 * 3.0 / 2)) {
                        assertEquals(String.format(
                                "Expected %s to map to WHITE", color),
                                Color.WHITE, underTest.filter(color));
                    } else {
                        assertEquals(String.format(
                                "Expected %s to map to BLACK", color),
                                Color.BLACK, underTest.filter(color));
                    }
                }
            }
        }
    }
}
