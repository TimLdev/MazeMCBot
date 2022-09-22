package dev.tim.mazemc.listeners;

import dev.tim.mazemc.utils.Utils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class TicketModalListener extends ListenerAdapter {

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();

        if(event.getModalId().equals("bugmodal")){
            String subject = event.getValue("subject").getAsString();
            String bug = event.getValue("bug").getAsString();
            Utils.createTicket(guild, member, "Bug", "Onderwerp:", subject, "Bug:", bug, null, null);
            event.reply("Ticket gemaakt, " + member.getAsMention()).setEphemeral(true).queue();
        } else if(event.getModalId().equals("klachtmodal")){
            String subject = event.getValue("subject").getAsString();
            String who = event.getValue("who").getAsString();
            String why = event.getValue("why").getAsString();
            Utils.createTicket(guild, member, "Klacht", "Onderwerp:", subject, "Over wie/wat gaat je klacht?", who, "Waarom vind jij dit niet kunnen?", why);
            event.reply("Ticket gemaakt, " + member.getAsMention()).setEphemeral(true).queue();
        } else if(event.getModalId().equals("storemodal")){
            String subject = event.getValue("subject").getAsString();
            String fout = event.getValue("fout").getAsString();
            Utils.createTicket(guild, member, "Store", "Onderwerp:", subject, "Wat is er fout gegaan?", fout, null, null);
            event.reply("Ticket gemaakt, " + member.getAsMention()).setEphemeral(true).queue();
        } else if(event.getModalId().equals("sollimodal")){
            String optie = event.getValue("options").getAsString();
            Utils.createTicket(guild, member, "Sollicitatie", "Waar voor solliciteer je?", optie, null, null, null, null);
            event.reply("Ticket gemaakt, " + member.getAsMention()).setEphemeral(true).queue();
        } else if(event.getModalId().equals("overigmodal")){
            String subject = event.getValue("subject").getAsString();
            Utils.createTicket(guild, member, "Overig", "Onderwerp:", subject, null, null, null, null);
            event.reply("Ticket gemaakt, " + member.getAsMention()).setEphemeral(true).queue();
        }
    }

}
