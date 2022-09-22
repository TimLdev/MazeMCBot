package dev.tim.mazemc.listeners;

import dev.tim.mazemc.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SuggestModalListener extends ListenerAdapter {

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        if(event.getModalId().equals("suggestmodal")){
            event.deferReply().queue();

            String suggestion = event.getValue("suggestion").getAsString();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("\uD83D\uDCDC | Suggestie");
            embed.addField("Suggestie:", suggestion, false);
            embed.addField("Ingediend door:", event.getMember().getAsMention(), false);
            embed.setFooter("MazeMC", event.getGuild().getIconUrl());
            embed.setColor(Color.WHITE);
            event.getGuild().getTextChannelById(Utils.SUGGEST_CHANNEL_ID).sendMessageEmbeds(embed.build()).queue(message -> {
                message.addReaction(Utils.SUGGEST_PLUS_EMOJI_ID).queue();
                message.addReaction(Utils.SUGGEST_MINUS_EMOJI_ID).queue();
            });

            // LOGS EMBED
            EmbedBuilder embedLog = new EmbedBuilder();
            embedLog.setTitle("\uD83D\uDCDC | Suggestie");
            embedLog.addField("Suggestie:", suggestion, false);
            embedLog.addField("Ingediend door:", event.getMember().getAsMention(), false);
            embedLog.setFooter("MazeMC", event.getGuild().getIconUrl());
            embedLog.setColor(Color.WHITE);
            event.getGuild().getTextChannelById(Utils.SUGGEST_LOGS_CHANNEL_ID).sendMessageEmbeds(embedLog.build()).queue();

            event.getHook().sendMessage(":white_check_mark: Suggestie geplaatst!").queue();
        }
    }
}
