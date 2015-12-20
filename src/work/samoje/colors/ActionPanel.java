package work.samoje.colors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import work.samoje.colors.drawing.ColorSelectorPanel;
import work.samoje.colors.grid.Canvas;
import work.samoje.colors.modification.combiner.selection.CombinePanel;
import work.samoje.colors.modification.combiner.selection.CombinerSelector;
import work.samoje.colors.modification.filter.selection.FilterPanel;
import work.samoje.colors.modification.filter.selection.FilterSelector;

/**
 * JPanel which contains all {@link JComponent}s that allow actions on the
 * {@link Canvas}. Currently also responsible for orchestrating some actions on
 * the {@link Canvas}.
 *
 * @author Jennie Sadler
 */
/*
 * TODO: Migrate Initialize and Save Capture/Clips functionality to existing
 * Observer pattern, to remove orchestration responsibility/action listeners
 * from this JPanel.
 *
 * This change will require more complex update logic in Canvas and ColorGrid.
 * See ColorCombinerBus, FilterBus for examples of Observables registered with
 * the Canvas.
 */
public class ActionPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int PANEL_WIDTH = 200;
    private static final int PANEL_HEIGHT = 780;
    private final Canvas canvas;
    private final CombinePanel combinePanel;

    /**
     * Default constructor. Instantiates {@link JComponent}s and wires up
     * listeners.
     *
     * @param canvas
     *            The {@link Canvas}
     * @param colorSelectorPanel
     *            The {@link ColorSelectorPanel}, used for choosing the color
     *            that will be used when overriding edge values on the
     *            {@link Canvas}.
     * @param combinerBus
     *            The {@link CombinerSelector}, used to create a
     *            {@link CombinePanel} instance
     * @param filterBus
     *            The {@link FilterSelector}, used to create a
     *            {@link FilterPanel} instance
     */
    public ActionPanel(final Canvas canvas,
            final ColorSelectorPanel colorSelectorPanel,
            final CombinerSelector combinerBus, final FilterSelector filterBus) {
        this.canvas = canvas;
        this.combinePanel = new CombinePanel(combinerBus);

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        this.add(new JLabel("Ink color selection"));
        this.add(colorSelectorPanel);
        this.add(newSeparator());
        this.add(new JLabel("Color combiner selection"));
        this.add(combinePanel);
        this.add(newSeparator());
        this.add(new JLabel("Filter selection"));
        this.add(new FilterPanel(filterBus));
        this.add(newSeparator());

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 150));
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        final JButton reinit = new JButton("Renitialize");
        reinit.addActionListener(new ReinitializeButtonListener());
        buttonsPanel.add(reinit);

        final JButton screenCap = new JButton("Save Capture");
        screenCap.addActionListener(new SaveCaptureListener());
        buttonsPanel.add(screenCap);

        final JButton fullScaleCapture = new JButton("Save Clips");
        fullScaleCapture.addActionListener(new SaveClipsListener());
        buttonsPanel.add(fullScaleCapture);
        this.add(buttonsPanel);

        validate();
    }

    private JSeparator newSeparator()
    {
        final JSeparator sep = new JSeparator();
        sep.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sep.setPreferredSize(new Dimension(PANEL_WIDTH, 1));
        return sep;
    }

    /**
     * Sends a command to the canvas to re-initialize.
     */
    public class ReinitializeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            canvas.initialize();
        }
    }

    /**
     * Saves the current canvas state and image.
     *
     * @author Jennie Sadler
     */
    public class SaveCaptureListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            final String path = getDefaultPath();
            makeDirectories(path);
            canvas.saveState(path);

            final String imagePath = path + "capture";
            canvas.saveCapture(imagePath);
        }
    }

    /**
     * Saves the current canvas state, the saves an image for each possible
     * {@link CombinePanel} multiplier.
     *
     * @author Jennie Sadler
     */
    public class SaveClipsListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            final String path = getDefaultPath();
            makeDirectories(path);
            canvas.saveState(path);

            for (int value = 0; value < combinePanel.getMaxMultiplier(); value++) {
                combinePanel.setMultiplier(value);
                final String imagePath = path + "capture-" + value;
                canvas.saveCapture(imagePath);
            }
        }
    }

    private String getDefaultPath() {
        return String.format("color-array-out/%s/", UUID.randomUUID());
    }

    private void makeDirectories(final String path) {
        final File filePath = new File(path);
        filePath.mkdirs();
    }
}