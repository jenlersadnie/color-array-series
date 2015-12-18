package work.samoje.colors;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JPanel;

import work.samoje.colors.combiner.selection.ColorCombinerBus;
import work.samoje.colors.combiner.selection.CombinePanel;
import work.samoje.colors.drawing.ColorPanel;
import work.samoje.colors.filter.selection.FilterBus;
import work.samoje.colors.filter.selection.FilterPanel;
import work.samoje.colors.persistence.StateSaver;


public class ActionPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final Canvas canvas;
    private final CombinePanel combinePanel;

    public ActionPanel(final Canvas canvas, final ColorPanel colorPanel,
            final ColorCombinerBus combinerProvider, final FilterBus filterBus) {
        this.canvas = canvas;
        this.combinePanel = new CombinePanel(combinerProvider);

        this.add(colorPanel);
        this.add(combinePanel);
        this.add(new FilterPanel(filterBus));

        final JButton reinit = new JButton("Initialize");
        reinit.addActionListener(new InitializeButtonListener());
        this.add(reinit);

        final JButton screenCap = new JButton("Save Capture");
        screenCap.addActionListener(new ScreenCapListener());
        this.add(screenCap);

        final JButton fullScaleCapture = new JButton("Save All Clips");
        fullScaleCapture.addActionListener(new CombinerClipsListener());
        this.add(fullScaleCapture);


        validate();
    }

    public class InitializeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            canvas.initialize();
        }
    }

    public class ScreenCapListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            final String path = String.format("out/color-array-%s/", UUID.randomUUID());
            final File filePath = new File(path);
            filePath.mkdirs();

            StateSaver.saveStateToPath(canvas.getCanvasState(), filePath);

            final String imagePath = path + "capture.png";
            canvas.saveCapture(imagePath);
        }
    }

    public class CombinerClipsListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            final String path = String.format("out/color-array-%s/", UUID.randomUUID());
            final File filePath = new File(path);
            filePath.mkdirs();
            StateSaver.saveStateToPath(canvas.getCanvasState(), filePath);

            for (int value = 0; value < combinePanel.getMaxMultiplier(); value++) {
                combinePanel.setMultiplier(value);
                final String imagePath = path + "capture-" + value + ".png";
                canvas.saveCapture(imagePath);
            }
        }
    }

    @Override
    public LayoutManager getLayout() {
        return new GridLayout(4, 1);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 780);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(200, 780);
    }

}