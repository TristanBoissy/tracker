package com.tracker.user.stats;

import com.tracker.constant.RustPlayerStatsConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class RustPlayersStats {

    private int kills;
    private int deaths;

    public RustPlayersStats(JSONObject json){
        final String playerStatsKey = "playerstats";
        final String statsKey = "stats";
        JSONArray playerStats = (JSONArray) json.getJSONObject(playerStatsKey).get(statsKey);

        this.kills = getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.KILL_PLAYER);
        this.deaths = getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.DEATHS);
    }

    private int getValueFromPlayerStats(JSONArray playerStats, String key){
        for(int i = 0; i < playerStats.length(); i++){
            if (playerStats.getJSONObject(i).get("name").equals(key)){
                return (int) playerStats.getJSONObject(i).get("value");
            }
        }
        return -1;
    }

    public int getPlayerKills(){
        return kills;
    }

    public int getPlayerDeaths(){
        return deaths;
    }
}
