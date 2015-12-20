package work.samoje.colors.modification.combiner.combiners;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RGBSumModTest {
    @Test
    public void test_combine_findsTheSumModulus() {
        for (int modulo = 1; modulo < 256; modulo++) {
            final RGBSumMod underTest = new RGBSumMod(modulo, 255);

            for (int i = 0; i < modulo; i++) {
                assertEquals(
                    String.format(" (%s + %s) mod %s = %s failed", 0, i, modulo, i),
                    i, underTest.combine(0, i));
                assertEquals(
                    String.format(" (%s + %s) mod %s = %s failed", i, 0, modulo, i),
                    i, underTest.combine(i, 0));
                assertEquals(
                    String.format(" (%s + %s) mod %s = %s failed", 0, i + modulo, modulo, i),
                    i, underTest.combine(0, i + modulo));
                assertEquals(
                    String.format(" (%s + %s) mod %s = %s failed", i + modulo, 0, modulo, i),
                    i, underTest.combine(i + modulo, 0));
            }
        }
    }
}
