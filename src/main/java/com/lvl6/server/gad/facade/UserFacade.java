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
}
