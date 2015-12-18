package work.samoje.colors.filter.selection;

import java.util.EnumSet;

import work.samoje.colors.filter.filters.Filter;

public interface FilterProvider {
    public Filter getFilter();

    public EnumSet<FilterMethod> getFilterMethods();

    public FilterState getFilterState();
    public void load(FilterState state);
}
