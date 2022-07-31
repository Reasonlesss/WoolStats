package codes.reason.wool.common;

import java.awt.*;

public enum TextColor {
    DARK_RED(new Color(0xAA0000)),
    RED(new Color(0xFF5555)),
    GOLD(new Color(0xFFAA00)),
    YELLOW(new Color(0xFFFF55)),
    DARK_GREEN(new Color(0x00AA00)),
    GREEN(new Color(0x55FF55)),
    AQUA(new Color(0x55FFFF)),
    DARK_AQUA(new Color(0x00AAAA)),
    DARK_BLUE(new Color(0x0000AA)),
    BLUE(new Color(0x5555FF)),
    LIGHT_PURPLE(new Color(0xFF55FF)),
    DARK_PURPLE(new Color(0xAA00AA)),
    WHITE(new Color(0xFFFFFF)),
    GRAY(new Color(0xAAAAAA)),
    DARK_GRAY(new Color(0x555555)),
    BLACK(new Color(0x000000));

    private final Color color;

    TextColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
