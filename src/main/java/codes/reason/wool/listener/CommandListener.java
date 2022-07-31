package codes.reason.wool.listener;

import codes.reason.wool.WoolStats;
import codes.reason.wool.command.Command;
import codes.reason.wool.command.CommandManager;
import codes.reason.wool.util.MongoHelper;
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
            MongoHelper.incrementCommandStat(command.getName());
        }
    }

}
