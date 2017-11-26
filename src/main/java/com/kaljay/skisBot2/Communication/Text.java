package com.kaljay.skisBot2.Communication;


import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IPrivateChannel;

import java.io.File;
import java.io.FileNotFoundException;

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

    public static boolean sendToTextChannel(IChannel channel, File file) {
        try {
            channel.sendFile(file);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
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

    public static boolean sendToPM(IPrivateChannel channel, File file) {
        try {
            channel.sendFile( file);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


}
