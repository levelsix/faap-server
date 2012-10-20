package com.lvl6.server.gad.rest.resource;

import org.restlet.data.Status;
import org.restlet.resource.Get;

public class SetEmail extends BaseServerResource {
    
    private String userId;
    private String email;
    
    @Override
    public void doInit() {

        super.doInit();
        
        //Get request parameters

        userId = incoming.get("userId");
        email = incoming.get("email");
        
        if (userId == null || email == null) {
            setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE, new Error(), "406, Missing required parameter");
        }
        
    }

    @Override
    public void doRelease() {
    }

    @Get
    public String setEmail() throws Exception {
        
        boolean success = userFacade.updateEmail(userId, email);
        
        if (success)
            return "0";
        
        return "1";
    }
    
}