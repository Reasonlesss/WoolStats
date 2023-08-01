package codes.reason.wool.embed.components;

import codes.reason.wool.common.NumberFormatter;
import codes.reason.wool.common.TextColor;
import codes.reason.wool.embed.Component;
import codes.reason.wool.embed.ImageHelper;
import codes.reason.wool.embed.text.MinecraftFont;
import codes.reason.wool.embed.text.TextPart;
import codes.reason.wool.embed.text.TextUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableComponent implements Component {

    private final List<String> columns = new ArrayList<>();
    private final List<List<Component>> rows = new ArrayList<>();

    public TableComponent(String...  columns) {
        for (String column : columns) {
            this.columns.add(column);
            rows.add(new ArrayList<>());
        }
    }

    public TableComponent addRow(Object... objects) {
        int i = 0;
        for (Object object : objects) {
            if (object instanceof Number num) {
                object = NumberFormatter.formatNumber(num);
            }

            if (object instanceof String string) {
                List<TextPart> parts = List.of(new TextPart(string, TextColor.WHITE));
                object = new TextComponent(MinecraftFont.MEDIUM, parts);
            }

            if (object instanceof Component component) {
                this.rows.get(i).add(component);
            }

            i++;
        }
        return this;
    }

    public TableComponent addRow(Component... components) {
        int i = 0;
        for (Component component : components) {
            this.rows.get(i).add(component);
            i++;
        }
        return this;
    }

    @Override
    public BufferedImage render() {
        final BufferedImage image = ImageHelper.createImage(1024, 1024);
        final Graphics2D g2d = image.createGraphics();

        int x = 2;
        int y = 0;


        FontMetrics smallMetrics = g2d.getFontMetrics(MinecraftFont.SMALL);

        Map<Integer, Integer> heights = new HashMap<>();

        for (int column = 0; column < this.columns.size(); column++) {
            int i = 0;
            for (Component component : this.rows.get(column)) {
                BufferedImage bufferedImage = component.render();
                int h = bufferedImage.getHeight();
                heights.putIfAbsent(i, 0);
                heights.put(i, Math.max(heights.get(i), h));
                i++;
            }
        }

        int column = 0;
        for (String columnName : this.columns) {
            y = 7;
            g2d.setFont(MinecraftFont.SMALL);
            TextUtil.drawColoredText(g2d, List.of(new TextPart(columnName, TextColor.GRAY)), x, y);

            int width = smallMetrics.stringWidth(columnName);

            g2d.setFont(MinecraftFont.MEDIUM);
            y += 6;

            int i = 0;
            for (Component component : this.rows.get(column)) {
                BufferedImage bufferedImage = component.render();
                int w = bufferedImage.getWidth();
                int h = bufferedImage.getHeight();
                g2d.drawImage(bufferedImage, x, y + ((heights.get(i) - h) / 2), w, h, null);
                y += heights.get(i);
                width = Math.max(width, w);

                i++;
            }

            x += width + 22;
            column++;
        }

        g2d.setColor(TextColor.GRAY.getColor());
        g2d.fillRect(0, 10, x, 2);


        g2d.dispose();
        return image.getSubimage(0, 0, x, y + 5);
    }
}
