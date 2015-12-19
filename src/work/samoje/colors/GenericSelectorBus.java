package work.samoje.colors;

import java.util.EnumSet;
import java.util.Observable;
import java.util.Set;

public abstract class GenericSelectorBus<E extends Enum<E>> extends Observable {
    protected static final int DEFAULT_MAX_MULTIPLIER = 100;

    public abstract EnumSet<E> getAllOptions();
    public abstract EnumSet<E> getEnumSetFor(Set<String> selections);

    public abstract EnumSet<E> getSelected();
    public abstract void update(EnumSet<E> selections, final int multiplier);

    public int getMaxMultiplier() {
        return DEFAULT_MAX_MULTIPLIER;
    }
}
