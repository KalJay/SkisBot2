package com.kaljay.skisBot2.comms;


import com.kaljay.skisBot2.skisBot2;
import jdk.internal.util.xml.impl.Input;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IPrivateChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Project: SkisBot2
 * Created by KalJay on 22/11/2017 at 5:59 PM.
 */

public class Text {

    public static boolean sendToTextChannel(IChannel channel, String text) {
        channel.sendMessage(text);
        return true;
    }

    public static boolean sendToTextChannel(IChannel channel, String text, File file) {
        try {
            channel.sendFile(text, file);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendToTextChannel(IChannel channel, String text, InputStream file, String fileName) {
        channel.sendFile(text, file, fileName);
        return true;
    }

    public static boolean sendToTextChannel(IChannel channel, File file) {
        try {
            channel.sendFile(file);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendToTextChannel(IChannel channel, InputStream file, String fileName) {
        String text = "";
        channel.sendFile(text, file, fileName);
        return true;
    }

    public static boolean sendToPM(IPrivateChannel channel, String text) {
        channel.sendMessage(text);
        return true;
    }

    public static boolean sendToPM(IPrivateChannel channel, String text, File file) {
        try {
            channel.sendFile(text, file);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendToPM(IPrivateChannel channel, String text, InputStream file, String fileName) {
        channel.sendFile(text, file, fileName);
        return true;
    }

    public static boolean sendToPM(IPrivateChannel channel, File file) {
        try {
            channel.sendFile( file);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendToPM(IPrivateChannel channel, InputStream file, String fileName) {
        String text = ";";
        channel.sendFile(text, file, fileName);
        return true;
    }

    public static boolean sendToAllGuilds(String text) {
        for (IGuild guild: skisBot2.getGuilds()) {
            sendToTextChannel(guild.getDefaultChannel(), text);
        }
        return true;
    }


}
