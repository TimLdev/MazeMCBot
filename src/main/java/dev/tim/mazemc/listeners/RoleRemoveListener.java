package dev.tim.mazemc.listeners;

import dev.tim.mazemc.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class RoleRemoveListener extends ListenerAdapter {

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.RED);
        embed.setTitle("Role verwijderd");
        embed.addField("Rol:", event.getRoles().toString(), false);
        embed.addField("Aan:", event.getMember().getAsMention(), false);
        embed.setFooter("MazeMC", event.getGuild().getIconUrl());

        event.getGuild().getTextChannelById(Utils.LOGS_CHANNEL_ID).sendMessageEmbeds(embed.build()).queue();

    }
}
