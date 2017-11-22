package com.kaljay.skisBot2;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.audio.AudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Project: SkisBot2
 * Created by KalJay on 22/11/2017 at 6:11 PM.
 */
public class Voice {

    static boolean engaged = false;
    static IVoiceChannel engagedChannel = null;

    public static boolean sendToVoiceChannel(IVoiceChannel channel, String file) {
        if(!engaged) {
            engaged = true;
            if(!channel.isPrivate()) {
                engagedChannel = channel;
                channel.join();
                try {
                    playAudioFromFile(file, channel);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    channel.leave();
                    return false;
                }
            } else {
                System.out.println("SKIS: WARNING: Tried to play audio in a PM");
                return false;
            }
        } else {
            System.out.println("SKIS: WARNING: Tried to join a voice channel while engaged");
            return false;
        }
    }

    public static boolean leaveVoiceChannel() {
        if(engagedChannel != null) {
            engagedChannel.leave();
            engaged = false;
            return true;
        } else {
            System.out.println("SKIS: WARNING: Tried to leave a voice channel while not engaged");
            return false;
        }
    }

    public static void playAudioFromFile(String s_file, IChannel channel) throws IOException, UnsupportedAudioFileException {
        URL url;
        if (skisBot2.class.getResource("skisBot2.class").toString().startsWith("file:")) {
            url = new File("src/main/resources/resources/" + s_file).toURI().toURL();
        } else {
            //System.out.println(SkisBot.class.getResource("/resources/" + s_file).toString());
            System.out.println("/resources/" + s_file);
            url = skisBot2.class.getResource("/resources/" + s_file);
        }
        AudioPlayer player = AudioPlayer.getAudioPlayerForGuild(channel.getGuild());
        player.queue(url);
    }
}
