package Snake;

import java.awt.*;

/**
 * Class implements item (apple).
 */
public class Apple {

    private int x, y, width, height;
    private int type;

    /**
     * Item class constructor
     * @param x item's X position (column number)
     * @param y item's Y position (row number)
     * @param type type of item
     * @param size size of item
     */
    public Apple(int x, int y, int type, int size){
        this.x = x;
        this.y = y;
        this.type = type;
        this.width = size;
        this.height = size;
    }

    /**
     * Item drawing method
     * @param g
     * item eating support
     */
    public void draw(Graphics g){
        if(type == 1) {
            g.setColor(Color.RED);
            g.fillRect(x * width, y * height, width, height);//apple
        }
        else if(type == 2){
            g.setColor(Color.YELLOW);
            g.fillRect(x * width, y * height, width, height);//speed up
        }
        else if(type == 3){
            g.setColor(Color.BLUE);
            g.fillRect(x * width, y * height, width, height);//speed down
        }
        else if(type == 4){
            g.setColor(Color.BLACK);
            g.fillRect(x * width, y * height, width, height);//wall
        }
    }

    /**
     * Item's X position getter
     * @return item's X position
     */
    public int getX(){
        return x;
    }

    /**
     * Item's Y position getter
     * @return item's Y position
     */
    public int getY(){
        return y;
    }

    /**
     * Item's type getter
     * @return Type of item
     */
    public int getType(){
        return type;
    }
}
