import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.Timer;
import java.awt.Font;

public class Pong {

    static int SCREEN_WIDTH = 1000;
    static int SCREEN_HEIGHT = 700;
    static int DELAY = 2;
    static Color[] COLORS = {
        new Color(0x001020),
        new Color(0xDDDDDD),
        new Color(0xE00000),
        new Color(0x0000E0),
        new Color(0xBA2ABA)
    };
    
    static JFrame f;

    public static void main(String[] args) {
        
        f = new JFrame();
        f.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game_logic logic_runner = new Game_logic(SCREEN_WIDTH, SCREEN_HEIGHT);
        Thread logicThread = new Thread(logic_runner);
        Game_gui gui = new Game_gui(logic_runner, COLORS, SCREEN_WIDTH);
        logicThread.start(); 
        f.setLocationRelativeTo(null);
        f.add(gui);
        f.setVisible(true);
    }
}

class Game_logic implements Runnable{

    public boolean gamerun = false;

    private double ball_x;
    private double ball_y;
    private double ball_move_x;
    private double ball_move_y;

    private int platform_pos_A;
    private int platform_pos_B;

    private Random rand = new Random();

    byte win = 0;
    boolean[] pressed_keys = new boolean[4];

    Game_logic(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.ball_x = SCREEN_WIDTH / 2; this.ball_y = SCREEN_HEIGHT / 2;
        this.ball_move_x = 0.70; this.ball_move_y = rand.nextDouble(0.5) * 2;
        this.platform_pos_A = SCREEN_HEIGHT / 2; this.platform_pos_B = SCREEN_HEIGHT / 2;
        Pong.f.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                gamerun = true;
                switch (e.getKeyCode()) {
                    case 87:pressed_keys[0] = true;break;
                    case 83:pressed_keys[1] = true;break;
                    case 38:pressed_keys[2] = true;break;
                    case 40:pressed_keys[3] = true;break;
                    default:break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 87:pressed_keys[0] = false;break;
                    case 83:pressed_keys[1] = false;break;
                    case 38:pressed_keys[2] = false;break;
                    case 40:pressed_keys[3] = false;break;
                    default:break;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
               
            }
            
        });
    }

    int getPlatformApos(){
        return this.platform_pos_A;
    }
    int getPlatformBpos(){
        return this.platform_pos_B;
    }
    int[] getBallPos(){
        return new int[]{(int) this.ball_x, (int) this.ball_y};
    }

                void move(){
        //platforms
        if(pressed_keys[0] == true && platform_pos_A > 7){platform_pos_A -= 2;}
        if(pressed_keys[1] == true && platform_pos_A < Pong.SCREEN_HEIGHT - 128){platform_pos_A += 1;}
        if(pressed_keys[2] == true && platform_pos_B > 7){platform_pos_B -= 2;}
        if(pressed_keys[3] == true && platform_pos_B < Pong.SCREEN_HEIGHT - 128){platform_pos_B += 1;}
        //ball
        this.ball_x += this.ball_move_x; this.ball_y += this.ball_move_y;
        if(this.ball_y < 0 || this.ball_y > Pong.SCREEN_HEIGHT - 50){this.ball_move_y -= (ball_move_y * 2);}
        //win and lose system
        if(ball_x < -5){gamerun = false; win = 1;}
        if(ball_x > Pong.SCREEN_WIDTH - 20){gamerun = false; win = 2;}
        //platform bouncing
        if(ball_x < 25){
            if(ball_y > platform_pos_A && ball_y < platform_pos_A + 90 && ball_move_x < 0){ball_move_x -= (ball_move_x * 2); ball_move_y = rand.nextDouble(2)-1;}
        }
        if(ball_x > Pong.SCREEN_WIDTH - 50){
            if(ball_y > platform_pos_B && ball_y < platform_pos_B + 90 && ball_move_x > 0){ball_move_x -= (ball_move_x * 2); ball_move_y = rand.nextDouble(2)-1;}
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(Pong.DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(gamerun){move();}
        }
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
        Timer timer = new Timer(1, e -> repaint());
        timer.start();
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

        Font font = new Font(null, Font.PLAIN, 24);
        g.setFont(font);
        g.setColor(COLORS[4]);

        if(logic.win == 1){
        g.drawString("blue win", 40, 50);
        }
        
        if(logic.win == 2){
        g.drawString("red win", 40, 50);
        }
    }

}