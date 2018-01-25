package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.SQL.Database;
import com.kaljay.skisBot2.modules.help.HelpModule;
import com.kaljay.skisBot2.skisBot2;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.impl.events.user.PresenceUpdateEvent;

import java.util.ArrayList;
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
    private static ArrayList<Module> loadedModules = new ArrayList<>();

    private static boolean ready = false;

    private static StatusModule statusModule;

    public static void initialiseModules(IDiscordClient discordClient, String riotAPIKey, String OAuthToken, String OAuthSecret) {
        ModuleManager.discordClient = discordClient;
        ModuleManager.riotAPIKey = riotAPIKey;
        ModuleManager.OAuthToken = OAuthToken;
        ModuleManager.OAuthSecret = OAuthSecret;

        Database.connect();

        HelpModule helpModule = new HelpModule(); //stays as first module, so that HelpModule is available for modules to register help pages with.

        //CalendarEvents.setTimers(); //to be fixed at a later date
        LeagueModule leagueModule = new LeagueModule(riotAPIKey);
        MemesModule memesModule = new MemesModule();
        statusModule = new StatusModule(discordClient);


        Database.initialiseModuleTables(); //stays last, unless a module wants access to its new table upon initialisation immediately

    }

    public static void onUserJoin(UserJoinEvent event) {
        Database.UpdateOrInsertDefaultTables();
    }

    public static void onGuildJoin(GuildCreateEvent event) {
        Database.UpdateOrInsertDefaultTables();
    }

    public static void onPresenceUpdateEvent(PresenceUpdateEvent event) {
        if(isReady()) {
            statusModule.setStatus();
        }
    }

    public static void onReadyEvent(ReadyEvent event) {
        ready = true;
        statusModule.setStatus();
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
        if(!loadedModules.contains(module)) {
            registerModule(module);
        }
        skisBot2.logInfo(module.getName() + " has registered the prefix '" + prefix.trim() + "'");
    }

    public static void registerModule(Module module) {
        loadedModules.add(module);
        skisBot2.logInfo(module.getName() + " has registered with SKIS Module Manager");
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

    public static boolean isModuleLoaded(String name) {
        for(Module entry : loadedModules) {
            if(entry.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isReady() {
        return ready;
    }
}
