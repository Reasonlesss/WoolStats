package codes.reason.wool.embed.components;

import codes.reason.wool.embed.Component;
import codes.reason.wool.util.ImageHelper;

import java.awt.image.BufferedImage;

public class VerticalSpacerComponent implements Component {

    @Override
    public BufferedImage render() {
        return ImageHelper.createImage(1, 5);
    }
}
