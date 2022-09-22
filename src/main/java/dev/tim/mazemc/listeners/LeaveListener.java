package dev.tim.mazemc.listeners;

import dev.tim.mazemc.utils.Utils;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class LeaveListener extends ListenerAdapter {

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        int membercount = event.getGuild().getMembers().size();

        event.getGuild().getVoiceChannelById(Utils.MEMBERCOUNT_CHANNEL_ID).getManager().setName("\uD83D\uDC65〡" + membercount).complete();
    }
}
