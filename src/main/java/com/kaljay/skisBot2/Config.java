package com.kaljay.skisBot2;


import java.io.*;
import java.util.Properties;


/**
 * Project: SkisBot2
 * Created by KalJay on 22/11/2017 at 2:36 AM.
 */
public class Config {


        private static String path = "./config.properties";

    public static boolean loadConfig() {

        Properties mainProperties = new Properties();
        FileInputStream file;
        try {
            file = new FileInputStream(path);
            mainProperties.load(file);
            file.close();

            skisBot2.discordToken = (String) mainProperties.get("discord.token");
            skisBot2.riotAPIKey = (String) mainProperties.get("riot.api.key");
            skisBot2.OAuthToken = (String) mainProperties.get("oauth.token");
            skisBot2.OAuthSecret = (String) mainProperties.get("oauth.secret");
            if(skisBot2.discordToken != "") {
                skisBot2.logInfo("Discord token found");
                return true;
            } else {
                skisBot2.logError("Discord token not found. Check config.properties and ensure correct syntax");
                return false;
            }
        } catch (FileNotFoundException e) {
            createProperties();
            skisBot2.logInfo("Properties file created, discord Token required for further operation");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void createProperties() {

        try {
            Properties properties = new Properties();
            properties.setProperty("discord.token", "");
            properties.setProperty("riot.api.key", "");
            properties.setProperty("oauth.token", "");
            properties.setProperty("oauth.secret", "");

            File file = new File(path);
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "SKIS Bot 2 Properties File");
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
