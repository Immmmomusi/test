public class Race{
    private int kamekyori = 100;
    private int usagikyori = 100;
    private boolean kameGoal = false;

    public synchronized boolean runKame() {
        try {
            while (kamekyori > 0) {
                kamekyori -= 10;
                Thread.sleep(500);
                System.out.println("かめは残り" + kamekyori + "mです");
                notifyAll();
                wait();
            }
        } catch (InterruptedException e) {
            System.out.println("おわり");;
        }
        kameGoal = true;
        notifyAll();
        return false;
    }
    public synchronized boolean runUsagi() {
        try {
            while (usagikyori > 0) {
            	notifyAll();
                Thread.sleep(200);
                usagikyori -= 20;
                System.out.println("うさぎは残り" + usagikyori + "mです");
                if (usagikyori <= 20) {
                    System.out.println("うさぎが休みました");
                    while(!kameGoal) {
                    	notifyAll();
                    	wait();
                    }
                    break;
                }
                wait();
            }
        } catch (InterruptedException e) {
            System.out.println("おわり");
        }
        return false;
    }

}