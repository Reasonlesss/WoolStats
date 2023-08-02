package codes.reason.wool.embed.components;

import codes.reason.wool.common.TextColor;
import codes.reason.wool.embed.Component;
import codes.reason.wool.embed.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ProgressComponent implements Component {

    private static final Color LINE = new Color(0x505050);
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
        final BufferedImage image = ImageHelper.createImage(width, 26);
        Graphics2D g2d = image.createGraphics();

        double multi = (double) this.value / (double) this.maxValue;
        int progress = (int) ((width - 6) * multi);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(LINE);
        g2d.drawRect(0, 5, width - 1, 20);

        g2d.setColor(BACKGROUND);
        g2d.fillRect(3, 8, width - 6, 15);
        g2d.setColor(TextColor.AQUA.getColor());
        g2d.fillRect(3, 8, progress, 15);

        g2d.dispose();
        return image;
    }

}
