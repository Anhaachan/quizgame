package src.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class MyButton extends JButton {
	public MyButton(String name, String color) {
		super(name);
		setFont(new Font("JetBrains Mono", Font.BOLD, 13));
		setFocusPainted(false);
		switch (color) {
			case "primary" :
				setBackground(new Color(24, 89, 201));
				setForeground(Color.white);
				break;
			default :
				setBackground(new Color(233, 236, 240));
				setForeground(Color.DARK_GRAY);
				break;
		}

	}
	public MyButton(String name) {
		super(name);
		setFont(new Font("JetBrains Mono", Font.BOLD, 13));
		setFocusPainted(false);

		setBackground(new Color(233, 236, 240));
		setForeground(Color.DARK_GRAY);

	}

}
