package work.samoje.colors.combiner.combiners;

import java.util.Random;
import java.util.stream.IntStream;

public abstract class BinaryValueWiseCombiner extends ValueWiseCombiner {
    protected final Random rand = new Random();

    @Override
    protected int combine(final IntStream intStream) {
        return intStream.reduce(rand.nextInt(255), (left, right) -> binaryCombine(left, right));
    }

    protected abstract int binaryCombine(int left, int right);
}