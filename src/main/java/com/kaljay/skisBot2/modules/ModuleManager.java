package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.SQL.Database;
import sx.blah.discord.api.IDiscordClient;

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

        Database.initialiseModuleTables(); //stays last, unless a module wants access to its new table upon initialisation immediately

    }
}
