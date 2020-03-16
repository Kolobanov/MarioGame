package company;


import java.awt.*;
import java.util.ArrayList;

import static company.Main.marioGraphics;


public class MainHero implements GameObject {
    public Rectangle position = new Rectangle(0,0,0,0);
    public int dx = 0;
    public int dy = 0;
    public boolean jumping = false;
    public String levelCompleted = "";
    public static boolean levelChanged = false;

    int temp = 1;

    public void update(ArrayList<company.Obstacle> obstacles, ArrayList<Coin> coins, ArrayList<Coin> stars) {
        dy+=10;

        if (dy > 30)
            dy = 30;
        int lastX = position.x;
        int lastY = position.y;
        position.setBounds(
                position.x + dx,
                position.y,
                position.width,
                position.height
        );

        if(lastY > 700 && MarioGraphics.alive){ // if we fall
            levelCompleted = "GAME OVER!";
            MarioGraphics.lives--; // decrease the number of lives
            MarioGraphics.alive = false; // if mainhero falls, he is not alive
        }

        if((lastX > 8200) && !levelChanged && MarioGraphics.alive){
            levelCompleted = "Level is completed!";
            MarioGraphics.currentLevel++;
            MarioGraphics.livesTaken = false;
            levelChanged = true;
        }




        boolean intersects = false;
        for (Obstacle obstacle : obstacles) {
            if (obstacle.position.intersects(position)) {
                intersects = true;
            }
        }

        if (intersects)
            position.setBounds(lastX,position.y,position.width,position.height);

        position.setBounds(
                position.x,
                position.y + dy,
                position.width,
                position.height
        );


        for (Obstacle obstacle : obstacles) {
            if (obstacle.position.intersects(position) && obstacle.type != 2 && obstacle.type != 5) { // wall
                if(lastY+position.height > obstacle.position.y){ //obstacle.position.y-=10;
                    position.setBounds(position.x,lastY,position.width,position.height);
                    jumping = true;}
                else{
                    jumping = false;

                    position.setBounds(position.x,obstacle.position.y - 2 - position.height,position.width,position.height);
                }}

            if(obstacle.type == 5) { // manipulating mushrooms
                if (temp <= 240 && temp >= 0) {
                    obstacle.position.x += 4;
                    temp++;
                }
                if (temp < 0 && temp >= -240) {
                    obstacle.position.x -= 4;
                    temp--;
                }
                if (temp == -240) temp = 1;
                if (temp == 240) temp = -1;
            }

            if (obstacle.position.intersects(position) && obstacle.type == 2) {  // box
                if(lastY+position.height > obstacle.position.y){ obstacle.position.y-=10;
                    position.setBounds(position.x,lastY-10,position.width,position.height);
                    jumping = true;

                    if(lastX%2 == 0)
                        coins.add(new Coin(obstacle.position.x + 20, obstacle.position.y - 20, 40, 40, 0));
                    else
                        stars.add(new Coin(obstacle.position.x + 20, obstacle.position.y - 20, 40, 40, 0));
                    obstacle.visible = false;
                    obstacle.position.setBounds(0,0,0,0);
                }
                else{
                    jumping = false;
                    position.setBounds(position.x,obstacle.position.y - 2 - position.height,position.width,position.height);
                }
            }
        }
    }

    public void collision () {
        for (int i = 0; i < GameLogic.getCurrentGame().coins.size(); i++)
            if (position.intersects(GameLogic.getCurrentGame().coins.get(i).position)) {
                GameLogic.getCurrentGame().playSound();
                marioGraphics.coins++;
                GameLogic.getCurrentGame().coins.remove(i);

                if(MarioGraphics.coins > MarioGraphics.lastCoins) MarioGraphics.lastCoins = MarioGraphics.coins;
            }

        for (int i = 0; i < GameLogic.getCurrentGame().stars.size(); i++)
            if (position.intersects(GameLogic.getCurrentGame().stars.get(i).position)) {
                GameLogic.getCurrentGame().playSound();
                marioGraphics.stars++;
                GameLogic.getCurrentGame().stars.remove(i);

            }


        for (int i = 0; i < GameLogic.getCurrentGame().lives.size(); i++)
            if (position.intersects(GameLogic.getCurrentGame().lives.get(i).position)) {
                GameLogic.getCurrentGame().playSound();
                MarioGraphics.lives++;
                GameLogic.getCurrentGame().lives.remove(i);
                MarioGraphics.livesTaken = true;
            }

        for (int i = 0; i < GameLogic.getCurrentGame().obstacles.size(); i++)
            if ((GameLogic.getCurrentGame().obstacles.get(i).type == 5 )&& position.intersects(GameLogic.getCurrentGame().obstacles.get(i).position)) {
                if(position.x%2 == 0)
                    GameLogic.getCurrentGame().coins.add(new Coin(GameLogic.getCurrentGame().obstacles.get(i).position.x + 60,
                            GameLogic.getCurrentGame().obstacles.get(i).position.y - 20, 40, 40, 0));
                else
                    GameLogic.getCurrentGame().stars.add(new Coin(GameLogic.getCurrentGame().obstacles.get(i).position.x + 60,
                            GameLogic.getCurrentGame().obstacles.get(i).position.y - 20, 40, 40, 0));
                GameLogic.getCurrentGame().obstacles.remove(i);
            }

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


