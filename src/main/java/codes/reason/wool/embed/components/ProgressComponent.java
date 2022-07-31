package codes.reason.wool.embed.components;

import codes.reason.wool.embed.Component;
import codes.reason.wool.util.ImageHelper;
import codes.reason.wool.text.TextColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ProgressComponent implements Component {

    private static final Color BACKGROUND = new Color(0x282828);

    private final int width;
    private final int value;
    private final int maxValue;

    public ProgressComponent(int width, int value, int maxValue) {
        this.width = width;
        this.value = value;
        this.maxValue = maxValue;
    }

    @Override
    public BufferedImage render() {
        final BufferedImage image = ImageHelper.createImage(width, 20);
        Graphics2D g2d = image.createGraphics();

        double multi = (double) this.value / (double) this.maxValue;
        int progress = (int) (width * multi);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(BACKGROUND);
        g2d.fillRoundRect(0, 2, width, 16, 16, 16);
        g2d.setColor(TextColor.AQUA.getColor());
        g2d.fillRoundRect(0, 2, progress, 16, 16, 16);

        g2d.dispose();
        return image;
    }

}
