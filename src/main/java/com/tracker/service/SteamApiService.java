package com.tracker.service;

import com.tracker.user.stats.RustPlayersStats;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tracker.constant.SteamConstants;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SteamApiService {

    @Value("${steam.api.key}")
    private String steamApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public RustPlayersStats getUserStats(String appid, String steamID64){
        try{
            return new RustPlayersStats(new JSONObject(getPlayerOwnedGames(steamID64)), new JSONObject(getPlayerInfo(steamID64)), new JSONObject(getPlayerStats(appid, steamID64)));
        } catch (Exception e){
            e.printStackTrace();
            //TODO
            return null;
        }
    }

    private String getPlayerOwnedGames(String steamID64) throws Exception {

        ResponseEntity<String> response = restTemplate.exchange(getPlayerOwnedGameURi(steamID64), HttpMethod.GET, createHttpEntity(), String.class);

        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody();

        } else if (response.getStatusCode().is5xxServerError()){
            // The profile does not share their information
            //TODO return error to display message
            throw new Exception("The profile is private");
        }

        //TODO add other status code errors
        return null;
    }

    private ArrayList<MediaType> newMediaTypeArrayList(){
        return new ArrayList<>();
    }

    private String getPlayerInfo(String steamID64)throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(getPlayerInformationURi(steamID64), HttpMethod.GET, createHttpEntity(), String.class);

        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody();

        } else if (response.getStatusCode().is5xxServerError()){
            // The profile does not share their information
            //TODO return error to display message
            throw new Exception("The profile is private");
        }

        //TODO add other status code errors
        return null;
    }

    private String getPlayerStats(String appid, String steamID64) throws Exception {

        ResponseEntity<String> response = restTemplate.exchange(getPlayerStatsURi(appid, steamID64), HttpMethod.GET, createHttpEntity(), String.class);

        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody();

        } else if (response.getStatusCode().is5xxServerError()){
            // The profile does not share their information
            //TODO return error to display message
            throw new Exception("The profile is private");
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
