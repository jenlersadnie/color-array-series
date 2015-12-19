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

import javax.swing.JComponent;

import work.samoje.colors.filter.filters.Filter;
import work.samoje.colors.filter.selection.FilterBus;
import work.samoje.colors.persistence.ImageSaver;
import work.samoje.colors.persistence.StateSaver;

public class Canvas extends JComponent implements Observer {
    private static final long serialVersionUID = 1L;
    private static final int BOX_WIDTH = 1;

    private final ColorGrid grid;
    private final FilterBus filterProvider;
    private final ImageSaver imageSaver;
    private final StateSaver stateSaver;

    public Canvas(final ColorGrid grid, final FilterBus filterProvider, final ExecutorService executor) {
        this.grid = grid;
        this.filterProvider = filterProvider;
        this.imageSaver = new ImageSaver(executor);
        this.stateSaver = new StateSaver();
        initialize();

        this.grid.addObserver(this);
        this.filterProvider.addObserver(this);
    }

    public void initialize() {
        grid.initialize();
        repaint();
    }

    public Point getBlockForPosition(final Point position) {
        final Point block = new Point(indexOf(position.x), indexOf(position.y));
        return block;
    }

    public void writeColorToPoints(final Set<Point> points, final Color color) {
        grid.writeToNearestEdge(points, color);
        repaint();
    }

    private int indexOf(final int coordinate) {
        return (int) Math.floor((coordinate + 1) / BOX_WIDTH);
    }

    private int positionOf(final int index) {
        return 1 + (index * BOX_WIDTH);
    }

    private void fillGrid(final Graphics g) {
        final Filter filter = filterProvider.getFilter();
        for (int w = 0; w < grid.getHeight(); w++) {
            for (int h = 0; h < grid.getWidth(); h++) {
                final Optional<Color> maybeColor = grid.getColorForPoint(h, w);
                if (maybeColor.isPresent()) {
                    g.setColor(filter.filter(maybeColor.get()));
                    g.fillRect(positionOf(h), positionOf(w), BOX_WIDTH,
                            BOX_WIDTH);
                }
            }
        }
    }

    public void saveCapture(final String fileName) {
        imageSaver.saveImageForGrid(grid, filterProvider.getFilter(), fileName);
    }

    private CanvasState getCanvasState() {
        return new CanvasState(grid.getGridState(), filterProvider.getFilterState());
    }

    public void saveState(final String fileName) {
        stateSaver.saveState(getCanvasState(), fileName);
    }

    @Override
    public void paintComponent(final Graphics g) {
        fillGrid(g);
    }

    /**
     * The component will look bad if it is sized smaller than this
     */
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(50, 30);
    }

    /**
     * The component will look best at this size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension((grid.getWidth() * BOX_WIDTH) + 2,
                (grid.getHeight() * BOX_WIDTH) + 2);
    }

    @Override
    public void update(final Observable o, final Object arg) {
        repaint();
    }
}