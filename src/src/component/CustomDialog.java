package src.component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomDialog extends JDialog {
    private JButton oneMoreInsertButton;
    private JButton letsPlayButton;

    public CustomDialog(JFrame parentFrame, String message, ActionListener letsPlayActionListener) {
        super(parentFrame, "Success", true);
        setLayout(new BorderLayout());
        setSize(300, 150);

        JLabel label = new JLabel(message);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        oneMoreInsertButton = new JButton("One More Insert");
        letsPlayButton = new JButton("Let's Play");

        buttonPanel.add(oneMoreInsertButton);
        buttonPanel.add(letsPlayButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for "One More Insert" button
        oneMoreInsertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the dialog
                dispose();
            }
        });

        // Action listener for "Let's Play" button
        letsPlayButton.addActionListener(letsPlayActionListener);

        setLocationRelativeTo(parentFrame);
        setResizable(false);
    }
}
