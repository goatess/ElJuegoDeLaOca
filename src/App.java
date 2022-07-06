import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        NewGame newGame = new NewGame();
        newGame.startGame();
    }
}

class NewGame {
    Player player = new Player();
    GameBoard gameBoard = new GameBoard();
    String storedMessage = "";

    void startGame() {
    }

    void commandRoll(String command) {
        String finalmove = "Wins!!";
        if (!storedMessage.contains(finalmove)) {
            String numbersOnly = command.replaceAll("[^0-9]", "");
            String number1 = numbersOnly.substring(0, 1);
            String number2 = numbersOnly.substring(1);
            int dice1 = Integer.parseInt(number1);
            int dice2 = Integer.parseInt(number2);
            manualMove(dice1, dice2, command);
        }
    }

    void diceRoll(String command) {
        int dice1 = gameBoard.dice.rollDice();
        int dice2 = gameBoard.dice.rollDice();
        manualMove(dice1, dice2, command);
    }

    String manualMove(int dice1, int dice2, String command) {
        final String finalmove = "Wins!!";
        String message = "";
        int tirada = dice1 + dice2;
        if (!storedMessage.contains(finalmove)) {
            command = command.replaceAll("[\\.\\,\\(\\)0-9]", "");
            command = command.replaceFirst("move ", "");
            String name = command.replaceAll(" ", "");
            int currentIndex = getIndex(name);

            message = "COMM Player " + name + " rolls " + dice1 + "," + dice2 + ". ";
            message += getPlayer(currentIndex) + gameBoard.makeAMove(currentIndex, tirada);
            storedMessage = message;
            System.out.println(message);
        }
        return message;
    }

    String autoMove() {
        String finalmove = "Wins!!";
        String message = "";
        while (!message.contains(finalmove)) { // para que entre en bucle
            for (String player : player.playerList) { // para iterar a los jugadores
                if (!message.contains(finalmove)) { // para hacer que compruebe la condicion (que el juego sigue)
                    int dice1 = gameBoard.dice.rollDice();
                    int dice2 = gameBoard.dice.rollDice();
                    int tirada = dice1 + dice2;
                    int currentIndex = getIndex(player);
                    message = "TURN Player " + getPlayer(currentIndex) + " rolls " + dice1 + "," + dice2 + ". ";
                    message += getPlayer(currentIndex) + gameBoard.makeAMove(currentIndex, tirada);
                    System.out.println(message);
                } else
                    break; // para salir completamente del bucle cuando acaba el juego
            }
        }
        return message;
    }

    public String getPlayer(int index) {
        return player.playerList.get(index);
    }

    public int getIndex(String name) {
        return player.playerList.indexOf(name);
    }

    public int getPosition(int index) {
        return gameBoard.positions[index];
    }

    public void setPosition(int index, int position) {
        gameBoard.positions[index] = position;
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

class Player {
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
    public int[] positions = new int[12];
    int endPosition = 63;
    int bridgePosition = 6;

    Dice dice = new Dice();

    String makeAMove(int index, int tirada) {
        String message = "";
        int oldPosition = positions[index];
        int position = tirada + positions[index];
        if (position <= endPosition) {
            message = " moves from " + oldPosition;
            if (position == bridgePosition) {
                positions[index] = 12;
                message += " to The Bridge. NOMBRE Jumps";
            } else {
                positions[index] = position;
            }
            message += " to " + positions[index];
            if (position == 63) {
                message += " NOMBRE Wins!!";
            }
        } else {
            position = endPosition - (position - endPosition);
            positions[index] = position;
            message = " bounces! Returns to " + positions[index];
        }
        return message;
    }
}