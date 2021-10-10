class Kame extends Thread {
    private Race race;
    private boolean running = true;
    
    public Kame(Race race) {
        this.race = race;
    }
    public void run() {
    	while(running) {
    		running = race.runKame();
    	}
    	System.out.println("かめのかち");
    }
}