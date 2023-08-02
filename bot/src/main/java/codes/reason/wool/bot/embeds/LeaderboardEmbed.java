package codes.reason.wool.bot.embeds;

import codes.reason.wool.api.response.LeaderboardResponse;
import codes.reason.wool.api.statistics.Leaderboard;
import codes.reason.wool.common.SkinHelper;
import codes.reason.wool.common.StatUtil;
import codes.reason.wool.embed.Embed;
import codes.reason.wool.embed.components.ImageComponent;
import codes.reason.wool.embed.components.StarComponent;
import codes.reason.wool.embed.components.TableComponent;

import java.awt.image.BufferedImage;

public class LeaderboardEmbed extends Embed {

    public LeaderboardEmbed(LeaderboardResponse response) {

        setTitle("Leaderboards");
        setDescription("Top Players ranked by Experience");
        TableComponent tableComponent = new TableComponent("Skin", "Username", "Stars");

        Leaderboard expLb = response.getGeneralLeaderboards().getExperience();

//        for (int i = 0; i < 8; i++) {
//            Leaderboard.LeaderboardEntry entry = expLb.getEntry(i);
//            tableComponent.addRow(
//                    new ImageComponent(SkinHelper.getFaceURL(entry.getUuid())),
//                    entry.getIgn(),
//                    new StarComponent(StatUtil.getStar(entry.getValue().intValue()), false));
//        }

        this.add(tableComponent);
    }

    private BufferedImage renderExp(double exp) {
//        int star;
//
//        System.out.println(exp);
//        BufferedImage bo = new StarComponent(star, false).render();
//        return bo.getSubimage(0, 2, bo.getWidth(), bo.getHeight() - 5);
        return null;
    }

}
