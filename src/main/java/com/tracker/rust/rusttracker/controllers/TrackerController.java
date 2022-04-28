package com.tracker.rust.rusttracker.controllers;

import com.tracker.rust.rusttracker.constant.SteamConstants;
import com.tracker.rust.rusttracker.user.stats.RustPlayersStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.rust.rusttracker.api.SteamApiCaller;

@RestController
public class TrackerController {

    @Autowired
    private SteamApiCaller steamApiCaller;

    @RequestMapping(value = "rust/{steamID64}",
            method = RequestMethod.GET)
    public @ResponseBody
    void getRustPlayerInformation(@PathVariable("steamID64") String steamID64){
        RustPlayersStats stats = steamApiCaller.getUserStats(SteamConstants.RUST_APPID, steamID64);
        System.out.println(stats.getPlayerKills());
        System.out.println(stats.getPlayerDeaths());
    }

}
