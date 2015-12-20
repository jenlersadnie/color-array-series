package work.samoje.colors.grid;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import work.samoje.colors.modification.combiner.combiners.ColorCombiner;
import work.samoje.colors.modification.combiner.selection.CombinerSelector;
import work.samoje.colors.modification.combiner.selection.CombinerState;

public class ColorGridTest extends EasyMockSupport {
    final CombinerSelector combinerProvider = createMock(CombinerSelector.class);

    @Before
    public void setUp() {
        combinerProvider.addObserver(isA(ColorGrid.class));
        expectLastCall();
    }

    @After
    public void tearDown() {
        verifyAll();
    }

    @Test
    public void test_constructor() {
        final int height = 3;
        final int width = 50;

        replayAll();

        final ColorGrid underTest = new ColorGrid(height, width,
                combinerProvider);
        assertEquals(height, underTest.getHeight());
        assertEquals(width, underTest.getWidth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_failsForTooSmallHeight() {
        resetAll();
        final int height = 1;
        final int width = 50;

        replayAll();

        new ColorGrid(height, width, combinerProvider);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_failsForTooSmallWidth() {
        resetAll();
        final int height = 10;
        final int width = 1;

        replayAll();

        new ColorGrid(height, width, combinerProvider);
    }

    @Test
    public void test_initiatlizeAndRecalculate() {
        final int height = 3;
        final int width = 4;

        final ColorCombiner mockCombiner = createMock(ColorCombiner.class);

        // Expect top edge: RED - CYAN - BLACK - MAGENTA
        expect(mockCombiner.combine(eq(Color.CYAN), eq(Color.RED))).andReturn(
                Color.BLACK);
        expect(mockCombiner.combine(eq(Color.BLACK), eq(Color.CYAN)))
                .andReturn(Color.MAGENTA);

        // Expect side edge: RED - YELLOW - BLUE
        expect(mockCombiner.combine(eq(Color.YELLOW), eq(Color.RED)))
                .andReturn(Color.BLUE);

        // Expect result
        // RED - CYAN - BLACK - MAGENTA
        // YELLOW - GREEN - DARK_GRAY - LIGHT_GRAY
        // BLUE - ORANGE - PINK - WHITE
        expect(mockCombiner.combine(eq(Color.CYAN), eq(Color.YELLOW)))
                .andReturn(Color.GREEN);
        expect(mockCombiner.combine(eq(Color.BLACK), eq(Color.GREEN)))
                .andReturn(Color.DARK_GRAY);
        expect(mockCombiner.combine(eq(Color.MAGENTA), eq(Color.DARK_GRAY)))
                .andReturn(Color.LIGHT_GRAY);
        expect(mockCombiner.combine(eq(Color.GREEN), eq(Color.BLUE)))
                .andReturn(Color.ORANGE);
        expect(mockCombiner.combine(eq(Color.DARK_GRAY), eq(Color.ORANGE)))
                .andReturn(Color.PINK);
        expect(mockCombiner.combine(eq(Color.LIGHT_GRAY), eq(Color.PINK)))
                .andReturn(Color.WHITE);

        expect(combinerProvider.getCombiner()).andReturn(mockCombiner).times(1);
        replayAll();

        final ColorGrid underTest = new ColorGrid(height, width,
                combinerProvider);
        underTest.initialize();

        assertEquals(Color.WHITE, underTest.getColorForPoint(3, 2).get());
    }

    @Test
    public void test_getColorForPoint_returnsEmptyOutsideOfBounds() {
        final int height = 2;
        final int width = 2;

        replayAll();

        final ColorGrid underTest = new ColorGrid(height, width,
                combinerProvider);
        assertFalse(underTest.getColorForPoint(-1, 0).isPresent());
        assertFalse(underTest.getColorForPoint(0, -1).isPresent());
        assertFalse(underTest.getColorForPoint(-1, -10).isPresent());

        assertFalse(underTest.getColorForPoint(width, height).isPresent());
        assertFalse(underTest.getColorForPoint(width, 0).isPresent());
        assertFalse(underTest.getColorForPoint(width + 1, 0).isPresent());
        assertFalse(underTest.getColorForPoint(width, height).isPresent());
        assertFalse(underTest.getColorForPoint(width, height + 1).isPresent());
        assertFalse(underTest.getColorForPoint(0, height).isPresent());
    }

    @Test
    public void test_getColorForPoint_returnsEmptyForNullValue() {
        final int height = 2;
        final int width = 2;

        replayAll();

        final ColorGrid underTest = new ColorGrid(height, width,
                combinerProvider);
        assertFalse(underTest.getColorForPoint(1, 1).isPresent());
    }

    @Test
    public void test_getColorForPoint_returnsValueWhenPresent() {
        final int height = 2;
        final int width = 2;

        final ColorCombiner mockCombiner = createMock(ColorCombiner.class);
        expect(mockCombiner.combine(isA(Color.class), isA(Color.class)))
                .andReturn(Color.BLACK).times(1);

        expect(combinerProvider.getCombiner()).andReturn(mockCombiner).times(1);

        replayAll();

        final ColorGrid underTest = new ColorGrid(height, width,
                combinerProvider);
        underTest.initialize();

        assertTrue(underTest.getColorForPoint(0, 0).isPresent());
        assertEquals(Color.RED, underTest.getColorForPoint(0, 0).get());

        assertTrue(underTest.getColorForPoint(1, 0).isPresent());
        assertEquals(Color.CYAN, underTest.getColorForPoint(1, 0).get());

        assertTrue(underTest.getColorForPoint(0, 1).isPresent());
        assertEquals(Color.YELLOW, underTest.getColorForPoint(0, 1).get());

        assertTrue(underTest.getColorForPoint(1, 1).isPresent());
        assertEquals(Color.BLACK, underTest.getColorForPoint(1, 1).get());
    }

    @Test
    public void test_getColorRow_getsACopy() {
        final int height = 2;
        final int width = 3;

        // Expect result
        // RED - CYAN - BLACK
        // YELLOW - GREEN - DARK_GRAY
        final ColorCombiner mockCombiner = createMock(ColorCombiner.class);
        expect(mockCombiner.combine(eq(Color.CYAN), eq(Color.RED))).andReturn(
                Color.BLACK).times(1);
        expect(mockCombiner.combine(eq(Color.CYAN), eq(Color.YELLOW)))
                .andReturn(Color.GREEN).times(1);
        expect(mockCombiner.combine(eq(Color.BLACK), eq(Color.GREEN)))
                .andReturn(Color.DARK_GRAY).times(1);

        expect(combinerProvider.getCombiner()).andReturn(mockCombiner).times(1);

        replayAll();

        final ColorGrid underTest = new ColorGrid(height, width,
                combinerProvider);
        underTest.initialize();

        final Color[] row = underTest.getColorRow(1);

        // Ensure we get the right values
        assertEquals(3, row.length);
        assertEquals(Color.YELLOW, row[0]);
        assertEquals(Color.GREEN, row[1]);
        assertEquals(Color.DARK_GRAY, row[2]);
        assertEquals(Color.GREEN, underTest.getColorForPoint(1, 1).get());

        // Ensure modifications to the column returned do not affect the grid
        row[1] = Color.WHITE;
        assertEquals(Color.GREEN, underTest.getColorForPoint(1, 1).get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getColorRow_failsWhenOutOfBounds() {
        final int height = 2;
        final int width = 3;

        // Expect result
        // RED - CYAN - BLACK
        // YELLOW - GREEN - DARK_GRAY
        final ColorCombiner mockCombiner = createMock(ColorCombiner.class);
        expect(mockCombiner.combine(eq(Color.CYAN), eq(Color.RED))).andReturn(
                Color.BLACK).times(1);
        expect(mockCombiner.combine(eq(Color.CYAN), eq(Color.YELLOW)))
                .andReturn(Color.GREEN).times(1);
        expect(mockCombiner.combine(eq(Color.BLACK), eq(Color.GREEN)))
                .andReturn(Color.DARK_GRAY).times(1);

        expect(combinerProvider.getCombiner()).andReturn(mockCombiner).times(1);

        replayAll();

        final ColorGrid underTest = new ColorGrid(height, width,
                combinerProvider);
        underTest.initialize();
        underTest.getColorRow(width);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getColorRow_failsWhenNegativeBounds() {
        final int height = 2;
        final int width = 3;

        // Expect result
        // RED - CYAN - BLACK
        // YELLOW - GREEN - DARK_GRAY
        final ColorCombiner mockCombiner = createMock(ColorCombiner.class);
        expect(mockCombiner.combine(eq(Color.CYAN), eq(Color.RED))).andReturn(
                Color.BLACK).times(1);
        expect(mockCombiner.combine(eq(Color.CYAN), eq(Color.YELLOW)))
                .andReturn(Color.GREEN).times(1);
        expect(mockCombiner.combine(eq(Color.BLACK), eq(Color.GREEN)))
                .andReturn(Color.DARK_GRAY).times(1);

        expect(combinerProvider.getCombiner()).andReturn(mockCombiner).times(1);

        replayAll();

        final ColorGrid underTest = new ColorGrid(height, width,
                combinerProvider);
        underTest.initialize();
        underTest.getColorRow(-1);
    }

    @Test
    public void test_writeToNearestEdge_writesToTheRightSpot() {
        final int height = 10;
        final int width = 15;

        final ColorCombiner mockCombiner = createMock(ColorCombiner.class);

        // For initialization, we combine for all values except the 3 hardcoded
        final int initializationCombinationCount = height * width - 3;
        // For repaint after write, we combine for all values except the edges
        final int redrawAfterWriteCount = (height - 1) * (width - 1);
        expect(mockCombiner.combine(isA(Color.class), isA(Color.class)))
                .andReturn(Color.BLACK).times(
                        initializationCombinationCount + redrawAfterWriteCount);

        // Get combiner once for initialization, once for recalculation after
        // write points
        expect(combinerProvider.getCombiner()).andReturn(mockCombiner).times(2);

        replayAll();

        final ColorGrid underTest = new ColorGrid(height, width,
                combinerProvider);
        underTest.initialize();

        final Set<Point> points = new HashSet<>();
        final Set<Point> expectedWhitePoints = new HashSet<>();
        points.add(new Point(2, 3)); // Should result in a write to (0, 3)
        expectedWhitePoints.add(new Point(0, 3));
        points.add(new Point(3, 4)); // Should result in a write to (0, 4)
        expectedWhitePoints.add(new Point(0, 4));
        points.add(new Point(5, 5)); // Should write to (5, 0) and (0, 5)
        expectedWhitePoints.add(new Point(5, 0));
        expectedWhitePoints.add(new Point(0, 5));
        points.add(new Point(6, 5)); // Should result in a write to (6, 0)
        expectedWhitePoints.add(new Point(6, 0));

        underTest.writeToNearestEdge(points, Color.WHITE);

        final Set<Point> hardcodedPoints = new HashSet<>();
        hardcodedPoints.add(new Point(0, 0));
        hardcodedPoints.add(new Point(1, 0));
        hardcodedPoints.add(new Point(0, 1));

        // Ensure we updated the right values
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final Point point = new Point(x, y);
                if (hardcodedPoints.contains(point)) {
                    assertNotEquals("Equality assertion failed for " + point,
                            Color.BLACK, underTest.getColorForPoint(x, y).get());
                    assertNotEquals("Equality assertion failed for " + point,
                            Color.WHITE, underTest.getColorForPoint(x, y).get());
                } else if (expectedWhitePoints.contains(point)) {
                    assertEquals("Equality assertion failed for " + point,
                            Color.WHITE, underTest.getColorForPoint(x, y).get());
                } else {
                    assertEquals("Equality assertion failed for " + point,
                            Color.BLACK, underTest.getColorForPoint(x, y).get());
                }
            }
        }
    }

    @Test
    public void test_getGridState_shouldGetTheGridState() {
        final CombinerState mockState = createMock(CombinerState.class);
        expect(combinerProvider.getCombinerState()).andReturn(mockState).times(
                1);
        replayAll();

        final ColorGrid underTest = new ColorGrid(2, 2, combinerProvider);

        final GridState result = underTest.getGridState();
        assertEquals(mockState, result.getCombinerState());
    }
}
