package work.samoje.colors.screencapture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import work.samoje.colors.filter.selection.FilterProvider;
import work.samoje.colors.structures.ColorGrid;

public class ScreenCapListener implements ActionListener {

    private final ColorGrid colorGrid;
    private final FilterProvider filterProvider;

    public ScreenCapListener(final ColorGrid colorGrid,
            final FilterProvider filterPanel) {
        this.colorGrid = colorGrid;
        this.filterProvider = filterPanel;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        ImageSaver.saveImageForGrid(colorGrid.getRawGrid(),
                filterProvider.getFilter(),
                new File("color-array-" + System.currentTimeMillis() + ".png"));
    }
}