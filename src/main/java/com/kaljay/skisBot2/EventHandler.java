package com.kaljay.skisBot2;

import com.kaljay.skisBot2.SQL.Database;
import com.kaljay.skisBot2.comms.Voice;
import com.kaljay.skisBot2.modules.Module;
import com.kaljay.skisBot2.modules.ModuleManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.impl.events.user.PresenceUpdateEvent;
import sx.blah.discord.util.audio.events.TrackFinishEvent;

import java.util.Map;

/**
 * Project: SkisBot2
 * Created by KalJay on 22/11/2017 at 2:23 AM.
 */
public class  EventHandler {

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        skisBot2.logInfo("SKIS Bot 2 Online and Ready!");
        Database.UpdateOrInsertDefaultTables();
        ModuleManager.onReadyEvent(event);
    }



    @EventSubscriber
    public void onTrackFinishEvent(TrackFinishEvent event) {
        Voice.leaveVoiceChannel();
    }

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event) {

        ModuleManager.onMessageReceivedEvent(event);
    }

    @EventSubscriber
    public void onUserJoinEvent(UserJoinEvent event) {
        ModuleManager.onUserJoin(event);
    }

    @EventSubscriber
    public void onGuildCreateEvent(GuildCreateEvent event) {
        ModuleManager.onGuildJoin(event);
    }

    @EventSubscriber
    public void onPresenceUpdateEvent(PresenceUpdateEvent event) {
        ModuleManager.onPresenceUpdateEvent(event);
    }

}
