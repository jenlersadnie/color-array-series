package work.samoje.colors.combiner.combiners;

public class RGBAvg extends BinaryValueWiseCombiner {
    @Override
    public int combine(final int left, final int right) {
        return (int) ((left + right) / 2.0);
    }
}