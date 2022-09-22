package dev.tim.mazemc;

import dev.tim.mazemc.commands.general.SuggestCommand;
import dev.tim.mazemc.commands.info.HelpCommand;
import dev.tim.mazemc.commands.moderation.*;
import dev.tim.mazemc.commands.music.*;
import dev.tim.mazemc.commands.owner.SetReactionRolesCommand;
import dev.tim.mazemc.commands.owner.SetTicketCommand;
import dev.tim.mazemc.listeners.*;
import dev.tim.mazemc.managers.SlashCommandManager;
import dev.tim.mazemc.utils.Utils;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    private final Dotenv config;
    private final ShardManager shardManager;

    public DiscordBot() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("/help | MazeMC"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.DIRECT_MESSAGES);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ONLINE_STATUS, CacheFlag.VOICE_STATE);
        shardManager = builder.build();

        shardManager.addEventListener(
                // Managers
                new SlashCommandManager(),
                // Commands
                new JoinCommand(),
                new LeaveCommand(),
                new NowPlayingCommand(),
                new PlayCommand(),
                new QueueCommand(),
                new RepeatCommand(),
                new SkipCommand(),
                new StopCommand(),
                new SuggestCommand(),
                new SuggestAcceptCommand(),
                new SuggestDeclineCommand(),
                new SetTicketCommand(),
                new SetReactionRolesCommand(),
                new BanCommand(),
                new TimeoutCommand(),
                new ClearCommand(),
                new KickCommand(),
                new LockCommand(),
                new UnlockCommand(),
                new HelpCommand(),
                // Listeners
                new JoinListener(),
                new SuggestModalListener(),
                new TicketButtonListener(),
                new TicketModalListener(),
                new ReactionRoleAddListener(),
                new ReactionRoleRemoveListener(),
                new RoleAddListener(),
                new RoleRemoveListener(),
                new LeaveListener()
        );

    }

    public Dotenv getConfig(){
        return config;
    }

    public ShardManager getShardManager(){
        return shardManager;
    }

    public static void main(String[] args){
        new Utils();
        try {
            DiscordBot bot = new DiscordBot();
        } catch (LoginException e){
            System.out.println("ERROR: Bot token niet gevonden!");
        }
    }

}
