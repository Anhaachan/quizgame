package src;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import src.connection.ConnectionUtil;
import src.dao.QuizDAO;
import src.model.Quiz;

public class ClickTable {
	Connection connection;
	static class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer(String text) {
			setText(text);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return this;
		}
	}

	static class ButtonEditor extends DefaultCellEditor {
		private JButton button;
		private JTable table;

		public ButtonEditor(JCheckBox checkBox, String label, JTable table) {
			super(checkBox);
			this.table = table;
			button = new JButton(label);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						if (label.equals("編集")) {
							// 編集ボタンをクリックしたら
							editRow(selectedRow);
						} else if (label.equals("削除")) {
							deleteRow(selectedRow);
						}
					}
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			return button.getText();
		}
		private void deleteRow(int row)
		{
			int selectedRow = table.getSelectedRow();
			if(selectedRow != -1)
			{ int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "このクイズを削除してもよろしいですか?",
                    "削除の確認",
                    JOptionPane.YES_NO_OPTION);
			  if (confirm == JOptionPane.YES_OPTION) {
                  // Get the quiz ID from the selected row
                  int quizId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

                  // Delete the quiz from the database
                  QuizDAO quizDAO = new QuizDAO();
                  int result = quizDAO.deleteQuiz(quizId);

                  if (result > 0) {
                	// Refresh the table
						DefaultTableModel updatedModel = fetchDataFromDatabase();
						table.setModel(updatedModel);
						table.getColumnModel().getColumn(0).setPreferredWidth(20);
						table.getColumnModel().getColumn(4).setPreferredWidth(70);
						table.getColumnModel().getColumn(5).setPreferredWidth(70);
						table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("編集"));
						table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("削除"));
						table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), "編集", table));
						table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "削除", table));
						table.revalidate();
						table.repaint();
                  } else {
                      JOptionPane.showMessageDialog(null, "クイズの削除に失敗しました。");
                  }
              }
			}
		}
		private void editRow(int row) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				int quizId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
				String question = table.getValueAt(selectedRow, 1).toString();
				boolean answer = (boolean) table.getValueAt(selectedRow, 2);
				String author = table.getValueAt(selectedRow, 3).toString();

				JDialog editDialog = new JDialog();
				editDialog.setTitle("クイズの編集");
				editDialog.setSize(400, 200);
				editDialog.setLayout(new GridLayout(5, 2));
				
				JLabel questionLabel = new JLabel("質問：");
				JTextField questionField = new JTextField(question);
				JLabel answerLabel = new JLabel("答え： チックをつけたら T になる");
				JCheckBox answerCheckBox = new JCheckBox();
				answerCheckBox.setSelected(answer);
				JLabel authorLabel = new JLabel("著者：");
				JTextField authorField = new JTextField(author);

				JButton editButton = new JButton("編集");
				JButton cancelButton = new JButton("キャンセル");

				editButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// Get the edited values

						String editedQuestion = questionField.getText();
						boolean editedAnswer = answerCheckBox.isSelected();
						String editedAuthor = authorField.getText();

						// Update the quiz data
						Quiz editedQuiz = new Quiz();
						editedQuiz.setId(quizId);
						editedQuiz.setQuestion(editedQuestion);
						editedQuiz.setAnswer(editedAnswer);
						editedQuiz.setAuthor(editedAuthor);

						// Update the quiz data in the database
						QuizDAO quizDAO = new QuizDAO();
						int result = quizDAO.updateQuiz(editedQuiz);

						if (result > 0) {
							// Successful update, close the dialog
							JOptionPane.showMessageDialog(null, "クイズは正常に更新されました。");
							editDialog.dispose();

							// Refresh the table
							DefaultTableModel updatedModel = fetchDataFromDatabase();
							table.setModel(updatedModel);
							table.getColumnModel().getColumn(0).setPreferredWidth(20);
							table.getColumnModel().getColumn(4).setPreferredWidth(70);
							table.getColumnModel().getColumn(5).setPreferredWidth(70);
							table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("編集"));
							table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("削除"));
							table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), "編集", table));
							table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "削除", table));
							table.revalidate();
							table.repaint();
						} else {
							JOptionPane.showMessageDialog(null, "クイズの更新に失敗しました。");
						}
					}
				});

				// Action listener for the Cancel button
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						editDialog.dispose();
					}
				});

				// Add components to the edit dialog
				editDialog.add(questionLabel);
				editDialog.add(questionField);
				editDialog.add(answerLabel);
				editDialog.add(answerCheckBox);
				editDialog.add(authorLabel);
				editDialog.add(authorField);
				editDialog.add(editButton);
				editDialog.add(cancelButton);

				editDialog.setVisible(true);
				editDialog.setLocationRelativeTo(null);
			}
		}
	}

	static DefaultTableModel fetchDataFromDatabase() {
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 4 || column == 5;
			}
		};
		
		model.addColumn("ID");
		model.addColumn("内容");
		model.addColumn("解答");
		model.addColumn("作成者");
		model.addColumn("編集");
		model.addColumn("削除");
		
		
		try (Connection connection = ConnectionUtil.getConnection();
				Statement statement = connection.createStatement()) {

			String query = "SELECT id, question, answer, author FROM quiz ORDER BY id ASC";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String content = resultSet.getString("question");
				Boolean answer = resultSet.getBoolean("answer");
				String creator = resultSet.getString("author");

				Vector<Object> row = new Vector<>();
				row.add(id);
				row.add(content);
				row.add(answer);
				row.add(creator);
				row.add("編集");
				row.add("削除");

				model.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return model;
	}
}
