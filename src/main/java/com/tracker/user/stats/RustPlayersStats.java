package com.tracker.user.stats;

import com.tracker.constant.RustPlayerStatsConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class RustPlayersStats {

    private String playerStatsKey = "playerstats";
    private String statsKey = "stats";

    private JSONArray playerStats;


    public RustPlayersStats(JSONObject json){
        playerStats = (JSONArray) json.getJSONObject(playerStatsKey).get(statsKey);
    }

    private int getValueFromPlayerStats(String key){
        for(int i = 0; i < playerStats.length(); i++){
            if (playerStats.getJSONObject(i).get("name").equals(key)){
                return (int) playerStats.getJSONObject(i).get("value");
            }
        }
        return -1;
    }

    public int getPlayerKills(){
        return getValueFromPlayerStats(RustPlayerStatsConstants.KILL_PLAYER);
    }

    public int getPlayerDeaths(){
        return getValueFromPlayerStats(RustPlayerStatsConstants.DEATHS);
    }
}
