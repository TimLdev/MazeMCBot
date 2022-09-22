package dev.tim.mazemc.commands.general;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;

public class SuggestCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equalsIgnoreCase("suggestie")){

            TextInput body = TextInput.create("suggestion", "Suggestie", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Typ hier je suggestie")
                    .setMinLength(10)
                    .setMaxLength(200)
                    .build();

            Modal modal = Modal.create("suggestmodal", "Suggestie")
                    .addActionRows(ActionRow.of(body))
                    .build();

            event.replyModal(modal).queue();
        }
    }
}
