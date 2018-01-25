package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.skisBot2;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Project: SkisBot2
 * Created by KalJay on 25/01/2018 at 12:50 AM.
 */
public class StatusModule implements Module{

    IDiscordClient client;

    public StatusModule(IDiscordClient client) {
        this.client = client;
        ModuleManager.registerModule(this);
    }

    public void setStatus() {
        client.changePlayingText(randomStatus());
    }

    private String randomStatus() {
        double number = java.lang.Math.random();
        if(number < 0.25) {
            //System.out.print("1, " + number);
            return "hiitline bling";
        } else {
            if(number < 0.5) {
                //System.out.print("2, " + number);
                return "IDM Youtube";
            } else {
                if (number < 0.75) {
                    //System.out.print("3, " + number);
                    return "flames";
                } else {
                    //System.out.print("4, " + number);
                    return "SKIS mixtape";
                }
            }
        }
    }

    @Override
    public void command(String[] args, MessageReceivedEvent event) {}

    @Override
    public String getName() {
        return "StatusModule";
    }

    @Override
    public String getDescription() {
        return "StatusModule changes the status of SKIS bot 2 automatically based on the online presence changes of other users it interacts with.";
    }
}
