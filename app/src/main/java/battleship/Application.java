package battleship;

public class Application {
    Field field1;
    Field field2;
    InputHandler input;

    Application() {
        field1 = new Field("Player 1");
        field2 = new Field("Player 2");
        input = new InputHandler();
    }

    public void start() {

        prepareBattlefield(field1, field1.getPlayer());
        waitForEnterToContinue();
        prepareBattlefield(field2, field2.getPlayer());
        waitForEnterToContinue();

        while (true) {
            shot(field1, field2, field1.getPlayer());
            shot(field2, field1, field2.getPlayer());
        }

    }

    public void prepareBattlefield(Field field, String player) {

        String[][] board = field.getBoard();
        String[][] checkBoard = field.getCheckBoard();
        String shipSymbol = field.getSHIP_SYMBOL();

        System.out.printf("%n%s, place your ships on the game field%n", player);
        field.printBoard(board);

        for (Ships ship : Ships.values()) {
            field.placeShipOnBoard(input.getCoordinatesForShip(ship, checkBoard, shipSymbol, field));
            field.printBoard(board);
        }
    }

    public void shot(Field playerField, Field enemyField, String player) {

        String[][] board = playerField.getBoard();
        String[][] foggyBoard = enemyField.getFoggyBoard();

        enemyField.printBoard(foggyBoard);
        System.out.printf("%n---------------------%n");
        playerField.printBoard(board);
        System.out.printf("%n%s, it's your turn:%n%n", player);

        enemyField.makeShot(input.getShotCoordinates());

        waitForEnterToContinue();
    }

    private void waitForEnterToContinue() {
        System.out.println("\nPress Enter and pass the move to another player.\n" +
                "...");
        input.getScanner().nextLine();
    }
}
