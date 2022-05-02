package com.tracker.controllers;

import com.tracker.constant.SteamConstants;
import com.tracker.service.SteamApiService;
import com.tracker.user.stats.RustPlayersStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrackerController {

    @Autowired
    private SteamApiService steamApiService;

    @RequestMapping(value = "rust/{steamID64}",
            method = RequestMethod.GET)
    public @ResponseBody RustPlayersStats getRustPlayerInformation(@PathVariable("steamID64") String steamID64){
        return steamApiService.getUserStats(SteamConstants.RUST_APPID, steamID64);
    }

}
