package codes.reason.wool.bot.util;

import codes.reason.wool.common.TimeUtil;
import codes.reason.wool.embed.Embed;
import codes.reason.wool.embed.ImageHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageUpdateAction;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class EmbedUtil {

    private static CompletableFuture<WebhookMessageUpdateAction<Message>> prepareEmbed(InteractionHook hook, Embed embed, MessageEmbed discordEmbed) {
        CompletableFuture<WebhookMessageUpdateAction<Message>> future = new CompletableFuture<>();
        embed.render().thenAccept(image -> {
            try {
                future.complete(hook.editOriginalEmbeds(discordEmbed)
                        .addFile(ImageHelper.toBytes(image), "embed.png"));
            } catch (IOException e) {
                future.completeExceptionally(e);
                e.printStackTrace();
            }
        }).exceptionally(e -> {
            future.completeExceptionally(e);
            e.printStackTrace();
            return null;
        });
        return future;
    }

    public static CompletableFuture<WebhookMessageUpdateAction<Message>> prepareEmbed(InteractionHook hook, Embed embed) {
        return prepareEmbed(hook, embed, Embeds.STATS_EMBED);
    }

    public static CompletableFuture<WebhookMessageUpdateAction<Message>> prepareEmbed(InteractionHook hook, Embed embed, long timestamp) {
        MessageEmbed messageEmbed = new EmbedBuilder()
                .setAuthor("WoolWars Statistics", "https://discord.gg/3ANhMZtfU2", "https://github.com/Owen1212055/mc-assets/blob/main/assets/OAK_SIGN.png?raw=true")
                .setDescription("`•` Information was updated `" + TimeUtil.toRelativeTime(timestamp) + "`.")
                .setImage("attachment://embed.png")
                .setFooter("Made with ❤️ by Harpieee")
                .setColor(Color.CYAN)
                .build();
        return prepareEmbed(hook, embed, messageEmbed);
    }

    public static MessageEmbed createError(Throwable throwable) {
        System.out.println(throwable.getClass().getName());
        String simpleError = throwable.getMessage();//.replaceFirst(throwable.getClass().getName() + ": ", "");
        System.out.println("simpleError = " + simpleError);

        throwable.printStackTrace();

        return new EmbedBuilder()
                .setDescription(":warning: An error occurred when trying to do this, " + simpleError + "")
                .setColor(Color.RED)
                .build();
    }

}
