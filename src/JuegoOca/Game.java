package JuegoOca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    public int numberOfPlayers = 5;
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

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.settings(4, 2, true);
        game.start();
        game.settings(99, 2, false);
        game.start();
    }

    Game() {
    }

    void start() {
        initialSettings();
        if (auto) {
            startAutomatedGame();
        } else
            startManualGame();
    }

    private void initialSettings() {
        players.clear();
        ended = false;
        nameFound = false;
        dicesValue = 0;
        moveMessage = "";
    }

    void startManualGame() {
        do {
            determineCommandType();
        } while (!ended);
        initialSettings();
        startManualGame();
    }

    void determineCommandType() {
        String command = scanner.nextLine();
            if (command.contains("add player")) {
                managePlayerAddCommand(command);
            } else if (players.size() > 0 ){
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
        moveMessage(player);
        playerTurn(player);
        return dices;
    }

    void playerTurn(int player) {
        moveMessage(player);
        int possiblePosition = countBoxes(player);
        movePlayer(player, possiblePosition);
    }

    private int countBoxes(int player) {
        int possiblePosition = getPlayerPosition(player);
        possiblePosition += dicesValue;
        return possiblePosition;
    }

    void movePlayer(int player, int possiblePosition) {
        int position = board.makeAMove(player, possiblePosition);
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

    // FOR PC vs PC GAME

    void startAutomatedGame() {
        addPlayersAuto();
        gameLoop();
    }

    void addPlayersAuto() {
        String[] names = { "if", "you", "can", "read", "this", "players", "are", "sorted", "ascending", "when",
                "turned" };
        for (int index = 0; index < numberOfPlayers; index++) {
            players.add(new Player());
            players.get(index).setName(names[index]);
        }
        System.out.println(displayPlayerList());
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
            } else {
                break;
            }
        }
    }

    // MESSAGES

    String moveMessage(int player) {
        moveMessage = "";
        moveMessage = "Player NAME rolls " + dices[0] + "," + dices[1] + ". ";
        moveMessage += "NAME moves from ";
        if (getPlayerPosition(player) == 0) {
            moveMessage += "Start";
        } else {
            moveMessage += getPlayerPosition(player);
        }
        moveMessage = moveMessage.replaceAll("NAME", getPlayerName(player));

        board.setMessage("");
        return moveMessage;
    }

    String boxMessage(int player) {
        String message = "";
        String name = getPlayerName(player);
        String position = String.valueOf(getPlayerPosition(player));
        message = moveMessage + board.getMessage();
        message = message.replaceAll("NAME", name);
        message = message.replaceAll("POSITION", position);
        System.out.println(message);
        return message;
    }

    // GETTERS & SETTERS
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setNumberOfDices(int numberOfDices) {
        this.numberOfDices = numberOfDices;
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

    public boolean isEnded() {
        return ended;
    }

    public void settings(int numberOfPlayers, int numberOfDices, boolean auto) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfDices = numberOfDices;
        this.auto = auto;
    }
}