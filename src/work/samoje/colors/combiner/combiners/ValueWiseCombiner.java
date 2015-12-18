package work.samoje.colors.combiner.combiners;

import java.awt.Color;
import java.util.Arrays;
import java.util.stream.IntStream;

public abstract class ValueWiseCombiner implements ColorCombiner {
    @Override
    public Color combine(final Color... parents) {
        return new Color(
                combine(Arrays.stream(parents).mapToInt(p -> p.getRed())),
                combine(Arrays.stream(parents).mapToInt(p -> p.getGreen())),
                combine(Arrays.stream(parents).mapToInt(p -> p.getBlue())));
    }

    protected abstract int combine(final IntStream intStream);
}