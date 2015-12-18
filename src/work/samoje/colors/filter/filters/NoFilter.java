package work.samoje.colors.filter.filters;

import java.awt.Color;

public class NoFilter implements Filter {

    @Override
    public Color filter(final Color a) {
        return a;
    }

}
