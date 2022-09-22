package dev.tim.mazemc.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import dev.tim.mazemc.lavaplayer.GuildMusicManager;
import dev.tim.mazemc.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equalsIgnoreCase("queue")){
            event.deferReply().queue();

            GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
            BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

            if(queue.isEmpty()){
                event.getHook().sendMessage("`❌` De wachtrij is momenteel leeg.").queue();
                return;
            }

            final int trackCount = Math.min(queue.size(), 10);
            final List<AudioTrack> trackList = new ArrayList<>(queue);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("```\uD83C\uDFB5``` **| WACHTRIJ**");
            embed.setFooter("MaceMC", event.getGuild().getIconUrl());
            embed.setColor(new Color(0, 255, 142));

            for(int i = 0; i < trackCount; i++){
                final AudioTrack track = trackList.get(i);
                AudioTrackInfo info = track.getInfo();

                embed.addField("#" + (i + 1) + " `" + info.title + "`", "**Auteur:** `" + info.author + "`", false);
            }

            if(trackList.size() > trackCount){
                embed.addField("", "**En " + (trackList.size() - trackCount) + " meer...**", false);
            }

            event.getHook().sendMessageEmbeds(embed.build()).queue();

        }
    }

    private String formatTime(long timeInMillis){
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
