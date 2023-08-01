package codes.reason.wool.bot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

// todo: remove
public class Embeds {

    public static MessageEmbed STATS_EMBED = new EmbedBuilder()
            .setImage("attachment://embed.png")
            .setFooter("Made with ❤️ by Harpieee")
            .build();

    public static MessageEmbed ERROR_EMBED = new EmbedBuilder()
            .setDescription("An error occurred when trying to execute this command!")
            .setColor(Color.RED)
            .build();

}
