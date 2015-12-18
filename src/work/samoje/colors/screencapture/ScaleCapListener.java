package work.samoje.colors.screencapture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import work.samoje.colors.combiner.selection.ColorCombinerBus;
import work.samoje.colors.filter.selection.FilterProvider;
import work.samoje.colors.structures.ColorGrid;

public class ScaleCapListener implements ActionListener {

    private final ColorCombinerBus combinerProvider;
    private final FilterProvider filterProvider;
    private final ColorGrid colorGrid;

    public ScaleCapListener(final ColorGrid colorGrid,
            final ColorCombinerBus combinerProvider,
            final FilterProvider filterPanel) {
        this.colorGrid = colorGrid;
        this.combinerProvider = combinerProvider;
        this.filterProvider = filterPanel;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        /*
         * final String root = "color-array-"+System.currentTimeMillis(); final
         * File directory = new File(root); directory.mkdir(); for (int value =
         * 1; value < CombinePanel.VALUE_SELECTOR_RANGE; value++) { final File
         * file = new File(root + "/frame-" + value + ".png");
         * combinerProvider.setMultiplier(value);
         * ImageSaver.saveImageForGrid(colorGrid.getRawGrid(),
         * filterProvider.getFilter(), file); }TODO
         */
    }
}