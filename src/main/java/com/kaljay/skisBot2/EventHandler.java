package com.kaljay.skisBot2;

import com.kaljay.skisBot2.SQL.DataDictionary;
import com.kaljay.skisBot2.SQL.DataTable;
import com.kaljay.skisBot2.SQL.Database;
import com.kaljay.skisBot2.comms.Voice;
import com.kaljay.skisBot2.SQL.SQL;
import com.kaljay.skisBot2.modules.Module;
import com.kaljay.skisBot2.modules.ModuleManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.impl.events.user.PresenceUpdateEvent;
import sx.blah.discord.util.audio.events.TrackFinishEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: SkisBot2
 * Created by KalJay on 22/11/2017 at 2:23 AM.
 */
public class  EventHandler {

    private static Map<String, Module> moduleList = new HashMap<>();

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        skisBot2.logInfo("SKIS Bot 2 Online and Ready!");
        Database.UpdateOrInsertDefaultTables();
    }



    @EventSubscriber
    public void onTrackFinishEvent(TrackFinishEvent event) {
        Voice.leaveVoiceChannel();
    }

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event) {

        for(Map.Entry<String, Module> entry : moduleList.entrySet()) {
            if(event.getMessage().getContent().startsWith(entry.getKey())) {
                entry.getValue().command(event.getMessage().getContent().substring(entry.getKey().length()).split(" "), event);
                if(!event.getChannel().isPrivate()) {
                    event.getMessage().delete();
                }
            }
        }
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

    public static void addCommandPrefix(String prefix, Module module) {
        moduleList.put(prefix, module);
    }
}
