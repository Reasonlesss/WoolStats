package codes.reason.wool.embed.components;

import codes.reason.wool.embed.Component;
import codes.reason.wool.util.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageComponent implements Component {

    private final String url;

    public ImageComponent(String url) {
        this.url = url;
    }

    @Override
    public BufferedImage render() {
        try {
            return ImageIO.read(new URL(this.url));
        } catch (IOException e) {
            return ImageHelper.createImage(1, 1);
        }
    }

}
