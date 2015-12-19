package work.samoje.colors.modification.filter.filters;

import java.awt.Color;

public class NoFilter implements Filter {

    @Override
    public Color filter(final Color a) {
        return a;
    }
}
