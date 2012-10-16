package com.lvl6.server.gad.rest.resource;

import java.util.ArrayList;

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
        
        ArrayList<GameMetaDTO> games = new ArrayList<GameMetaDTO>();
        
        GameMetaDTO test1 = new GameMetaDTO();
        test1.setDateFree(System.currentTimeMillis()/1000 - 86400);
        test1.setGameId("g1");
        test1.setGameTitle("Age of Chaos");
        games.add(test1);

        GameMetaDTO test2 = new GameMetaDTO();
        test2.setDateFree(System.currentTimeMillis()/1000);
        test2.setGameId("g2");
        test2.setGameTitle("Ninja Army");
        games.add(test2);

        GameMetaDTO test3 = new GameMetaDTO();
        test3.setDateFree(System.currentTimeMillis()/1000 + 86400);
        test3.setGameId("g3");
        test3.setGameTitle("Angry Birds");
        games.add(test3);

        return JSON.defaultJSON().forValue(games);
    }
    
}
