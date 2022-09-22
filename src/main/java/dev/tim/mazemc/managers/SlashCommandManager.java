package dev.tim.mazemc.managers;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SlashCommandManager extends ListenerAdapter {

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList();

        commandData.add(Commands.slash("join", "Laat de bot jouw voice channel betreden"));
        commandData.add(Commands.slash("play", "Speel een nummer af")
                .addOption(OptionType.STRING, "nummer", "De YouTube link of naam van het nummer", true));
        commandData.add(Commands.slash("stop", "Stop de muziek"));
        commandData.add(Commands.slash("skip", "Skip het huidige nummer"));
        commandData.add(Commands.slash("nowplaying", "Bekijk welk nummer nu afspeelt"));
        commandData.add(Commands.slash("queue", "Bekijk de nummers in de wachtrij"));
        commandData.add(Commands.slash("repeat", "Herhaal het huidige nummer"));
        commandData.add(Commands.slash("leave", "Laat de bot de voice channel verlaten"));
        commandData.add(Commands.slash("suggestie", "Plaats een suggestie"));
        commandData.add(Commands.slash("suggestaccept", "Accepteer een suggestie")
                .addOption(OptionType.STRING, "id", "Typ hier de ID van de suggestie", true)
                .addOption(OptionType.STRING, "reden", "Reden van suggestie aangenomen", true));
        commandData.add(Commands.slash("suggestdecline", "Suggestie afwijzen")
                .addOption(OptionType.STRING, "id", "Typ hier de ID van de suggestie", true)
                .addOption(OptionType.STRING, "reden", "Reden van suggestie afgewezen", true));
        commandData.add(Commands.slash("setticket", "Zet de Ticket embed"));
        commandData.add(Commands.slash("setreactionroles", "Zet de reaction role embed"));
        commandData.add(Commands.slash("ban", "Ban een gebruiker van de server")
                .addOption(OptionType.USER, "gebruiker", "De gebruiker die je wilt bannen", true)
                .addOption(OptionType.STRING, "reden", "De reden van de ban", true));
        commandData.add(Commands.slash("timeout", "Timeout een gebruiker")
                .addOption(OptionType.USER, "gebruiker", "De gebruiker die je wilt timeouten", true)
                .addOption(OptionType.INTEGER, "minuten", "Het aantal minuten dat de timeout duurt", true)
                .addOption(OptionType.STRING, "reden", "De reden van de timeout", true));
        commandData.add(Commands.slash("clear", "Verwijder een aantal berichten")
                .addOptions(new OptionData(OptionType.INTEGER, "aantal", "Het aantal berichten dat je verwijderd", true)
                        .setRequiredRange(2, 100)));
        commandData.add(Commands.slash("kick", "Kick een gebruiker van de server")
                .addOption(OptionType.USER, "gebruiker", "De gebruiker die je wilt kicken", true)
                .addOption(OptionType.STRING, "reden", "De reden van de kick", true));
        commandData.add(Commands.slash("lock", "Lock een kanaal"));
        commandData.add(Commands.slash("unlock", "Unlock een kanaal"));
        commandData.add(Commands.slash("help", "Krijg alle commands te zien"));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

}
