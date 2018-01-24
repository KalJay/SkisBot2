package com.kaljay.skisBot2;

import com.kaljay.skisBot2.SQL.DataDictionary;
import com.kaljay.skisBot2.SQL.DataTable;
import com.kaljay.skisBot2.SQL.Database;
import com.kaljay.skisBot2.comms.Voice;
import com.kaljay.skisBot2.SQL.SQL;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.util.audio.events.TrackFinishEvent;

import java.util.Map;

/**
 * Project: SkisBot2
 * Created by KalJay on 22/11/2017 at 2:23 AM.
 */
public class  EventHandler {

    private Map<String, Object> moduleList;

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        System.out.println("SkisBot2 Online and Ready!");
        Database.UpdateOrInsertDefaultTables();
    }



    @EventSubscriber
    public void onTrackFinishEvent(TrackFinishEvent event) {
        Voice.leaveVoiceChannel();
    }

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event) {

    }

    @EventSubscriber
    public void onUserJoinEvent(UserJoinEvent event) {
        Database.UpdateOrInsertDefaultTables();
    }

    @EventSubscriber
    public void onGuildCreateEvent(GuildCreateEvent event) {
        Database.UpdateOrInsertDefaultTables();
    }

}
