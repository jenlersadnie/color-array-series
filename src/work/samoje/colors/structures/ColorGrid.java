package work.samoje.colors.structures;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.Set;

import work.samoje.colors.combiner.combiners.ColorCombiner;
import work.samoje.colors.combiner.selection.ColorCombinerBus;

public class ColorGrid extends Observable implements ColorStructure, Observer {
    private final ColorCombinerBus combinerProvider;
    private final Color[][] grid;
    private final int width;
    private final int height;

    public ColorGrid(final int width, final int height,
            final ColorCombinerBus combinerProvider) {
        this.width = width;
        this.height = height;
        this.grid = new Color[width][height];
        this.combinerProvider = combinerProvider;

        this.combinerProvider.addObserver(this);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    @Override
    public void initialize() {
        final ColorCombiner combiner = combinerProvider.getCombiner();

        grid[0][0] = Color.RED;
        grid[1][0] = Color.YELLOW;
        grid[0][1] = Color.CYAN;

        for (int w = 2; w < width; w++) {
            grid[w][0] = combiner.combine(grid[w - 1][0], grid[w - 2][0]);
        }
        for (int h = 2; h < height; h++) {
            grid[0][h] = combiner.combine(grid[0][h - 1], grid[0][h - 2]);
        }
        recalculateContents();
    }

    @Override
    public void recalculateContents() {
        final ColorCombiner combiner = combinerProvider.getCombiner();
        for (int w = 1; w < width; w++) {
            for (int h = 1; h < height; h++) {
                grid[w][h] = combiner.combine(grid[w - 1][h], grid[w][h - 1]);
            }
        }
    }

    public Optional<Color> getColorForPoint(final int x, final int y) {
        if (x < 0 || x > width || y < 0 || y > height) {
            return Optional.empty();
        }
        return Optional.ofNullable(grid[x][y]);
    }

    public void writeColorToPoints(final Set<Point> points, final Color color)
    {
        for (final Point point : points) {
            writeColorToCoords(point.x, point.y, color);
        }
        recalculateContents();
    }

    private void writeColorToCoords(final int x, final int y, final Color color) {
        if (x == 0 || y == 0) {
            grid[y][x] = color;
        } else if (x > y) {
            grid[0][x] = color;
        } else if (y > x) {
            grid[y][0] = color;
        }
    }

    @Deprecated
    public Color[][] getRawGrid() {
        return grid;
    }

    @Override
    public void update(final Observable o, final Object arg) {
        recalculateContents();
        setChanged();
        notifyObservers();
    }
}
