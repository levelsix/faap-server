package com.lvl6.server.gad.rest.resource;

import org.restlet.data.Status;
import org.restlet.resource.Get;

public class SetPushNotification extends BaseServerResource {
    
    private String userId;
    private String deviceToken;
    
    @Override
    public void doInit() {

        super.doInit();
        
        //Get request parameters

        userId = incoming.get("userId");
        deviceToken = incoming.get("deviceToken");
        
        if (userId == null || deviceToken == null) {
            setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE, new Error(), "406, Missing required parameter");
        }
        
    }

    @Override
    public void doRelease() {
    }

    @Get
    public String setPushNotification() throws Exception {
        
        return "0";
    }
    
}