package work.samoje.colors.filter.selection;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import work.samoje.colors.GenericSelectorBus;
import work.samoje.colors.filter.filters.Absolute;
import work.samoje.colors.filter.filters.AbsoluteRGB;
import work.samoje.colors.filter.filters.Filter;
import work.samoje.colors.filter.filters.MetaFilter;
import work.samoje.colors.filter.filters.NoFilter;

public class FilterBus extends GenericSelectorBus<FilterMethod> implements FilterProvider {
    protected static final int MAX_MULTIPLIER = 100;

    private EnumSet<FilterMethod> filters;
    private int multiplier;

    public FilterBus() {
        this.filters = EnumSet.of(FilterMethod.NONE);
        this.multiplier = 0;
    }

    @Override
    public EnumSet<FilterMethod> getAllOptions() {
        return EnumSet.allOf(FilterMethod.class);
    }

    @Override
    public EnumSet<FilterMethod> getEnumSetFor(final Set<String> selections) {
        return EnumSet.copyOf(selections.stream().map(str -> FilterMethod.valueOf(str)).collect(Collectors.toList()));
    }

    @Override
    public EnumSet<FilterMethod> getSelected() {
        return filters;
    }

    @Override
    public void update(final EnumSet<FilterMethod> filters, final int multiplier) {
        this.filters = filters;
        this.multiplier = multiplier;
        setChanged();
        notifyObservers();
    }

    @Override
    public Filter getFilter() {
        final List<Filter> chosenFilters = filters(multiplier).entrySet()
                .stream().filter(entry -> filters.contains(entry.getKey()))
                .map(entry -> entry.getValue())
                .collect(Collectors.<Filter> toList());
        return new MetaFilter(chosenFilters);
    }

    public Map<FilterMethod, Filter> filters(final int value) {
        final Map<FilterMethod, Filter> filterMap = new HashMap<>();
        filterMap.put(FilterMethod.NONE, new NoFilter());
        filterMap.put(FilterMethod.ABSOLUTE, new Absolute());
        filterMap.put(FilterMethod.ABSOLUTE_RGB, new AbsoluteRGB());
        // filterMap.put(FilterMethod.RGB_SCALE_AVG, new RGBScaleAvg(value,
        // VALUE_SELECTOR_RANGE));
        return filterMap;
    }

    @Override
    public FilterState getFilterState() {
        return new FilterState(filters, multiplier);
    }

    @Override
    public void load(final FilterState state) {
        update(state.getFilters(), state.getMultiplier());
    }
}
