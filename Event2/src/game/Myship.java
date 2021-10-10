package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Myship {
    private Polygon s;
    private int[] hitbox = new int[4];
    private boolean dead = false;

    Myship() {
        int[] xpos = { 0, 10, 10, 20, 20, 30, 30, 0 };
        int[] ypos = { 10, 10, 3, 3, 10, 10, 20, 20 };
        s = new Polygon(xpos, ypos, xpos.length);
    }

    Myship(int x, int y) {
        this();
        for (int i = 0; i < s.xpoints.length; i++) {
            s.xpoints[i] += x;
            s.ypoints[i] += y;
        }
        hitbox[0] = s.xpoints[0];
        hitbox[1] = s.xpoints[5];
        hitbox[2] = s.ypoints[2];
        hitbox[3] = s.ypoints[6];
    }

    public int getShipXPosition() {
        return s.xpoints[3] - 5;
    }

    public int getShipYPosition() {
        return s.ypoints[0];
    }

    public int getHitBox(int x) {
        if (-1 < x || x < hitbox.length) {
            return hitbox[x];
        }
        return 0;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fill(s);
        g2.setColor(Color.white);
        g2.draw(s);
    }

    public void shipMoveLeft() {
        if (s.xpoints[0] > 10) {
            for (int i = 0; i < s.xpoints.length; i++) {
                s.xpoints[i] -= 3;
            }
            hitbox[0] = s.xpoints[0];
            hitbox[1] = s.xpoints[5];
        }
    }

    public void shipMoveRight() {
        if (s.xpoints[6] < 290) {
            for (int i = 0; i < s.xpoints.length; i++) {
                s.xpoints[i] += 3;
            }
            hitbox[0] = s.xpoints[0];
            hitbox[1] = s.xpoints[5];
        }
    }

    public void Kill() {
        this.dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public void shipReverse() {
        dead = false;
    }

}
