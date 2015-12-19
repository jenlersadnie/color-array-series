package work.samoje.colors.combiner.selection;

import work.samoje.colors.GenericSelectorBus;
import work.samoje.colors.GenericSelectorPanel;

public class CombinePanel extends GenericSelectorPanel<CombineMethod> {
    private static final long serialVersionUID = 1L;

    public CombinePanel(final GenericSelectorBus<CombineMethod> combineBus) {
        super(combineBus);
    }
}