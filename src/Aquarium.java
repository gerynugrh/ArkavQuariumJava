class Aquarium {

  int gold;
  final LinkedList<Edible> edibles;
  final LinkedList<Fish> fishes;
  final LinkedList<Valuable> valuables;
  final LinkedList<Collector> collectors;

  Aquarium() {
    edibles = new LinkedList<>();
    fishes = new LinkedList<>();
    valuables = new LinkedList<>();
    collectors = new LinkedList<>();

    gold = 1000;
  }

}
