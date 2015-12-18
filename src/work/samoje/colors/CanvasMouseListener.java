package work.samoje.colors;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.MouseInputAdapter;

public class CanvasMouseListener extends MouseInputAdapter {
    private final ActionPanel actionPanel;
    private Point startPoint = new Point(0, 0);

    public CanvasMouseListener(final ActionPanel actionPanel) {
        this.actionPanel = actionPanel;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        startPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        actionPanel.recolorCanvas(pointsBetween(startPoint, e.getPoint()));
    }

    private Set<Point> pointsBetween(final Point start, final Point end) {
        final Set<Point> points = new HashSet<Point>();
        for (int x = Math.min(start.x, end.x); x <= Math.max(start.x, end.x); x++) {
            for (int y = Math.min(start.y, end.y); y <= Math.max(start.y, end.y); y++) {
                points.add(new Point(x, y));
            }
        }
        return points;
    }
}