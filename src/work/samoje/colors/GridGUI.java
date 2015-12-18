package work.samoje.colors;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import work.samoje.colors.combiner.selection.ColorCombinerBus;
import work.samoje.colors.filter.selection.FilterBus;
import work.samoje.colors.structures.ColorGrid;

public class GridGUI {
    private static final boolean USE_MAX = false;
    private static final int MAX_W = 6500;
    private static final int MAX_H = 5525;
    private static final int DEFAULT_W = 780;
    private static final int DEFAULT_H = 780;
    private static final int H = USE_MAX ? MAX_H : DEFAULT_H;
    private static final int W = USE_MAX ? MAX_W : DEFAULT_W;

    public static void main(final String[] args) {
        final GridGUI GUI = new GridGUI();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI.createAndShowGUI();
            }
        });
    }

    public void createAndShowGUI() {
        // Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create and set up the window.
        final JFrame frame = new JFrame("Grid GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add components
        createComponents(frame);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void createComponents(final JFrame frame) {
        final Container pane = frame.getContentPane();
        pane.setLayout(getLayout());

        final ColorPanel colorPanel = new ColorPanel();
        final ColorCombinerBus colorCombinerBus = new ColorCombinerBus();
        final FilterBus filterBus = new FilterBus();
        final Canvas canvas = new Canvas(new ColorGrid(W, H, colorCombinerBus),
                filterBus);
        final ActionPanel actionPanel = new ActionPanel(canvas, colorPanel,
                colorCombinerBus, filterBus);

        canvas.addMouseListener(new CanvasMouseListener(canvas, colorPanel));
        pane.add(new JScrollPane(canvas), BorderLayout.CENTER);

        final Container west = new Container();
        pane.add(west, BorderLayout.WEST);

        pane.add(actionPanel, BorderLayout.EAST);

        final Container north = new Container();
        pane.add(north, BorderLayout.NORTH);

        final Container south = new Container();
        pane.add(south, BorderLayout.SOUTH);
    }

    private BorderLayout getLayout() {
        final BorderLayout layout = new BorderLayout();
        layout.setHgap(4);
        layout.setVgap(4);
        return layout;
    }
}