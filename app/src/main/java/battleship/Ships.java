package battleship;

public enum Ships {

    AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");

    final int length;
    final String name;

    Ships(int length, String name) {
        this.length = length;
        this.name = name;
    }
}
