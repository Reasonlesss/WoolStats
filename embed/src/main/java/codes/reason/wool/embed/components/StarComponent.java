package codes.reason.wool.embed.components;

import codes.reason.wool.common.PrestigeIcon;
import codes.reason.wool.common.TextColor;
import codes.reason.wool.embed.Component;
import codes.reason.wool.embed.ImageHelper;
import codes.reason.wool.embed.text.MinecraftFont;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Hacky solution since im not gonna modify the font
 */
public class StarComponent implements Component {

    public static List<TextColor> PRESTIGES = List.of(
            TextColor.GRAY,
            TextColor.WHITE,
            TextColor.RED,
            TextColor.GOLD,
            TextColor.YELLOW,
            TextColor.GREEN,
            TextColor.DARK_AQUA,
            TextColor.DARK_PURPLE,
            TextColor.LIGHT_PURPLE
            // i cba to do the rest
    );


    private static BufferedImage STAR;

    static {
        InputStream inputStream = StarComponent.class.getResourceAsStream("/star.png");

        try {
            assert inputStream != null;
            STAR = ImageIO.read(inputStream);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Font FONT = MinecraftFont.MEDIUM;
    private final int level;
    private final PrestigeIcon icon;
    private final boolean prefixShown;

    public StarComponent(int level, PrestigeIcon icon, boolean prefixShown) {
        this.level = level;
        this.icon = icon;
        this.prefixShown = prefixShown;
    }

    public StarComponent(int level, PrestigeIcon icon) {
        this(level, icon, true);
    }

    @Override
    public BufferedImage render() {
        BufferedImage bufferedImage = ImageHelper.createImage(1024, 1024);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setFont(FONT);

        FontMetrics metrics = g2d.getFontMetrics();

        TextColor prestigeColor = PRESTIGES.get((int) Math.min(PRESTIGES.size() - 1, Math.floor(level / 100.0)));

//        int height = (int) ((int) lineMetrics.getHeight() - lineMetrics.getDescent());
//        int y =
//        TextUtil.drawColoredText(g2d, partList, 0, y);

        int x = 0;
        int height = metrics.getHeight() - metrics.getDescent();
        int y = metrics.getAscent() - metrics.getDescent();

        if (prefixShown) {
            g2d.drawString("Level: ", x, y);
            x += metrics.stringWidth("Level: ") + 1;
        }

        g2d.setColor(prestigeColor.getColor());
        String starPrefix = "[" + level + icon.getIcon() + "]";
        g2d.drawString(starPrefix, x, y);
        x += metrics.stringWidth(starPrefix);

        g2d.dispose();

        return bufferedImage.getSubimage(0, 0, x, height - 1);
    }


}
