package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    static JFrame f = new JFrame();
    static company.MarioGraphics marioGraphics = new company.MarioGraphics();

    enum GameState { menu, game, pauseMenu, endOfGame };
    GameState gameState = GameState.menu;

    static Timer timer = new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            company.GameLogic.getCurrentGame().update();
            marioGraphics.repaint();
        }
    });

    public static void main(String[] args) {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(marioGraphics);
        f.setSize(1900,590);
        f.setBackground(Color.red);
        f.setVisible(true);
        f.setResizable(false);

        Main.f.addKeyListener(company.GameLogic.getCurrentGame().keyListener);
        timer.start();
    }
}
