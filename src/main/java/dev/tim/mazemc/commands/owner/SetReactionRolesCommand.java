package dev.tim.mazemc.commands.owner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SetReactionRolesCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equalsIgnoreCase("setreactionroles")){
            if(event.getMember().hasPermission(Permission.ADMINISTRATOR)){

                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(32, 214, 98));
                embed.setTitle("Reactie Rollen");
                embed.setDescription("Kies rollen door op de emojis te klikken\n**Rollen:**\n\uD83D\uDCE3 - Update\n\uD83D\uDC40 - Sneakpeak\n\uD83C\uDF89 - Event");
                embed.setFooter("MazeMC", event.getGuild().getIconUrl());
                event.getChannel().sendMessageEmbeds(embed.build()).queue(message ->{
                    message.addReaction("\uD83D\uDCE3").queue();
                    message.addReaction("\uD83D\uDC40").queue();
                    message.addReaction("\uD83C\uDF89").queue();
                });
                event.reply("Embed gezet!").setEphemeral(true).queue();

            } else {
                event.reply("`❌` Je hebt geen permissie voor dit command!").setEphemeral(true).queue();
            }
        }
    }
}
