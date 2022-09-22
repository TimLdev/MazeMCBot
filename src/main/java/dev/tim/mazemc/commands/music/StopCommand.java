package dev.tim.mazemc.commands.music;

import dev.tim.mazemc.lavaplayer.GuildMusicManager;
import dev.tim.mazemc.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class StopCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equalsIgnoreCase("stop")){
            event.deferReply().queue();

            Member self = event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if(!selfVoiceState.inAudioChannel()){
                event.getHook().sendMessage("`❌` Ik moet in een voice channel zitten!").setEphemeral(true).queue();
                return;
            }

            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();

            if(!memberVoiceState.inAudioChannel()){
                event.getHook().sendMessage("`❌` Je moet in een voice channel zitten!").setEphemeral(true).queue();
                return;
            }

            if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
                event.getHook().sendMessage("`❌` Je moet in dezelfde voice channel zitten als mij").setEphemeral(true).queue();
                return;
            }

            GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());

            musicManager.scheduler.player.stopTrack();
            musicManager.scheduler.queue.clear();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("```\uD83C\uDFB5``` **| STOP**");
            embed.setDescription("**・Muziek gestopt\n・Wachtrij verwijderd**");
            embed.addField("Door", event.getMember().getAsMention(), false);
            embed.setFooter("MaceMC", event.getGuild().getIconUrl());
            embed.setColor(new Color(0, 255, 142));

            event.getHook().sendMessageEmbeds(embed.build()).queue();
        }
    }

}
