package work.samoje.colors.combiner.combiners;

import java.util.stream.IntStream;

public class RGBAvg extends ValueWiseCombiner {

    @Override
    public int combine(final IntStream parentVal)
    {
        return (int) parentVal.average().getAsDouble();
    }
}