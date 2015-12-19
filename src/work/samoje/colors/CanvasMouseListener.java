package work.samoje.colors;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.MouseInputAdapter;

import work.samoje.colors.drawing.ColorOverrideProvider;
import work.samoje.colors.grid.Canvas;

/**
 * {@link MouseInputAdapter} for receiving click & drag actions against the
 * {@link Canvas}. Translates these actions into commands to write the
 * {@link ColorOverrideProvider}'s active {@link Color} to the edges of the
 * {@link Canvas}.
 *
 * This listener is not thread-safe.
 *
 * @author Jennie Sadler
 *
 */
public class CanvasMouseListener extends MouseInputAdapter {
    private Point startPoint = new Point(0, 0);

    private final Canvas canvas;
    private final ColorOverrideProvider colorOverrideProvider;

    /**
     * Default constructor.
     *
     * @param canvas
     *            {@link Canvas} to write against
     * @param colorOverrideProvider
     *            {@link ColorOverrideProvider} which specifies the
     *            {@link Color} to write to the {@link Canvas}
     */
    public CanvasMouseListener(final Canvas canvas,
            final ColorOverrideProvider colorOverrideProvider) {
        this.canvas = canvas;
        this.colorOverrideProvider = colorOverrideProvider;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        startPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        canvas.writeColorToEdges(pointsBetween(startPoint, e.getPoint()),
                colorOverrideProvider.getColor());
    }

    private Set<Point> pointsBetween(final Point start, final Point end) {
        final Set<Point> points = new HashSet<Point>();
        for (int x = Math.min(start.x, end.x); x <= Math.max(start.x, end.x); x++) {
            for (int y = Math.min(start.y, end.y); y <= Math
                    .max(start.y, end.y); y++) {
                points.add(new Point(x, y));
            }
        }
        return points;
    }
}