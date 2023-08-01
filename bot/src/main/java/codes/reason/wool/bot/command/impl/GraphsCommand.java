package codes.reason.wool.bot.command.impl;

import codes.reason.wool.bot.command.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class GraphsCommand implements Command {

    @Override
    public String getName() {
        return "graphs";
    }

    @Override
    public String getDescription() {
        return "View statistics graphs of a given player.";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "player", "The name of the player.", true),
                new OptionData(OptionType.STRING, "class", "The class you want to check.", false)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
//        WoolWarsAPI.getPlayer(player).thenAccept(response -> {
//            UUID uuid = response.getUuid();
//
//            WoolWarsAPI.getHistoricInformation(uuid, StatisticsCategory.OVERALL).thenAccept(historicResponse -> {
//                Embed embed = new Embed()
//                        .setTitle(response.getDisplay())
//                        .setDescription("Experience over Time");
//
//                EmbedUtil.prepareEmbed(event.getHook(), embed, response.getUpdated())
//                        .thenAccept(RestAction::queue);
//
//
//            }).exceptionally(e -> {
//                e.printStackTrace();
//                return null;
//            });
//        }).exceptionally(e -> {
//            e.printStackTrace();
//            return null;
//        });
    }
}
