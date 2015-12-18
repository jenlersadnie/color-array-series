package work.samoje.colors;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import work.samoje.colors.combiner.selection.ColorCombinerBus;
import work.samoje.colors.drawing.ColorPanel;
import work.samoje.colors.filter.selection.FilterBus;
import work.samoje.colors.structures.ColorGrid;

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

    public void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        final JFrame frame = new JFrame("Color Array Generation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents(frame);

        frame.pack();
        frame.setVisible(true);
    }

    public void createComponents(final JFrame frame) {
        final Container pane = frame.getContentPane();
        pane.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));

        final ColorPanel colorPanel = new ColorPanel();
        final ColorCombinerBus colorCombinerBus = new ColorCombinerBus();
        final FilterBus filterBus = new FilterBus();
        final Canvas canvas = new Canvas(new ColorGrid(W, H, colorCombinerBus),
                filterBus);
        final ActionPanel actionPanel = new ActionPanel(canvas, colorPanel,
                colorCombinerBus, filterBus);

        canvas.addMouseListener(new CanvasMouseListener(canvas, colorPanel));
        pane.add(new JScrollPane(canvas));


        pane.add(actionPanel);
    }
}