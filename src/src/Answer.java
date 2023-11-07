package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Answer extends JFrame {
	private JPanel container;
	private JLabel titleH1, message;
	private JButton btnReplay, btnModoru;

	public Answer(String answerMessage,  String username,String title) {
		setTitle(title + "- User:" + username);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		container = new JPanel();
		setContentPane(container);
		container.setLayout(null);

		titleH1 = new JLabel("「〇✕クイズ」　アプリ： 解答");
		titleH1.setBounds(10, 10, 400, 24);
		titleH1.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.BOLD, 24));
		container.add(titleH1);

		message = new JLabel(answerMessage);
		message.setBounds(10, 50, 400, 24);
		message.setForeground(Color.red);
		message.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.BOLD, 24));
		container.add(message);
		
		btnReplay = new JButton("リプレイ");
		btnReplay.setBounds(10, 95, 70, 20);
		btnReplay.setMargin(new Insets(0, 0, 0, 0)); // Set margin to zero
		btnReplay.setBorder(null);

		btnReplay.setContentAreaFilled(false);
		btnReplay.setBorderPainted(false);// Remove the border
		btnReplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Play mainFrame = new Play("Play",username);
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setVisible(true);
				mainFrame.setResizable(false);
			}
		});

		container.add(btnReplay);

		btnModoru = new JButton("CRUD画面へ");
		btnModoru.setBounds(10, 125, 80, 20);
		btnModoru.setMargin(new Insets(0, 0, 0, 0)); // Set margin to zero
		btnModoru.setBorder(null);

		btnModoru.setContentAreaFilled(false);
		btnModoru.setBorderPainted(false);// Remove the border
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

	}
}
