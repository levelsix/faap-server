package com.lvl6.server.gad.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TrackingDAO extends DAO {

    public void trackImpression(String userId, String gameId, double dateFree) {
        try {
            Integer.parseInt(userId);
        } catch (Exception ex) {
            System.out.println("trackImpression Invalid userId:"+userId);
            return;
        }
        
        Connection conn = null;
        
        try {
            String sql = "INSERT INTO tracking (game_id, user_id, date_free, impression, download) VALUES (?, ?, ?, 1, 0)";

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, gameId);
            ps.setInt(2, Integer.parseInt(userId));
            ps.setDate(3, new java.sql.Date((long) dateFree));

            ps.executeUpdate();
            ps.close();

        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            // user already has an impression for today..
        } catch (SQLException ex) {
            
            ex.printStackTrace();
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

    public void trackInstall(String userId, String gameId) {
        try {
            Integer.parseInt(userId);
        } catch (Exception ex) {
            System.out.println("trackImpression Invalid userId:"+userId);
            return;
        }
        
        Connection conn = null;
        
        try {
            String sql = "UPDATE tracking SET download=1 where game_id=? and user_id=?";

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, gameId);
            ps.setInt(2, Integer.parseInt(userId));

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            
            ex.printStackTrace();
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

}
