package work.samoje.colors.structures;

import java.awt.Color;
import java.util.Optional;

public interface ColorStructure {
    public void initialize();
    public void recalculateContents();

    public void overrideValues(final int x, final int y, final Color color);

    public Optional<Color> getColorForPoint(final int x, final int y);
}
