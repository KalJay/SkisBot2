package com.kaljay.skisBot2.modules;

import com.kaljay.skisBot2.EventHandler;
import com.kaljay.skisBot2.SQL.Database;
import com.kaljay.skisBot2.comms.Text;
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

    private static Map<IUser, String> verificationCodes = new HashMap<>();

    public LeagueModule(String riotAPIKey) {
        Orianna.setRiotAPIKey(riotAPIKey);
        Orianna.setDefaultRegion(Region.OCEANIA);
        EventHandler.addCommandPrefix("!lol ", this);
    }

    private boolean validTPC(String summonerName, String code){
        Summoner summoner = Orianna.summonerNamed(summonerName).get();
        try {
            skisBot2.logDebug("Retrieved Verification code: " + Orianna.verificationStringForSummoner(summoner).get());

            return Orianna.verificationStringForSummoner(summoner).get().getString().equals(code);
        } catch (NullPointerException e) {
            skisBot2.logWarn("Null pointer Exception avoided");
            return false;
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
        }

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
        ArrayList<ArrayList> results = Database.SQLQuery("SELECT Summoner_ID FROM Players WHERE Player_ID = " + event.getAuthor().getLongID());
        if (results.get(0).get(0) == null) {
            if(validTPC(summonerName, verificationCodes.get(event.getAuthor()))) {
                Database.SQLUpdate("UPDATE Players SET Summoner_ID = " + Orianna.summonerNamed(summonerName).get().getId() + " WHERE Player_ID = " + event.getAuthor().getLongID());
                Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "Successfully registered Summoner " + summonerName + " as Discord user " + event.getAuthor().getName());
                skisBot2.logInfo("Successfully registered Summoner " + summonerName + " as Discord user " + event.getAuthor().getName());
            } else {
                String code = createVerificationCode(event.getAuthor());
                Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "Verification failed. Check Summoner name spelling and use the new code '" + code + "' in the verification tab in the League of Legends client options");
                skisBot2.logInfo("Failed to register Summoner " + summonerName + " as Discord user " + event.getAuthor().getName());
            }
        } else {
            Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "You have already registered! Use !lol forgetme to delete your registration.");
            skisBot2.logWarn("User " + event.getAuthor().getName() + "(" + event.getAuthor().getLongID() + ") attempted to register while already registered.");
        }
    }

    private String createVerificationCode(IUser user) {
        String code = UUID.randomUUID().toString();
        verificationCodes.put(user, code);
        return code;
    }
}
