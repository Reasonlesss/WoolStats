package codes.reason.wool.bot;

import codes.reason.wool.bot.command.CommandManager;
import codes.reason.wool.bot.command.impl.BotStatsCommand;
import codes.reason.wool.bot.command.impl.GraphsCommand;
import codes.reason.wool.bot.command.impl.LeaderboardCommand;
import codes.reason.wool.bot.command.impl.StatsCommand;
import codes.reason.wool.bot.listener.CommandListener;
import codes.reason.wool.bot.listener.SelectMenuListener;
import codes.reason.wool.common.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;


/*
 * WoolStats is a stat checker for Hypixel's WoolWars
 *
 * APIs Used:
 *  - Hypixel Public API and woolwars.net for statistics
 *  - playerdb for Username-UUID queries
 *  - crafthead.net for Player Heads
 */
public class WoolStats {

    public static long SUPPORT_GUILD = 981658937541656576L;

    public static WoolStats INSTANCE;

    private final CommandManager commandManager;

    private final JDA jda;

    public static final boolean DEVELOPER_MODE = Config.getBoolean("DEV");

    public WoolStats(JDA jda) {
        INSTANCE = this;

        this.jda = jda;




        jda.getPresence().setPresence(Activity.listening("/stats"), false);

        this.commandManager = new CommandManager(jda);
        this.commandManager.register(new StatsCommand(), new LeaderboardCommand(), new GraphsCommand(), new BotStatsCommand());

        jda.addEventListener(new CommandListener(), new SelectMenuListener());
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public int getGuilds() {
        return this.jda.getGuilds().size();
    }
}
