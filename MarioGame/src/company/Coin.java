package company;

import java.awt.*;

public class Coin {
    public Rectangle position = new Rectangle(0,0,0,0);
    public int type = 0;
    public boolean visible = true;
    public Coin(int x, int y, int width, int height, int type) {
        position = new Rectangle(x,y,width,height);
        this.type = type;
    }
}
