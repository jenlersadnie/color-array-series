package work.samoje.colors.filter.selection;

import java.util.EnumSet;

public class FilterState {
    private final EnumSet<FilterMethod> filters;
    private final int multiplier;

    public FilterState(final EnumSet<FilterMethod> filters, final int multiplier) {
        this.filters = filters;
        this.multiplier = multiplier;
    }

    public EnumSet<FilterMethod> getFilters() {
        return filters;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
