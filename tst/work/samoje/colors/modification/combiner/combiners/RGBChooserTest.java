package work.samoje.colors.modification.combiner.combiners;

import java.awt.Color;

import org.easymock.EasyMockSupport;
import org.junit.Test;

public class RGBChooserTest extends EasyMockSupport {
    /**
     * When this test fails, it means that in 1000ms, the random boolean
     * generator used by {@link RGBChooser} failed to hit both true and false
     * during a single color combination. While this can statistically happen,
     * it is very concerning if it is the case.
     */
    @Test(timeout = 1000)
    public void test_combine_shouldEventuallyUseLeftAndRightValues() {
        final RGBChooser underTest = new RGBChooser();
        final Color colorLeft = Color.BLACK;
        final Color colorRight = Color.WHITE;

        Color combined = underTest.combine(colorLeft, colorRight);
        while (combined.equals(colorLeft) || combined.equals(colorRight)) {
            combined = underTest.combine(colorLeft, colorRight);
        }
    }
}
