package com.kaljay.skisBot2.modules;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Project: SkisBot2
 * Created by KalJay on 24/01/2018 at 5:12 PM.
 */
public interface Module {

    public void command(String[] args, MessageReceivedEvent event);

    public String getName();

    public String getDescription();
}
