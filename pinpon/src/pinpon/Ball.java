package pinpon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Ball {

    private boolean directionRight = false;
    private boolean directionUp = false;
    private int[] hitbox = new int[4];
    private boolean p1goal = false;
    private boolean p2goal = false;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int HIGHER = 2;
    public static final int UNDER = 3;
    private int speed = 1;
    int[] xpos = { 8, 8, 10, 10, 12, 12, 10, 10, 8, 8, 4, 4, 2, 2, 0, 0, 2, 2, 4, 4 };
    int[] ypos = { 0, 2, 2, 4, 4, 8, 8, 10, 10, 12, 12, 10, 10, 8, 8, 4, 4, 2, 2, 0 };
    Polygon ball = new Polygon(xpos, ypos, xpos.length);

    Ball() {
        int[] xpos = { 8, 8, 10, 10, 12, 12, 10, 10, 8, 8, 4, 4, 2, 2, 0, 0, 2, 2, 4, 4 };
        int[] ypos = { 0, 2, 2, 4, 4, 8, 8, 10, 10, 12, 12, 10, 10, 8, 8, 4, 4, 2, 2, 0 };
        ball = new Polygon(xpos, ypos, xpos.length);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.yellow);
        g.fill(ball);
        g.setColor(Color.yellow);
        g.draw(ball);
    }

    public void setPosision(int x, int y) {
        for (int i = 0; i < ball.xpoints.length; i++) {
            ball.xpoints[i] = xpos[i];
            ball.ypoints[i] = ypos[i];
        }
        for (int i = 0; i < ball.xpoints.length; i++) {
            ball.xpoints[i] += x;
            ball.ypoints[i] += x;
        }
        hitbox[0] = ball.xpoints[14];
        hitbox[1] = ball.xpoints[4];
        hitbox[2] = ball.ypoints[0];
        hitbox[3] = ball.ypoints[10];
        speed = 1;
    }

    public void moveBall(Player1 p1, Player2 p2) {
        if (directionRight && directionUp) {
            for (int i = 0; i < ball.xpoints.length; i++) {
                ball.xpoints[i] += speed;
                ball.ypoints[i] -= speed;
            }
        } else if (!directionRight && directionUp) {
            for (int i = 0; i < ball.xpoints.length; i++) {
                ball.xpoints[i] -= speed;
                ball.ypoints[i] -= speed;
            }
        } else if (directionRight && !directionUp) {
            for (int i = 0; i < ball.xpoints.length; i++) {
                ball.xpoints[i] += speed;
                ball.ypoints[i] += speed;
            }
        } else if (!directionRight && !directionUp) {
            for (int i = 0; i < ball.xpoints.length; i++) {
                ball.xpoints[i] -= speed;
                ball.ypoints[i] += speed;
            }
        }
        hitbox[0] = ball.xpoints[14];
        hitbox[1] = ball.xpoints[4];
        hitbox[2] = ball.ypoints[0];
        hitbox[3] = ball.ypoints[10];
        isHit(p1, p2);
        isGoal();
    }

    public void isGoal() {
        if (!p1goal || !p2goal) {
            if (100 <= hitbox[LEFT] && hitbox[RIGHT] <= 250 && hitbox[HIGHER] <= 50) {
                p1goal = true;
            }
            if (100 <= hitbox[LEFT] && hitbox[RIGHT] <= 250 && 440 <= hitbox[UNDER]) {
                p2goal = true;
            }
        }
    }

    public void isHit(Player1 p1, Player2 p2) {
        if (directionRight && directionUp) { //右上
            if (PinponPaint.MAX_WITDH <= hitbox[RIGHT]){
                directionRight = false;
                speed++;
            } else if (hitbox[HIGHER] <= PinponPaint.MAX_HIGHT || (hitbox[HIGHER] <= p2.getHitBox(UNDER)
                    && p2.getHitBox(LEFT) <= hitbox[LEFT] && hitbox[RIGHT] < p2.getHitBox(RIGHT)
                    && p2.getHitBox(HIGHER) <= hitbox[HIGHER])) {
                directionUp = false;
                speed++;
            }
        } else if (!directionRight && directionUp) { //左上
            if (hitbox[LEFT] <= PinponPaint.MIN_WITDH ) {
                directionRight = true;
                speed++;
            } else if (hitbox[HIGHER] <= PinponPaint.MAX_HIGHT || (hitbox[HIGHER] < p2.getHitBox(UNDER)
                    && p2.getHitBox(LEFT) <= hitbox[LEFT] && hitbox[RIGHT] <= p2.getHitBox(RIGHT)
                    && p2.getHitBox(HIGHER) <= hitbox[HIGHER])) {
                directionUp = false;
                speed++;
            }
        } else if (directionRight && !directionUp) { //右下
            if (PinponPaint.MAX_WITDH <= hitbox[RIGHT] ) {
                directionRight = false;
                speed++;
            } else if (PinponPaint.MIN_HIGHT <= hitbox[UNDER] || (p1.getHitBox(HIGHER) <= hitbox[UNDER]
                    && hitbox[RIGHT] <= p1.getHitBox(RIGHT) && p1.getHitBox(LEFT) <= hitbox[LEFT]
                    && hitbox[HIGHER] <= p1.getHitBox(UNDER))) {
                directionUp = true;
                speed++;
            }
        } else if (!directionRight && !directionUp) { //左下
            if (hitbox[LEFT] < PinponPaint.MIN_WITDH ) {
                directionRight = true;
                speed++;
            } else if (PinponPaint.MIN_HIGHT < hitbox[UNDER] || (p1.getHitBox(HIGHER) < hitbox[UNDER])
                    && hitbox[RIGHT] <= p1.getHitBox(RIGHT) && p1.getHitBox(LEFT) < hitbox[LEFT]
                    && hitbox[HIGHER] <= p1.getHitBox(UNDER)) {
                directionUp = true;
                speed++;
            }
        }

    }

    public boolean getGoal(int x) {
        if (x == 1) {
            return p1goal;
        }
        return p2goal;
    }

    public void setGoal() {
        p1goal = false;
        p2goal = false;
    }
}
