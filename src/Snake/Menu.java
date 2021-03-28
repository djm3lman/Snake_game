package Snake;

import javax.swing.*;
import java.awt.*;

/**
 * Class implements main menu screen with last score and high score.
 */
public class Menu extends JFrame {

    private int WIDTH, HEIGHT;
    private int score, highScore;

    /**
     * Menu class constructor.
     * @param width frame width
     * @param height frame height
     * @param score last game result
     * @param highScore best game result
     */
    public Menu(int width, int height, int score, int highScore) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.score = score;
        this.highScore = highScore;
    }

    /**
     * Menu drawing method
     * @param g object from Graphics class
     */
    public void draw(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Copperplate Gothic Bold",1,50));
        g.drawString("S N A K E", WIDTH/2 - g.getFontMetrics().stringWidth("S N A K E")/2, 100);
        g.setFont(new Font("Consolas",1,38));
        g.drawString("Score: " + score, WIDTH/2 - g.getFontMetrics().stringWidth("Score: " + score)/2,HEIGHT/2);
        g.drawString("High score: " + highScore, WIDTH/2 - g.getFontMetrics().stringWidth("Hight score: " + highScore)/2,HEIGHT/2 + 36);
        g.setFont(new Font("Consolas",1,20));
        g.drawString("Press SPACE to play", WIDTH/2 - g.getFontMetrics().stringWidth("Press SPACE to play")/2, HEIGHT - 40);
    }
}
