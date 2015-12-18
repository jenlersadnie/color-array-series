package work.samoje.colors.filter.selection;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.stream.Collectors;

import work.samoje.colors.filter.filters.Absolute;
import work.samoje.colors.filter.filters.AbsoluteRGB;
import work.samoje.colors.filter.filters.Filter;
import work.samoje.colors.filter.filters.MetaFilter;
import work.samoje.colors.filter.filters.NoFilter;

public class FilterBus extends Observable implements FilterProvider {
    protected static final int MAX_MULTIPLIER = 100;

    private EnumSet<FilterMethod> filters;
    private int multiplier;

    public FilterBus() {
        this.filters = EnumSet.of(FilterMethod.NONE);
        this.multiplier = 0;
    }

    public void update(final EnumSet<FilterMethod> filters, final int multiplier)
    {
        if (filters.isEmpty()) {
            this.filters = EnumSet.allOf(FilterMethod.class);
        } else {
            this.filters = filters;
        }
        this.multiplier = multiplier;
        notifyObservers();
    }

    @Override
    public Filter getFilter() {
        final List<Filter> chosenFilters = filters(multiplier).entrySet().stream()
                .filter(entry -> filters.contains(entry.getKey()))
                .map(entry -> entry.getValue())
                .collect(Collectors.<Filter> toList());
        return new MetaFilter(chosenFilters);
    }

    public Map<FilterMethod, Filter> filters(final int value)
    {
        final Map<FilterMethod, Filter> filterMap = new HashMap<>();
        filterMap.put(FilterMethod.NONE, new NoFilter());
        filterMap.put(FilterMethod.ABSOLUTE, new Absolute());
        filterMap.put(FilterMethod.ABSOLUTE_RGB, new AbsoluteRGB());
        //filterMap.put(FilterMethod.RGB_SCALE_AVG, new RGBScaleAvg(value, VALUE_SELECTOR_RANGE));
        return filterMap;
    }
}
