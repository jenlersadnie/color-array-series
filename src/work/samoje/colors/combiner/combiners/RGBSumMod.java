package work.samoje.colors.combiner.combiners;

public class RGBSumMod extends BinaryValueWiseCombiner {
    private final int modulus;

    public RGBSumMod(final double modifier, final double modifierScale) {
        this.modulus = Math.max(1, (int) ((modifier * 255.0) / modifierScale));
    }

    @Override
    protected int binaryCombine(final int left, final int right) {
        return (left + right) % modulus;
    }
}