package dev.tim.mazemc.commands.info;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class HelpCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equalsIgnoreCase("help")){
            event.deferReply().queue();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(255, 234, 0, 255));
            embed.setTitle("**Lijst van commands**");
            embed.setFooter("MazeMC", event.getGuild().getIconUrl());

            embed.addField("", "\uD83D\uDCCB **| INFORMATIE**", false);
            embed.addField("`/help`", "Krijg alle commands te zien", true);
            embed.addField("", "\uD83C\uDF75 **| ALGEMEEN**", false);
            embed.addField("`/suggestie`", "Plaats een suggestie voor de server", true);
            embed.addField("", "\uD83C\uDFB5 **| MUZIEK**", false);
            embed.addField("`/join`", "Laat de bot jouw voice channel betreden", true);
            embed.addField("`/play`", "Speel een nummer af", true);
            embed.addField("`/stop`", "Stop de muziek", true);
            embed.addField("`/skip`", "Skip het huidige nummer", true);
            embed.addField("`/nowplaying`", "Bekijk welk nummer nu afspeelt", true);
            embed.addField("`/queue`", "Bekijk de nummers in de wachtrij", true);
            embed.addField("`/repeat`", "Herhaal het huidige nummer", true);
            embed.addField("`/leave`", "Laat de bot de voice channel verlaten", true);
            embed.addField("", "\uD83D\uDEE0 **| MODERATIE**", false);
            embed.addField("`/clear`", "Verwijder een aantal berichten", true);
            embed.addField("`/kick`", "Kick een gebruiker van de server", true);
            embed.addField("`/ban`", "Ban een gebruiker van de server", true);
            embed.addField("`/timeout`", "Timeout een gebruiker", true);
            embed.addField("`/lock`", "Zet een kanaal op slot", true);
            embed.addField("`/unlock`", "Zet een kanaal open", true);
            embed.addField("`/suggestaccept`", "Accepteer een suggestie", true);
            embed.addField("`/suggestdecline`", "Een suggestie afwijzen", true);
            embed.addField("", "\uD83D\uDC51 **| EIGENAAR**", false);
            embed.addField("`/setticket`", "Zet de ticket embed", true);
            embed.addField("`/setreactionroles`", "Zet de reactie rollen", true);
            embed.addField("", "", false);

            event.getHook().sendMessageEmbeds(embed.build()).queue();
        }
    }
}
