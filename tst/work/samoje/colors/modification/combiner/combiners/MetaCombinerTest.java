package work.samoje.colors.modification.combiner.combiners;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.Test;

public class MetaCombinerTest extends EasyMockSupport {

    /**
     * When this test fails, it means that in 1000ms, the random number
     * generator used by {@link MetaCombiner} failed to hit all integers in
     * range [0, 4]. While this can statistically happen, it is very concerning
     * if it is the case.
     */
    @Test(timeout = 1000)
    public void test_combine_shouldEventuallyUseAllCombiners() {
        final List<TestCombiner> combiners = new ArrayList<TestCombiner>();
        combiners.add(new TestCombiner());
        combiners.add(new TestCombiner());
        combiners.add(new TestCombiner());
        combiners.add(new TestCombiner());
        combiners.add(new TestCombiner());

        final MetaCombiner underTest = new MetaCombiner(combiners);
        final Color color = Color.BLACK;

        while (combiners.stream().anyMatch(combiner -> !combiner.hasBeenUsed())) {
            underTest.combine(color, color);
        }
    }

    private static class TestCombiner implements ColorCombiner {
        private boolean hasBeenUsed = false;

        @Override
        public Color combine(final Color parentLeft, final Color parentRight) {
            hasBeenUsed = true;
            return parentLeft;
        }

        public boolean hasBeenUsed() {
            return hasBeenUsed;
        }
    }
}
