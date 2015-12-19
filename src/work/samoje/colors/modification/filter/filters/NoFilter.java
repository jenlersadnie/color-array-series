package work.samoje.colors.modification.filter.filters;

import java.awt.Color;

/**
 * The identity filter.
 *
 * #nofilter
 *
 * @author Jennie Sadler
 */
public class NoFilter implements Filter {
    @Override
    public Color filter(final Color a) {
        return a;
    }
}
