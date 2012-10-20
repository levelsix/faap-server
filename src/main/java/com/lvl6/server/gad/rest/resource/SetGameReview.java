package com.lvl6.server.gad.rest.resource;

import org.restlet.data.Status;
import org.restlet.resource.Get;

public class SetGameReview extends BaseServerResource {
    
    private String userId;
    private int rating;
    private String gameId;
    
    @Override
    public void doInit() {

        super.doInit();
        
        //Get request parameters

        userId = incoming.get("userId");
        gameId = incoming.get("gameId");
        
        try {
            rating = Integer.parseInt(incoming.get("rating"));
        } catch (Exception ex) {
            rating = -1;
        }
        
        if (userId == null || gameId == null || rating <= 0 || rating > 3) {
            setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE, new Error(), "406, Missing required parameter");
        }
        
    }

    @Override
    public void doRelease() {
    }

    @Get
    public String setGameReview() throws Exception {
        gameFacade.setRating(userId, gameId, rating);
        return "0";
    }
    
}