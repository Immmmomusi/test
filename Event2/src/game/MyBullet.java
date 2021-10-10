package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class MyBullet {
    private Polygon b;

    MyBullet() {
        int[] xpos = { 1, 1, 1, 1 };
        int[] ypos = { 1, 1, 10, 10 };
        b = new Polygon(xpos, ypos, xpos.length);
    }

    MyBullet(int x, int y) {
        this();
        for (int i = 0; i < b.xpoints.length; i++) {
            b.xpoints[i] += x;
            b.ypoints[i] += y;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setColor(Color.white);
        g2.draw(b);
    }

    public void shootBullet(Enemy enemy) {
        for (int i = 0; i < b.ypoints.length; i++) {
            b.ypoints[i] -= 5;
            if(isHit(enemy)) {
                break;
            }
        }
   }

    public boolean isHit(Enemy enemy) {
        for (int i = 0; i < enemy.getEnemyLength(); i++) {
            if (!enemy.isDead(i)) {
                if (enemy.getHitBox(i, 0) <= b.xpoints[0] && b.xpoints[0] <= enemy.getHitBox(i, 1)
                        && enemy.getHitBox(i, 2) <= b.ypoints[0] && b.ypoints[3] <= enemy.getHitBox(i, 3)) {
                    System.out.println(i +  "あたり");
                    enemy.Kill(i);
                    enemy.setHit(true, i);
                    return true;
                }
            }
        }
        return false;
    }

    public int getBulletX() {
        return b.xpoints[0];
    }

    public int getBulletY() {
        return b.ypoints[0];
    }
}
