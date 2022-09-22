package dev.tim.mazemc.commands.moderation;

import dev.tim.mazemc.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;

public class UnlockCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equalsIgnoreCase("unlock")){

            if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {

                Guild guild = event.getGuild();

                event.getTextChannel().getManager().putRolePermissionOverride(guild.getPublicRole().getIdLong(), EnumSet.of(Permission.MESSAGE_SEND), null).queue();

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("`\uD83D\uDD13` | Kanaal unlocked!");
                embed.addField("Door:", event.getMember().getAsMention(), false);
                embed.setThumbnail(event.getMember().getEffectiveAvatarUrl());
                embed.setColor(Color.GREEN);
                embed.setFooter("MazeMC", event.getGuild().getIconUrl());
                event.getChannel().sendMessageEmbeds(embed.build()).queue();

                // LOGS EMBED
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                EmbedBuilder logEmbed = new EmbedBuilder();
                logEmbed.setColor(new Color(0, 255, 34));
                logEmbed.setTitle("Kanaal unlocked!");
                logEmbed.addField("Kanaal", event.getTextChannel().getName(), false);
                logEmbed.addField("Door", event.getMember().getAsMention(), false);
                logEmbed.addField("Datum", dtf.format(now), false);
                logEmbed.setFooter("MazeMC", event.getGuild().getIconUrl());
                logEmbed.setThumbnail(event.getMember().getEffectiveAvatarUrl());
                event.getGuild().getTextChannelById(Utils.LOGS_CHANNEL_ID).sendMessageEmbeds(logEmbed.build()).queue();

                event.reply("`✅` Kanaal succesvol gelocked!").setEphemeral(true).queue();
            } else {
                event.reply("`❌` Je hebt geen permissie voor dit command!").setEphemeral(true).queue();
            }
        }
    }
}

