package com.lvl6.server.gad.facade;

import java.util.ArrayList;
import java.util.Date;

import com.lvl6.server.gad.db.GameDAO;
import com.lvl6.server.gad.db.TrackingDAO;
import com.lvl6.server.gad.entities.GameDataDTO;
import com.lvl6.server.gad.entities.GameMetaDTO;

public class GameFacade {

    private GameDAO gameDAO;
    private TrackingDAO trackingDAO;
    
    public void setGameDAO(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public void setTrackingDAO(TrackingDAO trackingDAO) {
        this.trackingDAO = trackingDAO;
    }

    public GameDataDTO getGameData(String gameId) {
        
        GameDataDTO game = gameDAO.getGameData(gameId);
        return game;
    }

    public ArrayList<GameMetaDTO> getGameList(Date yesterday, Date tomorrow) {
        
        return gameDAO.getGameList(yesterday, tomorrow);
    }

    public void setRating(String userId, String gameId, int rating) {
        gameDAO.setRating(userId, gameId, rating);
    }
    
    public GameMetaDTO getActiveGameMeta(String gameId) {
        
        // TODO: figure out how to sync on timezone, etc..  For now, just
        // rely on the server..
        
        Date yesterday = new Date(System.currentTimeMillis()-86400000);
        Date tomorrow = new Date(System.currentTimeMillis()+86400000);

        ArrayList<GameMetaDTO> currentGames = this.getGameList(yesterday, tomorrow);
        
        
        double compareDate = System.currentTimeMillis();
        compareDate = compareDate - (compareDate % 86400000);
        
//        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        
        GameMetaDTO activeGame = null;
        double timeDiff = 0;
        
        int i=0;
        
        for (GameMetaDTO game : currentGames) {
            if (game.getGameId().equalsIgnoreCase(gameId)) {
                // match..
                if (activeGame == null) {
                    // no active game found yet..
                    activeGame = game;
                    timeDiff = Math.abs(game.getDateFree()-compareDate);
                } else {
                    // active game already found, same game.. 
                    // make it the active game if it's closer to 'today'
                    double newTimeDiff = Math.abs(game.getDateFree()-compareDate);
                    
                    if (newTimeDiff < timeDiff) {
                        activeGame = game;
                        timeDiff = newTimeDiff;
                    }
                }
            }
            i++;
        }
        return activeGame;
    }

    public void setImpression(String userId, String gameId) {
        
        // find the current date for the free game.. 
        
        GameMetaDTO activeGame = this.getActiveGameMeta(gameId);
        
        if (activeGame == null) {
            System.out.println("ERROR: impression received for non active game:" + gameId +" from user:"+userId);
            return;
        }
        
        trackingDAO.trackImpression(userId, gameId, activeGame.getDateFree());
        
    }
    

    public void track(String userId, String gameId) {
        // track an install.. we may or may not have seen this userId via impressions
        // since we should be getting a list of ALL installs.
        
        trackingDAO.trackInstall(userId, gameId);
    }
    

}
