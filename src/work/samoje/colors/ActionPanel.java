package work.samoje.colors;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

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

        this.setPreferredSize(new Dimension(200, 780));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));

        this.add(colorSelectorPanel);
        this.add(combinePanel);
        this.add(new FilterPanel(filterBus));

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(200, 150));
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
        return String.format("out/color-array-%s/", UUID.randomUUID());
    }

    private void makeDirectories(final String path) {
        final File filePath = new File(path);
        filePath.mkdirs();
    }
}