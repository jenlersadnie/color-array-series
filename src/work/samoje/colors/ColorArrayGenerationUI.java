package work.samoje.colors;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import work.samoje.colors.drawing.ColorSelectorPanel;
import work.samoje.colors.grid.Canvas;
import work.samoje.colors.grid.ColorGrid;
import work.samoje.colors.modification.combiner.selection.CombinerSelector;
import work.samoje.colors.modification.filter.selection.FilterSelector;

/**
 * Main UI class for running the Color Array Generation Application.
 *
 * @author Jennie Sadler
 */
public class ColorArrayGenerationUI {
    private static final int W = 780;
    private static final int H = 780;

    public static void main(final String[] args) {
        final ColorArrayGenerationUI GUI = new ColorArrayGenerationUI();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI.createAndShowGUI();
            }
        });
    }

    /**
     * Standard Java swing instantiation.
     */
    private void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        final JFrame frame = new JFrame("Color Array Generation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents(frame);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Instantiates sub-components and domain-level dependencies for color
     * generation UI.
     *
     * @param frame
     *            The JFrame into which to add components
     */
    private void createComponents(final JFrame frame) {
        final ExecutorService executor = Executors.newCachedThreadPool();

        final Container pane = frame.getContentPane();
        pane.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));

        final CombinerSelector colorCombinerBus = new CombinerSelector();
        final FilterSelector filterBus = new FilterSelector();

        final ColorSelectorPanel colorPanel = new ColorSelectorPanel();
        final Canvas canvas = new Canvas(new ColorGrid(W, H, colorCombinerBus),
                filterBus, executor);
        final ActionPanel actionPanel = new ActionPanel(canvas, colorPanel,
                colorCombinerBus, filterBus);

        canvas.addMouseListener(new CanvasMouseListener(canvas, colorPanel));
        pane.add(new JScrollPane(canvas));

        pane.add(actionPanel);
    }
}