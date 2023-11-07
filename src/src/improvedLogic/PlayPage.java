package src.improvedLogic;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class PlayPage extends JFrame {
	private QuizDAO quizDAO;
	private UserDAO userDAO;
	private JPanel container;
	private JLabel titleH1, quizAnsDesc, scoreLbl;
	private JButton btnBatsu, btnMaru;
	private Quiz currentQuiz;
	private String loggedUser;
	private int score = 0;
	public PlayPage(String title, String loggedInUsername) {

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

		// setJMenuBar(menubar);
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

		scoreLbl = new JLabel("Score: " + score);
		scoreLbl.setBounds(10, 54, 200, 30);
		scoreLbl.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.BOLD, 20));
		container.add(scoreLbl);

		quizAnsDesc = new JLabel("<html>クイズ内容<br/><br/><br/><br/><br/></html>");
		quizAnsDesc.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.PLAIN, 16));
		quizAnsDesc.setBounds(10, 125, 400, 40); // テキストは多くなったら次のラインから始め
		container.add(quizAnsDesc);

		displayRandomQuizQuestion();

		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(300, 190, 100, 25);
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
	@SuppressWarnings("unused")
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

	public void checkAnswer(Boolean userAnswer) {
		if (currentQuiz != null) {
			Boolean correctAnswer = currentQuiz.getAnswer();
			if (correctAnswer != null) {
				String answerMessage = userAnswer
						.equals(correctAnswer.booleanValue()) ? "正解" : "不正解";
				if ("正解" == (answerMessage)) {
					score++;
					System.out.println(score);
					scoreLbl.setText("Score: " + score);
				}

				setVisible(false);
				AnswerPage answerFrame = new AnswerPage(answerMessage, loggedUser,
						"Answer");
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

	public void updateDatabaseAndExit() {
		dispose();
		Home p = new Home();
		p.setLocationRelativeTo(null);
		p.setVisible(true);
		p.setResizable(false);
	}
	public static void main(String[] args) {
		PlayPage f = new PlayPage("aa", "ss");
		f.setVisible(true);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setTitle("Quiz-Game");
	}

}
