package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.max;

/**
 * Class responsible for creating the game screen and setting its parameters
 */
public class GamePanel extends JPanel implements Runnable{

    private static final int WIDTH = 700, HEIGHT = 700;
    private static int cols = WIDTH/10-1, rows = HEIGHT/10-1;
    private Thread thread;
    private boolean running = false;

    private int x = WIDTH/20, y = HEIGHT/20;
    private int size = 5;
    private int speed = 350000;
    private boolean blueApple = false;
    private boolean goldApple = false;
    private boolean blueSpeed = false;
    private boolean goldSpeed = false;
    private int blueSpeedCounter = 0;
    private int goldSpeedCounter = 0;
    private int counter = 0;
    private int score  = 0;
    private int highScore = 0;

    private Menu menu;

    private BodyPart body;
    private ArrayList<BodyPart> snake;

    private Apple apple;
    private ArrayList<Apple> apples;
    private ArrayList<Apple> walls;

    private Random random;

    private boolean right = true, left = false, up = false, down = false;
    private int steps = 0;
    private Keyboard keyboard;

    /**
     * GamePanell class constructor.
     * Creates lists containing a snake and items. Creates keyboard control.
     */
    public GamePanel(){
        setFocusable(true);
        keyboard = new Keyboard();
        addKeyListener(keyboard);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        random = new Random();
        snake = new ArrayList<BodyPart>();
        apples = new ArrayList<Apple>();
        walls = new ArrayList<Apple>();
    }

    /**
     * Method responsible for:
     * creating items (apples) and placing them on board (red - when last was eaten and gold - every ten reds;
     * item eating support (when snake position equals apple position, apple is removing from list);
     * snake moving (new body part is added and last is removed based on direction parameters);
     * wall collision support (game ends when a wall collision occurs);
     * snake collision support (game ends when snake "eat itself");
     */
    public void refresh(){
        if(snake.size() == 0) {
            body = new BodyPart(x, y, 10);
            snake.add(body);
        }
        //creating apples
        //red apple
        if(apples.size() == 0) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            apple = new Apple(x, y, 1, 10);
            apples.add(apple);
            //gold apple
            if(random.nextInt(2) == 1){
                int tmpX = random.nextInt(cols);
                int tmpY = random.nextInt(rows);
                apple = new Apple(tmpX, tmpY, 2, 10);
                apples.add(apple);
                goldApple = true;
            }
            if(random.nextInt(2) == 1){
                int tmpX = random.nextInt(cols);
                int tmpY = random.nextInt(rows);
                apple = new Apple(tmpX, tmpY, 3, 10);
                apples.add(apple);
                blueApple = true;
            }
            if(random.nextInt(2) == 1){
                int tmpX = random.nextInt(cols);
                int tmpY = random.nextInt(rows);
                apple = new Apple(tmpX, tmpY, 4, 10);
                walls.add(apple);
            }


        }
        //apple eating
        for(int i=0; i<apples.size(); i++){
            //if gold and blue apple on board
            if(goldApple && blueApple){
                //firstly gold apple
                if (x == apples.get(i).getX() && y == apples.get(i).getY() && apples.get(i).getType() == 2) {
                    apples.remove(i);
                    i--;
                    goldSpeed = true;
                    goldSpeedCounter = 0;
                    goldApple = false;
                    speed/=2; //snake speed up
                }
                //firstly blue apple
                else if (x == apples.get(i).getX() && y == apples.get(i).getY() && apples.get(i).getType() == 3) {
                    apples.remove(i);
                    i--;
                    blueSpeed = true;
                    blueSpeedCounter = 0;
                    blueApple = false;
                    speed*=2; //snake speed down
                }
                //firstly red apple
                else if (x == apples.get(i).getX() && y == apples.get(i).getY() && apples.get(i).getType() == 1) {
                    size++;
                    apples.remove(i);
                    apples.remove(1);
                    apples.remove(0);
                    i--;
                    speedtest();
                    blueApple = false;
                    goldApple = false;
                    counter++;
                }
            }
            //if only blue apple on board
            else if(blueApple){
                //firstly blue apple
                if (x == apples.get(i).getX() && y == apples.get(i).getY() && apples.get(i).getType() == 3) {
                    apples.remove(i);
                    i--;
                    blueSpeed = true;
                    blueSpeedCounter = 0;
                    blueApple = false;
                    speed*=2; //snake speed down
                }
                //firstly red apple
                else if (x == apples.get(i).getX() && y == apples.get(i).getY() && apples.get(i).getType() == 1) {
                    size++;
                    apples.remove(i);
                    apples.remove(0);
                    blueApple = false;
                    i--;
                    speedtest();
                    counter++;
                }
            }
            //if only gold apple on board
            else if (goldApple) {
                    //firstly gold apple
                    if (x == apples.get(i).getX() && y == apples.get(i).getY() && apples.get(i).getType() == 2) {
                        apples.remove(i);
                        i--;
                        goldSpeed = true;
                        goldSpeedCounter = 0;
                        goldApple = false;
                        speed /= 2; //snake speed up
                    }
                    //firstly red apple
                    else if (x == apples.get(i).getX() && y == apples.get(i).getY() && apples.get(i).getType() == 1) {
                        size++;
                        apples.remove(i);
                        apples.remove(0);
                        goldApple = false;
                        i--;
                        speedtest();
                        counter++;
                    }
                }
            //if only red apple
            else if (x == apples.get(i).getX() && y == apples.get(i).getY() && apples.get(i).getType() == 1) {
                size++;
                apples.remove(i);
                i--;
                speedtest();
                counter++;
            }

        }
        //black wall (apple) collision
        for(int i=0; i<walls.size(); i++){
            if (x == walls.get(i).getX() && y == walls.get(i).getY() && walls.get(i).getType() == 4){
                reset();
                stop();
            }
        }
        //eating snake by itself
        for(int i = 0; i < snake.size(); i++){
            if(x == snake.get(i).getX() && y == snake.get(i).getY()){
                if(i != snake.size() - 1){
                    reset();
                    stop();
                }
            }
        }
        //screen wall collision
        if(x < 0 || x > 69 || y < 0 || y > 69){
            reset();
            stop();
        }

        //snake moving
        steps++;
        if(steps > speed){
            if(right)x++;
            if(left)x--;
            if(up)y--;
            if(down)y++;
            steps = 0;
            body = new BodyPart(x, y,10);
            snake.add(body);

            if(snake.size() > size){
                snake.remove(0);
            }
        }
    }

    /**
     * Game screen painting method.
     * @param g item eating support
     */
    public void paint(Graphics g){
        if(!running){
            menu = new Menu(WIDTH, HEIGHT, score, highScore);
            menu.draw(g);
        }
        else {
            //board painting
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,WIDTH,HEIGHT);
            g.setColor(Color.BLACK);
            for(int i = 0; i < WIDTH / 10; i++) {
                g.drawLine(i * 10, 0, i * 10, HEIGHT);
            }
            for(int i = 0; i < HEIGHT / 10; i++) {
                g.drawLine(0, i * 10, WIDTH, i * 10);
            }
            //snake painting
            for (int i = 0; i <  snake.size(); i++) {
                snake.get(i).draw(g);
            }
            //apple painting
            for (int i = 0;  i < apples.size(); i++) {
                apples.get(i).draw(g);
            }
            //walls painting
            for (int i = 0;  i < walls.size(); i++) {
                walls.get(i).draw(g);
            }

        }
    }

    public void speedtest(){
        if(blueSpeed) {
            blueSpeedCounter++;
            if (blueSpeedCounter == 2) {
                speed /= 2;
                blueSpeedCounter = 0;
                blueSpeed = false;
            }
        }
        if(goldSpeed){
            goldSpeedCounter++;
            if(goldSpeedCounter == 2){
                speed*=2;
                goldSpeedCounter = 0;
                goldSpeed = false;
            }
        }
    }

    /**
     * Method starting game thread
     */
    public void start(){
        running = true;
        thread = new Thread(this,"Game");
        thread.start();
    }

    /**
     * Method stopping game thread
     */
    public void stop(){
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method restoring initial values of snake position, size, speed, score and clearing snake and apples lists.
     */
    public void reset(){
        x = WIDTH/20;
        y = HEIGHT/20;
        size = 5;
        speed = 350000;
        blueApple = false;
        goldApple = false;
        blueSpeed = false;
        goldSpeed = false;
        blueSpeedCounter = 0;
        goldSpeedCounter = 0;
        score = counter;
        highScore = max(highScore, counter);
        counter = 0;
        right = true;
        left = false;
        up = false;
        down = false;
        snake.clear();
        apples.clear();
        walls.clear();
        repaint();
    }

    /**
     * Method calling refresh and repaint methods
     */
    public void run(){
        while(running){
            refresh();
            repaint();
        }
    }

    /**
     * Class responsible for keyboard support implements KeyListener
     */
    public class Keyboard implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        /**
         * Method responsible for keyboard support
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_RIGHT && !left){
                up = false;
                down = false;
                right = true;
            }
            if(key == KeyEvent.VK_LEFT && !right){
                up = false;
                down = false;
                left = true;
            }
            if(key == KeyEvent.VK_UP && !down){
                left = false;
                right = false;
                up = true;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                left = false;
                right = false;
                down = true;
            }
            if(key == KeyEvent.VK_SPACE && !running){
                start();
            }
            if(key == KeyEvent.VK_ESCAPE && running){
                reset();
                stop();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
