package dev.tim.mazemc.commands.moderation;

import dev.tim.mazemc.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeoutCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equalsIgnoreCase("timeout")) {

            OptionMapping option1 = event.getOption("gebruiker");
            OptionMapping option2 = event.getOption("minuten");
            OptionMapping option3 = event.getOption("reden");

            if (event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {

                if (option1 == null || option2 == null || option3 == null) {
                    event.reply("Er is iets fout gegaan bij dit command!").setEphemeral(true).queue();
                    return;
                }

                Member member = option1.getAsMember();
                long minutes = option2.getAsInt();
                String reason = option3.getAsString();
                Role role = event.getGuild().getRoleById(Utils.TIMEOUT_ROLE_ID);

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                if(!member.getRoles().contains(role)){

                    event.getGuild().addRoleToMember(member, role).queue();
                    event.reply("`✅` Gebruiker succesvol een timeout gegeven!").setEphemeral(true).queue();

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("`\uD83D\uDD07` | " + member.getEffectiveName() + " timeouted!");
                    embed.addField("Reden:", reason, false);
                    embed.addField("Door:", event.getMember().getAsMention(), false);
                    embed.setThumbnail(member.getEffectiveAvatarUrl());
                    embed.setColor(Color.RED);
                    embed.setFooter("MazeMC", event.getGuild().getIconUrl());
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();

                    // LOGS EMBED
                    EmbedBuilder logEmbed = new EmbedBuilder();
                    logEmbed.setColor(new Color(255, 241, 0));
                    logEmbed.setTitle("Gebruiker timeouted!");
                    logEmbed.addField("Timeouted", member.getAsMention() + " (`" + member.getId() + "`)", false);
                    logEmbed.addField("Timeout door", event.getMember().getAsMention(), false);
                    logEmbed.addField("Duur", minutes + " minuten", false);
                    logEmbed.addField("Reden", reason, false);
                    logEmbed.addField("Datum", dtf.format(now), false);
                    logEmbed.setThumbnail(member.getEffectiveAvatarUrl());
                    event.getGuild().getTextChannelById(Utils.LOGS_CHANNEL_ID).sendMessageEmbeds(logEmbed.build()).queue();

                    new java.util.Timer().schedule(
                            new java.util.TimerTask(){
                                @Override
                                public void run(){
                                    event.getGuild().removeRoleFromMember(member, role).queue();

                                    EmbedBuilder logEmbed = new EmbedBuilder();
                                    logEmbed.setColor(new Color(138, 126, 0));
                                    logEmbed.setTitle("Timeout verlopen!");
                                    logEmbed.addField("Timeout verlopen van", member.getAsMention() + " (`" + member.getId() + "`)", false);
                                    logEmbed.addField("Timeout door", event.getMember().getAsMention(), false);
                                    logEmbed.addField("Duur van timeout", minutes + " minuten", false);
                                    logEmbed.addField("Reden", reason, false);
                                    logEmbed.addField("Datum van timeout", dtf.format(now), false);
                                    logEmbed.setThumbnail(member.getEffectiveAvatarUrl());
                                    event.getGuild().getTextChannelById(Utils.LOGS_CHANNEL_ID).sendMessageEmbeds(logEmbed.build()).queue();

                                }
                            },
                            minutes * 1000 * 60
                    );
                } else {
                    event.reply("`❌` Deze gebruiker is al timeouted!").setEphemeral(true).queue();
                }

            } else {
                event.reply("`❌` Je hebt geen permissie voor dit command!").setEphemeral(true).queue();
            }
        }
    }
}

