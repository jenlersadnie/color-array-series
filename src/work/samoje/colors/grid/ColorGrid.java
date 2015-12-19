package work.samoje.colors.grid;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.Set;

import work.samoje.colors.combiner.combiners.ColorCombiner;
import work.samoje.colors.combiner.selection.ColorCombinerBus;
import work.samoje.colors.combiner.selection.CombinerProvider;

/**
 * Represents a 2D color grid.
 */
public class ColorGrid extends Observable implements Observer {
    private final ColorCombinerBus combinerProvider;
    private final Color[][] grid;
    private final int height;
    private final int width;

    /**
     * Default constructor. Instantiates grid and registers as an observer with
     * the {@link CombinerProvider}.
     *
     * @param height
     *      The height specification for the {@link ColorGrid}.
     * @param width
     *      The width specification for the {@link ColorGrid}.
     * @param combinerProvider
     *      The {@link CombinerProvider} instance to be used for calculating
     *      grid contents.
     */
    public ColorGrid(final int height, final int width,
            final ColorCombinerBus combinerProvider) {
        this.height = height;
        this.width = width;
        this.grid = new Color[height][width];
        this.combinerProvider = combinerProvider;
        this.combinerProvider.addObserver(this);
    }

    /**
     * Gets the height of the color grid.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the color grid.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Initializes and recalculates color grid contents using the
     * {@link CombinerProvider}'s active {@link ColorCombiner} instance.
     *
     * Grid is initialized first by setting the origin and the two orthogonally
     * adjacent positions to hard-coded values. Next, grid edges are
     * instantiated by combining the two adjacent edge values which have already
     * been initialized.
     *
     * For example, on the 4x4 color grid below, and coordinates (x,y)
     *  -> (0,0), (1,0), and (0,1) are initialized to hard-coded colors
     *  -> (2,0) = combined((0,0), (1,0)), (3,0) = combined((1,0), (2,0))
     *  -> (0,2) = combined((0,0), (0,1)), (0,3) = combined((0,1), (0,2))
     *  -> All "-----"  positions in the grid are populated during recalculation
     *
     *   y
     * x-+------------------------>
     *   |(0,0)|(1,0)|(2,0)|(3,0)|
     *   |(0,1)|-----|-----|-----|
     *   |(0,2)|-----|-----|-----|
     *   |(0,3)|-----|-----|-----|
     *   V
     */
    public void initialize() {
        final ColorCombiner combiner = combinerProvider.getCombiner();

        grid[0][0] = Color.RED;
        grid[1][0] = Color.YELLOW;
        grid[0][1] = Color.CYAN;

        for (int y = 2; y < height; y++) {
            grid[y][0] = combiner.combine(grid[y - 1][0], grid[y - 2][0]);
        }
        for (int x = 2; x < width; x++) {
            grid[0][x] = combiner.combine(grid[0][x - 1], grid[0][x - 2]);
        }
        recalculateContents();
    }

    /**
     * Recalculates grid contents based on existing grid edge values and using
     * the active {@link CombinerProvider}'s {@link ColorCombiner}.
     *
     * Each grid position is calculated by combining the horizontal and vertical
     * parent's colors, i.e. (x, y) = combine((x, y-1), (x-1, y))
     *
     * For example, on the 4x4 color grid below,
     *  -> (1,1) = combine((1,0), (0,1))
     *  -> (3,1) = combine((3,0), (2,1))
     *  -> (1,2) = combine((1,1), (0,2))
     *  -> (2,2) = combine((2,1), (1,2)) and so on..
     *
     *   y
     * x-+------------------------>
     *   |(0,0)|(1,0)|(2,0)|(3,0)|
     *   |(0,1)|(1,1)|(2,1)|(3,1)|
     *   |(0,2)|(1,2)|(2,2)|-----|
     *   |(0,3)|-----|-----|-----|
     *   V
     */
    private void recalculateContents() {
        final ColorCombiner combiner = combinerProvider.getCombiner();
        for (int y = 1; y < height; y++) {
            for (int x = 1; x < width; x++) {
                grid[y][x] = combiner.combine(grid[y - 1][x], grid[y][x - 1]);
            }
        }
    }

    /**
     * Returns an optional {@link Color} at the provided (x, y) position.
     * Coordinates follow an inverted Cartesian plan, wherein the X axis
     * is the horizontal axis, Y axis is the vertical axis, and the
     * origin is positioned at the top left.
     *
     *   y
     * x-+------------>
     *   |(0,0)|(0,1)|
     *   |(1,0)|(1,1)|
     *   |(2,0)|(2,1)|
     *   V
     *
     * @param x
     *      Horizontal position of the {@link Color} point desired
     * @param y
     *      Vertical position of the {@link Color} point desired
     *
     * @return {@link Optional} {@link Color} value, empty if value is
     *      null or position is not on the grid.
     *
     */
    protected Optional<Color> getColorForPoint(final int x, final int y) {
        if (y < 0 || y > height || x < 0 || x > width) {
            return Optional.empty();
        }
        return Optional.ofNullable(grid[y][x]);
    }

    public Color[] getColorColumn(final int y) {
        if (y < 0 || y > height) {
            throw new IllegalArgumentException(
                    String.format("Column %s does not exist!", y));
        }
        return grid[y].clone();
    }

    /**
     * Overwrites the edge positions nearest to the given {@link Point}s
     * with the specified {@link Color}, then recalculates.
     *
     * @param points The {@link Point}s used for finding the nearest edges.
     * @param color The {@link Color} to write to the edge positions.
     */
    public void writeToNearestEdge(final Set<Point> points, final Color color) {
        for (final Point point : points) {
            writeToNearestEdge(point.x, point.y, color);
        }
        recalculateContents();
    }

    private void writeToNearestEdge(final int x, final int y, final Color color) {
        if (x == 0 || y == 0) {
            grid[y][x] = color;
        } else if (x > y) {
            grid[0][Math.min(x, width - 1)] = color;
        } else if (y > x) {
            grid[Math.min(y, height - 1)][0] = color;
        }
    }

    /**
     * Returns a {@link GridState} object representing the
     * active {@link ColorGrid} configuration.
     *
     * @return The {@link GridState}
     */
    public GridState getGridState() {
        return new GridState(combinerProvider.getCombinerState());
    }

    /**
     * The {@link Observer} override for {@link ColorGrid}.
     * Triggered by {@link CombinerProvider} changes.
     * Triggers changes which notify any observers.
     */
    @Override
    public void update(final Observable o, final Object arg) {
        recalculateContents();
        setChanged();
        notifyObservers();
    }
}
