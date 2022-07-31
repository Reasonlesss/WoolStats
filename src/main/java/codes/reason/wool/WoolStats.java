package codes.reason.wool;

import codes.reason.wool.command.CommandManager;
import codes.reason.wool.command.impl.BotStatsCommand;
import codes.reason.wool.command.impl.GraphsCommand;
import codes.reason.wool.command.impl.LeaderboardCommand;
import codes.reason.wool.listener.CommandListener;
import codes.reason.wool.command.impl.StatsCommand;
import codes.reason.wool.listener.SelectMenuListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
 * WoolStats is a stat checker for Hypixel's WoolWars
 *
 * APIs Used:
 *  - Hypixel Public API
 *  - woolwars.net
 *  - playerdb for Username -> UUID and UUID -> Username queries
 *  - crafatar for player heads
 */
public class WoolStats {

    public static long SUPPORT_GUILD = 981658937541656576L;

    public static WoolStats INSTANCE;
    private final ExecutorService executorService = Executors.newFixedThreadPool(40);

    private final CommandManager commandManager;

    private final JDA jda;

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

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public int getGuilds() {
        return this.jda.getGuilds().size();
    }
}
