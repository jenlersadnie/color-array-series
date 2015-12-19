package work.samoje.colors.modification.combiner.selection;

import java.util.EnumSet;

/**
 * Represents the state of the {@link CombinerSelector}. This includes the
 * active {@link CombineMethod}s and the active multiplier.
 *
 * @author Jennie Sadler
 */
public class CombinerState {
    private final EnumSet<CombineMethod> combiners;
    private final int multiplier;

    public CombinerState(final EnumSet<CombineMethod> combiners,
            final int multiplier) {
        this.combiners = combiners;
        this.multiplier = multiplier;
    }

    public EnumSet<CombineMethod> getCombiners() {
        return combiners;
    }

    public int getMultiplier() {
        return multiplier;
    }
}