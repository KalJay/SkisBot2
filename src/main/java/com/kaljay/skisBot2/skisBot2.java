package com.kaljay.skisBot2;

import com.kaljay.skisBot2.modules.ModuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.DiscordException;

import java.util.List;

/**
 * Project: SkisBot2
 * Created by KalJay on 22/11/2017 at 2:21 AM.
 */
public class skisBot2 {

    private static IDiscordClient discordClient;
    static String discordToken = "";
    static String riotAPIKey = "";
    static String OAuthToken = "";
    static String OAuthSecret = "";
    static Logger logger;

    public static void main(String[] args) throws Exception {
        logger = LoggerFactory.getLogger("SKIS Bot 2");
        if(Config.loadConfig()) {
            discordClient = getClient();
            discordClient.getDispatcher().registerListener(new EventHandler());
            loadModules();
        } else {
            logError("Shutting Down...");
        }
    }

    private static IDiscordClient getClient() throws DiscordException {
        return new ClientBuilder().withToken(discordToken).login();
    }

    private static void loadModules() {
        ModuleManager.initialiseModules(discordClient, riotAPIKey, OAuthToken, OAuthSecret);
    }

    public static List<IGuild> getGuilds() {
        return discordClient.getGuilds();
    }

    public static void logError(String string) {
        logger.error(string);
    }

    public static void logInfo(String string) {
        logger.info(string);
    }

    public static void logDebug(String string) {
        logger.debug(string);
    }

    public static void logWarn(String string) {
        logger.warn(string);
    }

}
