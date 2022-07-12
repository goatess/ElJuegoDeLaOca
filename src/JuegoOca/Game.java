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
    Dice dice = new Dice();
    Board board = new Board();
    Scanner scanner = new Scanner(System.in);
    int[] dices = new int[numberOfDices];
    List<Player> players = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.changeSettings(4, 2, true);
        game.start();
        game.changeSettings(99, 2, false);
        game.start();
    }

    Game() {
    }

    void start() {
        initialSetup();
        if (auto) {
            startAutomatedGame();
        } else
            startManualGame();
    }

    private void initialSetup() {
        players.clear();
        ended = false;
        nameFound = false;
        dicesValue = 0;
        moveMessage = "";
    }

    public void changeSettings(int numberOfPlayers, int numberOfDices, boolean auto) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfDices = numberOfDices;
        this.auto = auto;
    }

    void startManualGame() {
        do {
            insertCommand();
        } while (!ended);
        initialSetup();
        startManualGame();
    }

    void insertCommand() {
        String command = scanner.nextLine();
        if (command.contains("add player")) {
            useAddPlayerCommand(command);
        } else if (players.size() > 0) {
            useRollDiceCommand(command);
        }
    }

    String useAddPlayerCommand(String command) {
        String name = extractName(command);
        String message = "";
        boolean nameFound = searchName(name);
        if (!nameFound) {
            addPlayer(name);
            message = displayPlayerList();
        } else {
            message = alreadyExistingNameMessage(name);
        }

        System.out.println(message);
        return message;
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

    int[] useRollDiceCommand(String command) {
        String name = "";
        int player = 0;
        determineDiceType(command);
        name = extractName(command);
        player = getPlayer(name);
        movePlayer(player);
        return dices;
    }

    void movePlayer(int player) {
        moveMessage(player);
        int possiblePosition = countBoxes(player);
        makeAMove(player, possiblePosition);
    }

    private int countBoxes(int player) {
        int possiblePosition = getPlayerPosition(player);
        possiblePosition += dicesValue;
        return possiblePosition;
    }

    void makeAMove(int player, int possiblePosition) {
        int position = board.determineMoveResult(player, possiblePosition);
        setPlayerPosition(player, position);
        boxMessage(player);
        ended = (position == 63);
    }

    void determineDiceType(String command) {
        String onlyNumbers = command;
        onlyNumbers = onlyNumbers.replaceAll("[^0-9]", "");
        if (onlyNumbers.length() > 0) {
            extractDices(onlyNumbers);
        } else
            rollDices();
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

    void extractDices(String command) {
        String onlyNumbers = command;
        for (int i = 0; i < 2; i++) {
            dices[i] = Integer.parseInt(onlyNumbers.substring(i, i + 1));
        }
        addDicesValues(dices);
    }

    String extractName(String command) {
        String name = command;
        name = name.replaceAll("[\\.\\,\\(\\)0-9]", "");
        name = name.replaceAll("add player ", "");
        name = name.replaceFirst("move ", "");
        name = name.replaceAll(" ", "");
        return name;
    }

    // AUTOMATED PC vs PC GAME

    void startAutomatedGame() {
        addPlayersAutomated();
        gameLoop();
    }

    void addPlayersAutomated() {
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
            turn();
        } while (!ended);

    }

    void turn() {
        for (int player = 0; player < players.size(); player++) {
            if (!ended) {
                rollDices();
                movePlayer(player);
            } else {
                break;
            }
        }
    }

    // MESSAGES

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

    private String alreadyExistingNameMessage(String name) {
        return "Player " + name + " already exists. Please insert a new player.";
    }

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
    /*
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setNumberOfDices(int numberOfDices) {
        this.numberOfDices = numberOfDices;
    }
 
 */
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

    public boolean isEnded() {
        return ended;
    }
}