package work.samoje.colors.combiner.combiners;


public class RGBScaleAvg extends BinaryValueWiseCombiner {
    private final double muliplier;

    public RGBScaleAvg(final double modifier, final double modifierScale)
    {
        this.muliplier = 0.25 + (modifier * 0.5) / modifierScale;
    }

    @Override
    protected int binaryCombine(final int left, final int right)
    {
        return Math.max(0, Math.min((int)Math.floor((left+right)*muliplier), 255));
    }
}