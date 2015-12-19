package work.samoje.colors.filter.selection;

import work.samoje.colors.GenericSelectorBus;
import work.samoje.colors.GenericSelectorPanel;

public class FilterPanel extends GenericSelectorPanel<FilterMethod> {
    private static final long serialVersionUID = 1L;

    public FilterPanel(final GenericSelectorBus<FilterMethod> filterBus) {
        super(filterBus);
    }
}