package work.samoje.colors.grid;

import work.samoje.colors.modification.combiner.selection.CombinerState;

/**
 * Represents the state of a {@link ColorGrid}. This includes the
 * {@link CombinerState}, but does not include any details about the color grid
 * contents (i.e. the color values in the grid).
 *
 * @author Jennie Sadler
 *
 */
public class GridState {
    private final CombinerState combinerState;

    public GridState(final CombinerState combinerState) {
        this.combinerState = combinerState;
    }

    public CombinerState getCombinerState() {
        return combinerState;
    }
}
