class test {

    public static void main(String[] args) {
        Race race = new Race();
        Kame kam = new Kame(race);
        Usagi usa = new Usagi(race);
        System.out.println("うさぎとかめが競争します");
        usa.start();
        kam.start();
    }
}
