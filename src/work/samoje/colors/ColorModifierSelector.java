package work.samoje.colors;

import java.util.EnumSet;
import java.util.Observable;
import java.util.Set;

/**
 * Generic {@link Observable} intended for managing arbitrary color modifiers.
 * Modifier options are specified by the parametrized {@link Enum} type and
 * may be combined/modified by a configured multiplier.
 *
 * @author Jennie Sadler
 *
 * @param <E>
 *            Enum type, specifies Color Modifier options
 */
public abstract class ColorModifierSelector<E extends Enum<E>> extends
Observable {
    protected static final int DEFAULT_MAX_MULTIPLIER = 100;

    /**
     * @return All possible enum options from which to choose.
     */
    public abstract EnumSet<E> getAllOptions();

    /**
     * Translates the given set of strings into a set of enum selections.
     *
     * @param selections
     *            A set of string values of the parametrized Enum
     * @return An EnumSet representing the provided set of string values
     */
    public abstract EnumSet<E> getEnumSetFor(Set<String> selections);

    /**
     * Returns the active set of enum selections.
     *
     * @return
     */
    public abstract EnumSet<E> getSelected();

    /**
     * Updates the active set of enum selections.
     *
     * @param selections
     * @param multiplier
     */
    public abstract void update(EnumSet<E> selections, final int multiplier);

    public int getMaxMultiplier() {
        return DEFAULT_MAX_MULTIPLIER;
    }
}
