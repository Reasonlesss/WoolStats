package codes.reason.wool.embed.components;

import codes.reason.wool.embed.Component;
import codes.reason.wool.util.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Item List:
     - Wood Sword
     - Wood Pickaxe
     - Wool
     - Shears
     - Keystone
     - Iron Pickaxe
     - Stone Spade
     - Potion_16396 (health)
     - Potion_16389 (health 1)
     - Potion_16421 (damage)
     - Potion_16387 (absorption)
     - Wood Axe
     - Bow
     - Arrow
     - Stone Sword
 */
public class HotbarComponent implements Component {

    private static final Color BACKGROUND = new Color(0x282828);

    private static final Map<String, BufferedImage> ITEM_CACHE = new HashMap<>();

    static {
        for (String item : new String[] {"WOOD_SWORD", "WOOD_PICKAXE", "WOOL", "SHEARS", "KEYSTONE", "IRON_PICKAXE",
        "STONE_SPADE", "POTION_16396", "POTION_16389", "POTION_16421", "POTION_16421", "POTION_16387", "WOOD_AXE",
        "BOW", "ARROW", "STONE_SWORD", "STONE_PICKAXE"}) {
            InputStream inputStream = HotbarComponent.class.getResourceAsStream("/items/" + item + ".png");

            if (inputStream == null) continue;

            try {
                final BufferedImage image = ImageIO.read(inputStream);
                ITEM_CACHE.put(item, image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private final List<String> slots;

    public HotbarComponent(List<String> slots) {
        this.slots = slots;
    }

    @Override
    public BufferedImage render() {
        BufferedImage bufferedImage = ImageHelper.createImage(355, 39);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = 0;
        for (String slot : slots) {
            g2d.setColor(BACKGROUND);
            g2d.fillRect(x, 2, 36, 36);
            g2d.setColor(Color.WHITE);
            g2d.drawImage(ITEM_CACHE.get(slot), x + 2, 5, null);
            x+=40;
        }


        g2d.dispose();
        return bufferedImage;
    }

}
