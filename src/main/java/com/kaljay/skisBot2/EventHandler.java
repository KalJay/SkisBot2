package com.kaljay.skisBot2;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

/**
 * Project: SkisBot2
 * Created by KalJay on 22/11/2017 at 2:23 AM.
 */
public class  EventHandler {

    @EventSubscriber
    public static void onReadyEvent(ReadyEvent event) {
        System.out.println("SkisBot2 Online and Ready!");
    }
}