package work.samoje.colors.filter.filters;

import java.awt.Color;

public class Absolute implements Filter {
    @Override
    public Color filter(final Color a) {
        final int c = abs(a);
        return new Color(c, c, c);
    }

    private int abs(final Color a) {
        return ((a.getRed() + a.getGreen() + a.getBlue()) > (255 * 3 / 2)) ? 255
                : 0;
    }
}