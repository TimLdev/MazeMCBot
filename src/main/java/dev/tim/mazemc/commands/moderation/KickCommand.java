package dev.tim.mazemc.commands.moderation;

import dev.tim.mazemc.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KickCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equalsIgnoreCase("kick")) {

            OptionMapping option1 = event.getOption("gebruiker");
            OptionMapping option2 = event.getOption("reden");

            if (event.getMember().hasPermission(Permission.KICK_MEMBERS)) {

                if (option1 == null || option2 == null) {
                    event.reply("Er is iets fout gegaan bij dit command!").setEphemeral(true).queue();
                    return;
                }

                User user = option1.getAsUser();
                String reason = option2.getAsString();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("`\uD83D\uDD28` | " + user.getName() + " gekickt!");
                embed.addField("Reden:", reason, false);
                embed.addField("Door:", event.getMember().getAsMention(), false);
                embed.setThumbnail(user.getEffectiveAvatarUrl());
                embed.setColor(Color.RED);
                embed.setFooter("MazeMC", event.getGuild().getIconUrl());

                // LOGS EMBED
                EmbedBuilder logEmbed = new EmbedBuilder();
                logEmbed.setColor(new Color(168, 64, 255));
                logEmbed.setTitle("Gebruiker gekickt!");
                logEmbed.addField("Gekickt", user.getAsMention() + " (`" + user.getId() + "`)", false);
                logEmbed.addField("Gekickt door", event.getMember().getAsMention(), false);
                logEmbed.addField("Reden", reason, false);
                logEmbed.addField("Datum", dtf.format(now), false);
                logEmbed.setThumbnail(user.getEffectiveAvatarUrl());

                event.getGuild().kick(user).reason(reason).queue();

                event.getGuild().getTextChannelById(Utils.LOGS_CHANNEL_ID).sendMessageEmbeds(logEmbed.build()).queue();
                event.getChannel().sendMessageEmbeds(embed.build()).queue();

                event.reply("`✅` **Gebruiker succesvol gekickt!**").setEphemeral(true).queue();

            } else {
                event.reply("`❌` Je hebt geen permissie voor dit command!").setEphemeral(true).queue();
            }
        }
    }
}

