package company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MarioGraphics extends JPanel {


    Image[] heroImage;
    Image[] obstacleImages;
    Image[] designImages;
    Image[] coinImages;
    Image[] mushroomImage;
    public static boolean lookLeft = false;
    public static int count = 0;
    public static int currentLevel = 1;

    public static int mainHeroHeight = 100;
    public static int mainHeroWidth = 64;
    public static int big = 0; //новое

    public static int coins = 0;
    public static int stars = 0;
    public static int lastCoins = 0;
    public static int lives = 3;
    public static int zaradov=0;//новое

    public static boolean livesTaken = false;
    public static boolean alive = true;

    public MarioGraphics() {
        obstacleImages = new Image[15];
        heroImage = new Image[8];
        coinImages = new Image[3];
        designImages = new Image[4];

        try {
            heroImage[0] = ImageIO.read(new File("images/hero1.png"));
            heroImage[1] = ImageIO.read(new File("images/hero2.png"));
            heroImage[2] = ImageIO.read(new File("images/hero3.png"));
            heroImage[3] = ImageIO.read(new File("images/hero4.png"));
            heroImage[4] = ImageIO.read(new File("images/hero5.png"));
            heroImage[5] = ImageIO.read(new File("images/hero6.png"));
            heroImage[6] = ImageIO.read(new File("images/hero7.png"));
            heroImage[7] = ImageIO.read(new File("images/hero8.png"));

            designImages[0] = ImageIO.read(new File("images/clouds.png"));
            designImages[1] = ImageIO.read(new File("images/bush.png"));
            designImages[2] = ImageIO.read(new File("images/castle.png"));
            designImages[3] = ImageIO.read(new File("images/lives.png"));

            obstacleImages[0] = ImageIO.read(new File("images/floor.png"));
            obstacleImages[1] = ImageIO.read(new File("images/wall.png"));
            obstacleImages[2] = ImageIO.read(new File("images/box.png"));
            obstacleImages[3] = ImageIO.read(new File("images/pipe.png"));
            obstacleImages[4] = ImageIO.read(new File("images/pipe2.png"));

            obstacleImages[5] = ImageIO.read(new File("images/mush1.png"));

            obstacleImages[6] = ImageIO.read(new File("images/wall2.png"));
            obstacleImages[7] = ImageIO.read(new File("images/floor2.png"));
            obstacleImages[8] = ImageIO.read(new File("images/pipe3.png"));

            obstacleImages[9] = ImageIO.read(new File("images/wall.png"));//новое
            obstacleImages[10] = ImageIO.read(new File("images/mush3.png"));//новое
            obstacleImages[11] = ImageIO.read(new File("images/flag2.png"));//новое
            obstacleImages[12] = ImageIO.read(new File("images/pods.png"));//новое
            obstacleImages[13] = ImageIO.read(new File("images/fire.png"));//новое
            obstacleImages[14] = ImageIO.read(new File("images/mush2.png"));//новое


            coinImages[0] = ImageIO.read(new File("images/coin.png"));
            coinImages[1] = ImageIO.read(new File("images/star.png"));
            coinImages[2] = ImageIO.read(new File("images/lives.png"));


            try {
                //create the font to use. Specify the size!
                Font marioFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Pixel Emulator.otf")).deriveFont(12f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                //register the font
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Pixel Emulator.otf")));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FontFormatException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("problems loading images");
            System.exit(1);
        }
    }


    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, 2000, 2000);
        //System.out.println("GameLogic.getCurrentGame().obst=" + GameLogic.getCurrentGame().obstacles == null);
        for (Obstacle obstacle : GameLogic.getCurrentGame().obstacles) {
            if (obstacle.visible) {
                g.drawImage(obstacleImages[obstacle.type],
                        obstacle.position.x + GameLogic.getCurrentGame().background.x,
                        obstacle.position.y,
                        obstacle.position.x + GameLogic.getCurrentGame().background.x + obstacle.position.width,
                        obstacle.position.y + obstacle.position.height,
                        0, 0,
                        obstacleImages[obstacle.type].getWidth(null),
                        obstacleImages[obstacle.type].getHeight(null),
                        null
                );
            }
        }

        for (DesignStuff designStuff : GameLogic.getCurrentGame().others) {
            g.drawImage(designImages[designStuff.type],
                    designStuff.position.x + GameLogic.getCurrentGame().background.x,
                    designStuff.position.y,
                    designStuff.position.x + GameLogic.getCurrentGame().background.x + designStuff.position.width,
                    designStuff.position.y + designStuff.position.height,
                    0, 0,
                    designImages[designStuff.type].getWidth(null),
                    designImages[designStuff.type].getHeight(null),
                    null
            );
        }

        if (GameLogic.getCurrentGame().mainHero.dx < 0) lookLeft = true;
        if (GameLogic.getCurrentGame().mainHero.dx > 0) lookLeft = false;

        if (!lookLeft) {
            if (GameLogic.getCurrentGame().mainHero.dx != 0) count++;
            g.drawImage(heroImage[count % 8],
                    GameLogic.getCurrentGame().mainHero.position.x + GameLogic.getCurrentGame().background.x,
                    GameLogic.getCurrentGame().mainHero.position.y,
                    GameLogic.getCurrentGame().mainHero.position.x + GameLogic.getCurrentGame().background.x + GameLogic.getCurrentGame().mainHero.position.width,
                    GameLogic.getCurrentGame().mainHero.position.y + GameLogic.getCurrentGame().mainHero.position.height,
                    0, 0,
                    64,
                    100, null
            );
        }
        if (lookLeft) {
            if (GameLogic.getCurrentGame().mainHero.dx != 0) count++;
            g.drawImage(heroImage[count % 8],
                    GameLogic.getCurrentGame().mainHero.position.x + GameLogic.getCurrentGame().background.x + GameLogic.getCurrentGame().mainHero.position.width,
                    GameLogic.getCurrentGame().mainHero.position.y,
                    GameLogic.getCurrentGame().mainHero.position.x + GameLogic.getCurrentGame().background.x,
                    GameLogic.getCurrentGame().mainHero.position.y + GameLogic.getCurrentGame().mainHero.position.height,
                    0, 0,
                    mainHeroWidth,
                    mainHeroHeight, null
            );
        }

        for (Coin coin : GameLogic.getCurrentGame().coins) {
            g.drawImage(coinImages[0],
                    coin.position.x + GameLogic.getCurrentGame().background.x,
                    coin.position.y,
                    coin.position.x + GameLogic.getCurrentGame().background.x + coin.position.width,
                    coin.position.y + coin.position.height,
                    0, 0,
                    coinImages[0].getWidth(null),
                    coinImages[0].getHeight(null),
                    null
            );
        }

        for (Coin star : GameLogic.getCurrentGame().stars) {
            g.drawImage(coinImages[1],
                    star.position.x + GameLogic.getCurrentGame().background.x,
                    star.position.y,
                    star.position.x + GameLogic.getCurrentGame().background.x + star.position.width,
                    star.position.y + star.position.height,
                    0, 0,
                    coinImages[1].getWidth(null),
                    coinImages[1].getHeight(null),
                    null
            );
        }

        for (Coin lives : GameLogic.getCurrentGame().lives) {
            g.drawImage(coinImages[2],
                    lives.position.x + GameLogic.getCurrentGame().background.x,
                    lives.position.y,
                    lives.position.x + GameLogic.getCurrentGame().background.x + lives.position.width,
                    lives.position.y + lives.position.height,
                    0, 0,
                    coinImages[2].getWidth(null),
                    coinImages[2].getHeight(null),
                    null
            );
        }

        g.setColor(Color.red);
        g.setFont(new Font("Pixel Emulator", Font.PLAIN, 32));
        g.drawString("Level: " + currentLevel + "", 100, 100);
        g.drawString("Max coins: " + lastCoins + "", 100, 60);
        g.drawString("Fire: " + zaradov + "", 100, 140); //новое

        g.drawImage(designImages[3],
                900,
                25,
                940,
                65,
                0, 0,
                designImages[3].getWidth(null),
                designImages[3].getHeight(null),
                null
        );

        g.drawString("" + lives + "", 950, 55);
        g.drawString("Coins: " + coins + "", 1600, 60);
        g.drawString("Stars: " + stars + "", 1600, 100);

        String levelCompleted = GameLogic.getCurrentGame().mainHero.levelCompleted;

        if (levelCompleted != "") {
            g.setColor(Color.red);
            g.setFont(new Font("Pixel Emulator", Font.PLAIN, 60));
            if (levelCompleted != "GAME OVER!") {
                g.drawString(levelCompleted, 500, 300);
                g.drawString("Press R to continue!", 500, 380);
            } else {
                if(lives <= 0){
                    g.drawString(levelCompleted, 700, 300);
                    g.drawString("Press R to start again!", 420, 380);
                }else if(lives == 1){
                    g.drawString("You have " + lives + " life", 570, 300);
                    g.drawString("Don't waste it!", 580, 380);
                }else {
                    g.drawString("You have " + lives + " lives", 620, 300);
                    g.drawString("Press R to restart!", 550, 380);
                }
            }
        }


    }
}
