package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.EventHandler;
import com.kaljay.skisBot2.comms.Text;
import com.kaljay.skisBot2.comms.Voice;
import com.kaljay.skisBot2.skisBot2;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.*;
import java.net.URL;

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
            case "timeforleague":
                postMeme("timeforleague", event);
        }
    }

    private void postMeme(String meme, MessageReceivedEvent event) {
        switch(meme) {
            case "timeforleague":
                Text.sendToTextChannel(event.getChannel(), getFileFromName("timeforskis.jpg"));
                break;
            case "timeforskis":
                Text.sendToTextChannel(event.getChannel(), "timeforskis.jpg");
                break;
            case "slashingprices":
                Text.sendToTextChannel(event.getChannel(), "slashingprices.png");
                break;
            case "backfromthedead":
                Text.sendToTextChannel(event.getChannel(), "skisisback.jpg");
                break;
            case "truck":
                Text.sendToTextChannel(event.getChannel(), "truck.jpg");
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

    private File getFileFromName(String name) {
        //try {
            URL url;
            if (MemesModule.class.getResource("MemesModule.class").toString().startsWith("file:")) {
                return new File("src/main/resources/resources/" + name);//.toURI().toURL();
            } else {
                //url = MemesModule.class.getResource("/resources/" + name);
                new File(getClass().getResource(name).toString());
            }
        //} //catch (IOException e) {
           // e.printStackTrace();
        //}
        return null;
    }

    private String decideFormat(String name) {
        if (name.contains(".")) {
            return name.substring(name.indexOf(".") + 1);
        } else {
            return "jpg";
        }
    }
}
