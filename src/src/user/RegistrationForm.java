package src.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import src.component.MyLabel;
import src.dao.UserDAO;
import src.model.User;

class RegistrationForm extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(300, 250);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding

        JLabel  usernameLabel = new MyLabel("Username:",14);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(100, 25)); // Set preferred size
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(usernameField, gbc);

        JLabel passwordLabel = new MyLabel("Password:",14);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(100, 25)); // Set preferred size
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(passwordField, gbc);

        JLabel confirmPasswordLabel = new MyLabel("Confirm Password:",14);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(100, 25)); // Set preferred size
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(confirmPasswordField, gbc);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
               
                if (password.equals(confirmPassword)) {
    				User user = new User();
    				user.setUsername(username);
    				user.setPassword(password);
    				user.setHighscore(new BigDecimal(0));
    				user.setRightanswer(new BigDecimal(0));
    				user.setWronganswer(new BigDecimal(0));
    				user.setScore(new BigDecimal(0));
    
    				UserDAO u = new UserDAO();
    				int result = u.addUser(user);
    				if (result == 1) {
    				    JOptionPane.showMessageDialog(null, "Registration Successful!");
    				    dispose();
    				} else if (result == -1) {
    				    JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different username.");
    				} else {
    				    JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
    				}
    				System.out.println(result);
                	dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }
}
