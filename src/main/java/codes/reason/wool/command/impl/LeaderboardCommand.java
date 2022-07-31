package codes.reason.wool.command.impl;

import codes.reason.wool.api.WoolWarsAPI;
import codes.reason.wool.command.Command;
import codes.reason.wool.embed.impl.LeaderboardEmbed;
import codes.reason.wool.util.EmbedUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.RestAction;

import java.util.List;
import java.util.Objects;

public class LeaderboardCommand implements Command {
    @Override
    public String getName() {
        return "leaderboard";
    }

    @Override
    public String getDescription() {
        return "View the leaderboard of a specific category.";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "category", "The category you wish to see the leaderboard for.", true),
                new OptionData(OptionType.STRING, "class", "Filter by class.", false)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String category = Objects.requireNonNull(event.getInteraction().getOption("category")).getAsString();

        WoolWarsAPI.getLeaderboards().thenAccept(response -> {
            LeaderboardEmbed embed = new LeaderboardEmbed(response);
            EmbedUtil.prepareEmbed(event.getHook(), embed).thenAccept(RestAction::queue);
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }
}
