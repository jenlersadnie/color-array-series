package work.samoje.colors.combiner.combiners;

import java.awt.Color;
import java.util.List;
import java.util.Random;

public class MetaCombiner implements ColorCombiner {
    private final List<ColorCombiner> combiners;
    private final Random rand = new Random();

    public MetaCombiner(final List<ColorCombiner> combiners) {
        this.combiners = combiners;
    }

    @Override
    public Color combine(final Color parentLeft, final Color parentRight) {
        final int choice = rand.nextInt(combiners.size());
        return combiners.get(choice).combine(parentLeft, parentRight);
    }
}
