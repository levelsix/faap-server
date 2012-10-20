package com.lvl6.server.gad.rest.resource;

import java.util.ArrayList;
import java.util.Date;

import org.svenson.JSON;

import org.restlet.data.Status;
import org.restlet.resource.Get;

import com.lvl6.server.gad.entities.GameMetaDTO;

public class GetGameList extends BaseServerResource {
    
    @Override
    public void doInit() {

        super.doInit();
        
        //Get request parameters

        incoming.get("");
        
        boolean nope = false;
        if (nope) {
            setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE, new Error(), "406, Missing required parameter");
        }
        
    }

    @Override
    public void doRelease() {
    }

    @Get
    public String getGameList() throws Exception {
        
        // TODO: figure out how to sync on timezone, etc..  For now, just
        // rely on the server..
        
        Date yesterday = new Date(System.currentTimeMillis()-86400000);
        Date tomorrow = new Date(System.currentTimeMillis()+86400000);
        
        ArrayList<GameMetaDTO> games = gameFacade.getGameList(yesterday, tomorrow);
        
        return JSON.defaultJSON().forValue(games);
    }
    
}
