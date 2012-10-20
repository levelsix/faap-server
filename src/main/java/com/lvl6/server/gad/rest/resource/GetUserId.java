package com.lvl6.server.gad.rest.resource;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.svenson.JSON;

public class GetUserId extends BaseServerResource {
    
    private String macAddress;
    private String odin1;
    private String openUdid;
    
    @Override
    public void doInit() {

        super.doInit();
        
        //Get request parameters

        macAddress = incoming.get("macAddress");
        odin1 = incoming.get("odin1");
        openUdid = incoming.get("openUdid");
        
        if (macAddress == null || odin1 == null || openUdid == null) {
            setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE, new Error(), "406, Missing required parameter");
        }
    }

    @Override
    public void doRelease() {
    }

    @Get
    public String getUserId() throws Exception {
        String userId = userFacade.getUser(macAddress, odin1, openUdid);
        
        if (userId == null) {
            // user does not exist.. add new user
            userId = userFacade.addUser(macAddress, odin1, openUdid);
            System.out.println("Adding new user " + userId + " - " + macAddress + ":"+odin1+":"+openUdid);
        } else {
            // existing user, just return it..
        }
        
        if (userId == null) {
            System.out.println("Error, user not found and user ID could not be generated" + " - " + macAddress + ":"+odin1+":"+openUdid);
            setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE, new Error(), "505, Invalid data");
            return "";
        }

        return JSON.defaultJSON().forValue(userId);
    }
    
}