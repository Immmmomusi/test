package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class EnemyBullet {
	private Polygon b;

	EnemyBullet() {
		int[] xpos = { 0, 0, 0, 0 };
		int[] ypos = { 0, 0, 10, 10 };
		b = new Polygon(xpos, ypos, xpos.length);
	}

	EnemyBullet(int x, int y) {
		this();
		for (int i = 0; i < b.xpoints.length; i++) {
			b.xpoints[i] += x;
			b.ypoints[i] += y;
		}
	}
	
	public void draw(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.fill(b);
		g2.setColor(Color.RED);
		g2.draw(b);
	}
	
	public void shootBullet(Myship ship) {
		for (int i = 0; i < b.ypoints.length; i++) {
			b.ypoints[i] += 3;
			if (!ship.isDead()) {
				isHit(ship);
			}
		}
	}

	public void isHit(Myship ship) {
		if (ship.getHitBox(0) <= b.xpoints[0] && b.xpoints[0] <= ship.getHitBox(1)
				&& ship.getHitBox(2) <= b.ypoints[3] && b.ypoints[3] <= ship.getHitBox(3)) {
			System.out.println("あたったよ");
			ship.Kill();
		}
	}
	
	public int getBulletX() {
		return b.xpoints[0];
	}

	public int getBulletY() {
		return b.ypoints[0];
	}
	
}
