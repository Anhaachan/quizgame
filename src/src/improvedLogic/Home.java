package src.improvedLogic;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.Timer;
//
//import src.component.MyButton;
//
//public class Home extends JFrame {
//    private JPanel c;
//    private JButton usrBtn, pwdBtn, lgnBtn, rgBtn;
//    private JLabel timerLabel;
//    private Timer timer;
//    private int timeLeft = 10; // Initial time in seconds
//
//    public Home() {
//        setBounds(10, 10, 300, 250);
//        c = new JPanel();
//        setContentPane(c);
//        c.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.insets = new Insets(0, 0, 20, 0);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.weightx = 0;
//        gbc.gridwidth = GridBagConstraints.RELATIVE;
//
//        usrBtn = new MyButton("PLAY", "primary");
//        c.add(usrBtn, gbc);
//
//        gbc.gridy++;
//        pwdBtn = new MyButton("Highscores");
//        c.add(pwdBtn, gbc);
//
//        gbc.gridy++;
//        rgBtn = new MyButton("Option");
//        c.add(rgBtn, gbc);
//
//        gbc.gridy++;
//        lgnBtn = new MyButton("Exit");
//        c.add(lgnBtn, gbc);
//
//        gbc.gridy++;
//        timerLabel = new JLabel("残り時: " + timeLeft + "　秒");
//        c.add(timerLabel, gbc);
//
//        // Create a timer that decreases timeLeft every second
//        timer = new Timer(1000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                timeLeft--;
//                timerLabel.setText("残り時: " + timeLeft + "　秒");
//                
//                if (timeLeft <= 0) {
//                    // Timer has reached 0, do something when the timer is finished
//                    timer.stop(); // Stop the timer
//                   timerLabel.setText("Time Out");
//                }
//            }
//        });
//
//        // Start the timer
//        timer.start();
//    }
//
//    public static void main(String[] args) {
//        Home f = new Home();
//        f.setVisible(true);
//        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        f.setLocationRelativeTo(null);
//        f.setTitle("Quiz-Game Home");
//    }
//}
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import src.component.MyButton;

public class Home extends JFrame {
    private JPanel c;
    private JButton usrBtn, pwdBtn, lgnBtn, rgBtn;
    private JLabel timerLabel;
    private Timer timer;
    private int timeLeft = 60; // Initial time in seconds
    private GridBagConstraints gbc;
    public Home() {
        setBounds(10, 10, 300, 250);
        c = new JPanel();
        setContentPane(c);
        c.setLayout(new GridBagLayout());
         gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;
        gbc.gridwidth = GridBagConstraints.RELATIVE;

        setupHomeComponents(); // Initialize the home page components

        // Timer setup
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft + " seconds");

                if (timeLeft <= 0) {
                    timer.stop();
                }
            }
        });

        timer.start();
    }

    private void setupHomeComponents() {
        // Clear existing components in the panel
        c.removeAll();

        // Add components for the home page
        usrBtn = new MyButton("PLAY", "primary");
        usrBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	InsertPlayerNamae(); // Navigate to game mode selection page
            }
        });
        c.add(usrBtn, gbc);

        gbc.gridy++;
        pwdBtn = new MyButton("Highscores");
        c.add(pwdBtn, gbc);

        gbc.gridy++;
        rgBtn = new MyButton("Option");
        c.add(rgBtn, gbc);

        gbc.gridy++;
        lgnBtn = new MyButton("Exit");
        c.add(lgnBtn, gbc);

        gbc.gridy++;
        timerLabel = new JLabel("Time left: " + timeLeft + " seconds");
//        c.add(timerLabel, gbc);

        // Revalidate and repaint the panel to update the UI
        c.revalidate();
        c.repaint();
    }

    private void setupGameModeButtons(String name) {
        // Clear existing components in the panel
        c.removeAll();

        // Add game mode buttons
        JButton classicBtn = new MyButton("Classic");
        classicBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	 PlayPage f = new PlayPage("Quiz Game", name);
               f.setVisible(true);
               f.setDefaultCloseOperation(EXIT_ON_CLOSE);
               f.setLocationRelativeTo(null);
            }
        });
        c.add(classicBtn, gbc);
        gbc.gridy++;

        JButton timeBtn = new MyButton("Time");
        timeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Time button click
                // For example, start a timed game
                // ...

                // After handling the action, navigate back to the home page
                setupHomeComponents();
            }
        });
        c.add(timeBtn, gbc);

        // ... (add other components as needed)

        // Revalidate and repaint the panel to update the UI
        c.revalidate();
        c.repaint();
    }
  public void InsertPlayerNamae() {
    c.removeAll();
   
    // Add components
    JTextField tf = new JTextField("Player", 12);
    gbc.insets = new Insets(0, 0, 14, 0); // Add vertical space between components
    c.add(tf, gbc);

    gbc.gridy++;

    JButton okBtn = new MyButton("OK");
    gbc.insets = new Insets(10, 0, 0, 0); // Add vertical space between components
    okBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle button click
            // For example, retrieve text from the text field: tf.getText()
            // ...
        	String passName = tf.getText();
            // After handling the action, navigate back to the home page
            setupGameModeButtons(passName);
        }
    });
    c.add(okBtn, gbc);

    // Revalidate and repaint the panel to update the UI
    c.revalidate();
    c.repaint();
}

    public static void main(String[] args) {
        Home f = new Home();
        f.setVisible(true);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setTitle("Quiz-Game Home");
    }
}

