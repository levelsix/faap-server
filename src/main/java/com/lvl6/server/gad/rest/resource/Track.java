package com.lvl6.server.gad.rest.resource;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.resource.Get;

public class Track extends BaseServerResource {
    
    private String gameId;
    private String macAddress;
    private String odin1;
    private String openUdid;
    
    @Override
    public void doInit() {
        // don't require secret
//        super.doInit();
        
        //Get request parameters
        Form queryParams = getReference().getQueryAsForm();
        incoming = queryParams.getValuesMap();
        
        //Get request parameters

        macAddress = incoming.get("macAddress");
        odin1 = incoming.get("odin1");
        openUdid = incoming.get("openUdid");
        gameId = incoming.get("gameId");
        
        if ((macAddress == null && odin1 == null && openUdid == null) || gameId == null) {
            setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE, new Error(), "406, Missing required parameter");
        }
    }

    @Override
    public void doRelease() {
    }

    @Get
    public String track() throws Exception {
        
        System.out.println("Track RCVD gameId:"+gameId+" odin1:"+odin1+" openUdid:"+openUdid+" macAddress:"+macAddress);
        String userId = null;
        
        if (macAddress != null)
            userId = userFacade.getUserByMacAddress(macAddress);
        
        if (userId == null && odin1 != null)
            userId = userFacade.getUserByOdin1(odin1);
        

        if (userId == null && openUdid != null)
            userId = userFacade.getUserByOpenUdid(openUdid);

        if (userId != null) {
            gameFacade.track(userId, gameId);
            System.out.println("Track TRACK " + gameId + " " + userId);
        } else {
            System.out.println("Track NOUSR" + gameId);
        }
        return "0";
    }
    
}