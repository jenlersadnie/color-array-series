package work.samoje.colors.modification.filter.filters;

import java.awt.Color;

import work.samoje.colors.modification.ColorModifier;

public interface Filter extends ColorModifier {
    public Color filter(Color a);
}
