package src.model;

import java.math.BigDecimal;

public class User {
	private int userid;
	private String username;
	private String password;
	private BigDecimal highscore;
	private BigDecimal rightanswer;
	private BigDecimal wronganswer;
	private BigDecimal score;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public BigDecimal getHighscore() {
		return highscore;
	}
	public void setHighscore(BigDecimal highscore) {
		this.highscore = highscore;
	}
	public BigDecimal getRightanswer() {
		return rightanswer;
	}
	public void setRightanswer(BigDecimal rightanswer) {
		this.rightanswer = rightanswer;
	}
	public BigDecimal getWronganswer() {
		return wronganswer;
	}
	public void setWronganswer(BigDecimal wronganswer) {
		this.wronganswer = wronganswer;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	
}
