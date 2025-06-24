package cookies250.shipyardcore.ships;

import java.util.ArrayList;
import java.util.List;

public class ShipManager {

    private static final List<Ship> ships = new ArrayList<>(); // TODO Load this from a file

    private static int nextShipID; // TODO Load this from a file, never reset it


    public static int getNextShipID() {
        return nextShipID++;
    }


    public static void addShip(Ship ship) {
        ships.add(ship);
    }

    public static void removeShip(Ship ship) {
        ships.remove(ship);
    }

    public static List<Ship> getShips() {
        return ships;
    }

    public static List<String> getShipNames() {
        List<Ship> ships = ShipManager.getShips();
        return ships.stream().map(Ship::getShipName).toList();
    }

    public static Ship getShipFromName(String name) {
        for (Ship ship : ShipManager.getShips()) {
            if (ship.getShipName().equals(name)) return ship;
        }
        return null;
    }
}