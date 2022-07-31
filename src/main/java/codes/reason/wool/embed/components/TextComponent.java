package codes.reason.wool.embed.components;

import codes.reason.wool.embed.Component;
import codes.reason.wool.util.ImageHelper;
import codes.reason.wool.text.TextColor;
import codes.reason.wool.util.TextUtil;
import codes.reason.wool.text.TextPart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TextComponent implements Component {

    private final Font font;
    private final List<TextPart> partList = new ArrayList<>();

    public TextComponent(Font font, String text) {
        this.font = font;
        partList.add(new TextPart(text, TextColor.WHITE));
    }

    public TextComponent(Font font, TextPart... text) {
        this(font, List.of(text));
    }

    public TextComponent(Font font, List<TextPart> text) {
        this.font = font;
        partList.addAll(text);
    }

    public TextComponent append(TextPart part) {
        this.partList.add(part);
        return this;
    }

    @Override
    public BufferedImage render() {
        BufferedImage bufferedImage = ImageHelper.createImage(1024, 1024);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setFont(font);

        FontMetrics metrics = g2d.getFontMetrics(font);

        int height = metrics.getHeight() - metrics.getDescent();
        int y = metrics.getAscent() - metrics.getDescent();
        TextUtil.drawColoredText(g2d, partList, 0, y);
        int width = TextUtil.getWidth(g2d, partList);

        g2d.dispose();



        return bufferedImage.getSubimage(0, 0, width > 0 ? width : 1, height - 1);
    }


}
