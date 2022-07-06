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
    Players player = new Players();
    GameBoard gameBoard = new GameBoard();
    Dice dice = new Dice();
    String storedMessage = "";

    void startGame() {
        player.addPlayer("add player Sara");
        player.addPlayer("add player Juan");
        player.addPlayer("add player Pepito");
        turn();
    }

    void manualRoll(String command) {
        if (!storedMessage.contains(FINAL_MOVE)) {
            String numbersOnly = command.replaceAll("[^0-9]", "");
            String number1 = numbersOnly.substring(0, 1);
            String number2 = numbersOnly.substring(1);
            int dice1 = Integer.parseInt(number1);
            int dice2 = Integer.parseInt(number2);
            commandMove(dice1, dice2, command);
        }
    }

    void diceRoll(String command) {
        int dice1 = dice.rollDice();
        int dice2 = dice.rollDice();
        commandMove(dice1, dice2, command);
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
            message += "NOMBRE" + gameBoard.manageBoxes(currentIndex, tirada);
            message = message.replaceAll("NOMBRE", getPlayer(currentIndex));
            storedMessage = message;
            System.out.println(message);
        }
        return message;
    }

    String turn() {
        String message = "";
        do {        // para que entre en bucle
            for (String player : player.playerList) { // para iterar a los jugadores
                if (!message.contains(FINAL_MOVE)) { // para hacer que compruebe la condicion (que el juego sigue)
                    int dice1 = dice.rollDice();
                    int dice2 = dice.rollDice();
                    int tirada = dice1 + dice2;
                    int currentIndex = getIndex(player);
                    message = "Player NOMBRE rolls " + dice1 + "," + dice2 + ". ";
                    message += "NOMBRE" + gameBoard.manageBoxes(currentIndex, tirada);
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
        if (index >= gameBoard.positionsList.size()) {
            gameBoard.positionsList.add(0);   
        }
        gameBoard.positionsList.set(index, position);
    }

}

class Dice {
    int min = 1;
    int max = 6;

    int rollDice() {
        int oneDice = (int) Math.floor(Math.random() * (max - min + 1) + min);
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
            message = "Player: " + name + " already exists. Please insert a new player.";
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
    List<Integer> positionsList = new ArrayList<Integer>();
    int endPosition = 63;
    int bridgePosition = 6;

    Dice dice = new Dice();

    String manageBoxes(int index, int tirada) {
        if (index >= positionsList.size()) {
            positionsList.add(0);   
        }
        String message = "";
        int oldPosition = positionsList.get(index);
        int position = tirada + oldPosition;
        if (position <= endPosition) {
            message = " moves from " + oldPosition;
            if (position == bridgePosition) {
                positionsList.set(index, 12);
                message += " to The Bridge. NOMBRE jumps";
            } else {
                positionsList.set(index, position);
            }
            message += " to " + positionsList.get(index);
            if (position == 63) {
                positionsList.set(index, 63);
                message += " NOMBRE Wins!!";
            }
        } else {
            position = endPosition - (position - endPosition);
            positionsList.set(index, position);
            message = " bounces! Returns to " + positionsList.get(index);
        }
        return message;
    }
}