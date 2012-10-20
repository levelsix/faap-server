package com.lvl6.server.gad.db;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
    private DataSource dataSource;
    
    /**
     * Add a new user.  Note mac addresses are lowercased
     * @param macAddress
     * @param odin1
     * @param openUdid
     * @return
     */
    public String addUser(final String macAddress, final String odin1, final String openUdid) {
        
        Connection conn = null;
        
        int userId = -1;

        try {
            conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO user_id (macAddress, odin1, openUdid)" +
            " VALUES ('" + macAddress.toLowerCase() + "'," +
            "'" + odin1 + "'," +
            "'" + openUdid + "'" +
            ")";
            
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) {
                userId = rs.getInt(1);
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        if (userId < 0)
            return null;
        
        return String.format("%09d", userId);
        
    }
    
    /**
     * Get user ID by querying for odin1
     * @param odin1
     * @return
     */
    public String getUserByOdin1(final String odin1) {
        Connection conn = null;
        
        int userId = -1;

        try {
            String sql = "SELECT user_id FROM user_id WHERE odin1=?";

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, odin1);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                userId = rs.getInt(1);
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        if (userId < 0)
            return null;
        return String.format("%09d", userId);
    }
    
    /**
     * Get user ID by querying by openUdid
     * @param openUdid
     * @return
     */
    public String getUserByOpenUdid(final String openUdid) {
        Connection conn = null;
        
        int userId = -1;

        try {
            String sql = "SELECT user_id FROM user_id WHERE openUdid=?";

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, openUdid);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                userId = rs.getInt(1);
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        if (userId < 0)
            return null;
        return String.format("%09d", userId);
    }
    
    /**
     * get userId by querying with mac address.  Note mac addresses are lowercased
     * @param macAddress
     * @return
     */
    public String getUserByMacAddress(final String macAddress) {
        Connection conn = null;
        
        int userId = -1;

        try {
            String sql = "SELECT user_id FROM user_id WHERE macAddress=?";

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, macAddress.toLowerCase());

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                userId = rs.getInt(1);
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        if (userId < 0)
            return null;
        return String.format("%09d", userId);
    }
    protected void testInsert() {
        String sql = "INSERT INTO user_id " +
        "(macAddress, odin1, openUdid) VALUES (?, ?, ?)";
        Connection conn = null;
        
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "mac");
            ps.setString(2, "odin");
            ps.setString(3, "udid");
            ps.executeUpdate();
            ps.close();
        
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public UserDAO() {
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
