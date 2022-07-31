package codes.reason.wool.embed;

import codes.reason.wool.WoolStats;
import codes.reason.wool.text.MinecraftFont;
import codes.reason.wool.text.TextColor;
import codes.reason.wool.text.TextPart;
import codes.reason.wool.util.TextUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnusedReturnValue")
public class Embed {



    private String icon;
    private String description;
    private List<TextPart> title = new ArrayList<>();
    private final List<Component> components = new ArrayList<>();

    public Embed setIcon(String url) {
        this.icon = url;
        return this;
    }

    public Embed setDescription(String description) {
        this.description = description;
        return this;
    }

    public Embed setTitle(List<TextPart> title) {
        this.title = title;
        return this;
    }

    public Embed setTitle(String title) {
        this.title = TextUtil.parseCodes('§', title);
        return this;
    }

    public Embed add(Component component) {
        components.add(component);
        return this;
    }

    /**
     * Renders the embed image, title and components so that it
     * can be attached to a discord embed.
     * @return The rendered embed
     */
    public CompletableFuture<BufferedImage> render() {
        final CompletableFuture<BufferedImage> future = new CompletableFuture<>();

        WoolStats.INSTANCE.getExecutorService().submit(() -> {
            InputStream inputStream = getClass().getResourceAsStream("/template.png");

            // this should never EVER happen
            if (inputStream == null) throw new RuntimeException("Could not load the template.png resource.");

            final BufferedImage image;
            try {
                image = ImageIO.read(inputStream);
            } catch (IOException e) {
                future.completeExceptionally(e);
                return;
            }
            final Graphics2D g2d = image.createGraphics();

            int textX = 20;
            int textY = 37;

            if (this.icon != null) {
                try {
                    BufferedImage avatar = ImageIO.read(new URL(this.icon));
                    g2d.drawImage(avatar, 14, 14, 32, 32, null);
                    textX += 40;
                } catch (IOException ignored) {}
            }

            if (this.description != null) {
                textY -= 5;
                g2d.setFont(MinecraftFont.SMALL);
                TextUtil.drawColoredText(g2d, Collections.singletonList(new TextPart(this.description, TextColor.GRAY)), textX, 43);
            }

            g2d.setFont(MinecraftFont.MEDIUM);
            TextUtil.drawColoredText(g2d, title, textX, textY);

            int width = 0;
            int x = 15;
            int y = 60;

            for (Component component : components) {
                BufferedImage comp = component.render();

                if (y + comp.getHeight() > 225) {
                    y = 60;
                    x += width + 8;
                    width = 0;
                }

                g2d.setColor(Color.WHITE);
//                g2d.drawRect(x, y, comp.getWidth(), comp.getHeight());
                g2d.drawImage(comp, x, y, null);
                width = Math.max(width, comp.getWidth());

                y += comp.getHeight() ;
            }

            future.complete(image);
        });

        return future;
    }

}
