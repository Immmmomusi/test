package pinpon;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PinponPaint extends Frame implements Runnable, KeyListener {
    Image offimg;
    Graphics2D offimg_g;
    Player1 p1 = new Player1(0);
    Player2 p2 = new Player2(0);
    Ball ball = new Ball();
    public static final int P1_UP_NUMBER = 0;
    public static final int P1_DOWN_NUMBER = 1;
    public static final int P1_LEFT_NUMBER = 2;
    public static final int P1_RIGHT_NUMBER = 3;
    public static final int P2_UP_NUMBER = 0;
    public static final int P2_DOWN_NUMBER = 1;
    public static final int P2_LEFT_NUMBER = 2;
    public static final int P2_RIGHT_NUMBER = 3;
    public static final int MAX_WITDH = 340;
    public static final int MIN_WITDH = 10;
    public static final int MAX_HIGHT = 50;
    public static final int MIN_HIGHT = 440;
    private String result = "";
    private String point1 = "0";
    private String point2 = "0";
    boolean[] p1direction = { false, false, false, false };
    boolean[] p2direction = { false, false, false, false };
    boolean start = false;
    boolean end = false;

    PinponPaint() {
        addWindowListener(new MyWindowListener());
        setTitle("ぴんぽん");
        setSize(350, 500);
        setBounds(300, 100, 350, 500);
        setVisible(true);
        setResizable(false);
        setBackground(Color.white);
        setFocusable(true);
        addKeyListener(this);
        offimg = createImage(350, 500);
        offimg_g = (Graphics2D) offimg.getGraphics();
    }

    @Override
    public void run() {
        try {
            p1.setPosision(140, 280);
            p2.setPosision(140, 200);
            ball.setPosision(200, 140);
            while (!p1.isWin() && !p2.isWin()) {
                if (!ball.getGoal(1) && !ball.getGoal(2)) {
                    ball.moveBall(p1, p2);
                }
                if (ball.getGoal(1)) {
                    ball.setPosision(200, 140);
                    p1.setPoint();
                    ball.setGoal();
                }
                if (ball.getGoal(2)) {
                    ball.setPosision(200, 140);
                    p2.setPoint();
                    ball.setGoal();
                }
                Thread.sleep(30);
                repaint();
            }
            if (p1.isWin()) {
                result = "１の勝ち";
                repaint();
            } else if (p2.isWin()) {
                result = "２の勝ち";
                repaint();
            }
            p1.end();
            p2.end();
        } catch (Exception e) {
        }
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        offimg_g.clearRect(0, 0, 350, 500);
        offimg_g.setColor(Color.white);
        offimg_g.fillRect(0, 0, 350, 500);
        offimg_g.setColor(Color.red);
        offimg_g.drawLine(0, 50, 100, 50);
        offimg_g.drawLine(350, 50, 250, 50);
        offimg_g.drawLine(0, 440, 100, 440);
        offimg_g.drawLine(350, 440, 250, 440);
        offimg_g.setColor(Color.BLUE);
        offimg_g.drawLine(0, 250, 350, 250);
        offimg_g.drawString(result, 140, 200);
        point1 = Integer.toString(p1.getPoint());
        point2 = Integer.toString(p2.getPoint());
        offimg_g.drawString(point1,10,40);
        offimg_g.drawString("-", 22, 40);
        offimg_g.drawString(point2, 30, 40);
        p1.draw(offimg_g);
        p2.draw(offimg_g);
        ball.draw(offimg_g);
        if (ball.getGoal(1)) {
            p1.setPoint();
            ball.setGoal();
        }
        if (ball.getGoal(2)) {
            p2.setPoint();
            ball.setGoal();
        }
        g.drawImage(offimg, 0, 0, this);
    }

    public class MyWindowListener extends WindowAdapter {
        public void windowClosing(WindowEvent w) {
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO 自動生成されたメソッド・スタブ
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            p1.isDirection(P1_UP_NUMBER, true);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            p1.isDirection(P1_DOWN_NUMBER, true);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            p1.isDirection(P1_LEFT_NUMBER, true);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            p1.isDirection(P1_RIGHT_NUMBER, true);
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            p2.isDirection(P2_UP_NUMBER, true);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            p2.isDirection(P2_DOWN_NUMBER, true);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            p2.isDirection(P2_RIGHT_NUMBER, true);
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            p2.isDirection(P2_LEFT_NUMBER, true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            p1.isDirection(P1_UP_NUMBER, false);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            p1.isDirection(P1_DOWN_NUMBER, false);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            p1.isDirection(P1_LEFT_NUMBER, false);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            p1.isDirection(P1_RIGHT_NUMBER, false);
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            p2.isDirection(P2_UP_NUMBER, false);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            p2.isDirection(P2_DOWN_NUMBER, false);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            p2.isDirection(P2_RIGHT_NUMBER, false);
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            p2.isDirection(P2_LEFT_NUMBER, false);
        }
    }
}
