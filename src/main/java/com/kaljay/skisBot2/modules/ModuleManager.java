package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.SQL.Database;
import sx.blah.discord.api.IDiscordClient;

/**
 * Project: SkisBot2
 * Created by KalJay on 1/01/2018 at 3:02 AM.
 */
public class ModuleManager {

    private static IDiscordClient discordClient;
    static String discordToken = "";
    static String riotAPIKey = "";
    static String OAuthToken = "";
    static String OAuthSecret = "";

    public static void initialiseModules(IDiscordClient discordClient, String discordToken, String riotAPIKey, String OAuthToken, String OAuthSecret) {
        discordClient = ModuleManager.discordClient;
        riotAPIKey = ModuleManager.riotAPIKey;
        OAuthToken = ModuleManager.OAuthToken;
        OAuthSecret = ModuleManager.OAuthSecret;

        Database.connect();
        //CalendarEvents.setTimers(); //to be fixed at a later date

        LeagueModule.run(riotAPIKey);

        Database.initialiseModuleTables(); //stays last, unless a module wants access to its new table upon initialisation immediately

    }
}
