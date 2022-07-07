import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        Game newGame = new Game();
        newGame.startGame();
    }
}

class Game {
    final String FINAL_MOVE = "Wins!!";
    String storedMessage = "";

    Players player = new Players();
    GameBoard gameBoard = new GameBoard();
    Dice dice = new Dice();

    void startGame() {
        player.addPlayer("add player Sara");
        player.addPlayer("add player Juan");
        player.addPlayer("add player NOMBRE");
        diceRoll("move NOMBRE");
        diceRoll("move NOMBRE");
        diceRoll("move NOMBRE");

    }

    String manualRoll(String command) {
        String message = "";
        if (!storedMessage.contains(FINAL_MOVE)) {
            String numbersOnly = command.replaceAll("[^0-9]", "");
            String number1 = numbersOnly.substring(0, 1);
            String number2 = numbersOnly.substring(1);
            int dice1 = Integer.parseInt(number1);
            int dice2 = Integer.parseInt(number2);
            message = commandMove(dice1, dice2, command);
        }
        return message;
    }

    String diceRoll(String command) {
        int dice1 = dice.rollDice();
        int dice2 = dice.rollDice();
        String message = commandMove(dice1, dice2, command);
        return message;
    }

    String commandMove(int dice1, int dice2, String command) {
        String message = "";
        int tirada = dice1 + dice2;
        if (!storedMessage.contains(FINAL_MOVE)) {
            command = command.replaceAll("[\\.\\,\\(\\)0-9]", "");
            command = command.replaceFirst("move ", "");
            String name = command.replaceAll(" ", "");
            int currentIndex = getIndex(name);

            message = "Player NOMBRE rolls " + dice1 + "," + dice2 + ". ";
            message += gameBoard.manageBoxes(currentIndex, tirada);
            message = message.replaceAll("NOMBRE", getPlayer(currentIndex));
            storedMessage = message;
            System.out.println(message);
        }
        return message;
    }

    String turn() {
        String message = "";
        do { // para que entre en bucle
            for (String player : player.playerList) { // para iterar a los jugadores
                if (!message.contains(FINAL_MOVE)) { // para hacer que compruebe la condicion (que el juego sigue)
                    int dice1 = dice.rollDice();
                    int dice2 = dice.rollDice();
                    int tirada = dice1 + dice2;
                    int currentIndex = getIndex(player);
                    message = "Player NOMBRE rolls " + dice1 + "," + dice2 + ". ";
                    message += gameBoard.manageBoxes(currentIndex, tirada);
                    message = message.replaceAll("NOMBRE", getPlayer(currentIndex));
                    System.out.println(message);
                } else
                    break; // para salir completamente del bucle cuando acaba el juego
            }
        } while (!message.contains(FINAL_MOVE));
        return message;
    }

    public String getPlayer(int index) {
        return player.playerList.get(index);
    }

    public int getIndex(String name) {
        return player.playerList.indexOf(name);
    }

    public int getPosition(int index) {
        return gameBoard.positionsList.get(index);
    }

    public void setPosition(int index, int position) {
        do {
            gameBoard.positionsList.add(0);
        } while (index >= gameBoard.positionsList.size());
        gameBoard.positionsList.set(index, position);
    }
}

class Dice {
    final int MIN = 1;
    final int MAX = 6;

    int rollDice() {
        int oneDice = (int) Math.floor(Math.random() * (MAX - MIN + 1) + MIN);
        return oneDice;
    }
}

class Players {
    List<String> playerList = new ArrayList<String>();

    String addPlayer(String command) {
        String name = command.replaceAll("[\\.\\,\\(\\)] ", "");
        name = command.replaceAll("add player ", "");
        String message = "";

        if (playerList.contains(name)) {
            message = "Player " + name + " already exists. Please insert a new player.";
        } else {
            playerList.add(name);
            message = "Players: ";
            for (int index = 0; index < playerList.size(); index++) {
                message += playerList.get(index);
                if (index != playerList.size() - 1) {
                    message += ", ";
                }
            }
        }
        System.out.println(message);
        return message;
    }
}

class GameBoard {
    final int END_POSITION = 63;
    final int BRIDGE_POSITION = 6;
    List<Integer> positionsList = new ArrayList<Integer>();

    String manageBoxes(int index, int tirada) {
        String message = "";
        do {
            positionsList.add(0);
        } while (index >= positionsList.size());

        int oldPosition = positionsList.get(index);
        int position = tirada + oldPosition;

        message = "NOMBRE moves from ";
        if (oldPosition == 0) {
            message += "Start";
        } else
            message += oldPosition;
        positionsList.set(index, position);
        if (position == BRIDGE_POSITION) {
            positionsList.set(index, 12);
            message += " to The Bridge. NOMBRE jumps";
        }
        message += " to ";

        if (position > END_POSITION) {
            message += END_POSITION;
            position = END_POSITION - (position - END_POSITION);
            positionsList.set(index, position);
            message += ". NOMBRE bounces! NOMBRE Returns to " + positionsList.get(index);
        } else
            message += positionsList.get(index);
        if (position == END_POSITION) {
            positionsList.set(index, END_POSITION);
            message += ". NOMBRE Wins!!";
        }
        return message;
    }
}