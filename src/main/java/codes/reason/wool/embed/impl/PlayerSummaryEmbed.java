package codes.reason.wool.embed.impl;

import codes.reason.wool.database.player.Player;
import codes.reason.wool.embed.Embed;
import codes.reason.wool.embed.components.GroupComponent;
import codes.reason.wool.embed.components.ProgressComponent;
import codes.reason.wool.embed.components.StarComponent;
import codes.reason.wool.embed.components.TextComponent;
import codes.reason.wool.util.SkinHelper;
import codes.reason.wool.util.NumberFormatter;
import codes.reason.wool.util.StatUtil;
import codes.reason.wool.text.MinecraftFont;
import codes.reason.wool.text.TextColor;
import codes.reason.wool.text.TextPart;

public class PlayerSummaryEmbed extends Embed {


    public PlayerSummaryEmbed(Player player) {

        int stars = player.getStar();
        double experience = player.getExperience();

        double required = StatUtil.getRequired(stars);
        double progress = StatUtil.getProgress(stars, (int) experience);

        this.setTitle(player.getUsername());
        this.setDescription("Statistics Summary");
        this.setIcon(SkinHelper.getFaceURL(player.getUuid()));


        this.add(new StarComponent(stars));
        this.add(new TextComponent(MinecraftFont.SMALL, new TextPart("This player is ranked #" + NumberFormatter.formatNumber(player.getRank()) + " based on their level.", TextColor.DARK_GRAY)));

        this.add(
                new GroupComponent(8, 10, 6, 12)
                        .add(
                                new TextComponent(
                                    MinecraftFont.MEDIUM,
                                    new TextPart("Progress: ", TextColor.WHITE),
                                    new TextPart(NumberFormatter.shortenNumber(progress), TextColor.AQUA),
                                    new TextPart("/", TextColor.WHITE),
                                    new TextPart(NumberFormatter.shortenNumber(required), TextColor.GREEN)
                                ),
                                new ProgressComponent(200, (int) progress, (int) required)
                        )
        );

        this.add(new TextComponent(
                MinecraftFont.MEDIUM,
                new TextPart("Wool: ", TextColor.WHITE),
                new TextPart(NumberFormatter.formatNumber(player.getWool()), TextColor.GOLD)
        ));
        this.add(new TextComponent(
                MinecraftFont.MEDIUM,
                new TextPart("Layers: ", TextColor.WHITE),
                new TextPart(String.valueOf(player.getLayers()), TextColor.GREEN)
        ));

        String playtime = NumberFormatter.formatTime(player.getPlaytime());

        this.add(new TextComponent(
                MinecraftFont.MEDIUM,
                new TextPart("Playtime: ", TextColor.WHITE),
                new TextPart(playtime, TextColor.AQUA)
        ));
        this.add(new TextComponent(MinecraftFont.SMALL, new TextPart("Playtime is an estimate based on experience.", TextColor.DARK_GRAY)));



    }

}
