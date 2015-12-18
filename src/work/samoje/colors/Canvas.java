package work.samoje.colors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.Set;

import javax.swing.JComponent;

import work.samoje.colors.filter.filters.Filter;
import work.samoje.colors.filter.selection.FilterBus;
import work.samoje.colors.persistence.ImageSaver;

public class Canvas extends JComponent implements Observer {
    private static final long serialVersionUID = 1L;
    private static final int BOX_WIDTH = 1;

    private final ColorGrid grid;
    private final FilterBus filterProvider;

    public Canvas(final ColorGrid grid, final FilterBus filterProvider) {
        this.grid = grid;
        this.filterProvider = filterProvider;
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
        grid.writeColorToPoints(points, color);
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
        for (int w = 0; w < grid.getWidth(); w++) {
            for (int h = 0; h < grid.getHeight(); h++) {
                final Optional<Color> maybeColor = grid.getColorForPoint(w, h);
                if (maybeColor.isPresent()) {
                    g.setColor(filter.filter(maybeColor.get()));
                    g.fillRect(positionOf(h), positionOf(w), BOX_WIDTH,
                            BOX_WIDTH);
                }
            }
        }
    }

    public void saveCapture(final String fileName) {
        ImageSaver.saveImageForGrid(grid.getRawGrid(),
                filterProvider.getFilter(),
                new File(fileName));
    }

    public CanvasState getCanvasState() {
        return new CanvasState(grid.getGridState(), filterProvider.getFilterState());
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
        return new Dimension((grid.getHeight() * BOX_WIDTH) + 2,
                (grid.getWidth() * BOX_WIDTH) + 2);
    }

    @Override
    public void update(final Observable o, final Object arg) {
        repaint();
    }
}