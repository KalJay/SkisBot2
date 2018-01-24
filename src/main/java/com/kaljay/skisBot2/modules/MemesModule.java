package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.EventHandler;
import com.kaljay.skisBot2.comms.Text;
import com.kaljay.skisBot2.comms.Voice;
import com.kaljay.skisBot2.skisBot2;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * Project: SkisBot2
 * Created by KalJay on 24/01/2018 at 11:31 PM.
 */
public class MemesModule implements Module {


    public MemesModule() {
        EventHandler.addCommandPrefix("!m ", this);
    }

    @Override
    public void command(String[] args, MessageReceivedEvent event) {
        switch(args[0]) {
            case "skis":
                playVoiceMeme("skis", event);
                break;
            case "skislongthisisalljusttodiscouragespamandtopreventoverusekthanksbailolrektming":
                playVoiceMeme("skislong", event);
                break;
            case "jesus":
                playVoiceMeme("jesus", event);
                break;
            case "shame":
                playVoiceMeme("shame", event);
                break;
            case "hiitline":
                playVoiceMeme("hiitline", event);
                break;
            case "god":
                playVoiceMeme("god", event);
                break;
        }
    }

    private void playVoiceMeme(String voice, MessageReceivedEvent event) {
        if(event.getAuthor().getVoiceStateForGuild(event.getGuild()) == null) {
            Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "You must be in a Voice channel for this command to work.");
            skisBot2.logWarn("Discord user " + event.getAuthor().getName() + " attempted to use a voice command while not being in a voice channel");
        } else {
            switch (voice) {
                case "skis":
                    Voice.sendToVoiceChannel(event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel(), "skis.wav");
                    break;
                case "skislong":
                    Voice.sendToVoiceChannel(event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel(), "skislong.wav");
                    break;
                case "jesus":
                    Voice.sendToVoiceChannel(event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel(), "jesus.wav");
                    break;
                case "shame":
                    Voice.sendToVoiceChannel(event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel(), "shame.mp3");
                    break;
                case "hiitline":
                    Voice.sendToVoiceChannel(event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel(), "hiitline.mp3");
                    break;
                case "god":
                    Voice.sendToVoiceChannel(event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel(), getRandomGod());
            }
        }
    }

    private String getRandomGod() {
        double number = Math.random();
        if (number < 0.25) {
            return "god1.wav";
        } else if (number < 0.5) {
            return "god2.wav";
        } else if (number < 0.75) {
            return "god3.wav";
        } else {
            return "god4.wav";
        }
    }
}
