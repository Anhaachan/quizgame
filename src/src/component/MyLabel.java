package src.component;

import java.awt.Font;

import javax.swing.JLabel;

public class MyLabel extends JLabel {

    public MyLabel(String name, int size) {
        	super(name); 
        	try {
        
        setFont(new Font("JetBrains Mono", Font.BOLD, size));
        }
        catch (Throwable e)
        {
        	e.printStackTrace();
        }
        }
}
