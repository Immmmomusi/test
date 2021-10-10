package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Enemy {
    private Polygon[] e = new Polygon[3];
    public static final int E1 = 0;
    public static final int E2 = 1;
    public static final int E3 = 2;
    public int speed = 1;
    public static final int[][] HIGHER_POINT = {
            { 2, 4 },
            { 5, 6 },
            { 6, 7 }
    };
    private boolean[] dead = { false, false, false };
    private int[] life = new int[e.length];
    private boolean[] hitcheck = { false, false, false };
    private int[][] hitbox = new int[3][4];
    private boolean[] right_end = { false, false, false };

    Enemy() {
        int[] e1_xpos = { 0, 10, 20, 16, 16, 12, 12, 8, 8, 4, 4 };
        int[] e1_ypos = { 10, 0, 10, 10, 20, 20, 10, 10, 20, 20, 10 };
        int[] e2_xpos = { 0, 10, 10, 15, 15, 25, 25, 0 };
        int[] e2_ypos = { 0, 0, 20, 20, 0, 0, 30, 30 };
        int[] e3_xpos = { 0, 10, 10, 20, 40, 40, 50, 50, 0 };
        int[] e3_ypos = { 10, 10, 0, 10, 0, 10, 10, 20, 20 };
        e[E1] = new Polygon(e1_xpos, e1_ypos, e1_xpos.length);
        e[E2] = new Polygon(e2_xpos, e2_ypos, e2_xpos.length);
        e[E3] = new Polygon(e3_xpos, e3_ypos, e3_xpos.length);
        for (int i = 0; i < life.length; i++) {
            life[i] = 3;
        }
    }

    Enemy(int x, int y) {
        this();
        for (int i = 0; i < e.length; i++) {
            for (int j = 0; j < e[i].xpoints.length; j++) {
                e[i].xpoints[j] += x;
                e[i].ypoints[j] += y;
            }
            y = y + 40;
            x = x + 20;
        }
    }

    public void draw(Graphics2D g2, int type) {
        g2.setColor(Color.white);
        g2.fill(e[type]);
        g2.setColor(Color.white);
        g2.draw(e[type]);

    }

    public void enemyMove(int type) {
        if (!right_end[type]) {
            for (int i = 0; i < e[type].xpoints.length; i++) {
                e[type].xpoints[i] += speed;
                if (e[type].xpoints[HIGHER_POINT[type][0]] > 290) {
                    right_end[type] = true;
                }
            }

        } else {
            for (int i = 0; i < e[type].xpoints.length; i++) {
                e[type].xpoints[i] -= speed;
                if (e[type].xpoints[0] < 10) {
                    right_end[type] = false;
                }
            }

        }
        hitbox[type][0] = e[type].xpoints[0];
        hitbox[type][1] = e[type].xpoints[HIGHER_POINT[type][0]];
        hitbox[type][2] = e[type].ypoints[0];
        hitbox[type][3] = e[type].ypoints[HIGHER_POINT[type][1]];
    }

    public int getEnemyXPosition(int type) {
        return e[type].xpoints[0] + 5;
    }

    public int getEnemyYPosition(int type) {
        return e[type].ypoints[0];
    }

    public int getHitBox(int type, int pos) {
        return hitbox[type][pos];
    }

    public int getEnemyLength() {
        return e.length;
    }

    public void setHit(boolean check, int type) {
        hitcheck[type] = check;
    }

    public boolean getHit(int type) {
        return hitcheck[type];
    }

    public void setLife() {
        for (int i = 0; i < life.length; i++) {
            life[i] = 3;
        }
    }
    public void setSpeed(int i) {
        speed = i;
    }

    public boolean Kill(int type) {
        life[type]--;
        if (life[type] == 0) {
            dead[type] = true;
            return true;
        }
        return false;
    }

    public boolean isDead(int type) {
        return dead[type];
    }

    public void Reverse() {
        for (int i = 0; i < dead.length; i++) {
            dead[i] = false;
        }
    }
}
