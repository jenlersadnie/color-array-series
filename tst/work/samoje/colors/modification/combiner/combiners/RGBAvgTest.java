package work.samoje.colors.modification.combiner.combiners;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RGBAvgTest {
    @Test
    public void test_combine_findsTheAverage() {
        final RGBAvg underTest = new RGBAvg();
        assertEquals(3, underTest.combine(0, 6));
    }
}
