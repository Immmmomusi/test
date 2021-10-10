package game;


public class GameMain {
    public static void main(String args[]) {
        GamePanel panel = new GamePanel();
        Thread th = new Thread(panel);
        th.start();
    }
}
