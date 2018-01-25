package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.comms.Text;
import com.kaljay.skisBot2.comms.Voice;
import com.kaljay.skisBot2.modules.help.HelpEntryBuilder;
import com.kaljay.skisBot2.modules.help.HelpModule;
import com.kaljay.skisBot2.skisBot2;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.*;

/**
 * Project: SkisBot2
 * Created by KalJay on 24/01/2018 at 11:31 PM.
 */
public class MemesModule implements Module {


    public MemesModule() {
        ModuleManager.addCommandPrefix("!m ", this);
        if(ModuleManager.isModuleLoaded("HelpModule")) {
            createHelpPages();
        }
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
                break;
            case "slashingprices":
                postMeme("slashingprices", event);
                break;
            case "backfromthedead":
                postMeme("backfromthedead", event);
                break;
            case "truck":
                postMeme("truck", event);
                break;
        }
    }

    @Override
    public String getName() {
        return "MemesModule";
    }

    @Override
    public String getDescription() {
        return "Provides a variety of SKIS memes literally on command.";
    }

    private void postMeme(String meme, MessageReceivedEvent event) {
        switch(meme) {
            case "timeforleague":
                Text.sendToTextChannel(event.getChannel(), getFileFromName("timeforleague.jpg"), "timeforleague.jpg");
                break;
            case "slashingprices":
                Text.sendToTextChannel(event.getChannel(), getFileFromName("slashingprices.png"), "slashingprices.png");
                break;
            case "backfromthedead":
                Text.sendToTextChannel(event.getChannel(), getFileFromName("skisisback.jpg"), "skisisback.jpg");
                break;
            case "truck":
                Text.sendToTextChannel(event.getChannel(), getFileFromName("truck.jpg"), "truck.jpg");
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

    private InputStream getFileFromName(String name) {
        return skisBot2.getResourceHandler().getFile(name);
    }

    private void createHelpPages() {
        HelpEntryBuilder builder = new HelpEntryBuilder(this);
        builder.addHelpEntry("skis", "", "skiskiski in voice channel you are currently connected to");
        builder.addHelpEntry("jesus", "", "jesus line from idmyt in voice channel you are currently connected to");
        builder.addHelpEntry("shame", "", "shame shame shame from idmyt in voice channel you are currently connected to");
        builder.addHelpEntry("hiitline", "", "plays some glorius hiitline in the voice channel you are currently connect to");
        builder.addHelpEntry("god", "", "dominus the touch of god plus an extra 4th line");
        builder.addHelpEntry("timeforleague", "", "sends time for league picture");
        builder.addHelpEntry("slashingprices", "", "sends goodguys picture to main chat, ask angus for reasons why");
        builder.addHelpEntry("backfromthedead", "", "SKISBOT IS BACK");
        builder.addHelpEntry("truck", "", "sends the truck into fullview as requested by angus");
        HelpModule helpModule = (HelpModule) ModuleManager.getModuleByName("HelpModule");
        helpModule.buildEntry(builder);
    }
}
