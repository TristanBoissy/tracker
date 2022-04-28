package com.tracker.rust.rusttracker.api;

import com.tracker.rust.rusttracker.user.stats.RustPlayersStats;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tracker.rust.rusttracker.constant.SteamConstants;

import java.util.Arrays;

@Component
public class SteamApiCaller {

    @Value("${steam.api.key}")
    private String steamApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public RustPlayersStats getUserStats(String appid, String steamID64){
        try{
            return new RustPlayersStats(new JSONObject(makeApiCall(appid, steamID64)));
        } catch (Exception e){
            e.getMessage();
            //TODO
            return null;
        }
    }

    private String makeApiCall(String appid, String steamID64) throws Exception{

        ResponseEntity<String> response = restTemplate.exchange(getURi(appid, steamID64), HttpMethod.GET, createHttpEntity(), String.class);

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

    private String getURi(String appid, String steamID){
        return SteamConstants.STEAM_USER_STATS_URL + "?appid=" + appid + "&key=" + steamApiKey + "&steamid=" + steamID;
    }

    private HttpEntity createHttpEntity(){

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(header);
    }
}
