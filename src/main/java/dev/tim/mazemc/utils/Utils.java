package dev.tim.mazemc.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;

public class Utils {

    public static long LOGS_CHANNEL_ID;
    public static long WELCOME_CHANNEL_ID;
    public static long MEMBER_ROLE_ID;
    public static long SUGGEST_CHANNEL_ID;
    public static String SUGGEST_PLUS_EMOJI_ID;
    public static String SUGGEST_MINUS_EMOJI_ID;
    public static long SUGGEST_ACCEPTED_CHANNEL_ID;
    public static long SUGGEST_DECLINED_CHANNEL_ID;
    public static long SUGGEST_LOGS_CHANNEL_ID;
    public static long TICKET_CATEGORY_ID;
    public static long TICKET_SUPPORT_ROLE_ID;
    public static long REACTION_ROLE_CHANNEl_ID;
    public static long UPDATE_ROLE_ID;
    public static long SNEAKPEAK_ROLE_ID;
    public static long EVENT_ROLE_ID;
    public static long MEMBERCOUNT_CHANNEL_ID;
    public static long TIMEOUT_ROLE_ID;

    public Utils(){
        LOGS_CHANNEL_ID = 972466532116471810L;

        WELCOME_CHANNEL_ID = 1014124598742220823L;
        MEMBER_ROLE_ID = 972466531378282506L;

        SUGGEST_CHANNEL_ID = 972466531567042608L;
        SUGGEST_PLUS_EMOJI_ID = ":plus:1014141145472848002";
        SUGGEST_MINUS_EMOJI_ID = ":minus:1014141144373940314";
        SUGGEST_ACCEPTED_CHANNEL_ID = 972473622524477480L;
        SUGGEST_DECLINED_CHANNEL_ID = 972473700114894858L;
        SUGGEST_LOGS_CHANNEL_ID = 972842342170132530L;

        TICKET_CATEGORY_ID = 972466532116471817L;
        TICKET_SUPPORT_ROLE_ID = 972466531390873610L;

        REACTION_ROLE_CHANNEl_ID = 972468120000286770L;
        UPDATE_ROLE_ID = 972466531365703698L;
        SNEAKPEAK_ROLE_ID = 972466531365703697L;
        EVENT_ROLE_ID = 972466531365703696L;

        MEMBERCOUNT_CHANNEL_ID = 972817636293480478L;

        TIMEOUT_ROLE_ID = 1021462384491110530L;
    }

    public static void createTicket(Guild guild, Member member, String category, String headField1, String field1, String headField2, String field2, String headField3, String field3){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(32, 214, 98));
        embed.setTitle("\uD83C\uDFAB | " + category + " Ticket");
        embed.setDescription("**Aangemaakt door:** " + member.getAsMention());
        embed.addField(headField1, field1, false);
        embed.addField(headField2, field2, false);
        embed.addField(headField3, field3, false);
        embed.setFooter("Bedankt voor de informatie, we zullen binnen 24 uur reageren en u verder helpen!");
        embed.setThumbnail(member.getEffectiveAvatarUrl());
        guild.createTextChannel(member.getUser().getName().toLowerCase() + "-" + category.toLowerCase(), guild.getCategoryById(Utils.TICKET_CATEGORY_ID))
                .addPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL), null)
                .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                .addPermissionOverride(guild.getRoleById(Utils.TICKET_SUPPORT_ROLE_ID), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY), null)
                .complete()
                .sendMessageEmbeds(embed.build()).setActionRow(Button.danger("closeButton", "Sluit"))
                .queue();

        // LOG EMBED
        EmbedBuilder logEmbed = new EmbedBuilder();
        logEmbed.setColor(new Color(32, 214, 98));
        logEmbed.setTitle("\uD83C\uDFAB | " + category + " Ticket");
        logEmbed.setDescription("**Aangemaakt door:** " + member.getAsMention());
        logEmbed.addField(headField1, field1, false);
        logEmbed.addField(headField2, field2, false);
        logEmbed.addField(headField3, field3, false);
        logEmbed.setFooter("MazeMC", guild.getIconUrl());
        guild.getTextChannelById(Utils.LOGS_CHANNEL_ID).sendMessageEmbeds(logEmbed.build()).queue();
    }

}
