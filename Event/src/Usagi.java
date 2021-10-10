class Usagi extends Thread {
    private Race race;
    private boolean running = true;

    public Usagi(Race race) {
        this.race = race;
    }
    public void run() {
    	while(running) {
    		running = race.runUsagi();
    	}
    }
}