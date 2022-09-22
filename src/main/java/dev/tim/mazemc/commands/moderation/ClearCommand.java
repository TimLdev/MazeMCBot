package dev.tim.mazemc.commands.moderation;

import dev.tim.mazemc.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;;import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClearCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equalsIgnoreCase("clear")) {

            OptionMapping option = event.getOption("aantal");

            if (event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {

                if (option == null) {
                    event.reply("`❌` Je hebt geen aantal meegegeven!").setEphemeral(true).queue();
                    return;
                }

                int aantal = option.getAsInt();

                List<Message> messageList = event.getChannel().getHistory().retrievePast(aantal).complete();
                event.getTextChannel().deleteMessages(messageList).queue();

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("`\uD83D\uDDD1` | Berichten verwijderd!");
                embed.addField("Door:", event.getMember().getAsMention(), false);
                embed.addField("Aantal verwijderd:", String.valueOf(aantal), false);
                embed.setThumbnail(event.getMember().getEffectiveAvatarUrl());
                embed.setColor(Color.RED);
                embed.setFooter("MazeMC", event.getGuild().getIconUrl());
                event.getChannel().sendMessageEmbeds(embed.build()).queue();

                event.reply("`✅` " + aantal + " berichten verwijderd!").setEphemeral(true).queue();

                // LOGS EMBED
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                EmbedBuilder logEmbed = new EmbedBuilder();
                logEmbed.setColor(new Color(111, 255, 126));
                logEmbed.setTitle("Clear command");
                logEmbed.setDescription(event.getMember().getEffectiveName() + " heeft `/clear` gebruikt.");
                logEmbed.addField("Gebruiker", event.getMember().getAsMention() + " (`" + event.getMember().getId() + "`)", false);
                logEmbed.addField("Aantal verwijderd", String.valueOf(aantal), false);
                logEmbed.addField("Datum", dtf.format(now), false);
                logEmbed.setThumbnail(event.getMember().getEffectiveAvatarUrl());

                event.getGuild().getTextChannelById(Utils.LOGS_CHANNEL_ID).sendMessageEmbeds(logEmbed.build()).queue();

            } else {
                event.reply("`❌` Je hebt geen permissie voor dit command!").setEphemeral(true).queue();
            }
        }
    }

}

