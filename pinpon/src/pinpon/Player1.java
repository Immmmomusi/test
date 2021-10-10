package pinpon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Player1 implements Runnable {
    private Polygon p1;
    private int point;
    private boolean win = false;
    private static final int P1_UP_NUMBER = 0;
    private static final int P1_DOWN_NUMBER = 1;
    private static final int P1_LEFT_NUMBER = 2;
    private static final int P1_RIGHT_NUMBER = 3;
    boolean[] p1direction = { false, false, false, false };
    private int[] hitbox = new int[4];
    private boolean active = true;

    Player1(int point) {
        int[] xpos = { 0, 40, 40, 0 };
        int[] ypos = { 0, 0, 10, 10 };
        p1 = new Polygon(xpos, ypos, xpos.length);
        this.point = point;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fill(p1);
        g2.setColor(Color.black);
        g2.draw(p1);
    }

    public void playerMoveLeft() {
        if (p1.xpoints[0] > 10) {
            for (int i = 0; i < p1.xpoints.length; i++) {
                p1.xpoints[i] -= 5;
            }
        }
        setHitBox(p1.xpoints[0], p1.xpoints[1], p1.ypoints[0], p1.ypoints[3]);
    }

    public void playerMoveRight() {
        if (p1.xpoints[1] < 340) {
            for (int i = 0; i < p1.xpoints.length; i++) {
                p1.xpoints[i] += 5;
            }

        }
        setHitBox(p1.xpoints[0], p1.xpoints[1], p1.ypoints[0], p1.ypoints[3]);
    }

    public void playerMoveUp() {

        if (p1.ypoints[0] > 250) {
            for (int i = 0; i < p1.xpoints.length; i++) {
                p1.ypoints[i] -= 5;
            }
        }
        setHitBox(p1.xpoints[0], p1.xpoints[1], p1.ypoints[0], p1.ypoints[3]);
    }

    public void playerMoveDown() {
        if (p1.ypoints[2] < 440) {
            for (int i = 0; i < p1.ypoints.length; i++) {
                p1.ypoints[i] += 5;
            }
        }
        setHitBox(p1.xpoints[0], p1.xpoints[1], p1.ypoints[0], p1.ypoints[3]);
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
        for (int i = 0; i < p1.xpoints.length; i++) {
            p1.xpoints[i] += x;
            p1.ypoints[i] += y;
        }
        setHitBox(p1.xpoints[0], p1.xpoints[1], p1.ypoints[0], p1.ypoints[3]);
    }

    public boolean isWin() {
        return win;
    }

    public Polygon getPlayerpos() {
        return p1;
    }

    public void setPlayerpos(Polygon pos) {

        for (int i = 0; i < p1.xpoints.length; i++) {
            p1.xpoints[i] = pos.xpoints[i];
            p1.ypoints[i] = pos.ypoints[i];
        }
    }

    public void isDirection(int pos, boolean boo) {
        p1direction[pos] = boo;
    }

    @Override
    public void run() {
        try {
            while (active) {
                if (p1direction[P1_UP_NUMBER]) {
                    playerMoveUp();
                } else if (p1direction[P1_DOWN_NUMBER]) {
                    playerMoveDown();
                } else if (p1direction[P1_LEFT_NUMBER]) {
                    playerMoveLeft();
                } else if (p1direction[P1_RIGHT_NUMBER]) {
                    playerMoveRight();
                }
                Thread.sleep(20);
            }
        } catch (Exception e) {

        }
    }
    public void end() {
        active = false;
    }
}
