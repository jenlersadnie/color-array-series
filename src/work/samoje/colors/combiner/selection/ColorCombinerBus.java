package work.samoje.colors.combiner.selection;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.stream.Collectors;

import work.samoje.colors.combiner.combiners.ColorCombiner;
import work.samoje.colors.combiner.combiners.MetaCombiner;
import work.samoje.colors.combiner.combiners.RGBAvg;
import work.samoje.colors.combiner.combiners.RGBChooser;
import work.samoje.colors.combiner.combiners.RGBNoisyAvg;
import work.samoje.colors.combiner.combiners.RGBScaleAvg;
import work.samoje.colors.combiner.combiners.RGBSumMod;

public class ColorCombinerBus extends Observable implements CombinerProvider {
    protected static final int MAX_MULTIPLIER = 100;
    private EnumSet<CombineMethod> combineMethods;
    private int multiplier;

    public ColorCombinerBus() {
        combineMethods = EnumSet.of(CombineMethod.RGB_AVG);
        multiplier = 0;
    }

    public void update(final EnumSet<CombineMethod> combineMethods,
            final int multiplier) {
        this.combineMethods = combineMethods;
        this.multiplier = multiplier;
        setChanged();
        notifyObservers();
    }

    public void setMultiplier(final int multiplier) {
        this.multiplier = multiplier;
        setChanged();
        notifyObservers();
    }

    @Override
    public ColorCombiner getCombiner() {
        final List<ColorCombiner> chosenCombiners = combiners(multiplier)
                .entrySet().stream()
                .filter(entry -> combineMethods.contains(entry.getKey()))
                .map(entry -> entry.getValue())
                .collect(Collectors.<ColorCombiner> toList());
        return new MetaCombiner(chosenCombiners);
    }

    @Override
    public EnumSet<CombineMethod> getCombineMethods() {
        return combineMethods;
    }

    public Map<CombineMethod, ColorCombiner> combiners(final int value) {
        final Map<CombineMethod, ColorCombiner> combineMap = new HashMap<>();
        combineMap.put(CombineMethod.RGB_AVG, new RGBAvg());
        combineMap.put(CombineMethod.RGB_SUM_MOD, new RGBSumMod(value,
                MAX_MULTIPLIER));
        combineMap.put(CombineMethod.RGB_SCALE_AVG, new RGBScaleAvg(value,
                MAX_MULTIPLIER));
        combineMap.put(CombineMethod.NOISY_AVG, new RGBNoisyAvg(value,
                MAX_MULTIPLIER));
        combineMap.put(CombineMethod.RGB_CHOOSER, new RGBChooser());
        return combineMap;
    }
}