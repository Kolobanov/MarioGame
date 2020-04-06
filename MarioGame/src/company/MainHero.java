package company;


import java.awt.*;
import java.util.ArrayList;

import static company.Main.marioGraphics;


public class MainHero implements GameObject {
    public Rectangle position = new Rectangle(0,0,0,0);
    public int dx = 0;
    public int dy = 0;
    public int firex=0;//новое
    public int firey=0;//новое

    public boolean jumping = false;

    public boolean flaging = false;//новое
    public String levelCompleted = "";
    public static boolean levelChanged = false;
    public static boolean biging = false;//новое
    public static int fireging = 0;//новое
    public static boolean fire = false;//новое

    int temp = 1;
    int temp2 = 1;//новое
    int temp3 = 1;//новое

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
            if (obstacle.position.intersects(position) && obstacle.type != 2 && obstacle.type != 5 && obstacle.type != 10 && obstacle.type != 12) { // wall
                if(lastY+position.height > obstacle.position.y){ //obstacle.position.y-=10;
                    position.setBounds(position.x,lastY,position.width,position.height);
                    jumping = true;}
                else{
                    jumping = false;
                    position.setBounds(position.x,obstacle.position.y - 2 - position.height,position.width,position.height);
                    if(obstacle.type == 9) { // новое, если запрыгнул на движ. блок
                        position.setBounds(obstacle.position.x,obstacle.position.y - 2 - position.height,position.width,position.height);
                    }
                    if(obstacle.type == 11) { // новое, если запрыгнул на движ. блок
                        flaging = true;
                    }


                }}

            if(obstacle.type == 5 || obstacle.type == 10 || obstacle.type == 14) { //новое manipulating mushrooms
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

            if(obstacle.type == 9) { // новое движ. блок
                if (temp2 <= 100 && temp2 >= 0) {
                    obstacle.position.x += 4;
                    temp2++;
                }
                if (temp2 < 0 && temp2 >= -100) {
                    obstacle.position.x -= 4;
                    temp2--;
                }
                if (temp2 == -100) temp2 = 1;
                if (temp2 == 100) temp2 = -1;
            }

            if(obstacle.type == 11 && flaging) { // новое
                System.out.println("flag 0");
                if (obstacle.position.y <= 396) {
                    System.out.println("flag 1");
                    if (obstacle.position.y >= 50) {
                        System.out.println("flag 2");
                        flaging=false;
                        marioGraphics.coins=marioGraphics.coins+2;
                        MarioGraphics.lastCoins = MarioGraphics.coins;
                    }
                    obstacle.position.y += 4;
                }
            }

            if(obstacle.type == 12) { // новое движ. блок
                if (temp3 <= 8 && temp3 >= 0) {
                    obstacle.position.x += 4;
                    temp3++;
                }
                if (temp3 < 0 && temp3 >= -8) {
                    obstacle.position.x -= 4;
                    temp3--;
                }
                if (temp3 == -8) temp3 = 1;
                if (temp3 == 8) temp3 = -1;
            }




            if(obstacle.type == 13 && fireging!=0) { // новое
                if (fire){
                    obstacle.visible=true;
                    obstacle.position.x=position.x;
                    obstacle.position.y=position.y;
                    firex=obstacle.position.x;
                    firey=obstacle.position.y;
                    fire=false;
                }
                obstacle.position.x += fireging;
                firex=obstacle.position.x;
            }

            if(obstacle.type == 13 && fireging==0) { // новое
                obstacle.position.x=0;
                obstacle.position.y=0;
            }

            if (obstacle.type == 14 && (Math.abs(firex-obstacle.position.x)<20 && Math.abs(firey-obstacle.position.y)<30)){
                MarioGraphics.big=0;
                biging = false  ;
                System.out.println("boom!!!");
                obstacle.visible = false;
                obstacle.position.setBounds(0,0,0,0);
                fireging=0;
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

        for (int i = 0; i < GameLogic.getCurrentGame().obstacles.size(); i++){
            if ((GameLogic.getCurrentGame().obstacles.get(i).type == 5)&& position.intersects(GameLogic.getCurrentGame().obstacles.get(i).position)) {
                if(position.x%2 == 0)
                    GameLogic.getCurrentGame().coins.add(new Coin(GameLogic.getCurrentGame().obstacles.get(i).position.x + 60,
                            GameLogic.getCurrentGame().obstacles.get(i).position.y - 20, 40, 40, 0));
                else
                    GameLogic.getCurrentGame().stars.add(new Coin(GameLogic.getCurrentGame().obstacles.get(i).position.x + 60,
                            GameLogic.getCurrentGame().obstacles.get(i).position.y - 20, 40, 40, 0));
                GameLogic.getCurrentGame().obstacles.remove(i);
            }

            if ((GameLogic.getCurrentGame().obstacles.get(i).type == 10) && position.intersects(GameLogic.getCurrentGame().obstacles.get(i).position)) {//новое
                MarioGraphics.lives++;
                MarioGraphics.big=100;
                biging = true;
                GameLogic.getCurrentGame().obstacles.remove(i);
            }

            if ((GameLogic.getCurrentGame().obstacles.get(i).type == 12) && position.intersects(GameLogic.getCurrentGame().obstacles.get(i).position)) {//новое
                marioGraphics.zaradov=4;
                GameLogic.getCurrentGame().obstacles.remove(i);
            }

            if ((GameLogic.getCurrentGame().obstacles.get(i).type == 14) && position.intersects(GameLogic.getCurrentGame().obstacles.get(i).position)) {//новое
                MarioGraphics.lives--;
                MarioGraphics.big=0;
                biging = false  ;
                GameLogic.getCurrentGame().obstacles.remove(i);
            }


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


