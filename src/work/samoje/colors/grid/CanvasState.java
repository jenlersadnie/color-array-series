package work.samoje.colors.grid;

import work.samoje.colors.modification.filter.selection.FilterState;

/**
 * Represents the state of a {@link Canvas} instance. This includes
 * {@link GridState}, {@link FilterState}.
 *
 * @author Jennie Sadler
 *
 */
public class CanvasState {
    private final GridState gridState;
    private final FilterState filterState;

    public CanvasState(final GridState gridState, final FilterState filterState) {
        this.gridState = gridState;
        this.filterState = filterState;
    }

    public GridState getGridState() {
        return gridState;
    }

    public FilterState getFilterState() {
        return filterState;
    }
}
