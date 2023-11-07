package src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.connection.ConnectionUtil;
import src.model.Quiz;

public class QuizDAO {
	
	Connection con;
	private PreparedStatement ps;
	private static final String SELECT_ALL_QUERY  = "SELECT * FROM quiz ORDER BY id ASC";
	
	public int addQuiz(Quiz quiz) {
		con = ConnectionUtil.getConnection();
		try {
			ps = con.prepareStatement("INSERT INTO quiz ( question, answer, author) VALUES (?,?,?)");
			ps.setString(1, quiz.getQuestion());
			ps.setBoolean(2, quiz.getAnswer());
			ps.setString(3, quiz.getAuthor());
			int n = ps.executeUpdate();
			con.close();
			return n;
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	public int updateQuiz(Quiz quiz) {
	    con = ConnectionUtil.getConnection();
	    try {
	        ps = con.prepareStatement("UPDATE quiz SET question=?, answer=?, author=? WHERE id=?");
	        ps.setString(1, quiz.getQuestion());
	        ps.setBoolean(2, quiz.getAnswer());
	        ps.setString(3, quiz.getAuthor());
	        ps.setInt(4, quiz.getId());
	        int n = ps.executeUpdate();
	        con.close();
	        return n;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 0;
	    }
	}
	public int selectQuiz()
	{
		 con = ConnectionUtil.getConnection();
		    try {
		        ps = con.prepareStatement(SELECT_ALL_QUERY);
		        int n = ps.executeUpdate();
		        con.close();
		        return n;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return 0;
		    }
		
	}
	public int deleteQuiz(int quizId) {
	    con = ConnectionUtil.getConnection();
	    try {
	        ps = con.prepareStatement("DELETE FROM quiz WHERE id=?");
	        ps.setInt(1, quizId);
	        int n = ps.executeUpdate();
	        con.close();
	        return n;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 0;
	    }
	}

	
	public Quiz selectRandomQuiz() {
        con = ConnectionUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT question, answer FROM quiz ORDER BY RANDOM() LIMIT 1");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String question = rs.getString("question");
                Boolean answer = rs.getBoolean("answer");
                con.close();
                Quiz quiz = new Quiz();
                quiz.setQuestion(question);
                quiz.setAnswer(answer);
                return quiz;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null; // Return null if no question is found
    }
}
