package codes.reason.wool.bot.listener;

import codes.reason.wool.bot.WoolStats;
import codes.reason.wool.bot.command.Command;
import codes.reason.wool.bot.command.CommandManager;
import codes.reason.wool.database.analytics.AnalyticsHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        CommandManager commandManager = WoolStats.INSTANCE.getCommandManager();
        Command command = commandManager.getCommandFromID(event.getCommandId());
        if (command != null) {
            command.execute(event);
            AnalyticsHandler.incrementCommandStat(command.getName());
            AnalyticsHandler.updateDailyStats(event.getJDA().getGuilds().size());
        }
    }

}
