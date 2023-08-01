package codes.reason.wool.bot.listener;

import codes.reason.wool.api.statistics.StatisticCategory;
import codes.reason.wool.api.statistics.Statistics;
import codes.reason.wool.bot.embeds.PlayerClassEmbed;
import codes.reason.wool.bot.embeds.PlayerGeneralEmbed;
import codes.reason.wool.bot.util.EmbedUtil;
import codes.reason.wool.database.player.PlayerHandler;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

// I want to change this system one day but im not sure when.
public class SelectMenuListener extends ListenerAdapter {

    @Override
    public void onSelectMenuInteraction(@NotNull SelectMenuInteractionEvent event) {
        String id = event.getInteraction().getComponentId();

        if (id.equalsIgnoreCase("stat_type")) {
            event.deferReply().setEphemeral(true).queue();

            String value = event.getInteraction().getSelectedOptions().get(0).getValue();
            String[] split = value.split("_");

            UUID uuid = UUID.fromString(split[1]);
            StatisticCategory category = StatisticCategory.valueOf(split[0].toUpperCase());

            PlayerHandler.getPlayer(uuid).thenAccept(player -> {
                Statistics stats = player.getStats().get(category);
                if (category == StatisticCategory.OVERALL) {
                    EmbedUtil.prepareEmbed(event.getHook(), new PlayerGeneralEmbed(player, stats), player.getCacheTime())
                            .thenAccept(RestAction::queue);
                }else {
                    EmbedUtil.prepareEmbed(event.getHook(), new PlayerClassEmbed(category, player, stats), player.getCacheTime())
                            .thenAccept(RestAction::queue);
                }
            });

        }

    }
}
