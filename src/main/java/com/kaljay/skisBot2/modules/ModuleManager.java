package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.SQL.Database;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.impl.events.user.PresenceUpdateEvent;

/**
 * Project: SkisBot2
 * Created by KalJay on 1/01/2018 at 3:02 AM.
 */
public class ModuleManager {

    private static IDiscordClient discordClient;
    private static String riotAPIKey;
    private static String OAuthToken;
    private static String OAuthSecret;

    public static void initialiseModules(IDiscordClient discordClient, String riotAPIKey, String OAuthToken, String OAuthSecret) {
        ModuleManager.discordClient = discordClient;
        ModuleManager.riotAPIKey = riotAPIKey;
        ModuleManager.OAuthToken = OAuthToken;
        ModuleManager.OAuthSecret = OAuthSecret;

        Database.connect();
        //CalendarEvents.setTimers(); //to be fixed at a later date

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
}
