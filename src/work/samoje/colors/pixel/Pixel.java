package work.samoje.colors.pixel;

import java.awt.Color;
import java.awt.Point;

public interface Pixel {
    void addChild(Pixel childPixel);
    void update();
    Color getColor();
    Point getPosition();
    void validate();
}
