package work.samoje.colors.grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import work.samoje.colors.modification.filter.filters.Filter;
import work.samoje.colors.modification.filter.selection.FilterSelector;
import work.samoje.colors.persistence.ImageSaver;
import work.samoje.colors.persistence.StateSaver;

/**
 * {@link JComponent} for displaying the active {@link ColorGrid} with the
 * active {@link Filter}.
 *
 * Observes updates from {@link FilterSelector} and {@link ColorGrid} to trigger
 * a repaint.
 *
 * @author Jennie Sadler
 *
 */
public class Canvas extends JComponent implements Observer {
    private static final long serialVersionUID = 1L;

    private final ColorGrid grid;
    private final FilterSelector filterProvider;
    private final ImageSaver imageSaver;
    private final StateSaver stateSaver;
    private final int boxSize;

    public Canvas(final ColorGrid grid, final FilterSelector filterProvider,
            final ExecutorService executor, final int boxSize) {
        this.grid = grid;
        this.filterProvider = filterProvider;
        this.imageSaver = new ImageSaver(executor);
        this.stateSaver = new StateSaver();
        this.boxSize = boxSize;
        initialize();

        this.grid.addObserver(this);
        this.filterProvider.addObserver(this);
    }

    /**
     * Initializes the grid and repaints.
     */
    public void initialize() {
        this.setPreferredSize(new Dimension((grid.getWidth() * boxSize) + 2,
                (grid.getHeight() * boxSize) + 2));
        grid.initialize();
        repaint();
    }

    /**
     * Overwrites the {@link ColorGrid} edges nearest the {@link Point}s
     * specified with the {@link Color} specified, then repaints.
     *
     * @param points
     *            Set of points on the canvas
     * @param color
     *            Color to write to the grid edges
     */
    public void writeColorToEdges(final Set<Point> points, final Color color) {
        grid.writeToNearestEdge(points.stream()
                .map(p -> getBlockForPosition(p)).collect(Collectors.toSet()),
                color);
        repaint();
    }

    /**
     * Gets the {@link ColorGrid} coordinates for the selected point. This
     * intermediate calculation is particularly useful when the box size is
     * greater than 1.
     *
     * @param position
     *            A position on the canvas.
     * @return The x, y coordinates on the color grid for the selected position.
     */
    public Point getBlockForPosition(final Point position) {
        final Point block = new Point(gridIndexOf(position.x),
                gridIndexOf(position.y));
        return block;
    }

    private int gridIndexOf(final int coordinate) {
        return (int) Math.floor((coordinate) / boxSize);
    }

    /**
     * For all points in the {@link ColorGrid}, paints a rectangle of the
     * configured box size on the canvas.
     *
     * @param g
     *            A reference to the active graphics object for this canvas.
     */
    private void fillGrid(final Graphics g) {
        final Filter filter = filterProvider.getFilter();
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                final Optional<Color> maybeColor = grid.getColorForPoint(x, y);
                if (maybeColor.isPresent()) {
                    g.setColor(filter.filter(maybeColor.get()));
                    g.fillRect(positionOf(x), positionOf(y), boxSize, boxSize);
                }
            }
        }
    }

    private int positionOf(final int index) {
        return (index * boxSize);
    }

    public void saveCapture(final String fileName) {
        imageSaver.saveImageForGrid(grid, filterProvider.getFilter(), fileName);
    }

    private CanvasState getCanvasState() {
        return new CanvasState(grid.getGridState(),
                filterProvider.getFilterState());
    }

    /**
     * Saves the active {@link CanvasState} using a {@link StateSaver}.
     *
     * @param fileName
     *            The file name to use for saving state.
     */
    public void saveState(final String fileName) {
        stateSaver.saveState(getCanvasState(), fileName);
    }

    @Override
    public void paintComponent(final Graphics g) {
        fillGrid(g);
    }

    @Override
    public void update(final Observable o, final Object arg) {
        repaint();
    }
}