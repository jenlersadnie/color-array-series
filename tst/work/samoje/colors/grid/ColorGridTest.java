package work.samoje.colors.grid;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;
import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import work.samoje.colors.modification.combiner.combiners.ColorCombiner;
import work.samoje.colors.modification.combiner.selection.CombinerSelector;

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
        //   RED    - CYAN   - BLACK     - MAGENTA
        //   YELLOW - GREEN  - DARK_GRAY - LIGHT_GRAY
        //   BLUE   - ORANGE - PINK      - WHITE
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
}
