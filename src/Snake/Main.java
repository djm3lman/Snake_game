package Snake;

import javax.swing.*;
import java.awt.*;

/**
 * Program main class
 */
public class Main extends JFrame {

	/**
	 * The main function of the program that creates the application window
	 * @param args string table
	 */
	public static void main(String[] args) {
	    JFrame frame = new JFrame("Snake");
	    frame.setSize(700,700);
	    frame.setResizable(false);
	    frame.setLocationRelativeTo(null);
	    frame.setLayout(new GridLayout(1,1,0,0));
	    GamePanel gamePanel = new GamePanel();
	    frame.add(gamePanel);
		frame.setDefaultCloseOperation(3);
	    frame.setVisible(true);
	    frame.pack();
    }
}
