package work.samoje.colors.modification.filter.selection;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class FilterSelectorTest {
    FilterSelector underTest;

    @Before
    public void setUp() {
        underTest = new FilterSelector();
    }

    @Test
    public void test_getAllOptions_getsAllTheOptions() {
        final FilterMethod[] expected = FilterMethod.values();

        final EnumSet<FilterMethod> actual = underTest.getAllOptions();

        // Since actual is an EnumSet of FilterMethod, we can safely assume
        // equality by checking length.
        assertEquals(expected.length, actual.size());
    }

    @Test
    public void test_getEnumSetFor_getsAllTheOptions() {
        final Set<String> methodNames = Arrays.stream(FilterMethod.values())
                .map(m -> m.toString()).collect(Collectors.toSet());

        assertEquals(EnumSet.allOf(FilterMethod.class),
                underTest.getEnumSetFor(methodNames));
    }

    @Test
    public void test_getEnumSetFor_getsOneOption() {
        final Set<String> methodNames = new HashSet<String>();
        methodNames.add(FilterMethod.NONE.toString());

        assertEquals(EnumSet.of(FilterMethod.NONE),
                underTest.getEnumSetFor(methodNames));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getEnumSetFor_failsForInvalidName() {
        final Set<String> methodNames = new HashSet<String>();
        methodNames
                .add("Definitely not an enum name, why would you name a filter method this?");
        underTest.getEnumSetFor(methodNames);
    }

    @Test
    public void test_update_acceptsChangesToAttributes() {
        // Verify start defaults
        final FilterState startingState = underTest.getFilterState();
        assertEquals(EnumSet.of(FilterMethod.NONE), startingState.getFilters());
        assertEquals(EnumSet.of(FilterMethod.NONE), underTest.getSelected());
        assertEquals(0, startingState.getMultiplier());

        final EnumSet<FilterMethod> methodUpdate = EnumSet.of(
                FilterMethod.RGB_ABSOLUTES, FilterMethod.RGB_ABSOLUTES);
        final int multiplierUpdate = 12;

        // Update and verify changes as expected
        underTest.update(methodUpdate, multiplierUpdate);
        final FilterState endingState = underTest.getFilterState();
        assertEquals(methodUpdate, endingState.getFilters());
        assertEquals(methodUpdate, underTest.getSelected());
        assertEquals(multiplierUpdate, endingState.getMultiplier());
    }
}
