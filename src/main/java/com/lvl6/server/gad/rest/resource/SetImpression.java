package com.lvl6.server.gad.rest.resource;

import org.restlet.data.Status;
import org.restlet.resource.Get;

public class SetImpression extends BaseServerResource {
    
    private String userId;
    private String gameId;
    
    @Override
    public void doInit() {

        super.doInit();
        
        //Get request parameters

        userId = incoming.get("userId");
        gameId = incoming.get("gameId");
        
        if (userId == null || gameId == null) {
            setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE, new Error(), "406, Missing required parameter");
        }
        
    }

    @Override
    public void doRelease() {
    }

    @Get
    public String setImpression() throws Exception {
        
        return "itms://itunes.com/apps/age-of-chaos";
    }
    
}