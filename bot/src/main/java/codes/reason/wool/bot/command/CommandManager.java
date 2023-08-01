package codes.reason.wool.bot.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {

    private final JDA jda;
    private final Map<String, Command> commands = new HashMap<>();

    public CommandManager(JDA jda) {
        this.jda = jda;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void register(Command... commands) {
        for (Command command : commands) {
            CommandCreateAction action = this.jda.upsertCommand(command.getName(), command.getDescription());

            for (OptionData option : command.getOptions()) {
                action.addOptions(option);
            }

            action.queue(cmd -> this.commands.put(cmd.getId(), command));
        }
    }

    public Command getCommandFromID(String id) {
        return commands.get(id);
    }

    public Map<String, Command> getCommands() {
        return this.commands;
    }

    public List<String> getCommandList() {
        List<String> commandList = new ArrayList<>();
        for (Command value : this.commands.values()) {
            commandList.add(value.getName());
        }
        return commandList;
    }

}
