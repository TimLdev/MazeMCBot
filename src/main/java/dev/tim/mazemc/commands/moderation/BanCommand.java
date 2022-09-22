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

public class BanCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equalsIgnoreCase("ban")){

            OptionMapping option1 = event.getOption("gebruiker");
            OptionMapping option2 = event.getOption("reden");

            if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {

                if (option1 == null || option2 == null) {
                    event.reply("Er is iets fout gegaan bij dit command!").setEphemeral(true).queue();
                    return;
                }

                User user = option1.getAsUser();
                String reason = option2.getAsString();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("`⛔` | " + user.getName() + " verbannen!");
                embed.addField("Reden:", reason, false);
                embed.addField("Door:", event.getMember().getAsMention(), false);
                embed.setThumbnail(user.getEffectiveAvatarUrl());
                embed.setColor(Color.RED);
                embed.setFooter("MazeMC", event.getGuild().getIconUrl());

                // LOGS EMBED
                EmbedBuilder logEmbed = new EmbedBuilder();
                logEmbed.setColor(new Color(255, 105, 0));
                logEmbed.setTitle("Gebruiker verbannen!");
                logEmbed.addField("Verbannen", user.getAsMention() + " (`" + user.getId() + "`)", false);
                logEmbed.addField("Verbannen door", event.getMember().getAsMention(), false);
                logEmbed.addField("Reden", reason, false);
                logEmbed.addField("Datum", dtf.format(now), false);
                logEmbed.setFooter("MazeMC", event.getGuild().getIconUrl());
                logEmbed.setThumbnail(user.getEffectiveAvatarUrl());

                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                event.getGuild().getTextChannelById(Utils.LOGS_CHANNEL_ID).sendMessageEmbeds(logEmbed.build()).queue();

                event.getGuild().ban(user, 7, reason).queue();
                event.reply("`✅` **Gebruiker succesvol verbannen!**").setEphemeral(true).queue();

            } else {
                event.reply("`❌` Je hebt geen permissie voor dit command!").setEphemeral(true).queue();
            }
        }
    }
}
