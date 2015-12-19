package work.samoje.colors.modification.combiner.selection;

import work.samoje.colors.modification.combiner.combiners.ColorCombiner;

/**
 * Provides {@link ColorCombiner} instances. Also allows state preservation and
 * loading.
 *
 * @author Jennie Sadler
 */
public interface CombinerProvider {
    /**
     * Returns a {@link ColorCombiner} instance based on the active
     * {@link CombinerState}.
     *
     * @return A {@link ColorCombiner} instance
     */
    public ColorCombiner getCombiner();

    /**
     * Returns the active {@link CombinerState}.
     *
     * @return A {@link CombinerState} instance
     */
    public CombinerState getCombinerState();

    /**
     * Loads the provided {@link CombinerState}
     *
     * @param state
     *            A {@link CombinerState} instance
     */
    public void load(CombinerState state);
}
