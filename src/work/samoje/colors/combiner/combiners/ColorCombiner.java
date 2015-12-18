package work.samoje.colors.combiner.combiners;

import java.awt.Color;

public interface ColorCombiner {
    Color combine(Color parentLeft, Color parentRight);
}