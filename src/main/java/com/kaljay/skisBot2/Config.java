package com.kaljay.skisBot2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.kaljay.skisBot2.skisBot2.discordToken;
import static com.kaljay.skisBot2.skisBot2.riotAPIKey;
import static com.kaljay.skisBot2.skisBot2.OAuthToken;
import static com.kaljay.skisBot2.skisBot2.OAuthSecret;

/**
 * Project: SkisBot2
 * Created by KalJay on 22/11/2017 at 2:36 AM.
 */
public class Config {

    final static String[] template = {
            "DISCORD_TOKEN: " ,
            "RIOT_API_KEY: " ,
            "OAUTH_TOKEN: " ,
            "OAUTH_SECRET: "
    };

    private static String token = "config.txt";
    private static Path configPath = Paths.get(token);
    private static Charset utf8 = StandardCharsets.UTF_8;

    public static boolean loadConfig() {
        try {
            if (!Files.exists(configPath)) {
                Files.createFile(configPath);
                
                List<String> configLines = new ArrayList<>();
                configLines.addAll(Arrays.asList(template));
                
                Files.write(configPath, configLines, utf8);
                skisBot2.logWarn("Created Config File: token required for further operation");
                return false;
            } else {
                return readConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static boolean readConfig() {
        List<String> configLines;
        try {
            configLines = Files.readAllLines(configPath, utf8);
            for(String line: configLines) {
                importLine(line);
            }
            if (!discordToken.equals("")) {
                skisBot2.logInfo("Discord Token Found");
            } else {
                skisBot2.logError("Discord Token Not Found");
            }
            return !discordToken.equals("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void importLine(String line) {
        if(!line.startsWith("//")) {
            if (line.contains(template[0])) {
                discordToken = line.substring(template[0].length());
            }
            if (line.contains(template[1])) {
                riotAPIKey = line.substring(template[1].length());
            }
            if (line.contains(template[2])) {
                OAuthToken = line.substring(template[2].length());
            }
            if (line.contains(template[3])) {
                OAuthSecret = line.substring(template[3].length());
            }
        }
    }
}
