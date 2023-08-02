package codes.reason.wool.bot.embeds;

import codes.reason.wool.bot.WoolStats;
import codes.reason.wool.common.NumberFormatter;
import codes.reason.wool.common.TextColor;
import codes.reason.wool.database.analytics.CommandStats;
import codes.reason.wool.database.analytics.DailyStats;
import codes.reason.wool.embed.Embed;
import codes.reason.wool.embed.components.GroupComponent;
import codes.reason.wool.embed.components.LineGraphComponent;
import codes.reason.wool.embed.components.TableComponent;
import codes.reason.wool.embed.components.TextComponent;
import codes.reason.wool.embed.components.VerticalSpacerComponent;
import codes.reason.wool.embed.text.MinecraftFont;
import codes.reason.wool.embed.text.TextPart;

import java.util.List;

public class BotStatsEmbed extends Embed {

    public BotStatsEmbed(CommandStats commandStats, List<DailyStats> dailyStats) {

        this.setTitle("Bot Statistics");

        // Command Statistics
        TableComponent tableComponent = new TableComponent("Command", "Times Ran");

        commandStats.stats().entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(3L)
                .forEach(entry -> tableComponent.addRow("/" + entry.getKey(), NumberFormatter.shortenNumber(entry.getValue())));

        // Normal Statistics
        this.add(new GroupComponent(0, 0, 0, 0, 120)
                .add(
                        new TextComponent(MinecraftFont.SMALL, new TextPart("Guilds", TextColor.GRAY)),
                        new TextComponent(MinecraftFont.MEDIUM, new TextPart(NumberFormatter.formatNumber(WoolStats.INSTANCE.getGuilds()), TextColor.GREEN)),
                        new VerticalSpacerComponent(),

                        new TextComponent(MinecraftFont.SMALL, new TextPart("Commands Ran", TextColor.GRAY)),
                        new TextComponent(MinecraftFont.MEDIUM, new TextPart(NumberFormatter.shortenNumber(commandStats.total()), TextColor.GOLD)),
                        tableComponent
                )
        );

        // Graphs
        LineGraphComponent guilds = new LineGraphComponent(180, 65);
        LineGraphComponent commands = new LineGraphComponent(180, 65);

        int i = 0;
        for (DailyStats dailyStat : dailyStats) {
            guilds.add(i, dailyStat.guilds());
            commands.add(i, dailyStat.commands());
            i++;
        }

        guilds.add(i, WoolStats.INSTANCE.getGuilds());
        commands.add(i, commandStats.total());

        this.add(new GroupComponent(0, 10, 0, 0, 90)
                .add(
                        new TextComponent(MinecraftFont.SMALL, new TextPart("Guilds Over Time", TextColor.GRAY)),
                        guilds,

                        new TextComponent(MinecraftFont.SMALL, new TextPart("Commands Over Time", TextColor.GRAY)),
                        commands
                ));

    }
}
