package com.lvl6.server.gad.facade;

import com.lvl6.server.gad.db.UserDAO;

public class UserFacade {

    public UserFacade() {
        
    }
    
    private UserDAO userDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String getUser(String macAddress, String odin1, String openUdid) {
        // we should be able to just check any of them.. let's use odin1.
        
        String userId = userDAO.getUserByOdin1(odin1);
        
        return userId;
    }
    
    public String addUser(String macAddress, String odin1, String openUdid) {
        String userId = userDAO.addUser(macAddress, odin1, openUdid);
        
        return userId;
    }

    public boolean updateEmail(String userId, String email) {
        return userDAO.updateEmailAddress(userId, email);
    }

    public boolean updateDeviceToken(String userId, String deviceToken) {
        return userDAO.updateDeviceToken(userId, deviceToken);
    }

    public String getUserByMacAddress(String macAddress) {
        return userDAO.getUserByMacAddress(macAddress);
    }

    public String getUserByOdin1(String odin1) {
        return userDAO.getUserByOdin1(odin1);
    }

    public String getUserByOpenUdid(String openUdid) {
        return userDAO.getUserByOpenUdid(openUdid);
    }
    
}
