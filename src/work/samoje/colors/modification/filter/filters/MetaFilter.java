package work.samoje.colors.modification.filter.filters;

import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * The one filter to rule them all. Filters by randomly choosing a
 * {@link Filter} from the specified list.
 *
 * @author Jennie Sadler
 */
public class MetaFilter implements Filter {
    private final List<Filter> filters;
    final Random rand = new Random();

    public MetaFilter(final List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public Color filter(final Color a) {
        final int choice = rand.nextInt(filters.size());
        return filters.get(choice).filter(a);
    }
}
