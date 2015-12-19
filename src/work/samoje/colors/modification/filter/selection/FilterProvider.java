package work.samoje.colors.modification.filter.selection;

import work.samoje.colors.modification.filter.filters.Filter;

/**
 * Provides {@link Filter} instances. Also allows state preservation and
 * loading.
 *
 * @author Jennie Sadler
 */
public interface FilterProvider {
    /**
     * Returns a {@link Filter} instance based on the active {@link FilterState}
     * .
     *
     * @return A {@link Filter} instance
     */
    public Filter getFilter();

    /**
     * Returns the active {@link FilterState}.
     *
     * @return A {@link FilterState} instance
     */
    public FilterState getFilterState();

    /**
     * Loads the provided {@link FilterState}
     *
     * @param state
     *            A {@link FilterState} instance
     */
    public void load(FilterState state);
}
