package com.kaljay.skisBot2.modules;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

/**
 * Project: SkisBot2
 * Created by KalJay on 24/01/2018 at 2:10 AM.
 */
public class LeagueModule {

    private static String riotAPIKey = "";
    private static RiotApi api;

    public static void run(String riotAPIKey) {
        riotAPIKey = LeagueModule.riotAPIKey;
        ApiConfig config = new ApiConfig().setKey(riotAPIKey);
        api = new RiotApi(config);
    }

    private static boolean validateUser(Summoner summoner){
        try {
            if(api.getThirdPartyCodeBySummoner(Platform.OCE, summoner.getId()).equals("skis")) {
                //validate user
                return true;
            } else {
                return false;
            }
        } catch (RiotApiException e) {
            e.printStackTrace();
            return false;
        }
    }
}
