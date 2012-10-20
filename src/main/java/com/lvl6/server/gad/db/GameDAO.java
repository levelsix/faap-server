package com.lvl6.server.gad.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.lvl6.server.gad.entities.GameDataDTO;
import com.lvl6.server.gad.entities.GameMetaDTO;

public class GameDAO extends DAO {

    public GameDataDTO getGameData(String gameId) {
        Connection conn = null;
        
        GameDataDTO gameData = null;
        
        try {
            String sql = "SELECT * FROM games WHERE game_id=?";

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, gameId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                gameData = new GameDataDTO();
                gameData.setGameId(rs.getString("game_id"));
                gameData.setGameTitle(rs.getString("game_title"));
                gameData.setGameDescription(rs.getString("game_description"));
                gameData.setReviewTitle(rs.getString("review_title"));
                gameData.setReviewDescription(rs.getString("review_description"));
                gameData.setPrice(rs.getString("price"));
                gameData.setUserCount(rs.getInt("user_count"));
                gameData.setRatingArtwork(rs.getFloat("rating_artwork"));
                gameData.setRatingFun(rs.getFloat("rating_fun"));
                gameData.setRatingGameplay(rs.getFloat("rating_gameplay"));
                gameData.setRatingSound(rs.getFloat("rating_sound"));
                gameData.setRatingStory(rs.getFloat("rating_story"));
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
        
        return gameData;
    }

    public ArrayList<GameMetaDTO> getGameList(Date yesterday, Date tomorrow) {
        Connection conn = null;
        
        ArrayList<GameMetaDTO> games = new ArrayList<GameMetaDTO>();
        
        try {
            String sql = "SELECT calendar.game_id, calendar.date_free, games.game_title FROM calendar INNER JOIN games ON calendar.game_id = games.game_id" +
            		" WHERE date_free >=? AND date_free <= ?";

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(yesterday.getTime()));
            ps.setDate(2, new java.sql.Date(tomorrow.getTime()));
//            System.out.println(ps.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                GameMetaDTO gameMeta = new GameMetaDTO();
                gameMeta.setGameId(rs.getString("game_id"));
                gameMeta.setDateFree(rs.getDate("date_free").getTime());
                gameMeta.setGameTitle(rs.getString("game_title"));
                games.add(gameMeta);
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
        
        return games;
        
    }

    public void setRating(String userId, String gameId, int rating) {
        try {
            Integer.parseInt(userId);
        } catch (Exception ex) {
            System.out.println("setRating Invalid userId:"+userId);
            return;
        }
        
        Connection conn = null;
        
        try {
            String sql = "INSERT INTO ratings (game_id, user_id, rating) VALUES (?, ?, ?)";

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, gameId);
            ps.setInt(2, Integer.parseInt(userId));
            ps.setInt(3, rating);

            ps.executeUpdate();
            ps.close();

        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            try {
                String sql = "UPDATE ratings SET rating = ? WHERE game_id=? AND user_id=?";

                conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, rating);
                ps.setString(2, gameId);
                ps.setString(3, userId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }
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
