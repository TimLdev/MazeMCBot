package dev.tim.mazemc.listeners;

import dev.tim.mazemc.utils.Utils;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReactionRoleAddListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if(event.getNewsChannel().equals(event.getGuild().getNewsChannelById(Utils.REACTION_ROLE_CHANNEl_ID))) {
            if (event.getReactionEmote().getEmoji().equals("\uD83D\uDCE3")) {
                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(Utils.UPDATE_ROLE_ID)).queue();
            } else if (event.getReactionEmote().getEmoji().equals("\uD83D\uDC40")) {
                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(Utils.SNEAKPEAK_ROLE_ID)).queue();
            } else if (event.getReactionEmote().getEmoji().equals("\uD83C\uDF89")) {
                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(Utils.EVENT_ROLE_ID)).queue();
            }
        }
    }
}