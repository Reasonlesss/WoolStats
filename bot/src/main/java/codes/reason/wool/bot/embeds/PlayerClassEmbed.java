package codes.reason.wool.bot.embeds;

import codes.reason.wool.api.statistics.StatisticCategory;
import codes.reason.wool.api.statistics.StatisticType;
import codes.reason.wool.api.statistics.Statistics;
import codes.reason.wool.common.NumberFormatter;
import codes.reason.wool.common.SkinHelper;
import codes.reason.wool.common.StringUtil;
import codes.reason.wool.common.TextColor;
import codes.reason.wool.database.player.Player;
import codes.reason.wool.embed.Embed;
import codes.reason.wool.embed.components.GroupComponent;
import codes.reason.wool.embed.components.HotbarComponent;
import codes.reason.wool.embed.components.TableComponent;
import codes.reason.wool.embed.components.TextComponent;
import codes.reason.wool.embed.components.VerticalSpacerComponent;
import codes.reason.wool.embed.text.MinecraftFont;
import codes.reason.wool.embed.text.TextPart;

public class PlayerClassEmbed extends Embed {

    public PlayerClassEmbed(StatisticCategory theClass, Player playerData, Statistics stats) {
        this.setTitle(playerData.getUsername());
        this.setDescription(StringUtil.uppercaseFirst(theClass.name().toLowerCase()) + " Statistics");
        this.setIcon(SkinHelper.getFaceURL(playerData.getUuid()));

        double roundedKdr = (double) Math.round(stats.getStat(StatisticType.KDR).getValue().doubleValue() * 100) / 100;

        double kadr = (stats.getStat(StatisticType.KILLS).getValue().doubleValue() + stats.getStat(StatisticType.ASSISTS).getValue().doubleValue())
                / Math.max(stats.getStat(StatisticType.DEATHS).getValue().doubleValue(), 1);
        double roundedKadr = (double) Math.round(kadr * 100) / 100;


        TableComponent table = new TableComponent("Stat", "Amount", "LB #");
        addStatsToTable(table, "Kills", stats.getStat(StatisticType.KILLS));
        addStatsToTable(table, "Deaths", stats.getStat(StatisticType.DEATHS));
        addStatsToTable(table, "Assists", stats.getStat(StatisticType.ASSISTS));
        addStatsToTable(table, "Placed", stats.getStat(StatisticType.WOOL_PLACED));
        addStatsToTable(table, "Broken", stats.getStat(StatisticType.BLOCKS_BROKEN));

        this.add(new GroupComponent(0, 0, 0, 2, 130).add(
                new TextComponent(MinecraftFont.SMALL, new TextPart("Class K/D", TextColor.GRAY)),
                new TextComponent(MinecraftFont.MEDIUM, new TextPart(String.valueOf(roundedKdr), TextColor.GREEN)),
                new VerticalSpacerComponent(),

                new TextComponent(MinecraftFont.SMALL, new TextPart("Class K+A/D", TextColor.GRAY)),
                new TextComponent(MinecraftFont.MEDIUM, new TextPart(String.valueOf(roundedKadr), TextColor.GREEN)),
                table
        ).setRowGap(14));

        this.add(new TextComponent(
           MinecraftFont.SMALL,
           new TextPart("Class Loadout", TextColor.GRAY)
        ));
        this.add(new HotbarComponent(playerData.getLayouts().get(theClass)));

    }

    private void addStatsToTable(TableComponent table, String name, Statistics.Statistic statistic) {
        table.addRow(name, statistic.getValue(), NumberFormatter.shortenNumber(statistic.getPosition()));
    }

}
