package com.lvl6.server.gad.rest.resource;

import java.util.Map;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.resource.ServerResource;

import com.lvl6.server.gad.facade.UserFacade;

public class BaseServerResource extends ServerResource {
    
    private String secretKey;
    protected Map<String, String> incoming;
    protected UserFacade userFacade;
    
    @Override
    public void doInit() {

        //Get request parameters
        Form queryParams = getReference().getQueryAsForm();
        incoming = queryParams.getValuesMap();

        this.secretKey = incoming.get("secretKey");
        if (secretKey == null) {
            setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE, new Error(), "406, Missing required parameter");
        }
        
        
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }


}
