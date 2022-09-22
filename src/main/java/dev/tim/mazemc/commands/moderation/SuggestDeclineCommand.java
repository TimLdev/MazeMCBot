package dev.tim.mazemc.commands.moderation;

import dev.tim.mazemc.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SuggestDeclineCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equalsIgnoreCase("suggestdecline")){

            if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {

                OptionMapping option = event.getOption("id");
                OptionMapping option2 = event.getOption("reden");

                if ((option == null) || (option2 == null)) {
                    event.reply("`❌` Error!").setEphemeral(true).queue();
                    return;
                }

                long id = option.getAsLong();
                String reason = option2.getAsString();

                TextChannel suggestionChannel = event.getGuild().getTextChannelById(Utils.SUGGEST_CHANNEL_ID);

                event.getGuild().getTextChannelById(Utils.SUGGEST_DECLINED_CHANNEL_ID).sendMessageEmbeds(suggestionChannel.retrieveMessageById(id).complete().getEmbeds()).queue();

                EmbedBuilder embedAccepted = new EmbedBuilder();
                embedAccepted.setTitle("❌ | Suggestie Afgewezen");
                embedAccepted.addField("Reden:", reason, false);
                embedAccepted.addField("Door:", event.getMember().getAsMention(), false);
                embedAccepted.setColor(Color.RED);
                embedAccepted.setFooter("MazeMC", event.getGuild().getIconUrl());
                event.getGuild().getTextChannelById(Utils.SUGGEST_DECLINED_CHANNEL_ID).sendMessageEmbeds(embedAccepted.build()).queue();

                event.reply("Suggestie succesvol afgewezen!").queue();

            } else {
                event.reply("`❌` Je hebt geen permissie voor dit command!").setEphemeral(true).queue();
            }
        }
    }
}
