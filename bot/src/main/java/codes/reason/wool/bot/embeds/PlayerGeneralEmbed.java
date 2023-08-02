package codes.reason.wool.bot.embeds;

import codes.reason.wool.api.statistics.StatisticType;
import codes.reason.wool.api.statistics.Statistics;
import codes.reason.wool.common.NumberFormatter;
import codes.reason.wool.common.SkinHelper;
import codes.reason.wool.common.TextColor;
import codes.reason.wool.database.player.Player;
import codes.reason.wool.embed.Embed;
import codes.reason.wool.embed.components.TableComponent;
import codes.reason.wool.embed.components.TextComponent;
import codes.reason.wool.embed.components.VerticalSpacerComponent;
import codes.reason.wool.embed.text.MinecraftFont;
import codes.reason.wool.embed.text.TextPart;

public class PlayerGeneralEmbed extends Embed {

    public PlayerGeneralEmbed(Player player, Statistics stats) {
        this.setTitle(player.getUsername());
        this.setDescription("General Statistics");

        this.setIcon(SkinHelper.getFaceURL(player.getUuid()));

        this.add(new TextComponent(MinecraftFont.SMALL, new TextPart("Winstreak", TextColor.GRAY)));
        this.add(
                new TextComponent(
                    MinecraftFont.MEDIUM,
                    new TextPart(NumberFormatter.formatNumber(player.getWinstreak()), TextColor.AQUA),
                    new TextPart("/", TextColor.WHITE),
                    new TextPart(NumberFormatter.formatNumber(player.getMaxWinstreak()), TextColor.AQUA)
                )
        );
        this.add(new VerticalSpacerComponent());

        this.add(new TextComponent(MinecraftFont.SMALL, new TextPart("W/L Ratio", TextColor.GRAY)));
        double roundedWlr = (double) Math.round(stats.getStat(StatisticType.WLR).getValue().doubleValue() * 100) / 100;
        this.add(new TextComponent(MinecraftFont.MEDIUM, new TextPart(String.valueOf(roundedWlr), TextColor.GREEN)));
        this.add(new VerticalSpacerComponent());

        this.add(new TextComponent(MinecraftFont.SMALL, new TextPart("K/D Ratio", TextColor.GRAY)));
        double roundedKdr = (double) Math.round(stats.getStat(StatisticType.KDR).getValue().doubleValue() * 100) / 100;
        this.add(new TextComponent(MinecraftFont.MEDIUM, new TextPart(String.valueOf(roundedKdr), TextColor.GREEN)));
        this.add(new VerticalSpacerComponent());

        double kdar = (stats.getStat(StatisticType.KILLS).getValue().doubleValue() + stats.getStat(StatisticType.ASSISTS).getValue().doubleValue())
                        / stats.getStat(StatisticType.DEATHS).getValue().doubleValue();
        double roundedKadr = (double) Math.round(kdar * 100) / 100;
        this.add(new TextComponent(MinecraftFont.SMALL, new TextPart("K+A/D Ratio", TextColor.GRAY)));
        this.add(new TextComponent(MinecraftFont.MEDIUM, new TextPart(String.valueOf(roundedKadr), TextColor.GREEN)));
        this.add(new VerticalSpacerComponent());

        TableComponent table = new TableComponent("Stat", "Amount", "LB #");
        addStatsToTable(table, "Wins", stats.getStat(StatisticType.WINS));
        addStatsToTable(table, "Losses", stats.getStat(StatisticType.LOSSES));
        addStatsToTable(table, "Kills", stats.getStat(StatisticType.KILLS));
        addStatsToTable(table, "Assists", stats.getStat(StatisticType.ASSISTS));
        addStatsToTable(table, "Deaths", stats.getStat(StatisticType.DEATHS));
        addStatsToTable(table, "Placed", stats.getStat(StatisticType.WOOL_PLACED));
        addStatsToTable(table, "Broken", stats.getStat(StatisticType.BLOCKS_BROKEN));
        addStatsToTable(table, "Power-ups", stats.getStat(StatisticType.POWERUPS_GOTTEN));
        this.add(table);
    }

    private void addStatsToTable(TableComponent tableComponent, String name, Statistics.Statistic statistic) {
        tableComponent.addRow(name, statistic.getValue(), statistic.getPosition());
    }



}
