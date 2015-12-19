package work.samoje.colors.grid;

import work.samoje.colors.combiner.selection.CombinerState;

public class GridState {
    private final CombinerState combinerState;

    public GridState(final CombinerState combinerState) {
        this.combinerState = combinerState;
    }

    public CombinerState getCombinerState() {
        return combinerState;
    }
}
