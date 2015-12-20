package work.samoje.colors.modification.combiner.selection;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class CombinerSelectorTest {
    CombinerSelector underTest;

    @Before
    public void setUp() {
        underTest = new CombinerSelector();
    }

    @Test
    public void test_getAllOptions_getsAllTheOptions() {
        final CombineMethod[] expected = CombineMethod.values();

        final EnumSet<CombineMethod> actual = underTest.getAllOptions();

        // Since actual is an EnumSet of CombineMethod, we can safely assume
        // equality by checking length.
        assertEquals(expected.length, actual.size());
    }

    @Test
    public void test_getEnumSetFor_getsAllTheOptions() {
        final Set<String> methodNames = Arrays.stream(CombineMethod.values())
                .map(m -> m.toString()).collect(Collectors.toSet());

        assertEquals(EnumSet.allOf(CombineMethod.class),
                underTest.getEnumSetFor(methodNames));
    }

    @Test
    public void test_getEnumSetFor_getsOneOption() {
        final Set<String> methodNames = new HashSet<String>();
        methodNames.add(CombineMethod.RGB_AVG.toString());

        assertEquals(EnumSet.of(CombineMethod.RGB_AVG),
                underTest.getEnumSetFor(methodNames));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getEnumSetFor_failsForInvalidName() {
        final Set<String> methodNames = new HashSet<String>();
        methodNames
                .add("Definitely not an enum name, why would you name a combine method this?");
        underTest.getEnumSetFor(methodNames);
    }

    @Test
    public void test_update_acceptsChangesToAttributes() {
        // Verify start defaults
        final CombinerState startingState = underTest.getCombinerState();
        assertEquals(EnumSet.of(CombineMethod.RGB_AVG),
                startingState.getCombiners());
        assertEquals(EnumSet.of(CombineMethod.RGB_AVG), underTest.getSelected());
        assertEquals(0, startingState.getMultiplier());

        final EnumSet<CombineMethod> methodUpdate = EnumSet.of(
                CombineMethod.RGB_SUM_MOD, CombineMethod.NOISY_AVG);
        final int multiplierUpdate = 12;

        // Update and verify changes as expected
        underTest.update(methodUpdate, multiplierUpdate);
        final CombinerState endingState = underTest.getCombinerState();
        assertEquals(methodUpdate, endingState.getCombiners());
        assertEquals(methodUpdate, underTest.getSelected());
        assertEquals(multiplierUpdate, endingState.getMultiplier());
    }

    @Test
    public void test_setMultiplier_onlyChangesTheMultiplier() {
        // Verify start defaults
        final CombinerState startingState = underTest.getCombinerState();
        assertEquals(EnumSet.of(CombineMethod.RGB_AVG),
                startingState.getCombiners());
        assertEquals(EnumSet.of(CombineMethod.RGB_AVG), underTest.getSelected());
        assertEquals(0, startingState.getMultiplier());

        final int multiplierUpdate = 12;

        // Update and verify changes as expected
        underTest.setMultiplier(multiplierUpdate);
        final CombinerState endingState = underTest.getCombinerState();
        assertEquals(EnumSet.of(CombineMethod.RGB_AVG),
                endingState.getCombiners());
        assertEquals(EnumSet.of(CombineMethod.RGB_AVG), underTest.getSelected());
        assertEquals(multiplierUpdate, endingState.getMultiplier());
    }
}
