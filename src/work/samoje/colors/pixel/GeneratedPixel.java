package work.samoje.colors.pixel;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import work.samoje.colors.combiner.selection.CombinePanel;

public class GeneratedPixel implements Pixel {
    private final List<Pixel> parents;
    private final List<Pixel> children;
    private final CombinePanel combinePanel;
    private final Point position;
    private boolean validated;
    private Color color;

    public GeneratedPixel(final CombinePanel combinePanel, final List<Pixel> parents, final Point position)
    {
        this.combinePanel = combinePanel;
        this.parents = registerWithParents(parents);
        this.position = position;

        this.children = new ArrayList<Pixel>();
        validated = false;
        setColor();
    }

    private List<Pixel> registerWithParents(final List<Pixel> parents) {
        for (final Pixel parent : parents) {
            parent.addChild(this);
        }
        return parents;
    }

    private void setColor()
    {
        //TODO final Color[] parentArray = parents.stream().map(p -> p.getColor()).toArray();
        //TODO color = combinePanel.getCombiner().combine(parentArray);
    }

    @Override
    public void addChild(final Pixel childPixel) {
        children.add(childPixel);
    }

    @Override
    public void validate()
    {
        if (!validated) {
            validated = true;
            for (final Pixel child : children) {
                child.validate();
            }
        } else {
            throw new IllegalStateException("Illegal child: " + toString());
        }
    }

    @Override
    public Color getColor() {
        return new Color(color.getRGB());
    }

    @Override
    public Point getPosition() {
        return new Point(position);
    }

    @Override
    public void update()
    {
        if (validated) {
            setColor();
            for (final Pixel child : children) {
                child.update();
            }
        } else {
            throw new IllegalStateException("Cannot update before validating.");
        }
    }

    @Override
    public String toString()
    {
        return String.format("Color: (%s, %s, %s), Position: (%s, %s)",
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                position.getX(),
                position.getY());
    }
}