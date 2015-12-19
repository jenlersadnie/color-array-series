package work.samoje.colors.colors;

import org.junit.Assert;
import org.junit.Test;

import work.samoje.colors.ColorHelpers;

public class ColorHelpersTest {
    @Test
    public void test_getNearestAbsoluteValue_returns0forMidpoint() {
        Assert.assertEquals(0, ColorHelpers.getNearestAbsoluteValue(255.0 / 2));
    }

    @Test
    public void test_getNearestAbsoluteValue_roundsDown() {
        for (double i = 1; i < 127; i++) {
            Assert.assertEquals(0, ColorHelpers.getNearestAbsoluteValue(i));
        }
    }

    @Test
    public void test_getNearestAbsoluteValue_roundsUp() {
        for (double i = 128; i < 256; i++) {
            Assert.assertEquals(255, ColorHelpers.getNearestAbsoluteValue(i));
        }
    }

    @Test
    public void test_boundByColorRange_returnsValuesInRange() {
        for (double i = 0; i < 256; i++) {
            Assert.assertEquals((int) i, ColorHelpers.boundByColorRange(i));
        }
    }

    @Test
    public void test_boundByColorRange_roundsUp() {
        for (double i = -1; i > -255 * 2; i--) {
            Assert.assertEquals(0, ColorHelpers.boundByColorRange(i));
        }
    }

    @Test
    public void test_boundByColorRange_roundsDown() {
        for (double i = 256; i < 255 * 2; i++) {
            Assert.assertEquals(255, ColorHelpers.boundByColorRange(i));
        }
    }
}
