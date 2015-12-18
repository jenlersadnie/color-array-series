package work.samoje.colors.screencapture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import work.samoje.colors.filter.filters.Filter;

public class ImageSaver {
    public static void saveImageForGrid(final Color[][] grid,
            final Filter filter, final File file) {
        final BufferedImage bufferedImage = new BufferedImage(grid[0].length,
                grid.length, BufferedImage.TYPE_INT_RGB);

        // Set each pixel of the BufferedImage to the color from the Color[][].
        final int sectionSize = Math.min(grid.length, 1200);
        for (int firstRow = 0; firstRow < grid.length; firstRow += sectionSize) {
            final int lastRow = Math.min(firstRow + sectionSize, grid.length);
            sectionWrite(grid, filter, firstRow, lastRow, bufferedImage);
        }

        try {
            ImageIO.write(bufferedImage, "png", file);
            System.out.println("Saved to file: " + file.getAbsolutePath());
        } catch (final IOException e1) {
            System.out.println(e1.getMessage());
        }
    }

    public static void sectionWrite(final Color[][] grid, final Filter filter,
            final int firstRow, final int lastRow,
            final BufferedImage bufferedImage) {
        final Thread[] threads = new Thread[lastRow - firstRow];
        for (int x = firstRow; x < lastRow; x++) {
            threads[x - firstRow] = new Thread(new WriteRow(x, grid[x], filter,
                    bufferedImage));
            threads[x - firstRow].start();
        }

        for (final Thread thread : threads) {
            try {
                thread.join();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class WriteRow implements Runnable {
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
        public void run() {
            for (int y = 0; y < rowValues.length; y++) {
                image.setRGB(y, xIndex, filter.filter(rowValues[y]).getRGB());
            }
        }
    }
}