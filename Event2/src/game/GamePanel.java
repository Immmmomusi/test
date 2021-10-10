package game;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GamePanel extends Frame implements KeyListener, Runnable {
    private Myship ship = new Myship(140, 250);
    private Enemy enemy = new Enemy(50, 50);
    private EnemyBullet[] enebul = new EnemyBullet[3];
    private MyBullet mybul;
    private boolean[] hitcheck = { false, false, false };//あたったかどうかの判定
    private boolean left = false;
    private boolean right = false;
    private boolean shoot = false;
    private boolean dead = false;
    private String end; //"ゲームオーバー"と表示するための変数
    private boolean StartGame = false; 
    private int score = 0;
    private int level = 0;
    int count = 0;
    Image offimg;
    Graphics2D offimg_g;

    GamePanel() {
        addWindowListener(new MyWindowListener());
        setTitle("game");
        setSize(500, 300);
        setBounds(300, 100, 500, 300);
        setVisible(true);
        setResizable(false);
        setBackground(Color.black);
        addKeyListener(this);
        offimg = createImage(500, 300);
        offimg_g = (Graphics2D) offimg.getGraphics();
    }

    public void run() {
        while (true) {
            end = "";
            resetGame();
            repaint();
            while (!dead) {
                try {
                    if (left) {
                        ship.shipMoveLeft();
                    } else if (right) {
                        ship.shipMoveRight();
                    } else if (shoot) {
                        if (mybul == null) {
                            mybul = new MyBullet(ship.getShipXPosition(), ship.getShipYPosition());
                        }
                    } else if (ship.isDead()) {
                        end = ("ゲームオーバー");
                        repaint();
                        Thread.sleep(1000);
                        StartGame = false;
                        dead = true;
                    }
                    repaint();
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dead = false;
        }
    }

    public void paint(Graphics g) {
        if (StartGame) {
            offimg_g.clearRect(0, 0, 500, 300);
            offimg_g.setColor(Color.black);
            offimg_g.fillRect(0, 0, 500, 300);
            offimg_g.setColor(Color.white);
            offimg_g.drawRect(300, 30, 190, 260);
            offimg_g.drawString("SCORE", 375, 140);
            String s = Integer.toString(score);
            String l = Integer.toString(level);
            offimg_g.drawString("レベル　" + l, 360, 100);
            offimg_g.drawString(s, 393, 160);
            offimg_g.drawString(end, 360, 180);
            count++;
            if (count % 5 == 0) {
                if (enebul[Enemy.E1] == null) {
                    enebul[Enemy.E1] = new EnemyBullet(enemy.getEnemyXPosition(Enemy.E1),
                            enemy.getEnemyYPosition(Enemy.E1));
                }
            } else if (count % 7 == 0) {
                if (enebul[Enemy.E2] == null) {
                    enebul[Enemy.E2] = new EnemyBullet(enemy.getEnemyXPosition(Enemy.E2),
                            enemy.getEnemyYPosition(Enemy.E2));
                }
            } else if (count % 12 == 0) {
                if (enebul[Enemy.E3] == null) {
                    enebul[Enemy.E3] = new EnemyBullet(enemy.getEnemyXPosition(Enemy.E3),
                            enemy.getEnemyYPosition(Enemy.E3));
                }
            }
            if (!ship.isDead()) {
                ship.draw(offimg_g);
            }
            if (!enemy.isDead(Enemy.E1)) {
                enemy.enemyMove(Enemy.E1);
                enemy.draw(offimg_g, Enemy.E1);
            }
            if (!enemy.isDead(Enemy.E2)) {
                enemy.enemyMove(Enemy.E2);
                enemy.draw(offimg_g, Enemy.E2);
            }
            if (!enemy.isDead(Enemy.E3)) {
                enemy.enemyMove(Enemy.E3);
                enemy.draw(offimg_g, Enemy.E3);
            }
            if (mybul != null) {
                mybul.draw(offimg_g);
                mybul.shootBullet(enemy);
                for (int i = 0; i < hitcheck.length; i++) {
                    hitcheck[i] = enemy.getHit(i);
                    if (mybul.getBulletY() < 0) {
                        mybul = null;
                        break;
                    } else if (hitcheck[i]) {
                        mybul = null;
                        enemy.setHit(false, i);
                        hitcheck[i] = false;
                        score += 100;
                        break;
                    }
                }
            }
            for (int i = 0; i < enebul.length; i++) {
                if (enebul[i] != null && !enemy.isDead(i)) {
                    enebul[i].draw(offimg_g);
                    enebul[i].shootBullet(ship);
                    if (300 < enebul[i].getBulletY()) {
                        enebul[i] = null;
                    }
                }
            }
            allDead();
            g.drawImage(offimg, 0, 0, this);
        } else {
            offimg_g.clearRect(0, 0, 500, 300);
            offimg_g.setColor(Color.black);
            offimg_g.fillRect(0, 0, 500, 300);
            offimg_g.setColor(Color.white);
            offimg_g.drawString("enterでスタート", 220, 160);
            g.drawImage(offimg, 0, 0, this);
        }
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shoot = true;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            StartGame = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 37) {
            left = false;
        } else if (e.getKeyCode() == 39) {
            right = false;
        } else if (e.getKeyCode() == 32) {
            shoot = false;
        }
    }

    public void allDead() {
        int deadenemy = 0;
        for (int i = 0; i < enemy.getEnemyLength(); i++) {
            if (enemy.isDead(i)) {
                deadenemy++;
            }
        }
        if (deadenemy == 3) {
            revivalEnemy();
        }
    }

    public void revivalEnemy() {
        enemy.setLife();
        enemy.Reverse();
        level++;
        enemy.setSpeed(level);
        for (int i = 0; i < enebul.length; i++) {
            enebul[i] = null;
        }
    }

    public void resetGame() {
        level = 1;
        enemy.Reverse();
        enemy.setLife();
        enemy.setSpeed(1);
        ship.shipReverse();
        for (int i = 0; i < enebul.length; i++) {
            enebul[i] = null;
        }
        score = 0;
    }

}

class MyWindowListener extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}