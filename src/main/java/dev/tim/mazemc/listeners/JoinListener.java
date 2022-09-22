package dev.tim.mazemc.listeners;

import dev.tim.mazemc.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class JoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        int membercount = event.getGuild().getMembers().size();
        String accountCreated = event.getMember().getTimeCreated().getDayOfMonth() + "/" + event.getMember().getTimeCreated().getMonthValue() + "/" + event.getMember().getTimeCreated().getYear();
        String joinedDate = event.getMember().getTimeJoined().getDayOfMonth() + "/" + event.getMember().getTimeJoined().getMonthValue() + "/" + event.getMember().getTimeJoined().getYear();

        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(Utils.MEMBER_ROLE_ID)).queue();

        EmbedBuilder logEmbed = new EmbedBuilder();
        logEmbed.setColor(Color.GREEN);
        logEmbed.setTitle(event.getMember().getEffectiveName() + " gejoined");
        logEmbed.addField("Gejoined", event.getMember().getAsMention() + " (`" + event.getMember().getId() + "`)", false);
        logEmbed.addField("Account gemaakt", accountCreated, false);
        logEmbed.addField("Gejoined op", joinedDate, false);
        logEmbed.addField("Discord members", membercount + " members", false);
        logEmbed.setThumbnail(event.getMember().getUser().getEffectiveAvatarUrl());
        event.getGuild().getTextChannelById(Utils.LOGS_CHANNEL_ID).sendMessageEmbeds(logEmbed.build()).queue();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(135, 255, 60));
        embed.setTitle("```\uD83D\uDC4B``` **| Welkom**");
        embed.setDescription("Welkom " + event.getMember().getAsMention() + " in de MazeMC Discord!\nGebruik de knoppen hieronder voor meer informatie.\n\nEr zitten nu **" + membercount + "** gebruikers in deze server.");
        embed.setThumbnail(event.getMember().getUser().getEffectiveAvatarUrl());
        embed.setFooter("MazeMC", event.getGuild().getIconUrl());
        event.getGuild().getTextChannelById(Utils.WELCOME_CHANNEL_ID).sendMessageEmbeds(embed.build())
                .setActionRow(Button.link("https://discord.com/channels/972466531365703690/972466531567042603", "\uD83D\uDCD9 | Regels"),
                        Button.link("https://store.mazemc.nl", "\uD83D\uDED2 | Shop"))
                .queue();

        // MEMBER COUNT
        event.getGuild().getVoiceChannelById(Utils.MEMBERCOUNT_CHANNEL_ID).getManager().setName("\uD83D\uDC65〡" + membercount).complete();
    }
}
