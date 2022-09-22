package dev.tim.mazemc.commands.owner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SetTicketCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equalsIgnoreCase("setticket")){
            if(event.getMember().hasPermission(Permission.ADMINISTRATOR)){
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.GREEN);
                embed.setTitle("\uD83C\uDFAB | Maak een Ticket");
                embed.setDescription("Kies een categorie door op de knoppen te klikken\n**Categorieën:**\n\uD83D\uDD28 - Klacht\n\uD83D\uDCC3 - Bug\n\uD83D\uDED2 - Store\n\uD83D\uDCDD - Sollicitatie\n\uD83E\uDDD1\u200D\uD83D\uDD27 - Overig");
                event.getChannel().sendMessageEmbeds(embed.build()).setActionRow(
                        Button.danger("klacht", "Klacht").withEmoji(Emoji.fromMarkdown("\uD83D\uDD28")),
                        Button.secondary("bug", "Bug").withEmoji(Emoji.fromMarkdown("\uD83D\uDCC3")),
                        Button.primary("store", "Store").withEmoji(Emoji.fromMarkdown("\uD83D\uDED2")),
                        Button.success("sollicitatie", "Sollicitatie").withEmoji(Emoji.fromMarkdown("\uD83D\uDCDD")),
                        Button.secondary("overig", "Overig").withEmoji(Emoji.fromMarkdown("\uD83E\uDDD1\u200D\uD83D\uDD27")))
                                .queue();
                event.reply("Ticket embed gezet!").setEphemeral(true).queue();
            } else {
                event.reply("`❌` Je hebt geen permissie voor dit command!").setEphemeral(true).queue();
            }
        }
    }
}
