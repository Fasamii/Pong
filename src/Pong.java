import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Graphics;

public class Pong {

    static int SCREEN_WIDTH = 600;
    static int SCREEN_HEIGHT = 500;
    static int SIZE = 40;
    static int DELAY = 100;

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.add(new Game_gui(new Game_logic(SCREEN_WIDTH, SCREEN_HEIGHT, SIZE))); 
        f.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        f.setVisible(true);
    }
}

class Game_logic {

    private int ball_x;
    private int ball_y;
    private int ball_last_x;
    private int ball_last_y;

    private int platform_pos_A;
    private int platform_pos_B;

    Game_logic(int SCREEN_WIDTH, int SCREEN_HEIGHT, int SIZE) {
        this.ball_x = SCREEN_WIDTH / 2; this.ball_y = SCREEN_HEIGHT / 2;
        this.ball_last_x = this.ball_x; this.ball_last_y = this.ball_y;
        this.platform_pos_A = SCREEN_HEIGHT / 2; this.platform_pos_B = SCREEN_HEIGHT / 2;
    }

}

class Game_gui extends JLabel {

    Game_gui(Game_logic logic) {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(10, 10, 50, 40);
    }

}