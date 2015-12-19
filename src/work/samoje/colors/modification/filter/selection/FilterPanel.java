package work.samoje.colors.modification.filter.selection;

import work.samoje.colors.modification.ColorModifierSelector;
import work.samoje.colors.modification.GenericSelectorPanel;

/**
 * {@link GenericSelectorPanel} for mixing {@link FilterMethod}s in the
 * {@link FilterSelector}.
 *
 * @author Jennie Sadler
 */
public class FilterPanel extends GenericSelectorPanel<FilterMethod> {
    private static final long serialVersionUID = 1L;

    public FilterPanel(final ColorModifierSelector<FilterMethod> filterBus) {
        super(filterBus);
    }
}