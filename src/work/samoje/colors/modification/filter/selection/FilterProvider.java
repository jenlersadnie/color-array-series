package work.samoje.colors.modification.filter.selection;

import work.samoje.colors.modification.filter.filters.Filter;

public interface FilterProvider {
    public Filter getFilter();

    public FilterState getFilterState();
    public void load(FilterState state);
}
