package codes.reason.wool.bot.command.impl;

import codes.reason.wool.bot.command.Command;
import codes.reason.wool.bot.embeds.PlayerSummaryEmbed;
import codes.reason.wool.bot.util.EmbedUtil;
import codes.reason.wool.database.player.PlayerHandler;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import java.util.List;
import java.util.Objects;

public class StatsCommand implements Command {

    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "Returns the stats of a given player.";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "player", "The name of the player.", true)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        event.deferReply().queue();

        String player = Objects.requireNonNull(event.getOption("player")).getAsString();

        PlayerHandler.getPlayer(player).thenAccept(response -> {
            PlayerSummaryEmbed embed = new PlayerSummaryEmbed(response);

            // ignore this please :innocent:
            SelectMenu selectMenu = SelectMenu.create("stat_type")
                    .setPlaceholder("Pick a category.")
                    .addOption("Overall Stats", "overall_" + response.getUuid(),
                            "View overall statistics such as kills, deaths and wins.",
                            Emoji.fromUnicode("\uD83D\uDCCB"))
                    .addOption("Tank Stats", "tank_" + response.getUuid(),
                            "View statistics of the tank class.",
                            Emoji.fromEmote("class_tank", 980595098083545118L, false))
                    .addOption("Assault Stats", "assault_" + response.getUuid(),
                            "View statistics of the assault class.",
                            Emoji.fromEmote("class_assault", 980595150290030613L, false))
                    .addOption("Archer Stats", "archer_" + response.getUuid(),
                            "View statistics of the archer class.",
                            Emoji.fromEmote("class_archer", 980595209731727421L, false))
                    .addOption("Swordsman Stats", "swordsman_" + response.getUuid(),
                            "View statistics of the swordsman class.",
                            Emoji.fromEmote("class_swordsman", 980595274873470996L, false))
                    .addOption("Golem Stats", "golem_" + response.getUuid(),
                            "View statistics of the golem class.",
                            Emoji.fromEmote("class_golem", 980595325683257414L, false))
                    .addOption("Engineer Stats", "engineer_" + response.getUuid(),
                            "View statistics of the engineer class.",
                            Emoji.fromEmote("class_engineer", 980595374005817364L, false))
                    .build();

            EmbedUtil.prepareEmbed(event.getHook(), embed, response.getCacheTime())
                    .thenAccept(action -> action.setActionRow(selectMenu).queue());

        }).exceptionally(e -> {
            event.getHook().editOriginalEmbeds(EmbedUtil.createError(e.getCause())).queue();
            return null;
        });

    }
}
