class Aquarium {

    int gold;
    LinkedList<Edible> edibles;
    LinkedList<Fish> fishes;
    LinkedList<Valuable> valuables;
    LinkedList<Collector> collectors;

    Aquarium() {
        edibles = new LinkedList<>();
        fishes = new LinkedList<>();
        valuables = new LinkedList<>();
        collectors = new LinkedList<>();

        gold = 1000;
    }

}
