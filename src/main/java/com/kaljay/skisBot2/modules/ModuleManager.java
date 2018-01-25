package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.SQL.Database;
import com.kaljay.skisBot2.modules.help.HelpModule;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.impl.events.user.PresenceUpdateEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: SkisBot2
 * Created by KalJay on 1/01/2018 at 3:02 AM.
 */
public class ModuleManager {

    private static IDiscordClient discordClient;
    private static String riotAPIKey;
    private static String OAuthToken;
    private static String OAuthSecret;

    private static Map<String, Module> moduleList = new HashMap<>();

    public static void initialiseModules(IDiscordClient discordClient, String riotAPIKey, String OAuthToken, String OAuthSecret) {
        ModuleManager.discordClient = discordClient;
        ModuleManager.riotAPIKey = riotAPIKey;
        ModuleManager.OAuthToken = OAuthToken;
        ModuleManager.OAuthSecret = OAuthSecret;

        Database.connect();
        //CalendarEvents.setTimers(); //to be fixed at a later date

        HelpModule helpModule = new HelpModule();
        LeagueModule leagueModule = new LeagueModule(riotAPIKey);
        MemesModule memesModule = new MemesModule();


        Database.initialiseModuleTables(); //stays last, unless a module wants access to its new table upon initialisation immediately

    }

    public static void onUserJoin(UserJoinEvent event) {
        Database.UpdateOrInsertDefaultTables();
    }

    public static void onGuildJoin(GuildCreateEvent event) {
        Database.UpdateOrInsertDefaultTables();
    }

    public static void onPresenceUpdateEvent(PresenceUpdateEvent event) {
        StatusModule.setStatus(discordClient);
    }

    public static void onReadyEvent(ReadyEvent event) {
        StatusModule statusModule = new StatusModule(discordClient);
    }

    public static void onMessageReceivedEvent(MessageReceivedEvent event) {

        for(Map.Entry<String, Module> entry : moduleList.entrySet()) {
            if(event.getMessage().getContent().startsWith(entry.getKey().substring(0, entry.getKey().length()))) {
                entry.getValue().command(event.getMessage().getContent().substring(entry.getKey().length()).split(" "), event);
                if(!event.getChannel().isPrivate()) {
                    event.getMessage().delete();
                }
            } else {
                if (event.getMessage().getContent().startsWith(entry.getKey().substring(0, entry.getKey().length()-1))) {
                    String[] args = new String[1];
                    args[0] = "";
                    entry.getValue().command(args, event);
                }
            }
        }
    }

    public static void addCommandPrefix(String prefix, Module module) {
        moduleList.put(prefix, module);
    }

    public static String getCommandPrefix(Module module) {
        for(Map.Entry<String, Module> entry : moduleList.entrySet()) {
            if(entry.getValue().equals(module)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Module getModuleByName(String name) {
        for(Map.Entry<String, Module> entry : moduleList.entrySet()) {
            if(entry.getValue().getName().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
