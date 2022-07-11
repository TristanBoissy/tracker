package com.tracker.service;

import com.tracker.constant.SteamConstants;
import com.tracker.user.stats.RustPlayersStats;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class SteamApiService {

    @Value("${steam.api.key}")
    private String steamApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public RustPlayersStats getUserStats(String appid, String steamID64) {

        JSONObject playerInfo = getPlayerInfo(steamID64);
        JSONObject playerOwnedGames = getPlayerOwnedGames(steamID64);
        JSONObject playerStats = getPlayerStats(appid, steamID64);

        return new RustPlayersStats(playerOwnedGames, playerInfo, playerStats, false);
    }

    private JSONObject getPlayerOwnedGames(String steamID64) {

        ResponseEntity<String> response = restTemplate.exchange(getPlayerOwnedGameURi(steamID64), HttpMethod.GET, createHttpEntity(), String.class);

        if(response.getStatusCode().is2xxSuccessful()){
            return new JSONObject(response.getBody());

        } else if (response.getStatusCode().is5xxServerError()){
            // The profile does not share their information
            //TODO return error to display message
            return null;
        }

        //TODO add other status code errors
        return null;
    }

    private ArrayList<MediaType> newMediaTypeArrayList(){
        return new ArrayList<>();
    }

    private JSONObject getPlayerInfo(String steamID64) {
        ResponseEntity<String> response = restTemplate.exchange(getPlayerInformationURi(steamID64), HttpMethod.GET, createHttpEntity(), String.class);

        if(response.getStatusCode().is2xxSuccessful()){
            return new JSONObject(response.getBody());

        } else if (response.getStatusCode().is5xxServerError()){
            // The profile does not share their information
            //TODO return error to display message
            return null;
        }

        //TODO add other status code errors
        return null;
    }

    private JSONObject getPlayerStats(String appid, String steamID64) {

        ResponseEntity<String> response = restTemplate.exchange(getPlayerStatsURi(appid, steamID64), HttpMethod.GET, createHttpEntity(), String.class);

        if(response.getStatusCode().is2xxSuccessful()){
            return new JSONObject(response.getBody());

        } else if (response.getStatusCode().is5xxServerError()){
            // The profile does not share their information
            //TODO return error to display message
            return null;
        }

        //TODO add other status code errors
        return null;
    }

    private String getPlayerOwnedGameURi(String steamID64){
        return SteamConstants.STEAM_API_BASE_URL +
                SteamConstants.STEAM_USER_OWNED_GAME_URL +
                "?key=" + steamApiKey +
                "&steamid=" + steamID64 +
                "&format=json";
    }

    private String getPlayerInformationURi(String steamID){
        return SteamConstants.STEAM_API_BASE_URL +
                SteamConstants.STEAM_USER_INFORMATION_URL +
                "?key=" + steamApiKey +
                "&steamids=" + steamID;
    }

    private String getPlayerStatsURi(String appid, String steamID){
        return SteamConstants.STEAM_API_BASE_URL +
                SteamConstants.STEAM_USER_STATS_URL +
                "?appid=" + appid +
                "&key=" + steamApiKey +
                "&steamid=" + steamID;
    }

    private HttpEntity createHttpEntity(){

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(header);
    }
}
