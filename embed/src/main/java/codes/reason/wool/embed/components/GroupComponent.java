package codes.reason.wool.embed.components;

import codes.reason.wool.embed.Component;
import codes.reason.wool.embed.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupComponent implements Component {

    // Padding
    private final int left;
    private final int top;
    private final int right;
    private final int bottom;

    private int maxHeight = 200;

    private int rowGap = 8;

    // Components
    private final List<Component> components = new ArrayList<>();

    public GroupComponent(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public GroupComponent(int left, int top, int right, int bottom, int maxHeight) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.maxHeight = maxHeight;
    }

    public GroupComponent setRowGap(int gap) {
        this.rowGap = gap;
        return this;
    }

    public GroupComponent add(Component... components) {
        this.components.addAll(Arrays.asList(components));
        return this;
    }

    @Override
    public BufferedImage render() {
        BufferedImage bufferedImage = ImageHelper.createImage(1024, 1024);
        Graphics2D g2d = bufferedImage.createGraphics();

        int imgWidth = 0;
        int imgHeight = 0;

        int width = 0;
        int x = left;
        int y = top;

        for (Component component : components) {
            BufferedImage comp = component.render();

            if (y + comp.getHeight() > maxHeight) {
                y = top;
                x += width + rowGap;
                width = 0;
            }

            g2d.setColor(Color.LIGHT_GRAY);
//            g2d.drawRect(x, y, comp.getWidth(), comp.getHeight());
            g2d.setColor(Color.WHITE);

            g2d.drawImage(comp, x, y, null);
            width = Math.max(width, comp.getWidth());
            imgWidth = Math.max(imgWidth, x + comp.getWidth());
            imgHeight = Math.max(imgHeight, y +  comp.getHeight());

            y += comp.getHeight();
        }

        g2d.dispose();

        return bufferedImage.getSubimage(0, 0, imgWidth + right, imgHeight + bottom);
    }
}
