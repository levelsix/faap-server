package com.lvl6.server.gad.db;

import javax.sql.DataSource;

public class DAO {
    protected DataSource dataSource;
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


}
