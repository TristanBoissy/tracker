package com.tracker.user.stats;

import com.tracker.constant.RustPlayerStatsConstants;
import com.tracker.constant.SteamConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.util.Map;

public class RustPlayersStats {

    private String playerName;
    private String playerImage;
    private String hoursPlayed;
    private float kda;
    private int kills;
    private int deaths;
    private int shotsFired;
    private Object headshots;
    private int bearKilled;
    private int boarKilled;
    private int wolfKilled;
    private int stagKilled;
    private int horseKilled;
    private int chickenKilled;
    private int scientistKilled;
    private int rocketsFired;
    private int grenadesThrown;
    private int blocksPlaced;
    private int blocksUpgrade;
    private BigDecimal speaking;
    private int gestures;
    private int woodHarvested;
    private int stoneHarvested;
    private int metalOreHarvested;
    private int sulfurOreHarvested;
    private int bonesHarvested;
    private int fatHarvested;
    private int leatherHarvested;
    private int clothHarvested;
    private int scrapHarvested;
    private int weaponAccuracy;
    private int bowAccuracy;
    private int structureHitWeapon;
    private int structureHitBow;
    private int killedBySuicide;
    private int killedByFalling;
    private int barrelsDestroyed;
    private int bowFired;

    public RustPlayersStats(JSONObject userGames, JSONObject userInfo, JSONObject userStats){
        final String playerStatsKey = "playerstats";
        final String statsKey = "stats";

        final String playerInfoResponse = "response";
        final String playerInfoPlayers = "players";

        final String playerOwnedgames = "games";

        JSONArray playerInfo = (JSONArray) userInfo.getJSONObject(playerInfoResponse).get(playerInfoPlayers);
        JSONArray playerStats = (JSONArray) userStats.getJSONObject(playerStatsKey).get(statsKey);
        JSONArray playerGames = (JSONArray) userGames.getJSONObject(playerInfoResponse).get(playerOwnedgames);

        initVariables(playerGames, (JSONObject) playerInfo.get(0), playerStats);
    }

    private void initVariables(JSONArray playerGames, JSONObject playerInfo, JSONArray playerStats){
        this.playerName = (String) playerInfo.get(RustPlayerStatsConstants.PLAYER_GAMERTAG);
        this.playerImage = (String) playerInfo.get(RustPlayerStatsConstants.PLAYER_IMAGE);
        this.hoursPlayed = getHoursPlayedFromPlayerGames(SteamConstants.RUST_APPID, playerGames);

        this.kills = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.KILL_PLAYER);
        this.deaths = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.DEATHS);
        this.kda = kills / deaths;
        this.shotsFired = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.BULLET_FIRED);
        this.headshots = shotsFired / (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.HEADSHOT);
        this.bearKilled = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.KILL_BEAR);
        this.boarKilled = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.KILL_BOAR);
        this.wolfKilled = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.KILL_WOLF);
        this.stagKilled = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.KILL_STAG);
        this.horseKilled = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.KILL_HORSE);
        this.chickenKilled = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.KILL_CHICKEN);
        this.scientistKilled = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.KILL_SCIENTIST);
        this.rocketsFired = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.ROCKET_FIRED);
        this.blocksPlaced = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.PLACED_BLOCKS);
        this.blocksUpgrade = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.UPGRADED_BLOCKS);
        this.speaking = (BigDecimal) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.SECONDS_SPEAKING);
        this.gestures = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.GESTURE_WAVE_COUNTS);
        this.woodHarvested = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.HARVESTED_WOOD);
        this.stoneHarvested = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.HARVESTED_STONES);
        this.metalOreHarvested = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.ACQUIRED_METAL_ORE);
        //this.sulfurOreHarvested = getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.);
        //this.bonesHarvested = getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.);
        //this.fatHarvested = getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.);
        this.leatherHarvested = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.HARVESTED_LEATHER);
        this.clothHarvested = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.HARVESTED_CLOTH);
        this.scrapHarvested = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.ACQUIRED_SCRAP);
        this.weaponAccuracy = shotsFired / (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.BULLET_HIT_PLAYER);
        this.bowFired = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.ARROW_FIRED);
        this.bowAccuracy = bowFired / (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.ARROW_HIT_PLAYER);
        this.barrelsDestroyed = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.DESTROYED_BARRELS);
        this.structureHitWeapon = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.SHOTGUN_HIT_BUILDING);
        this.structureHitBow = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.ARROW_HIT_BUILDING);
        this.killedBySuicide = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.DEATH_SUICIDE);
        this.killedByFalling = (int) getValueFromPlayerStats(playerStats, RustPlayerStatsConstants.DEATH_FALL);
    }

    private String getHoursPlayedFromPlayerGames(String appid, JSONArray list){
        for(int i = 0; i < list.length(); i++){
            JSONObject map = (JSONObject) list.get(i);
            if ((Integer) map.get(RustPlayerStatsConstants.APPID) == Integer.parseInt(SteamConstants.RUST_APPID)){
                return map.get(RustPlayerStatsConstants.PLAYER_PLAYTIME).toString();
            }
        }
        return null;
    }

    private Object getValueFromPlayerStats(JSONArray playerStats, String key){
        for(int i = 0; i < playerStats.length(); i++){
            if (playerStats.getJSONObject(i).get("name").equals(key)){
                return playerStats.getJSONObject(i).get("value");
            }
        }
        return -1;
    }

    public int getKills(){
        return this.kills;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerImage() {
        return playerImage;
    }

    public float getKda() {
        return kda;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public Object getHeadshots() {
        return headshots;
    }

    public int getBearKilled() {
        return bearKilled;
    }

    public int getBoarKilled() {
        return boarKilled;
    }

    public int getWolfKilled() {
        return wolfKilled;
    }

    public int getStagKilled() {
        return stagKilled;
    }

    public int getHorseKilled() {
        return horseKilled;
    }

    public int getChickenKilled() {
        return chickenKilled;
    }

    public int getScientistKilled() {
        return scientistKilled;
    }

    public int getRocketsFired() {
        return rocketsFired;
    }

    public int getGrenadesThrown() {
        return grenadesThrown;
    }

    public int getBlocksPlaced() {
        return blocksPlaced;
    }

    public int getBlocksUpgrade() {
        return blocksUpgrade;
    }

    public BigDecimal getSpeaking() {
        return speaking;
    }

    public int getGestures() {
        return gestures;
    }

    public int getWoodHarvested() {
        return woodHarvested;
    }

    public int getStoneHarvested() {
        return stoneHarvested;
    }

    public int getMetalOreHarvested() {
        return metalOreHarvested;
    }

    public int getSulfurOreHarvested() {
        return sulfurOreHarvested;
    }

    public int getBonesHarvested() {
        return bonesHarvested;
    }

    public int getFatHarvested() {
        return fatHarvested;
    }

    public int getLeatherHarvested() {
        return leatherHarvested;
    }

    public int getClothHarvested() {
        return clothHarvested;
    }

    public int getScrapHarvested() {
        return scrapHarvested;
    }

    public int getWeaponAccuracy() {
        return weaponAccuracy;
    }

    public int getBowAccuracy() {
        return bowAccuracy;
    }

    public int getStructureHitWeapon() {
        return structureHitWeapon;
    }

    public int getStructureHitBow() {
        return structureHitBow;
    }

    public int getKilledBySuicide() {
        return killedBySuicide;
    }

    public int getKilledByFalling() {
        return killedByFalling;
    }

    public int getBarrelsDestroyed() {
        return barrelsDestroyed;
    }

    public int getBowFired() {
        return bowFired;
    }
}
