package codes.reason.wool.embed.components;

import codes.reason.wool.common.NumberFormatter;
import codes.reason.wool.embed.Component;
import codes.reason.wool.embed.ImageHelper;
import codes.reason.wool.embed.text.MinecraftFont;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LineGraphComponent implements Component {

    private final int width;
    private final int height;
    private final List<Coordinate> coordinateList = new ArrayList<>();
    private int lowerX = 9999999;
    private int higherX = 0;
    private int lowerY = 9999999;
    private int higherY = 0;

    public LineGraphComponent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public LineGraphComponent add(int x, int y) {
        // Bounds?

        lowerX = Math.min(x, lowerX);
        lowerY = Math.min(y, lowerY);
        higherX = Math.max(x, higherX);
        higherY = Math.max(y, higherY);

        coordinateList.add(new Coordinate(x, y));

        return this;
    }

    @Override
    public BufferedImage render() {
        BufferedImage bufferedImage = ImageHelper.createImage(width, height);
        Graphics2D g2d = bufferedImage.createGraphics();

        this.coordinateList.sort(Comparator.comparingInt(Coordinate::x));

        g2d.setColor(new Color(0x282828));
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.WHITE);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int alternating = (int) Math.ceil(coordinateList.size() / (width / 46d));

        int differenceX = higherX - lowerX;
        int differenceY = higherY - lowerY;

        // Graph Axes
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(6, 4, 2, height - 8);
        g2d.fillRect(4, height - 8, width - 8, 2);
        g2d.setColor(Color.WHITE);

        int graphHeight = height - 19;
        int graphWidth = width - 19;

        int lastX = 0, lastY = 0;

        int i = 0;

        for (Coordinate coordinate : coordinateList) {
            double multiplierX = (double) (coordinate.x() - lowerX) / differenceX;
            double multiplierY = (double) (coordinate.y() - lowerY) / differenceY;
            int renderX = (int) (10 + (multiplierX * graphWidth));
            int renderY = 6 + graphHeight - (int) ((multiplierY * graphHeight));

            g2d.setColor(Color.WHITE);
            if (i > 0) {
                g2d.drawLine(lastX, lastY, renderX + 1, renderY + 1);
            }

            lastX = renderX + 1;
            lastY = renderY + 1;

            g2d.fillRect(renderX, renderY, 3, 3);

            i++;
        }


        // repeated code is poggers.
        i= 0;
        int size = coordinateList.size() - 1;
        for (Coordinate coordinate : coordinateList) {
            if ((size - i) % alternating == 0) {
                double multiplierX = (double) (coordinate.x() - lowerX) / differenceX;
                double multiplierY = (double) (coordinate.y() - lowerY) / differenceY;
                int renderX = (int) (10 + (multiplierX * graphWidth));
                int renderY = 6 + graphHeight - (int) ((multiplierY * graphHeight));

                g2d.setFont(MinecraftFont.SMALL);

                FontMetrics fontMetrics = g2d.getFontMetrics();


                String text = NumberFormatter.shortenNumber(coordinate.y());
                int x = renderX + (renderX > graphWidth / 2 ? (-fontMetrics.stringWidth(text)) + 2: 2);
                int y = renderY + (renderY > graphHeight / 2 ? -2 : 11);
                g2d.setColor(new Color(0x282828));
                g2d.fillRect(x, y - 8, fontMetrics.stringWidth(text), 10);
                g2d.setColor(Color.ORANGE);
                g2d.drawString(text, x, y);
            }
            i++;
        }

        g2d.dispose();
        return bufferedImage;
    }

    private record Coordinate(int x, int y) {
    }
}
