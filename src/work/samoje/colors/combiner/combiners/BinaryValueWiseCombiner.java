package work.samoje.colors.combiner.combiners;

import java.awt.Color;

public abstract class BinaryValueWiseCombiner implements ColorCombiner {
    @Override
    public Color combine(final Color parentLeft, final Color parentRight) {
        return new Color(combine(parentLeft.getRed(), parentRight.getRed()),
                combine(parentLeft.getGreen(), parentRight.getGreen()),
                combine(parentLeft.getBlue(), parentRight.getBlue()));
    }

    protected abstract int combine(int left, int right);
}