package dev.tim.mazemc.listeners;

import dev.tim.mazemc.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

import java.awt.*;

public class TicketButtonListener extends ListenerAdapter {

    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getButton().getId().equals("bug")) {

            TextInput subject = TextInput.create("subject", "Onderwerp?", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Typ hier je onderwerp")
                    .setMinLength(1)
                    .setMaxLength(50)
                    .build();

            TextInput bug = TextInput.create("bug", "Wat is de bug?", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Typ hier wat de bug is")
                    .setMinLength(1)
                    .setMaxLength(200)
                    .build();

            Modal modal = Modal.create("bugmodal", "Bug")
                    .addActionRows(ActionRow.of(subject), ActionRow.of(bug))
                    .build();

            event.replyModal(modal).queue();

        } else if(event.getButton().getId().equals("klacht")){

            TextInput subject = TextInput.create("subject", "Onderwerp?", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Typ hier je onderwerp")
                    .setMinLength(1)
                    .setMaxLength(50)
                    .build();

            TextInput who = TextInput.create("who", "Over wie/wat gaat je klacht?", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Typ hier over wie/wat het gaat")
                    .setMinLength(1)
                    .setMaxLength(200)
                    .build();

            TextInput why = TextInput.create("why", "Waarom vind jij dit niet kunnen?", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Typ hier waarom je vindt dat dit niet kan")
                    .setMinLength(1)
                    .setMaxLength(200)
                    .build();

            Modal modal = Modal.create("klachtmodal", "Klacht")
                    .addActionRows(ActionRow.of(subject), ActionRow.of(who), ActionRow.of(why))
                    .build();

            event.replyModal(modal).queue();

        } else if(event.getButton().getId().equals("store")){

            TextInput subject = TextInput.create("subject", "Onderwerp?", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Typ hier je onderwerp")
                    .setMinLength(1)
                    .setMaxLength(50)
                    .build();

            TextInput fout = TextInput.create("fout", "Wat is er fout gegaan?", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Typ hier wat er fout is gegaan")
                    .setMinLength(1)
                    .setMaxLength(300)
                    .build();

            Modal modal = Modal.create("storemodal", "Store")
                    .addActionRows(ActionRow.of(subject), ActionRow.of(fout))
                    .build();

            event.replyModal(modal).queue();

        } else if(event.getButton().getId().equals("sollicitatie")){

            TextInput options = TextInput.create("options", "Waar voor solliciteer je?", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("KIES UIT: Support, Builder, Developer")
                    .setMinLength(1)
                    .setMaxLength(10)
                    .build();

            Modal modal = Modal.create("sollimodal", "Sollicitatie")
                    .addActionRows(ActionRow.of(options))
                    .build();

            event.replyModal(modal).queue();

        } else if(event.getButton().getId().equals("overig")){

            TextInput subject = TextInput.create("subject", "Onderwerp?", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Typ hier je onderwerp")
                    .setMinLength(1)
                    .setMaxLength(300)
                    .build();

            Modal modal = Modal.create("overigmodal", "Overig")
                    .addActionRows(ActionRow.of(subject))
                    .build();

            event.replyModal(modal).queue();

        } else if(event.getButton().getId().equals("closeButton")){
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Weet u zeker dat u de Ticket wilt sluiten?");
            embed.setDescription("Er wordt geen transcript van deze Ticket gemaakt!");
            embed.setFooter("MazeMC", event.getGuild().getIconUrl());
            embed.setColor(new Color(32, 214, 98));
            event.getInteraction().getChannel().sendMessageEmbeds(embed.build())
                    .setActionRow(
                            Button.success("sluitButton", "Sluiten"),
                            Button.danger("nietsluitButton", "Niet Sluiten"))
                    .queue();
        } else if(event.getButton().getId().equals("sluitButton")){
            // LOG EMBED
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.RED);
            embed.setTitle("Ticket Gesloten");
            embed.addField("Kanaal:", event.getInteraction().getChannel().getName(), false);
            embed.addField("Geloten door:" , event.getMember().getAsMention(), false);
            embed.setFooter("MazeMC", event.getGuild().getIconUrl());
            event.getGuild().getTextChannelById(Utils.LOGS_CHANNEL_ID).sendMessageEmbeds(embed.build()).queue();

            event.getInteraction().getChannel().delete().queue();
        } else if(event.getButton().getId().equals("nietsluitButton")){
            event.getInteraction().getMessage().delete().queue();
            event.reply("Ticket niet gesloten!").setEphemeral(true).queue();
        }
    }
}
