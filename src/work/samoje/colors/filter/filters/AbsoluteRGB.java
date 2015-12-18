package work.samoje.colors.filter.filters;

import java.awt.Color;

public class AbsoluteRGB implements Filter {
    @Override
    public Color filter(final Color a) {
        return new Color(abs(a.getRed()), abs(a.getGreen()), abs(a.getBlue()));
    }

    private int abs(final int a) {
        return (a > (255 / 2)) ? 255 : 0;
    }
}