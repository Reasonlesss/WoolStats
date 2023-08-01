package codes.reason.wool.bot.command.impl;

import codes.reason.wool.bot.command.Command;
import codes.reason.wool.bot.embeds.BotStatsEmbed;
import codes.reason.wool.bot.util.EmbedUtil;
import codes.reason.wool.database.analytics.AnalyticsHandler;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.Collections;
import java.util.List;

public class BotStatsCommand implements Command {
    @Override
    public String getName() {
        return "botstats";
    }

    @Override
    public String getDescription() {
        return "Get the stats of the bot.";
    }

    @Override
    public List<OptionData> getOptions() {
        return Collections.emptyList();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        AnalyticsHandler.getCommandStats().thenAccept(commandStats -> {
            AnalyticsHandler.getDailyStats().thenAccept(dailyStats -> {

                BotStatsEmbed botStatsEmbed = new BotStatsEmbed(commandStats, dailyStats);

                EmbedUtil.prepareEmbed(event.getHook(), botStatsEmbed)
                        .thenAccept(action -> action.setActionRow(Button.link("https://discord.gg/3ANhMZtfU2", "Join our Discord!")
                                .withEmoji(Emoji.fromUnicode("\uD83D\uDDE8️"))).queue());
            });
        });

    }
}
