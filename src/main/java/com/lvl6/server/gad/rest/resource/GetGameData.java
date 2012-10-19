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
        
        GameDataDTO test1 = new GameDataDTO();
        test1.setDateFree(System.currentTimeMillis()/1000 - 86400);
        test1.setGameId("g1");
        test1.setGameTitle("Age of Chaos");
        test1.setUserCount(1000);
        test1.setGameDescription("Game description");
        test1.setReviewTitle("Review Title");
        test1.setReviewDescription("Review description");
        test1.setPrice("$1.99");
        test1.setRatingArtwork(9.5f);
        test1.setRatingFun(9.0f);
        test1.setRatingGameplay(8.5f);
        test1.setRatingSound(7.5f);
        test1.setRatingStory(7.0f);

        return JSON.defaultJSON().forValue(test1);
    }

}
