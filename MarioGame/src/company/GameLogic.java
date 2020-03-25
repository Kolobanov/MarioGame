package company;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameLogic {
    ArrayList<Obstacle> obstacles = new ArrayList<>();
    ArrayList<DesignStuff> others = new ArrayList<>();
    ArrayList<Coin> coins = new ArrayList<>();
    ArrayList<Coin> stars = new ArrayList<>();
    ArrayList<Coin> lives = new ArrayList<>();

    ArrayList<GameObject> gameObjects = new ArrayList<>();

    Point background = new Point(0,0);
    Random random = new Random();
    MainHero mainHero = new company.MainHero();

    static GameLogic gameLogic;

    public GameLogic() {

       //gameObjects.add(mainHero);

        if ((mainHero.position.x==0) && (mainHero.position.y==0)){mainHero.position.setBounds(650,200,40,64);}


        MarioGraphics.alive = true; // sets mainhero alive

        //новое сделали->
        String[] map=null;
        String ffile = null;
        if ( MarioGraphics.currentLevel == 1) ffile = "1.txt";
        if ( MarioGraphics.currentLevel == 2) ffile = "2.txt";
        if ( MarioGraphics.currentLevel == 3) ffile = "3.txt";

        Path wiki_path = Paths.get("levels/", ffile);
        try {
            String[] lines = Files.readAllLines(wiki_path, Charset.forName("UTF-8")).toArray(new String[0]);//загрузка из файла
            map = lines;
        } catch (IOException e) {
            e.printStackTrace();
        }

       //новое сделали-<


        for(int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[0].length(); i++) {
                if (map[j].charAt(i) == 'm') obstacles.add(new Obstacle(i*40, j*40, 40, 40, 5));  // star coins
                if (map[j].charAt(i) == 's') stars.add(new Coin(i*40, j*40, 40, 40, 1));  // star coins
                if (map[j].charAt(i) == 'h' && !MarioGraphics.livesTaken) lives.add(new Coin(i*40, j*40, 40, 40, 2));  // lives
                if (map[j].charAt(i) == '1') obstacles.add(new Obstacle(i*40, j*40, 40, 40, 0)); // floor
                 if (map[j].charAt(i) == 'e') obstacles.add(new Obstacle(i*40, j*40, 40, 40, 7)); // floor
                if (map[j].charAt(i) == '2') others.add(new DesignStuff(i*40, j*40, 50, 50, 0)); // clouds
                if (map[j].charAt(i) == '3') obstacles.add(new Obstacle(i*40, j*40, 40, 40, 1)); // wall
                 if (map[j].charAt(i) == 'w') obstacles.add(new Obstacle(i*40, j*40, 40, 40, 6)); // wall
                if (map[j].charAt(i) == '4') obstacles.add(new Obstacle(i*40, j*40, 40, 40, 2)); //box
                if (map[j].charAt(i) == '5') obstacles.add(new Obstacle(i*40, j*40, 60, 60, 3)); // pipe head
                if (map[j].charAt(i) == '6') others.add(new DesignStuff(i*40, j*40, 60, 60, 1)); //bush
                if (map[j].charAt(i) == '7') coins.add(new Coin(i*40, j*40, 40, 40, 0)); // coins
                if (map[j].charAt(i) == '8') obstacles.add(new Obstacle(i*40, j*40, 60, 60, 4)); // pipe base
                 if (map[j].charAt(i) == 'q') obstacles.add(new Obstacle(i*40, j*40, 60, 60, 8)); // pipe base
                if (map[j].charAt(i) == '9') others.add(new DesignStuff(i*40, j*40, 400, 400, 2)); //castle
            }
        }
    }

    public void playSound() {
        try {
            URL url = (new File("sounds/" + "coinSound.wav")).toURI().toURL();
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update() {
        System.out.println("X=" +  mainHero.position.x+" Y=" +  mainHero.position.y);
        mainHero.update(obstacles, coins, stars);
        mainHero.collision();

        if (mainHero.position.x < 100 - background.x)
            background.x = 100 - mainHero.position.x;

        if (mainHero.position.x > 600 - background.x)
            background.x = 600 - mainHero.position.x;
    }

    public KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key pressed: " + e.getKeyCode());

            if ((e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_S)) {
                mainHero.dy = +50;
                if (((mainHero.position.x>=3120 && mainHero.position.x<=3154)&& mainHero.position.y==254) && MarioGraphics.currentLevel==1){
                    MarioGraphics.currentLevel=3;
                    GameLogic.startNewGame();
                    Main.f.addKeyListener(GameLogic.getCurrentGame().keyListener);
                }
            }


            if ((e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_W) && !mainHero.jumping) {
                mainHero.dy = -50;
                mainHero.jumping = true;
            }
            if (e.getKeyCode() == e.VK_LEFT || e.getKeyCode() == e.VK_A) {
                mainHero.dx = -19;
            }
            if (e.getKeyCode() == e.VK_RIGHT || e.getKeyCode() == e.VK_D) {
                mainHero.dx = 19;

                if ((mainHero.position.x==1315 && mainHero.position.y==454) && MarioGraphics.currentLevel==3){
                    MarioGraphics.currentLevel=1;
                    GameLogic.startNewGame();
                    Main.f.addKeyListener(GameLogic.getCurrentGame().keyListener);
                }
            }

            if (e.getKeyCode() == e.VK_R) {
                if(company.MarioGraphics.lives > 0) {
                    GameLogic.startNewGame();
                    Main.f.addKeyListener(GameLogic.getCurrentGame().keyListener);
                    MarioGraphics.coins = 0;
                    MarioGraphics.stars = 0;
                    MainHero.levelChanged = false;
                } if(MarioGraphics.lives <= 0){
                    MarioGraphics.coins = 0;
                    MarioGraphics.lastCoins = 0;
                    MarioGraphics.stars = 0;
                    MarioGraphics.currentLevel = 1;
                    MarioGraphics.lives = 3;
                    MainHero.levelChanged = false;

                    GameLogic.startNewGame();
                    Main.f.addKeyListener(GameLogic.getCurrentGame().keyListener);
                }
            }
            if (e.getKeyCode() == e.VK_ESCAPE) {
                MarioGraphics.coins = 0;
                MarioGraphics.lastCoins = 0;
                MarioGraphics.stars = 0;
                MarioGraphics.currentLevel = 1;
                MarioGraphics.lives =3;
                MarioGraphics.livesTaken = false;
                MainHero.levelChanged = false;

                GameLogic.startNewGame();
                Main.f.addKeyListener(GameLogic.getCurrentGame().keyListener);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == e.VK_LEFT || code == e.VK_A || code == e.VK_RIGHT || code == e.VK_D) {
                mainHero.dx = 0;
            }
        }
    };

    public static GameLogic getCurrentGame() {
        if (gameLogic == null)
            gameLogic = new GameLogic();
        return gameLogic;
    }
    public static void startNewGame() {
        gameLogic = new GameLogic();
    }



}
