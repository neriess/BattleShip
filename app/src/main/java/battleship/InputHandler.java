package battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class InputHandler {
    private final String ENTER_SHIP_COORDINATES = "%nEnter the coordinates of the %s (%d cells):%n%n";
    private final String WRONG_LENGTH = "%nError! Wrong length of the %s! Try again:%n%n";
    private final String WRONG_LOCATION = "%nError! Wrong ship location! Try again:%n%n";
    private final String WRONG_COORDINATES = "%nError! You entered the wrong coordinates! Try again:%n%n";
    private final String PLACED_TOO_CLOSE = "%nError! You placed it too close to another one. Try again:%n%n";
    private final String BAD_PARAMETER = "%nBad parameter. Try again%n%n";

    int x1, x2, y1, y2;

    Scanner scanner;

    InputHandler() {
        scanner = new Scanner(System.in);
    }

    protected ArrayList<Integer> getCoordinatesForShip(Ships ship, String[][] checkBoard,
                                                       String shipSymbol, Field field) {

        String[] input;
        ArrayList<Integer> coordinates;

        System.out.printf(ENTER_SHIP_COORDINATES, ship.name, ship.length);

        while (true) {

            input = scanner.nextLine().toUpperCase().split("\\s");
            try {
                coordinates = translateInputToCoordinates(input);

                if (areShipCoordinatesCorrect(ship, checkBoard, shipSymbol)) {
                    initializeShipData(ship, field);
                    return coordinates;
                }
            } catch (Exception e) {
                System.out.printf(BAD_PARAMETER);
            }
        }
    }

    private ArrayList<Integer> translateInputToCoordinates(String[] input) {

        ArrayList<Integer> translatedCoordinates = new ArrayList<>();

        int x1 = input[0].charAt(0) - 65;
        int y1 = Integer.parseInt(input[0].substring(1)) - 1;
        int x2 = input[1].charAt(0) - 65;
        int y2 = Integer.parseInt(input[1].substring(1)) - 1;

        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);

        translatedCoordinates.add(Math.min(x1, x2));
        translatedCoordinates.add(Math.min(y1, y2));
        translatedCoordinates.add(Math.max(x1, x2));
        translatedCoordinates.add(Math.max(y1, y2));

        return translatedCoordinates;
    }


    private boolean areShipCoordinatesCorrect(Ships ship, String[][] checkBoard, String shipSymbol) {

        if ((x1 != x2 && y1 != y2) || (x1 == x2 && y1 == y2)) {
            System.out.printf(WRONG_LOCATION);
            return false;
        } else if (x1 + ship.length - 1 != x2 && y1 + ship.length - 1 != y2) {
            System.out.printf(WRONG_LENGTH, ship.name);
            return false;
        } else if (checkForProximity(checkBoard, shipSymbol)) {
            System.out.printf(PLACED_TOO_CLOSE);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkForProximity(String[][] checkBoard, String shipSymbol) {
        if (x1 == x2) {
            for (int i = y1 + 1; i <= y2 + 1; i++) {
                if (checkBoard[x1 + 1][i].equals(shipSymbol)) {
                    return true;
                }
            }
        }
        if (y1 == y2) {
            for (int i = x1 + 1; i <= x2 + 1; i++) {
                if (checkBoard[i][y1 + 1].equals(shipSymbol)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] getShotCoordinates() {

        int x;
        int y;
        String input;

        while (true) {
            try {
                input = scanner.nextLine().toUpperCase();
                if (input.length() == 2 || input.length() == 3) {
                    x = input.charAt(0) - 65;
                    y = Integer.parseInt(input.substring(1)) - 1;
                    if ((0 <= x && x < 10) && (0 <= y && y < 10)) {
                        return new int[] {x, y};
                    } else {
                        System.out.printf(WRONG_COORDINATES);
                    }
                } else {
                    System.out.printf(WRONG_COORDINATES);
                }
            } catch (Exception e) {
                System.out.printf(BAD_PARAMETER);
            }
        }
    }
    //0 -> length, 1 -> isHorizontal, 2 -> x, 3 -> y;
    private void initializeShipData(Ships ship, Field field) {

        int[][] shipData = field.getShipData();
        int index = ship.ordinal();

        shipData[index][0] = ship.length;

        if (x1 == x2) {
            shipData[index][1] = 1;
        } else {
            shipData[index][1] = 0;
        }

        shipData[index][2] = x1;
        shipData[index][3] = y1;

        field.setShipData(shipData);
    }

    public Scanner getScanner() {
        return scanner;
    }
}
