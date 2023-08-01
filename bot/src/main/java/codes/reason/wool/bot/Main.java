package codes.reason.wool.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) {
//        WoolWarsAPI.getLeaderboard()
//                .thenAccept(response -> {
//                    for (LeaderboardResponse.Player player : response.getLeaderboard()) {
//                        System.out.println(response.getCategory());
//                        System.out.println(player.getUuid() + ": " + player.getAmount());
//                    }
//                });


//        int amount = 0;
//        for (int i = 1; i < 100; i++) {
//            amount+= StatUtil.getRequired(i);
//        }
//        System.out.println("amount = " + amount);
//
//        System.exit(0);
//        if (true) return;

        try {
            JDA jda = JDABuilder.createLight(System.getenv("BOT_TOKEN"))
                    .build();

            new WoolStats(jda);
        } catch (LoginException e) {
            System.err.println(e.getMessage());
        }
    }

}
