package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import src.dao.QuizDAO;
import src.dao.UserDAO;
import src.model.Quiz;
import src.model.User;
import src.user.Login;

public class Play extends JFrame {
	private QuizDAO quizDAO;
	private UserDAO userDAO;
	private JPanel container;
	private JLabel titleH1, titleH2, quizAnsDesc,scoreLbl;
	private JButton btnBatsu, btnMaru, btnModoru;
	private Quiz currentQuiz;
	private String loggedUser;
	private int score = 0;
	public Play(String title, String loggedInUsername) {
		
		JMenuBar menubar = new JMenuBar();
		JMenu filemenu = new JMenu("File");
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		JMenuItem settingsMenuItem = new JMenuItem("Settings");
		
		exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					updateDatabaseAndExit();
			}
		});
		
		filemenu.add(exitMenuItem);
		filemenu.add(settingsMenuItem);
		menubar.add(filemenu);
		
//		setJMenuBar(menubar);
		this.loggedUser = loggedInUsername;
		setTitle(title + " - User:" + loggedUser);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		container = new JPanel();
		setContentPane(container);
		container.setLayout(null);
		quizDAO = new QuizDAO();
		userDAO = new UserDAO();
		titleH1 = new JLabel("「〇✕クイズ」　アプリ： PLAY");
		titleH1.setBounds(10, 10, 400, 24);
		titleH1.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.BOLD, 24));
		container.add(titleH1);

		 int highScore = getHighScoreForUser(loggedUser);
		titleH2 = new JLabel("High Score: " + highScore);
		titleH2.setBounds(10, 54, 200, 30);
		titleH2.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.BOLD, 20));
		container.add(titleH2);
		
		scoreLbl = new JLabel("Score: " + score);
		scoreLbl.setBounds(300, 54, 500, 30);
		scoreLbl.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.BOLD, 20));
		container.add(scoreLbl);

		btnModoru = new JButton("CRUD画面へ");
		btnModoru.setBounds(10, 95, 80, 20);
		btnModoru.setMargin(new Insets(0, 0, 0, 0));
		btnModoru.setBorder(null);
		btnModoru.setContentAreaFilled(false);
		btnModoru.setBorderPainted(false);
		btnModoru.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Main mainFrame = new Main();
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setVisible(true);
				mainFrame.setResizable(false);
			}
		});
		container.add(btnModoru);

		quizAnsDesc = new JLabel("<html>クイズ内容<br/><br/><br/><br/><br/></html>");
		quizAnsDesc.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.PLAIN, 16));
		quizAnsDesc.setBounds(10, 125, 400, 40); // テキストは多くなったら次のラインから始め
		container.add(quizAnsDesc);

		displayRandomQuizQuestion();
		
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(300,190,100,25);
		btnExit.setFont(new Font("JetBrains Mono", Font.BOLD, 13));
		btnExit.setFocusPainted(false);
		btnExit.setBackground(new Color(24, 89, 201));
		btnExit.setForeground(Color.WHITE);
		  btnExit.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Call the method to update the database and exit the game
	                updateDatabaseAndExit();
	            }
	        });
	        container.add(btnExit);
		
		btnMaru = new JButton("O");
		btnMaru.setBounds(10, 190, 25, 25);
		btnMaru.setFont(new Font("Arial", Font.BOLD, 24));
		btnMaru.setFocusPainted(false);
		btnMaru.setBackground(new Color(0, 255, 0));
		btnMaru.setForeground(Color.WHITE);
		btnMaru.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		btnMaru.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkAnswer(true);
			}
		});
		container.add(btnMaru);

		btnBatsu = new JButton("X");
		btnBatsu.setBounds(60, 190, 25, 25);
		btnBatsu.setFont(new Font("Arial", Font.BOLD, 24));
		btnBatsu.setFocusPainted(false);
		btnBatsu.setBackground(new Color(255, 0, 0));
		btnBatsu.setForeground(Color.WHITE);
		btnBatsu.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		btnBatsu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkAnswer(false);
			}
		});
		container.add(btnBatsu);

	}
	  private int getHighScoreForUser(String username) {
	        User user = userDAO.getUserByUsername(username);
	        if (user != null) {
	            return user.getHighscore().intValue();
	        } else {
	            return 0; // Return 0 if the user is not found
	        }
	    }
	 
	private void displayRandomQuizQuestion() {
		Quiz randomQuiz = quizDAO.selectRandomQuiz();
		if (randomQuiz != null) {
			quizAnsDesc.setText(
					"<html>" + randomQuiz.getQuestion() + "</html>" + "</br>");
			currentQuiz = randomQuiz; // チックしたあと現在のクイズを表示する
		} else {
			quizAnsDesc.setText("質問が見つかりませんでした。");
			currentQuiz = null; // クイズがなければエラーメッセージを表示する
		}
	}
	
//	public void checkAnswer(Boolean userAnswer) {
//		if (currentQuiz != null) {
//			Boolean correctAnswer = currentQuiz.getAnswer();
//			if (correctAnswer !=  null) {
//				String answerMessage = userAnswer
//						.equals(correctAnswer.booleanValue()) ? "正解" : "不正解";
//				// System.out.println(answerMessage); // テスト
//				setVisible(false);
//				Answer answerFrame = new Answer(answerMessage, loggedUser,
//						"Answer");
//				answerFrame.setLocationRelativeTo(null);
//				answerFrame.setVisible(true);
//				answerFrame.setResizable(false);
//			} else {
//				System.out.println("現在の質問に対する回答が見つかりませんでした。");
//			}
//		} else {
//			System.out.println("質問が見つかりませんでした。");
//		}
//	}
	public void checkAnswer(Boolean userAnswer) {
	    if (currentQuiz != null) {
	        Boolean correctAnswer = currentQuiz.getAnswer();
	        if (correctAnswer != null) {
	            String answerMessage = userAnswer.equals(correctAnswer.booleanValue()) ? "正解" : "不正解";
	            if ("正解" == (answerMessage)) {
	                // Correct answer, increase the local score variable
	                score++;
	                // Update the score label
	                System.out.println(score);
	                scoreLbl.setText("Score: " + score);
	            }

	            setVisible(false);
	            Answer answerFrame = new Answer(answerMessage, loggedUser, "Answer");
	            answerFrame.setLocationRelativeTo(null);
	            answerFrame.setVisible(true);
	            answerFrame.setResizable(false);
	        } else {
	            System.out.println("現在の質問に対する回答が見つかりませんでした。");
	        }
	    } else {
	        System.out.println("質問が見つかりませんでした。");
	    }
	}
	Login p = new Login();

	 public void updateDatabaseAndExit() {
	        // Update the database with the final 	
		 //update database with final score
		 userDAO.updateScore(loggedUser, BigDecimal.valueOf(score));
	        // Close the current frame (Play)
	        dispose();
	        // Open the login window or perform any other actions as needed
	        Login p = new Login();
	        p.setLocationRelativeTo(null);
	        p.setVisible(true);
	        p.setResizable(false);
	    }


}
