package JuegoOca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    int numberOfPlayers = 5;
    int numberOfDices = 2;
    int dicesValue = 0;
    boolean ended = false;
    boolean auto = false;
    boolean nameFound = false;
    String moveMessage = "";
    
    int[] dices = new int[numberOfDices];
    List<Player> players = new ArrayList<>();
    Dice dice = new Dice();
    Board board = new Board();
    Scanner scanner = new Scanner(System.in);

    void main() {
        
    }
    
    Game() {
        automaticGame();
    }

    Game(boolean auto) {
        if (auto) {
            automaticGame();
        } else
            ManualGame();
    }

    void ManualGame() {
        do {
            determineCommandType();
        } while (!ended);

    }

    void determineCommandType() {
        String command = scanner.nextLine();
        if (command.contains("add player")) {
            managePlayerAddCommand(command);
        } else {
            manageMoveCommand(command);
        }
    }

    String managePlayerAddCommand(String command) {
        String name = extractName(command);
        String message = "";
        boolean nameFound = searchName(name);

        if (!nameFound) {
            addPlayer(name);
            message = displayPlayerList();
        } else {
            message = "Player " + name + " already exists. Please insert a new player.";
        }

        System.out.println(message);
        return message;
    }

    String extractName(String command) {
        String name = "";
        command = command.replaceAll("[\\.\\,\\(\\)] ", "");
        command = command.replaceAll("add player ", "");
        name = command;
        return name;
    }

    void addPlayer(String name) {
        int player = players.size();
        players.add(player, new Player());
        players.get(player).setName(name);
    }

    boolean searchName(String nameTemp) {
        String name = "";
        for (int player = 0; player < players.size(); player++) {
            name = getPlayerName(player);
            if (name.contains(nameTemp)) {
                nameFound = true;
                break;
            } else {
                nameFound = false;
            }
        }
        return nameFound;
    }

    String displayPlayerList() {
        String playerList = "Players: ";
        for (int player = 0; player < players.size(); player++) {
            playerList += getPlayerName(player);
            if (player != players.size() - 1) {
                playerList += ", ";
            }
        }
        return playerList;
    }

    int[] manageMoveCommand(String command) {
        String name = "";
        int player = 0;
        name = extractMoveName(command);
        determineDiceType(command);
        player = getPlayer(name);
        moveMessage(player, dices[0], dices[1]);
        playerTurn(player);
        return dices;
    }

    void playerTurn(int player) {
        int position = getPlayerPosition(player);
        position += dicesValue;
        movePlayer(player, position);
    }

    void movePlayer(int player, int position) {
        position = board.makeAMove(player, position);
        setPlayerPosition(player, position);
        boxMessage(player);
        ended = (position == 63);
    }

    String extractMoveName(String command) {
        String name = "";

        command = command.replaceAll("[\\.\\,\\(\\)0-9]", "");
        command = command.replaceFirst("move ", "");
        command = command.replaceAll(" ", "");
        name = command;
        return name;
    }

    void determineDiceType(String command) {
        String onlyNumbers = command;
        onlyNumbers = onlyNumbers.replaceAll("[^0-9]", "");
        if (onlyNumbers.length() > 0) {
            extractDiceRoll(onlyNumbers);
        } else
            rollDices();
    }

    void extractDiceRoll(String onlyNumbers) {
        for (int i = 0; i < 2; i++) {
            dices[i] = Integer.parseInt(onlyNumbers.substring(i, i + 1));
        }
        addDicesValues(dices);
    }

    void rollDices() {
        for (numberOfDices = 0; numberOfDices < dices.length; numberOfDices++) {
            dices[numberOfDices] = dice.rollDice();
        }
        addDicesValues(dices);
    }

    int addDicesValues(int[] dices) {
        dicesValue = 0;
        for (int diceNum = 0; diceNum < numberOfDices; diceNum++) {
            dicesValue += dices[diceNum];
        }
        return dicesValue;
    }

    // FOR PC vs PC  GAME 

    void automaticGame() {
        addPlayersAuto();
        gameLoop();
    }

    void addPlayersAuto() {
        String[] names = { "Sara", "Juan", "Pepe" };
        for (int player = 0; player < numberOfPlayers; player++) {
            players.add(new Player());
            players.get(player).setName(names[player]);
        }
    }

    void gameLoop() {
        do {
            turnPC();
        } while (!ended);
        
    }

    void turnPC() {
        for (int player = 0; player < players.size(); player++) {
            if (!ended) {
                rollDices();
                playerTurn(player);
            } else {break;}
        }
    }

    String moveMessage(int player, int dice1, int dice2) {
        moveMessage = "Player NAME rolls " + dice1 + "," + dice2 + ". ";
        moveMessage += "NAME moves from ";
        if (getPlayerPosition(player) == 0) {
            moveMessage += "Start";
        } else {
            moveMessage += getPlayerPosition(player);
        }
        moveMessage = moveMessage.replaceAll("NAME", getPlayerName(player));
        
        return moveMessage;
    }

    String boxMessage(int player) {
        String message = moveMessage + board.getMessage();
        String name = getPlayerName(player);
        String position = String.valueOf(getPlayerPosition(player));
        message = message.replaceAll("NAME", name);
        message = message.replaceAll("POSITION", position);
        System.out.println(message);
        return message;
    }

    public int getPlayer(String name) {
        int player = 0;
        for (player = 0; player < players.size(); player++) {
            if (players.get(player).getName().contains(name)) {
                break;
            }
        }
        return player;
    }

    public String getPlayerName(int player) {
        String name = players.get(player).getName();
        return name;
    }

    public int getPlayerPosition(int player) {
        return players.get(player).getPosition();
    }

    public void setPlayerPosition(int player, int position) {
        players.get(player).setPosition(position);
    }

    public int getDicesValue() {
        return dicesValue;
    }

    public int getDice0() {
        return dices[0];
    }

    public int getDice1() {
        return dices[1];
    }
    public boolean isEnded(){
        return ended;
    }
}