package codes.reason.wool.command.impl;

import codes.reason.wool.WoolStats;
import codes.reason.wool.command.Command;
import codes.reason.wool.embed.impl.BotStatsEmbed;
import codes.reason.wool.util.EmbedUtil;
import codes.reason.wool.util.MongoHelper;
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

        String[] commandList = WoolStats.INSTANCE.getCommandManager().getCommandList().toArray(new String[]{});
        MongoHelper.getCommandStats(commandList).thenAccept(commandStats -> {
            MongoHelper.getDailyStats().thenAccept(dailyStats -> {

                BotStatsEmbed botStatsEmbed = new BotStatsEmbed(commandStats, dailyStats);

                EmbedUtil.prepareEmbed(event.getHook(), botStatsEmbed)
                        .thenAccept(action -> action.setActionRow(Button.link("https://discord.gg/3ANhMZtfU2", "Join our Discord!")
                                .withEmoji(Emoji.fromUnicode("\uD83D\uDDE8️"))).queue());
            });
        });

    }
}
