package src.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import src.Play;
import src.dao.UserDAO;
import src.model.User;

public class Login extends JFrame {
	private JPanel c;
	private JLabel usrLbl, pwdLbl;
	private JButton lgnBtn, rgBtn;
	private JTextField usrTf;
	private JPasswordField pwdTf;
	private String loggedInUsername;

	public Login() {
		setBounds(10, 10, 300, 250);
		c = new JPanel();
		setContentPane(c);
		c.setLayout(null);

		usrLbl = new JLabel("Username:");
		usrLbl.setBounds(20, 40, 100, 24);
		usrLbl.setFont(new Font("JetBrains Mono", Font.BOLD, 16));
		c.add(usrLbl);

		usrTf = new JTextField(15);
		usrTf.setBounds(160, 40, 100, 24);
		c.add(usrTf);

		pwdLbl = new JLabel("Password:");
		pwdLbl.setBounds(20, 80, 100, 24);
		pwdLbl.setFont(new Font("JetBrains Mono", Font.BOLD, 16));
		c.add(pwdLbl);

		pwdTf = new JPasswordField(15);
		pwdTf.setBounds(160, 80, 100, 24);
		c.add(pwdTf);

		rgBtn = new JButton("Register");
		rgBtn.setBounds(20, 120, 100, 24);
		rgBtn.setFont(new Font("JetBrains Mono", Font.BOLD, 13));
		rgBtn.setFocusPainted(false);
		rgBtn.setBackground(new Color(233, 236, 240));
		

		lgnBtn = new JButton("Login");
		lgnBtn.setBounds(160, 120, 100, 24);
		lgnBtn.setFont(new Font("JetBrains Mono", Font.BOLD, 13));
		lgnBtn.setFocusPainted(false);
		lgnBtn.setBackground(new Color(24, 89, 201));
		lgnBtn.setForeground(Color.WHITE);
		 lgnBtn.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            String username = usrTf.getText();
		            String password = new String(pwdTf.getPassword());
		            	
		            UserDAO loginDAO = new UserDAO();
		            User user = loginDAO.loginUser(username, password);

		            if (user != null) {
		            	loggedInUsername = username;
		                setVisible(false);
		                Play p = new Play("Play",loggedInUsername);
		                p.setLocationRelativeTo(null);
		                p.setVisible(true);
		                System.out.println("Login successful. User ID: " + user.getUserid());
		            } else {
		                // Display an error message or take appropriate action for failed login.
		                JOptionPane.showMessageDialog(null, "Login failed. Invalid username or password.");
		            }
		        }
		    });
		c.add(lgnBtn);

		rgBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RegistrationForm registrationForm = new RegistrationForm();
	             registrationForm.setVisible(true);		
	             registrationForm.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO 自動生成されたメソッド・スタブ
						rgBtn.setEnabled(false);
						lgnBtn.setEnabled(false);
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO 自動生成されたメソッド・スタブ
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO 自動生成されたメソッド・スタブ
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO 自動生成されたメソッド・スタブ
						
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO 自動生成されたメソッド・スタブ
						rgBtn.setEnabled(true);
						lgnBtn.setEnabled(true);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO 自動生成されたメソッド・スタブ
						rgBtn.setEnabled(true);
						lgnBtn.setEnabled(true);
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO 自動生成されたメソッド・スタブ
						
					}
				});
			}
		});
		c.add(rgBtn);
		
	}
	public static void main(String[] args) {
		Login f = new Login();
		f.setVisible(true);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setTitle("Quiz-Game Login");
	}

	}
