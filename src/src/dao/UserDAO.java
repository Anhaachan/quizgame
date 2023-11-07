package src.dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.connection.ConnectionUtil;
import src.model.User;	

public class UserDAO {
    private Connection con;
    private PreparedStatement ps;
    public UserDAO(){
    	  con = ConnectionUtil.getConnection();
    }
    public int addUser(User user) {
      

        // Check if the username already exists
        if (isUsernameExists(user.getUsername())) {
            return -1; // Username already exists
        }

        try {
            ps = con.prepareStatement("INSERT INTO users"
                    + " (username, password, highscore, rightanswer, wronganswer, score)"
                    + "  VALUES (?,?,?,?,?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setBigDecimal(3, user.getHighscore());
            ps.setBigDecimal(4, user.getRightanswer());
            ps.setBigDecimal(5, user.getWronganswer());
            ps.setBigDecimal(6, user.getScore());
            int n = ps.executeUpdate();

            return n;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private boolean isUsernameExists(String username) {
        try {
            ps = con.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public User loginUser(String username, String password) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserid(rs.getInt("userid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setHighscore(rs.getBigDecimal("highscore"));
                user.setRightanswer(rs.getBigDecimal("rightanswer"));
                user.setWronganswer(rs.getBigDecimal("wronganswer"));
                user.setScore(rs.getBigDecimal("score"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no matching user is found
    }
    public User getUserByUsername(String username) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserid(rs.getInt("userid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setHighscore(rs.getBigDecimal("highscore"));
                user.setRightanswer(rs.getBigDecimal("rightanswer"));
                user.setWronganswer(rs.getBigDecimal("wronganswer"));
                user.setScore(rs.getBigDecimal("score"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no matching user is found
    }
    public void updateScore(String username, BigDecimal newScore) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET score = ? WHERE username = ?");
            ps.setBigDecimal(1, newScore);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

