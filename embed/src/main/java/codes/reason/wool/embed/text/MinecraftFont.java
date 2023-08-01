package codes.reason.wool.embed.text;

import codes.reason.wool.embed.Embed;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class MinecraftFont {

    public static Font SUPER_SMALL;
    public static Font SMALL;
    public static Font MEDIUM;
    public static Font LARGE;

    static {
        try {

            SUPER_SMALL = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Embed.class.getResourceAsStream("/Minecraft.ttf")))
                    .deriveFont(Font.PLAIN, 6);

            SMALL = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Embed.class.getResourceAsStream("/Minecraft.ttf")))
                    .deriveFont(Font.PLAIN, 8);

            MEDIUM = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Embed.class.getResourceAsStream("/Minecraft.ttf")))
                    .deriveFont(Font.PLAIN, 16);

            LARGE = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Embed.class.getResourceAsStream("/Minecraft.ttf")))
                    .deriveFont(Font.PLAIN, 24);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }


}
