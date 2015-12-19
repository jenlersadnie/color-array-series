package work.samoje.colors.modification.combiner.combiners;

import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * The one combiner to rule them all. Combines colors by randomly
 * choosing a {@link ColorCombiner} from the specified list.
 *
 * @author Jennie Sadler
 */
public class MetaCombiner implements ColorCombiner {
    private final List<ColorCombiner> combiners;
    private final Random rand = new Random();

    /**
     * Default constructor.
     *
     * @param combiners The list of {@link ColorCombiner}s
     * to choose from.
     */
    public MetaCombiner(final List<ColorCombiner> combiners) {
        this.combiners = combiners;
    }

    @Override
    public Color combine(final Color parentLeft, final Color parentRight) {
        final int choice = rand.nextInt(combiners.size());
        return combiners.get(choice).combine(parentLeft, parentRight);
    }
}
