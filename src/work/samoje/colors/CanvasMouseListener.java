package work.samoje.colors;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.MouseInputAdapter;

import work.samoje.colors.drawing.ColorOverrideProvider;
import work.samoje.colors.grid.Canvas;

public class CanvasMouseListener extends MouseInputAdapter {
    private Point startPoint = new Point(0, 0);

    private final Canvas canvas;
    private final ColorOverrideProvider colorOverrideProvider;

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
        canvas.writeColorToPoints(pointsBetween(startPoint, e.getPoint()),
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