package codes.reason.wool.embed.components;

import codes.reason.wool.embed.Component;
import codes.reason.wool.text.MinecraftFont;
import codes.reason.wool.text.TextColor;
import codes.reason.wool.util.ImageHelper;

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
            TextColor.GRAY, TextColor.WHITE, TextColor.RED, TextColor.GOLD
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
    private final boolean prefixShown;

    public StarComponent(int level, boolean prefixShown) {
        this.level = level;
        this.prefixShown = prefixShown;
    }

    public StarComponent(int level) {
        this(level, true);
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
        g2d.drawString("[" + level, x, y);
        x += metrics.stringWidth("[" + level) + 1;

        // Draw the colored star separately
        g2d.drawImage(STAR, x, y - 13, null);
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.fillRect(x, y - 13, x + 15, y);
        g2d.setComposite(AlphaComposite.Src);

        x += 17;
        g2d.drawString("]", x, y);

        g2d.dispose();

        return bufferedImage.getSubimage(0, 0, x + metrics.stringWidth("]"), height - 1);
    }


}
