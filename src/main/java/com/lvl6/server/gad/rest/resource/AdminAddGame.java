package com.lvl6.server.gad.rest.resource;

import java.util.Map;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.svenson.JSON;

/**
 * This method is disabled, as we'll use MySQL WorkBench for communication
 * @author phsu
 *
 */
@SuppressWarnings("unused")
public class AdminAddGame extends BaseServerResource {

    private String gameTitle;
    private String gameDescription;
    private String reviewTitle;
    private String reviewDescription;
    private String price;
    private int userCount;
    private float ratingArtwork;
    private float ratingFun;
    private float ratingGameplay;
    private float ratingSound;
    private float ratingStory;
    private String appStoreName;
    
    @Override
    public void doInit() {

        //Get request parameters
        Form queryParams = getReference().getQueryAsForm();
        Map<String, String> incoming = queryParams.getValuesMap();

    }

    @Override
    public void doRelease() {
    }

    @Post
    public Representation setUserHometown(Representation entity) throws Exception {

        Integer retValue = new Integer("1");

        try {
            
            Form form = new Form(entity);
            
            gameTitle = form.getFirstValue("game_title");
            
            System.out.println("Game title is " + gameTitle);
            System.out.println(form.getNames());
            
            System.out.println(form.getValues("game_title"));
            gameDescription = form.getFirstValue("game_description");
            reviewTitle = form.getFirstValue("review_title");
            reviewDescription = form.getFirstValue("review_description");
            price = form.getFirstValue("price");
            userCount = Integer.parseInt(form.getFirstValue("user_count"));
            ratingArtwork = Float.parseFloat(form.getFirstValue("rating_artwork"));
            ratingFun = Float.parseFloat(form.getFirstValue("rating_fun"));
            ratingGameplay = Float.parseFloat(form.getFirstValue("rating_gameplay"));
            ratingSound = Float.parseFloat(form.getFirstValue("rating_sound"));
            ratingStory = Float.parseFloat(form.getFirstValue("rating_story"));
            appStoreName = form.getFirstValue("app_store_name");        

            

        } catch (Exception ex) {
            ex.printStackTrace();
            setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE, new Error(), "550, Server Error");
        }

        Representation rep = new StringRepresentation(JSON.defaultJSON().forValue(retValue),  
                MediaType.TEXT_PLAIN);  
        
        return rep;

    }

}
