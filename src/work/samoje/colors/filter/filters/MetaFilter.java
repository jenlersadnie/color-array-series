package work.samoje.colors.filter.filters;

import java.awt.Color;
import java.util.List;
import java.util.Random;

public class MetaFilter implements Filter {
    private final List<Filter> filters;

    public MetaFilter(final List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public Color filter(final Color a) {
        final Random rand = new Random();
        final int choice = rand.nextInt(filters.size());
        return filters.get(choice).filter(a);
    }
}
