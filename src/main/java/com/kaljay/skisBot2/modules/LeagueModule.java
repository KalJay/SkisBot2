package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.SQL.Database;
import com.kaljay.skisBot2.comms.Text;
import com.kaljay.skisBot2.modules.help.HelpEntryBuilder;
import com.kaljay.skisBot2.modules.help.HelpModule;
import com.kaljay.skisBot2.skisBot2;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Project: SkisBot2
 * Created by KalJay on 24/01/2018 at 2:10 AM.
 */
public class LeagueModule implements Module{

    private boolean autoPost = true;

    private Map<IUser, String> verificationCodes = new HashMap<>();

    public LeagueModule(String riotAPIKey) {
        Orianna.setRiotAPIKey(riotAPIKey);
        Orianna.setDefaultRegion(Region.OCEANIA);
        ModuleManager.addCommandPrefix("!lol ", this);
        if(ModuleManager.isModuleLoaded("HelpModule")) {
            createHelpPages();
        }
    }

    @Override
    public void command(String[] args, MessageReceivedEvent event) {
        //System.out.println("Args: " + args[0] + args[1] + args[2]);
        switch(args[0]) {
            case "gencode":
                generateCode(event);
                break;
            case "register":
                if (args.length < 2) {
                    Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "The correct syntax is '!lol register <Summoner Name>'.");
                } else {
                    String summonerName = "";
                    if (args.length != 2) {
                        for (String string : args) {
                            summonerName += string + " ";
                        }
                        summonerName = summonerName.substring(0, summonerName.length() - 1);
                        summonerName = summonerName.substring(9);
                    } else summonerName = args[1];
                    register(event, summonerName);
                }
                break;
            case "forgetme":
                deleteRegistration(event);
                break;
            case "vercode":
                    getVerificationCode(args, event);
        }

    }

    private boolean validTPC(String summonerName, String code){
        Summoner summoner = Orianna.summonerNamed(summonerName).get();
        try {
            String verCode = Orianna.verificationStringForSummoner(summoner).get().getString();
            skisBot2.logDebug("Retrieved Verification code: " + verCode);

            return Orianna.verificationStringForSummoner(summoner).get().getString().equals(code);
        } catch (NullPointerException e) {
            skisBot2.logWarn("Null pointer Exception avoided");
            return false;
        }

    }

    private void getVerificationCode(String[] arg, MessageReceivedEvent event) {
        Summoner summoner;
        try {
            summoner = Orianna.summonerNamed(arg[1]).get();
        } catch (IndexOutOfBoundsException e) {
            skisBot2.logDebug("isPlayerRegistered = " + isPlayerRegistered(event.getAuthor().getLongID()));
            if (isPlayerRegistered(event.getAuthor().getLongID())) {
                skisBot2.logDebug("SummonerID = " + Long.parseLong(getSummonerID(event.getAuthor().getLongID())));
                summoner = Orianna.summonerWithId(Long.parseLong(getSummonerID(event.getAuthor().getLongID()))).get();
            } else {
                summoner = null;
            }
        }

        try {
            String verCode = Orianna.verificationStringForSummoner(summoner).get().getString();
            skisBot2.logDebug("Retrieved Verification code: " + verCode);

            Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "Retrieved the following code: '" + verCode + "'");
        } catch (NullPointerException e) {
            skisBot2.logWarn("Null pointer Exception avoided");
            Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "Invalid Summoner Name");
        }
    }

    @Override
    public String getName() {
        return "LeagueModule";
    }

    @Override
    public String getDescription() {
        return "Provides an assortment of tools to enhance League of Legends experience, use !lol register <Summoner Name> or !lol gencode to begin!";
    }

    private void deleteRegistration(MessageReceivedEvent event) {
        Database.SQLUpdate("UPDATE Players SET Summoner_ID = NULL WHERE Player_ID = " + event.getAuthor().getLongID());
        Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "Registration for Discord user " + event.getAuthor().getName() + " has been deleted");
    }

    private void generateCode(MessageReceivedEvent event) {
        String code = createVerificationCode(event.getAuthor());
        Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "Here's your new verification code: " + code);
        skisBot2.logInfo("Served a new verification code to Discord user " + event.getAuthor().getName());
    }

    private void register(MessageReceivedEvent event, String summonerName) {

        if (!isPlayerRegistered(event.getAuthor().getLongID())) {
            if(validTPC(summonerName, verificationCodes.get(event.getAuthor()))) {
                Database.SQLUpdate("UPDATE Players SET Summoner_ID = " + Orianna.summonerNamed(summonerName).get().getId() + " WHERE Player_ID = " + event.getAuthor().getLongID());
                removeVerificationCode(event.getAuthor());
                Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "Successfully registered Summoner " + summonerName + " as Discord user " + event.getAuthor().getName());
                skisBot2.logInfo("Successfully registered Summoner " + summonerName + " as Discord user " + event.getAuthor().getName());
            } else {
                Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "Verification failed. Check Summoner name spelling and/or generate a new code with '!lol gencode'. This code goes in the verification tab in the League of Legends client options. It can take a while for this value to update so save and wait 5 minutes before troubleshooting");
                skisBot2.logInfo("Failed to register Summoner " + summonerName + " as Discord user " + event.getAuthor().getName());
            }
        } else {
            Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "You have already registered! Use !lol forgetme to delete your registration.");
            skisBot2.logWarn("User " + event.getAuthor().getName() + "(" + event.getAuthor().getLongID() + ") attempted to register while already registered.");
        }
    }

    private boolean isPlayerRegistered(Long longID) {
        ArrayList<ArrayList<String>> results = Database.SQLQuery("SELECT Summoner_ID FROM Players WHERE Player_ID = " + longID);
        return !(results.get(0).get(0) == null);
    }

    private String getSummonerID(Long longID) {
        ArrayList<ArrayList<String>> results = Database.SQLQuery("SELECT Summoner_ID FROM Players WHERE Player_ID = " + longID);
        return (results.get(0).get(0));
    }

    private String createVerificationCode(IUser user) {
        String code = UUID.randomUUID().toString();
        verificationCodes.put(user, code);
        return code;
    }

    private void removeVerificationCode(IUser user) {
        verificationCodes.remove(user);
    }

    private void createHelpPages() {
        HelpEntryBuilder builder = new HelpEntryBuilder(this);
        builder.addHelpEntry("gencode", "", "Creates a new verification code to use to connect your League of Legends account.");
        builder.addHelpEntry("vercode", "", "Check the code currently saved on your League account's verification tab without generating a new one.");
        builder.addHelpEntry("register", "<Summoner Name>", "Attempts to verify and link your League of Legends account to your Discord Account");
        builder.addHelpEntry("forgetme", "", "Removes all links your Discord account has with a League of Legends account");
        HelpModule helpModule = (HelpModule) ModuleManager.getModuleByName("HelpModule");
        helpModule.buildEntry(builder);
    }


}
