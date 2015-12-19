package work.samoje.colors.combiner.selection;

import work.samoje.colors.combiner.combiners.ColorCombiner;

public interface CombinerProvider {
    public ColorCombiner getCombiner();

    public CombinerState getCombinerState();
    public void load(CombinerState state);
}
