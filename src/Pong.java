import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Graphics;

public class Pong {

    static int SCREEN_WIDTH = 600;
    static int SCREEN_HEIGHT = 500;
    static int DELAY = 100;
    static Color[] COLORS = {
        new Color(0x001020),
        new Color(0xDDDDDD),
        new Color(0xE00000),
        new Color(0x0000E0),
        new Color(0xAA2AAA)
    };

    public static void main(String[] args) {
        
        JFrame f = new JFrame();
        f.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new Game_gui(new Game_logic(SCREEN_WIDTH, SCREEN_HEIGHT), COLORS, SCREEN_WIDTH)); 
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

    Game_logic(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.ball_x = SCREEN_WIDTH / 2; this.ball_y = SCREEN_HEIGHT / 2;
        this.ball_last_x = this.ball_x; this.ball_last_y = this.ball_y;
        this.platform_pos_A = SCREEN_HEIGHT / 2; this.platform_pos_B = SCREEN_HEIGHT / 2;
    }

    int getPlatformApos(){
        return this.platform_pos_A;
    }
    int getPlatformBpos(){
        return this.platform_pos_B;
    }
    int[] getBallPos(){
        int[] temp = {this.ball_x, this.ball_y};
        return temp;
    }
}

class Game_gui extends JLabel {

    private Color[] COLORS;
    private Game_logic logic;
    private int WIDTH;

    Game_gui(Game_logic logic, Color[] COLORS, int WIDTH) {
        this.COLORS = COLORS; this.logic = logic;
        this.WIDTH = WIDTH;
        this.setOpaque(true);
        this.setBackground(COLORS[0]);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        g.setColor(COLORS[2]);
        g.drawRect(7, logic.getPlatformApos(), 20, 90);
        g.setColor(COLORS[3]);
        g.drawRect(WIDTH - 30, logic.getPlatformBpos(), 20, 90);
        g.setColor(COLORS[4]);
        g.drawOval(logic.getBallPos()[0], logic.getBallPos()[1], 20, 20);
    }

}