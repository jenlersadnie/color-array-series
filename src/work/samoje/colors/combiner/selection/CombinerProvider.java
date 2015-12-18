package work.samoje.colors.combiner.selection;

import java.util.EnumSet;

import work.samoje.colors.combiner.combiners.ColorCombiner;

public interface CombinerProvider {
    public ColorCombiner getCombiner();

    public EnumSet<CombineMethod> getCombineMethods();
}
