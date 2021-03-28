package Snake;

import java.awt.*;

/**
 * Class including snake's body element (single square).
 */
public class BodyPart {

    private int x, y, width, height;

    /**
     * BodyPart constructor.
     * @param x body element's X position (column number)
     * @param y body element's Y position (row number)
     * @param size size of body element
     */
    public BodyPart(int x, int y, int size){
        this.x = x;
        this.y = y;
        this.width = size;
        this.height = size;
    }

    /**
     * Body element drawing method
     * @param g object from Graphics class
     */
    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(x * width, y * height, width, height);
        g.setColor(Color.GREEN);
        g.fillRect(x * width + 2, y * height + 2, width - 4, height - 4);
    }

    /**
     * X position getter
     * @return X position
     */
    public int getX(){
        return x;
    }

    /**
     * Y position getter
     * @return Y position
     */
    public int getY(){
        return y;
    }
}
