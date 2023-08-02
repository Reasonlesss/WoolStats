package codes.reason.wool.bot.embeds;

import codes.reason.wool.api.statistics.StatisticCategory;
import codes.reason.wool.api.statistics.StatisticType;
import codes.reason.wool.common.NumberFormatter;
import codes.reason.wool.common.SkinHelper;
import codes.reason.wool.common.StatUtil;
import codes.reason.wool.common.TextColor;
import codes.reason.wool.database.player.Player;
import codes.reason.wool.embed.Component;
import codes.reason.wool.embed.Embed;
import codes.reason.wool.embed.components.GroupComponent;
import codes.reason.wool.embed.components.ProgressComponent;
import codes.reason.wool.embed.components.StarComponent;
import codes.reason.wool.embed.components.TextComponent;
import codes.reason.wool.embed.components.VerticalSpacerComponent;
import codes.reason.wool.embed.text.MinecraftFont;
import codes.reason.wool.embed.text.TextPart;

public class PlayerSummaryEmbed extends Embed {


    public PlayerSummaryEmbed(Player player) {

        double experience = player.getExperience();

        int stars = StatUtil.getStar(experience);
        double required = StatUtil.getRequired(stars);
        double progress = StatUtil.getProgress(stars, (int) experience);

        this.setTitle(player.getUsername());
        this.setDescription("Statistics Summary");
        this.setIcon(SkinHelper.getFaceURL(player.getUuid()));

        String playtime = NumberFormatter.formatTime(player.getPlaytime());

        int wins = player.getStats().get(StatisticCategory.OVERALL).getStat(StatisticType.WINS).getValue().intValue();
        int played = player.getStats().get(StatisticCategory.OVERALL).getStat(StatisticType.GAMES_PLAYED).getValue().intValue();
        long time = player.getPlaytime() + (played * 25_000L);
        long winrate = 3600000L / (time / wins);


        //new TextComponent(MinecraftFont.SMALL, new TextPart("Estimated Win-rate", TextColor.GRAY)),
        //                        new TextComponent(MinecraftFont.MEDIUM, new TextPart(winrate + "/h", TextColor.YELLOW))

        Component stats = new GroupComponent(0, 0, 0, 0)
                .add(
                        new TextComponent(MinecraftFont.SMALL, new TextPart("Experience Rank", TextColor.GRAY)),
                        new TextComponent(MinecraftFont.MEDIUM, new TextPart("#" + NumberFormatter.formatNumber(player.getRank()), TextColor.AQUA)),
                        new VerticalSpacerComponent(),

                        new TextComponent(MinecraftFont.SMALL, new TextPart("Total Experience", TextColor.GRAY)),
                        new TextComponent(MinecraftFont.MEDIUM, new TextPart(NumberFormatter.shortenNumber(player.getExperience()), TextColor.AQUA)),
                        new VerticalSpacerComponent(),

                        new TextComponent(MinecraftFont.SMALL, new TextPart("Estimated Playtime", TextColor.GRAY)),
                        new TextComponent(MinecraftFont.MEDIUM, new TextPart(playtime, TextColor.GREEN)),
                        new VerticalSpacerComponent(),

                        new TextComponent(MinecraftFont.SMALL, new TextPart("Games Played", TextColor.GRAY)),
                        new TextComponent(MinecraftFont.MEDIUM, new TextPart(NumberFormatter.shortenNumber(played), TextColor.YELLOW))
                );

        Component scoreboard = new GroupComponent(0, 0, 0, 0)
                .add(
                        new StarComponent(stars),
                        new GroupComponent(0, 10, 6, 12)
                                .add(
                                        new TextComponent(
                                                MinecraftFont.MEDIUM,
                                                new TextPart("Progress: ", TextColor.WHITE),
                                                new TextPart(NumberFormatter.shortenNumber(progress), TextColor.AQUA),

                                                new TextPart("/", TextColor.WHITE),
                                                new TextPart(NumberFormatter.shortenNumber(required), TextColor.GREEN)
                                        ),
                                        new ProgressComponent(200, (int) progress, (int) required)
                                ),

                        new TextComponent(
                                MinecraftFont.MEDIUM,
                                new TextPart("Wool: ", TextColor.WHITE),
                                new TextPart(NumberFormatter.formatNumber(player.getWool()), TextColor.GOLD)
                        ),

                        new TextComponent(
                                MinecraftFont.MEDIUM,
                                new TextPart("Layers: ", TextColor.WHITE),
                                new TextPart(String.valueOf(player.getLayers()), TextColor.GREEN)
                        )

                );

        Component disclaimer = new TextComponent(
                MinecraftFont.SMALL,
                new TextPart("Note! ", TextColor.RED),
                new TextPart("Playtime is an estimate and may be slightly inaccurate.", TextColor.GRAY)
        );

        Component disclaimer2 = new TextComponent(
                MinecraftFont.SMALL,
                new TextPart("Time spent in the queue for a game is not considered as playtime.", TextColor.GRAY)
        );

        this.add(new GroupComponent(0, 0, 0, 0, 210).setRowGap(16).add(stats, scoreboard));
        this.add(new VerticalSpacerComponent());
        this.add(disclaimer);
        this.add(disclaimer2);
    }

}
