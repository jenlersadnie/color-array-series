package work.samoje.colors.persistence;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import work.samoje.colors.grid.ColorGrid;
import work.samoje.colors.modification.filter.filters.Filter;

/**
 * Responsible for exporting {@link ColorGrid} instances to image files, and
 * applying the active {@link Filter}.
 *
 * @author Jennie Sadler
 */
public class ImageSaver {
    private static final String IMAGE_TYPE = "PNG";

    private final ExecutorService executor;
    private final ExecutorService asyncSingleThreadExec;

    /**
     * Default constructor.
     *
     * @param executor
     *            Executor service for parallelizing the export process.
     */
    public ImageSaver(final ExecutorService executor) {
        this.executor = executor;
        this.asyncSingleThreadExec = Executors.newFixedThreadPool(1);
    }

    /**
     * Asynchronously saves the provided {@link ColorGrid} to the provided file
     * name, with the {@link Filter} applied. Terminating the program before
     * asynchronous execution is complete will result in an incomplete image.
     *
     * @param grid
     *            The {@link ColorGrid} to save to file
     * @param filter
     *            The color {@link Filter} instance to apply to output
     * @param fileName
     *            The file path target for the image file. Should not include
     *            file type.
     */
    public void saveImageForGrid(final ColorGrid grid, final Filter filter,
            final String fileName) {
        final File file = new File(fileName + "." + IMAGE_TYPE);
        final BufferedImage bufferedImage = new BufferedImage(grid.getWidth(),
                grid.getHeight(), BufferedImage.TYPE_INT_RGB);

        // Set each pixel of the BufferedImage to the color from the ColorGrid.
        final int sectionSize = Math.min(grid.getHeight(), 1200);
        for (int firstRow = 0; firstRow < grid.getHeight(); firstRow += sectionSize) {
            final int lastRow = Math.min(firstRow + sectionSize, grid.getHeight());
            sectionWrite(grid, filter, firstRow, lastRow, bufferedImage);
        }
        executeAsync(new WriteImageOut(bufferedImage, file));
    }

    private void executeAsync(final WriteImageOut writeImageOut) {
        asyncSingleThreadExec.submit(writeImageOut);
    }

    private class WriteImageOut implements Runnable {
        private final BufferedImage bufferedImage;
        private final File file;

        public WriteImageOut(final BufferedImage bufferedImage, final File file) {
            this.bufferedImage = bufferedImage;
            this.file = file;
        }

        @Override
        public void run() {
            try (final FileOutputStream fileOutputStream = new FileOutputStream(
                    file);
                    final BufferedOutputStream imageOutputStream = new BufferedOutputStream(
                            fileOutputStream)) {
                ImageIO.write(bufferedImage, IMAGE_TYPE, imageOutputStream);
                System.out
                .println(String.format("New capture saved to file: %s",
                        file.getAbsolutePath()));
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sectionWrite(final ColorGrid grid, final Filter filter,
            final int firstRow, final int lastRow,
            final BufferedImage bufferedImage) {
        final List<Callable<Void>> runnables = new ArrayList<>();
        for (int x = firstRow; x < lastRow; x++) {
            runnables.add(new WriteRow(x, grid.getColorColumn(x), filter,
                    bufferedImage));
        }

        try {
            final List<Future<Void>> futures = executor.invokeAll(runnables);

            for (final Future<Void> future : futures) {
                try {
                    future.get();
                } catch (final ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class WriteRow implements Callable<Void> {
        private final int xIndex;
        private final Color[] rowValues;
        private final BufferedImage image;
        private final Filter filter;

        public WriteRow(final int xIndex, final Color[] rowValues,
                final Filter filter, final BufferedImage image) {
            this.xIndex = xIndex;
            this.rowValues = rowValues;
            this.image = image;
            this.filter = filter;
        }

        @Override
        public Void call() {
            for (int y = 0; y < rowValues.length; y++) {
                image.setRGB(y, xIndex, filter.filter(rowValues[y]).getRGB());
            }
            return null;
        }
    }
}