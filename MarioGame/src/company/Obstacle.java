package company;

import java.awt.*;

public class Obstacle implements GameObject{
    public Rectangle position = new Rectangle(0,0,0,0);
    public int type = 0;
    public boolean visible = true;
    public Obstacle(int x, int y, int width, int height, int type) {
        position = new Rectangle(x,y,width,height);
        this.type = type;
    }

    @Override
    public int getImageNo() {
        return 0;
    }

    @Override
    public Rectangle getPosition() {
        return null;
    }
}
