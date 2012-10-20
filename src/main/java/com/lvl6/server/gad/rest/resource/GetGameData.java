package com.lvl6.server.gad.rest.resource;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.svenson.JSON;

import com.lvl6.server.gad.entities.GameDataDTO;

public class GetGameData extends BaseServerResource {
    
    private String gameId;
    
    @Override
    public void doInit() {

        super.doInit();
        
        //Get request parameters

        gameId = incoming.get("gameId");
        
        if (gameId == null) {
            setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE, new Error(), "406, Missing required parameter");
        }
        
    }

    @Override
    public void doRelease() {
    }

    @Get
    public String getGameData() throws Exception {
        
        
        GameDataDTO gameData = gameFacade.getGameData(gameId);
        
        return JSON.defaultJSON().forValue(gameData);
    }

}
