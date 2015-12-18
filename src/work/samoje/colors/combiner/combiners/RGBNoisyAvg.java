package work.samoje.colors.combiner.combiners;

import java.util.Random;

public class RGBNoisyAvg extends BinaryValueWiseCombiner {
    private final Random rand = new Random();
    private final double randomnessRange;

    public RGBNoisyAvg(final double modifier, final double modifierScale) {
        this.randomnessRange = (modifier * 255.0) / modifierScale;
    }

    @Override
    protected int combine(final int left, final int right) {
        return Math.max(0, Math.min(255, (left + right + randPlusMinus()) / 2));
    }

    private int randPlusMinus() {
        final int range = Math.max(1, (int) randomnessRange);
        return (rand.nextInt(range * 2) - range);
    }
}