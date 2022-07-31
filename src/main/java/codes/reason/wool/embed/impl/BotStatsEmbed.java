package codes.reason.wool.embed.impl;

import codes.reason.wool.WoolStats;
import codes.reason.wool.embed.Embed;
import codes.reason.wool.embed.components.GroupComponent;
import codes.reason.wool.embed.components.LineGraphComponent;
import codes.reason.wool.embed.components.TableComponent;
import codes.reason.wool.embed.components.TextComponent;
import codes.reason.wool.text.MinecraftFont;
import codes.reason.wool.text.TextColor;
import codes.reason.wool.text.TextPart;
import codes.reason.wool.util.NumberFormatter;

import java.util.List;
import java.util.Map;

public class BotStatsEmbed extends Embed {

    public BotStatsEmbed(Map<String, Integer> commandStats, List<Map<String, Integer>> dailyStats) {

        this.setTitle("Bot Statistics");

        int total = commandStats.remove("total");

        // Command Statistics
        TableComponent tableComponent = new TableComponent("Command", "Times Ran");

        commandStats.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(3L)
                .forEach(entry -> tableComponent.addRow("/" + entry.getKey(), NumberFormatter.shortenNumber(entry.getValue())));

        // Normal Statistics
        this.add(new GroupComponent(0, 0, 0, 0, 120)
                .add(new TextComponent(MinecraftFont.SMALL, new TextPart("Guilds", TextColor.GRAY)))
                .add(new TextComponent(MinecraftFont.MEDIUM, new TextPart(NumberFormatter.formatNumber(WoolStats.INSTANCE.getGuilds()), TextColor.GREEN)))

                .add(new TextComponent(MinecraftFont.SMALL, new TextPart("Commands Ran", TextColor.GRAY)))
                .add(new TextComponent(MinecraftFont.MEDIUM, new TextPart(NumberFormatter.shortenNumber(total), TextColor.GOLD)))

                .add(tableComponent)
        );

        // Graphs
        LineGraphComponent guilds = new LineGraphComponent(180, 65);
        LineGraphComponent commands = new LineGraphComponent(180, 65);

        int i = 0;
        for (Map<String, Integer> dailyStat : dailyStats) {
            guilds.add(i, dailyStat.get("guilds"));
            commands.add(i, dailyStat.get("commands"));
            i++;
        }

        guilds.add(i, WoolStats.INSTANCE.getGuilds());
        commands.add(i, total);

        this.add(new GroupComponent(0, 10, 0, 0, 90)
                .add(
                        new TextComponent(MinecraftFont.SMALL, new TextPart("Guilds Over Time", TextColor.GRAY)),
                        guilds,
                        new TextComponent(MinecraftFont.SMALL, new TextPart("Commands Over Time", TextColor.GRAY)),
                        commands
                ));

    }
}
