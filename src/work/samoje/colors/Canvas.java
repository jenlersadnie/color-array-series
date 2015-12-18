package work.samoje.colors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Optional;
import java.util.Set;

import javax.swing.JComponent;

import work.samoje.colors.combiner.selection.ColorCombinerBus;
import work.samoje.colors.filter.filters.Filter;
import work.samoje.colors.filter.selection.FilterBus;
import work.samoje.colors.structures.ColorGrid;

public class Canvas extends JComponent {
    private static final long serialVersionUID = 1L;
    private static final int BOX_WIDTH = 1;

    private final ColorGrid grid;
    private final FilterBus filterProvider;

    public Canvas(final int blocksTall, final int blocksWide, final ColorCombinerBus combinerProvider, final FilterBus filterProvider) {
        this.grid = new ColorGrid(blocksWide, blocksTall, combinerProvider);
        this.filterProvider = filterProvider;
        initialize();
    }

    public void initialize()
    {
        grid.initialize();
        repaint();
    }

    public Point getBlockForPosition(final Point position)
    {
        final Point block = new Point(indexOf(position.x), indexOf(position.y));
        return block;
    }

    public void updateBlock(final Point block, final Color color)
    {
        grid.overrideValues(block.x, block.y, color);
        repaint();
    }

    public void updateBlocks(final Set<Point> blocks, final Color color)
    {
        for (final Point block : blocks) {
            grid.overrideValues(block.x, block.y, color);
        }
        repaint();
    }

    private int indexOf(final int coordinate)
    {
        return (int)Math.floor((coordinate + 1) / BOX_WIDTH);
    }

    private int positionOf(final int index)
    {
        return 1 + (index * BOX_WIDTH);
    }

    private void fillGrid(final Graphics g) {
        final Filter filter = filterProvider.getFilter();
        for (int w = 0; w < grid.getWidth(); w++) {
            for (int h = 0; h < grid.getHeight(); h++) {
                final Optional<Color> maybeColor = grid.getColorForPoint(w, h);
                if (maybeColor.isPresent()) {
                    g.setColor(filter.filter(maybeColor.get()));
                    g.fillRect(positionOf(h), positionOf(w), BOX_WIDTH, BOX_WIDTH);
                }
            }
        }
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

    @Deprecated
    public ColorGrid getGrid() {
        return grid;
    }
}