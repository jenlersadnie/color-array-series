package work.samoje.colors;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import work.samoje.colors.combiner.selection.ColorCombinerBus;
import work.samoje.colors.combiner.selection.CombinePanel;
import work.samoje.colors.filter.selection.FilterBus;
import work.samoje.colors.filter.selection.FilterPanel;

public class ActionPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final ColorPanel colorPanel;
    private final CombinePanel combinerPanel;
    private final FilterPanel filterPanel;
    private final Canvas canvas;
    private final JLabel activeInfoLabel;

    public ActionPanel(final Canvas canvas, final ColorPanel colorPanel,
            final ColorCombinerBus combinerProvider, final FilterBus filterBus) {
        this.canvas = canvas;

        this.colorPanel = colorPanel;
        this.add(colorPanel);

        this.combinerPanel = new CombinePanel(combinerProvider);
        this.add(combinerPanel);

        this.filterPanel = new FilterPanel(filterBus);
        this.add(filterPanel);

        this.activeInfoLabel = new JLabel("                               ");
        this.add(activeInfoLabel);

        final JButton repaintButton = new JButton("Repaint");
        repaintButton.addActionListener(new RepaintButtonListener());
        this.add(repaintButton);

        final JButton reinit = new JButton("Re-initialize");
        reinit.addActionListener(new InitializeButtonListener());
        this.add(reinit);
        /*
         * final JButton screenCap = new JButton("Save Capture");
         * screenCap.addActionListener(new ScreenCapListener(canvas.getGrid(),
         * filterPanel)); this.add(screenCap);
         * 
         * final JButton fullScaleCapture = new JButton("Save Clips");
         * fullScaleCapture.addActionListener(new ScaleCapListener(canvas
         * .getGrid(), combinerPanel, filterPanel)); this.add(fullScaleCapture);
         */

        validate();
    }

    public class RepaintButtonListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            canvas.repaint();
        }
    }

    public class InitializeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            canvas.initialize();
        }
    }

    @Override
    public LayoutManager getLayout() {
        return new GridLayout(9, 1);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 400);
    }
}