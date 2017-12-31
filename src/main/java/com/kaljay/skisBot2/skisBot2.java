package com.kaljay.skisBot2;

import com.kaljay.skisBot2.modules.CalendarEvents;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.audio.AudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    public static void main(String[] args) throws Exception {
        if(Config.loadConfig()) {
            discordClient = getClient();
            discordClient.getDispatcher().registerListener(new EventHandler());
            loadModules();
        } else {
            System.out.println("SKIS: Shutting Down...");
        }
    }

    private static IDiscordClient getClient() throws DiscordException {
        return new ClientBuilder().withToken(discordToken).login();
    }

    private static void loadModules() {
        CalendarEvents.setTimers();
    }


    //should be moved
    public static void playAudioFromFile(String s_file, IGuild guild) throws IOException, UnsupportedAudioFileException {
        URL url;
        if (skisBot2.class.getResource("skisBot2.class").toString().startsWith("file:")) {
            url = new File("src/main/resources/resources/" + s_file).toURI().toURL();
        } else {
            //System.out.println(SkisBot.class.getResource("/resources/" + s_file).toString());
            System.out.println("/resources/" + s_file);
            url = skisBot2.class.getResource("/resources/" + s_file);
        }
        AudioPlayer player = AudioPlayer.getAudioPlayerForGuild(guild);
        player.queue(url);
    }

    public static List<IGuild> getGuilds() {
        return discordClient.getGuilds();
    }

}
