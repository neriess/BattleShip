package battleship;

import java.util.ArrayList;
import java.util.Arrays;

public class Field {
    private final String player;
    private final int BOARD_SIZE = 10;
    private final String FOG_SYMBOL = "~";
    private final String SHIP_SYMBOL = "O";
    private final String HIT_SYMBOL = "X";
    private final String MISS_SYMBOL = "M";
    private String[][] board;
    private String[][] foggyBoard;
    private String[][] checkBoard; //check board for proximity around ship
    private int[][] shipData = new int[5][4];

    Field(String player) {
        this.player = player;
        createBoard();
    }

    private void createBoard() {
        board = new String[BOARD_SIZE][BOARD_SIZE];
        Arrays.stream(board).forEach(a -> Arrays.fill(a, FOG_SYMBOL));
        foggyBoard = new String[BOARD_SIZE][BOARD_SIZE];
        Arrays.stream(foggyBoard).forEach(a -> Arrays.fill(a, FOG_SYMBOL));
        checkBoard = new String[BOARD_SIZE + 2][BOARD_SIZE + 2];
        Arrays.stream(checkBoard).forEach(a -> Arrays.fill(a, FOG_SYMBOL));
    }

    protected void printBoard(String[][] boardToPrint) {
        char firstRowChar = 'A';
        System.out.println("\n  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.printf("%c ", firstRowChar);
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.printf("%s ", boardToPrint[i][j]);
            }
            System.out.println();
            firstRowChar += 1;
        }
    }

    protected void placeShipOnBoard(ArrayList<Integer> coordinates) {

        int x1 = coordinates.get(0);
        int y1 = coordinates.get(1);
        int x2 = coordinates.get(2);
        int y2 = coordinates.get(3);

        if (x1 == x2) {
            for (int i = y1; i <= y2; i++) {
                board[x1][i] = SHIP_SYMBOL;
            }
            //place proximity to checkBoard around ship
            for (int i = y1; i <= y2 + 2; i++) {
                checkBoard[x1][i] = SHIP_SYMBOL;
                checkBoard[x1 + 1][i] = SHIP_SYMBOL;
                checkBoard[x1 + 2][i] = SHIP_SYMBOL;
            }
        }
        if (y1 == y2) {
            for (int i = x1; i <= x2; i++) {
                board[i][y1] = SHIP_SYMBOL;
            }
            //place proximity to checkBoard around ship
            for (int i = x1; i <= x2 + 2; i++) {
                checkBoard[i][y1] = SHIP_SYMBOL;
                checkBoard[i][y1 + 1] = SHIP_SYMBOL;
                checkBoard[i][y1 + 2] = SHIP_SYMBOL;
            }
        }
    }

    public void makeShot(int[] coordinates) {

        int x = coordinates[0];
        int y = coordinates[1];

        if (board[x][y].equals(FOG_SYMBOL)) {
            board[x][y] = MISS_SYMBOL;
            foggyBoard[x][y] = MISS_SYMBOL;
            System.out.printf("%nYou missed!%n%n");
        }
        if (board[x][y].equals(SHIP_SYMBOL) || board[x][y].equals(HIT_SYMBOL)) {
            board[x][y] = HIT_SYMBOL;
            foggyBoard[x][y] = HIT_SYMBOL;
            checkSankShip();
        }
    }

    int sankShips = 0;
    private void checkSankShip() {

        int tempNum = 0;

        for (int i = 0; i < 5; i++) {

            int x = shipData[i][2];
            int y = shipData[i][3];
            int numOfShipSymbols = 0;

            for (int j = 0; j < shipData[i][0]; j++) {
                if (shipData[i][1] == 1) {
                    if (board[x][y + j].equals(SHIP_SYMBOL)) {
                        numOfShipSymbols ++;
                    }
                } else {
                    if (board[x + j][y].equals(SHIP_SYMBOL)) {
                        numOfShipSymbols ++;
                    }
                }
            }
            if (numOfShipSymbols == 0) {
                tempNum++;
            }
        }

        if (tempNum == 5) {
            System.out.printf("%nYou sank the last ship. You won. Congratulations!%n");
            System.exit(0);
        } else if (sankShips < tempNum) {
            System.out.printf("%nYou sank a ship!%n%n");
            sankShips++;
        } else {
            System.out.printf("%nYou hit a ship!%n%n");
        }
    }

    public String[][] getBoard() {
        return board;
    }

    public String[][] getFoggyBoard() {
        return foggyBoard;
    }

    public String[][] getCheckBoard() {
        return checkBoard;
    }

    public String getSHIP_SYMBOL() {
        return SHIP_SYMBOL;
    }

    public String getPlayer() {
        return player;
    }

    public void setShipData(int[][] shipData) {
        this.shipData = shipData;
    }

    public int[][] getShipData() {
        return shipData;
    }
}
