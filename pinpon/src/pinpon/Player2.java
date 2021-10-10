package pinpon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Player2 implements Runnable {
    private Polygon p2;
    private int point;
    private boolean win = false;
    boolean[] p2direction = { false, false, false, false };
    private static final int P2_UP_NUMBER = 0;
    private static final int P2_DOWN_NUMBER = 1;
    private static final int P2_LEFT_NUMBER = 2;
    private static final int P2_RIGHT_NUMBER = 3;
    private int[] hitbox = new int[4];
    private boolean active = true;

    Player2(int point) {
        int[] xpos = { 0, 40, 40, 0 };
        int[] ypos = { 0, 0, 10, 10 };
        p2 = new Polygon(xpos, ypos, xpos.length);
        this.point = point;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fill(p2);
        g2.setColor(Color.black);
        g2.draw(p2);
    }

    public void playerMoveLeft() {
        if (p2.xpoints[0] > 10) {
            for (int i = 0; i < p2.xpoints.length; i++) {
                p2.xpoints[i] -= 5;
            }
        }
        setHitBox(p2.xpoints[0], p2.xpoints[1], p2.ypoints[0], p2.ypoints[3]);
    }

    public void playerMoveRight() {
        if (p2.xpoints[1] < 340) {
            for (int i = 0; i < p2.xpoints.length; i++) {
                p2.xpoints[i] += 5;
            }
        }
        setHitBox(p2.xpoints[0], p2.xpoints[1], p2.ypoints[0], p2.ypoints[3]);
    }

    public void playerMoveUp() {
        if (p2.ypoints[0] > 50) {
            for (int i = 0; i < p2.xpoints.length; i++) {
                p2.ypoints[i] -= 5;
            }
        }
        setHitBox(p2.xpoints[0], p2.xpoints[1], p2.ypoints[0], p2.ypoints[3]);
    }

    public void playerMoveDown() {
        if (p2.ypoints[2] < 250) {
            for (int i = 0; i < p2.ypoints.length; i++) {
                p2.ypoints[i] += 5;
            }
        }
        setHitBox(p2.xpoints[0], p2.xpoints[1], p2.ypoints[0], p2.ypoints[3]);
    }

    public void setHitBox(int x_low, int x_high, int y_low, int y_high) {
        hitbox[0] = x_low;
        hitbox[1] = x_high;
        hitbox[2] = y_low;
        hitbox[3] = y_high;
    }

    public int getHitBox(int check) {
        return hitbox[check];
    }

    public int getPoint() {
        return point;
    }

    public void setPoint() {
        point++;
        if (point == 5) {
            win = true;
        }
    }

    public void setPosision(int x, int y) {
        for (int i = 0; i < p2.xpoints.length; i++) {
            p2.xpoints[i] += x;
            p2.ypoints[i] += y;
        }
        setHitBox(p2.xpoints[0], p2.xpoints[1], p2.ypoints[0], p2.ypoints[3]);
    }

    public boolean isWin() {
        return win;
    }

    public void isDirection(int pos, boolean boo) {
        p2direction[pos] = boo;
    }

    @Override
    public void run() {
        try {
            while (active) {
                if (p2direction[P2_UP_NUMBER]) {
                    playerMoveUp();
                } else if (p2direction[P2_DOWN_NUMBER]) {
                    playerMoveDown();
                } else if (p2direction[P2_LEFT_NUMBER]) {
                    playerMoveLeft();
                } else if (p2direction[P2_RIGHT_NUMBER]) {
                    playerMoveRight();
                }
                Thread.sleep(30);
            }
        } catch (Exception e) {

        }
    }

    public void end() {
        active = false;
    }

}
