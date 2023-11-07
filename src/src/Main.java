package src;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import src.ClickTable.ButtonEditor;
import src.ClickTable.ButtonRenderer;
import src.dao.QuizDAO;
import src.model.Quiz;
import src.user.Login;

public class Main extends JFrame {
	private JPanel container;
	private JLabel titleH1, titleH2, quizDesc, quizAnsDesc, authorLbl, listTitleLbl, tableNoDataErrorText;
	private JTextArea txtarea;
	private JScrollPane scrollPane, tableScroll;
	private JRadioButton rb1, rb2;
	private JTextField txtfield;
	private JButton btnSend;
	private JSeparator sep;
	private JTable table;
	private JButton linkButton;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setTitle("Main Menu");
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		setTitle("Main Menu");
		DefaultTableModel model = ClickTable.fetchDataFromDatabase();
		table = new JTable(model);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		container = new JPanel();
		setContentPane(container);
		container.setLayout(null);

		titleH1 = new JLabel("「〇✕クイズ」　アプリ：CRUD");
		titleH1.setBounds(10, 10, 400, 24);
		titleH1.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.BOLD, 24));
		container.add(titleH1);

		titleH2 = new JLabel("タイトル");
		titleH2.setBounds(10, 54, 100, 20);
		titleH2.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.BOLD, 20));
		container.add(titleH2);

		quizDesc = new JLabel("クイズの内容:");
		quizDesc.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.PLAIN, 16));
		quizDesc.setBounds(10, 95, 150, 20);
		container.add(quizDesc);

		txtarea = new JTextArea(5, 30); // mur baganaa set-lene
		txtarea.setWrapStyleWord(true);
		txtarea.setLineWrap(true);

		scrollPane = new JScrollPane(txtarea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 125, 300, 70); // scrollpanel aa 設定
		container.add(scrollPane);

		quizAnsDesc = new JLabel("クイズの解答:");
		quizAnsDesc.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.PLAIN, 16));
		quizAnsDesc.setBounds(10, 200, 150, 20);
		container.add(quizAnsDesc);

		rb1 = new JRadioButton("「〇」");
		rb1.setBounds(10, 220, 80, 20);
		rb2 = new JRadioButton("「✕」");
		rb2.setBounds(90, 220, 150, 20);

		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rb1);
		btnGroup.add(rb2);
		container.add(rb1);
		container.add(rb2);

		authorLbl = new JLabel("作成者:");
		authorLbl.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.PLAIN, 16));
		authorLbl.setBounds(10, 240, 100, 20);
		container.add(authorLbl);

		txtfield = new JTextField(100);
		txtfield.setBounds(10, 270, 200, 20);
		container.add(txtfield);

		// このコードはクイズを作成してデータベースに登録します。
		//　ちゃんと入力しないといけない 

		btnSend = new JButton("送信");
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String question = txtarea.getText();
				boolean answer = rb1.isSelected() || rb2.isSelected();
				String author = txtfield.getText();

				if (question.isEmpty() || !answer || author.isEmpty()) {
					JOptionPane.showMessageDialog(null, "すべてのフィールドに入力し、回答を選択してください。", "エラーメッセージ",
							JOptionPane.ERROR_MESSAGE);
				} else {

					Quiz quiz = new Quiz();
					quiz.setQuestion(question);
					if (rb1.isSelected()) {
						quiz.setAnswer(answer);

					} else if (rb2.isSelected()) {
						quiz.setAnswer(!answer);
					}
					quiz.setAuthor(author);

					QuizDAO q = new QuizDAO();
					int c = q.addQuiz(quiz);

					if (c > 0) {
						// 登録することができたかどうかメッセージで表示する
						JDialog successDialog = new JDialog();
						successDialog.setTitle("メッセージ");
						successDialog.setSize(300, 100);
						successDialog.setLayout(new FlowLayout());

						// 真ん中に表示する
						successDialog.setLocationRelativeTo(null);
						JLabel mes = new JLabel("レコードが正常に挿入されました!!!");
						// また入力ボタンを作成
						JButton oneMoreButton = new JButton("また入力");
						oneMoreButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// ダイアログを閉じる
								successDialog.dispose();
								txtarea.setText("");
								btnGroup.clearSelection();
								txtfield.setText("");
							}
						});

						// クイズ画面へ案内する
						JButton nextPlayButton = new JButton("クイズ画面へ");
						nextPlayButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								successDialog.dispose();
								setVisible(false);
								Login p = new Login();
								p.setTitle("Play");
								p.setLocationRelativeTo(null);
								p.setVisible(true);
								p.setResizable(false);
							}
						});

						// ボタンらを追加する
						successDialog.add(mes);
						successDialog.add(oneMoreButton);
						successDialog.add(nextPlayButton);

						// ダイアログを表示する
						successDialog.setVisible(true);

						// ここでもし登録したらテーブルを再実行する
						DefaultTableModel updatedModel = ClickTable.fetchDataFromDatabase();
						table.setModel(updatedModel);
						table.getColumnModel().getColumn(0).setPreferredWidth(20);
						table.getColumnModel().getColumn(4).setPreferredWidth(70);
						table.getColumnModel().getColumn(5).setPreferredWidth(70);
						table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("編集"));
						table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("削除"));
						table.getColumnModel().getColumn(4)
								.setCellEditor(new ButtonEditor(new JCheckBox(), "編集", table));
						table.getColumnModel().getColumn(5)
								.setCellEditor(new ButtonEditor(new JCheckBox(), "削除", table));
						//						data_table.getTableHeader().setReorderingAllowed(false);
						table.getTableHeader().setReorderingAllowed(false);
						table.revalidate();
						table.repaint();
					} else {
						JOptionPane.showMessageDialog(null, "レコードを挿入できません!!!");
					}

					tableScroll.revalidate();
					tableScroll.repaint();
				}
			}
		});
		btnSend.setBounds(10, 290, 60, 20);
		container.add(btnSend);

		sep = new JSeparator();
		sep.setOrientation(SwingConstants.HORIZONTAL);
		sep.setBounds(10, 330, 410, 2);
		sep.setBackground(Color.LIGHT_GRAY);
		container.add(sep);

		//separotor - oos dooshoo ymnuudaa nemlee
		listTitleLbl = new JLabel("登録クイズ一覧：プレイ");
		listTitleLbl.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.BOLD, 20));
		listTitleLbl.setBounds(20, 350, 300, 20);
		container.add(listTitleLbl);

		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setPreferredWidth(70);
		table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("編集"));
		table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("削除"));
		table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), "編集", table));
		table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "削除", table));
		table.getTableHeader().setReorderingAllowed(false);
		tableScroll = new JScrollPane(table);
		tableScroll.setBounds(20, 400, 400, 100);
		container.add(tableScroll);

		tableNoDataErrorText = new JLabel("");
		tableNoDataErrorText.setBounds(20, 500, 400, 16);
		tableNoDataErrorText.setFont(new Font("ＭＳ Ｐゴシック 本文", Font.PLAIN, 16));
		int count = table.getRowCount();
		if (count < 1) {
			tableNoDataErrorText.setText("登録されているクイズはありません。");
		}
		container.add(tableNoDataErrorText);

		linkButton = new JButton("<<< クイズゲーム画面へ >>>");
		linkButton.setBounds(20, 520, 200, 16);
		linkButton.setContentAreaFilled(false);
		linkButton.setBorderPainted(false);

		linkButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Login p = new Login();
				p.setLocationRelativeTo(null);
				p.setVisible(true);
				p.setResizable(false);
			}
		});
		container.add(linkButton);
	}

}