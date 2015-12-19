package work.samoje.colors.modification.combiner.selection;

import work.samoje.colors.modification.combiner.combiners.ColorCombiner;

public interface CombinerProvider {
    public ColorCombiner getCombiner();

    public CombinerState getCombinerState();
    public void load(CombinerState state);
}
