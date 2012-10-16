package com.lvl6.server.gad.entities;

public class GameMetaDTO {

    private String gameId;
    private String gameTitle;
    private double dateFree;
    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    public String getGameTitle() {
        return gameTitle;
    }
    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }
    public double getDateFree() {
        return dateFree;
    }
    public void setDateFree(double dateFree) {
        this.dateFree = dateFree;
    }
    
    
}
