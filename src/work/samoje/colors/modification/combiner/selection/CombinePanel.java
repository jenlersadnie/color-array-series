package work.samoje.colors.modification.combiner.selection;

import work.samoje.colors.ColorModifierSelector;
import work.samoje.colors.GenericSelectorPanel;

/**
 * {@link GenericSelectorPanel} for mixing {@link CombineMethod}s in the
 * {@link CombinerSelector}.
 *
 * @author Jennie Sadler
 */
public class CombinePanel extends GenericSelectorPanel<CombineMethod> {
    private static final long serialVersionUID = 1L;

    public CombinePanel(final ColorModifierSelector<CombineMethod> combineBus) {
        super(combineBus);
    }
}