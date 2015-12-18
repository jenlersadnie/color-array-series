package work.samoje.colors.combiner.combiners;

import java.util.Random;

public class RGBChooser extends BinaryValueWiseCombiner {
    private final Random rand = new Random();

    @Override
    protected int binaryCombine(final int left, final int right) {
        return rand.nextBoolean() ? left : right;
    }
}
