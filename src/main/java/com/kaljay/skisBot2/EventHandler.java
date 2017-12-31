package com.kaljay.skisBot2;

import com.kaljay.skisBot2.SQL.DataDictionary;
import com.kaljay.skisBot2.SQL.DataTable;
import com.kaljay.skisBot2.SQL.Database;
import com.kaljay.skisBot2.comms.Voice;
import com.kaljay.skisBot2.SQL.SQL;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.util.audio.events.TrackFinishEvent;

/**
 * Project: SkisBot2
 * Created by KalJay on 22/11/2017 at 2:23 AM.
 */
public class  EventHandler {

    @EventSubscriber
    public static void onReadyEvent(ReadyEvent event) {
        System.out.println("SkisBot2 Online and Ready!");
        createGuildTable();
    }

    private static void createGuildTable() {
         DataDictionary guildDictionary = new DataDictionary();
        guildDictionary.addColumn("GuildID", SQL.BIGINT, true, false, true, false, true, false);
        guildDictionary.addColumn("GuildName", SQL.VARCHAR, false, false, false, false, true, false);
        guildDictionary.addColumn("botChannelID", SQL.BIGINT, false, false, false, false, false, false);

        Database.addDataTable(new DataTable("Guilds", guildDictionary));
    }


    @EventSubscriber
    public void onTrackFinishEvent(TrackFinishEvent event) {
        Voice.leaveVoiceChannel();
    }
}
