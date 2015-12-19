package work.samoje.colors.modification.filter.selection;

import java.util.EnumSet;

/**
 * Represents the state of the {@link FilterSelector}. This includes the active
 * {@link FilterMethod}s and the active multiplier.
 *
 * @author Jennie Sadler
 */
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
