package JuegoOca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private int numberOfPlayers = 4;
    private boolean ended = false;
    private boolean automated = false;
    private int numberOfDice = 2;
    private int sides = 6;
    private String message = "";
    private Board board = new Board();
    List<Player> players = new ArrayList<>();


    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.selectGameToStart(4, true);
        game.selectGameToStart(99, false);
    }

    Game() {
        clearValues();
        changeSettings(4, false);
    }

    public Game(int numberOfPlayers, int number, boolean automated, int sides) {
        clearValues();
        this.numberOfPlayers = numberOfPlayers;
        this.automated = automated;
        this.numberOfDice = number;
        this.sides = sides;
    }

    private void clearValues() {
        players.clear();
        ended = false;
        message = "";
    }

    public void changeSettings(int playersNumber, boolean auto) {
        this.numberOfPlayers = playersNumber;
        this.automated = auto;
    }

    void selectGameToStart(int numberOfPlayers, boolean auto) {
        clearValues();
        changeSettings(numberOfPlayers, auto);
        if (automated) {
            startAutomatedGame();
        } else
            startManualGame();
    }

    void startAutomatedGame() {
        createPlayersAutomated();
        do {
            turnPlayers();
        } while (!this.ended);
    }

    private void startManualGame() {
        do {
            insertCommand();
        } while (!this.ended);
    }

    private void turnPlayers() {
        for (int playerI = 0; playerI < players.size(); playerI++) {
            Player player = players.get(playerI);
            if (this.ended) {
                break;
            } else {
                player.rollDice();
                movePlayer(player);
            }
        }
    }

    private void insertCommand() {
        try (Scanner scanner = new Scanner(System.in)) {
            String command = scanner.nextLine();
            scanner.close();
            executeCommand(command);
        }
    }

    public String[] executeCommand(String command) {
        String[] splitted = new String[4];
        String lowerCase = "";

        command = command.replaceAll("[\\.\\\\(\\)]|^ +", "");
        command = command.replaceAll(",|( )+", " ");
        lowerCase = command.toLowerCase();
        splitted = command.split("( )+");

        if (lowerCase.startsWith("add player")) {
            executeAddPlayer(splitted);
        } else if (lowerCase.startsWith("move")) {
            executeMove(splitted);
        } else {
            errorMessage("Command not found");
        }
        return splitted;
    }

    private void executeAddPlayer(String[] splitted) {
        String name;
        if (splitted.length == 3) {
            name = splitted[2];
            if (!searchName(name)) {
                players.add(new Player(name,numberOfDice,sides));
                message = displayPlayerList();
            } else {
                alreadyExistingNameMessage(name);
            }
        } else{
            errorMessage("Bad syntax in command 'add player'");
        }
    }

    private void executeMove(String[] splitted) {
        String name;
        name = splitted[1];
        if (splitted.length == 2 || splitted.length == 4) {
            if (searchName(name)) {
                int playerI = getPlayerID(name);
                Player player = players.get(playerI);
                if (splitted.length == 4) {
                    int tempValue = 0;
                    int[] dices = new int[player.getDiceNumber()];
                    for (int i = 0; i < 2; i++) {
                        tempValue = Integer.parseInt(splitted[i + 2]);
                        dices[i] = tempValue;
                    }
                    player.insertDice(dices);
                } else {
                    player.rollDice();
                }
                movePlayer(player);
            } else {
                errorMessage("Name not found");
            }
        } else {
            errorMessage("Bad syntax in command 'move'");
        }
    }

    void movePlayer(Player player) {
        player.countBoxes();
        countSubMessage(player);
        int box = board.applyRules(player);
        player.setPosition(box);
        this.ended = (box == 63);
        message = moveMessage(player);
    }

    private void createPlayersAutomated() {
        String name;
        for (int player = 0; player < numberOfPlayers; player++) {
            name = String.valueOf("PC " + (player + 1));
            players.add(new Player(name));
        }
        displayPlayerList();
    }

    private boolean searchName(String nameSearched) {
        String nameStored = "";
        boolean found = false;
        for (int player = 0; player < players.size(); player++) {
            nameStored = getPlayerName(player);
            if (nameStored.equalsIgnoreCase(nameSearched)) {
                found = true;
                break;
            } else {
                found = false;
            }
        }
        return found;
    }

    public String getPlayerName(int player) {
        String name = players.get(player).getName();
        return name;
    }

    public int getPlayerID(String name) {
        int player = 0;
        String nameTemp = "";
        for (player = 0; player < players.size(); player++) {
            nameTemp = getPlayerName(player);
            if (nameTemp.equalsIgnoreCase(name)) {
                break;
            }
        }
        return player;
    }
        public int getRoll(int index) {
        Player player = players.get(index);
        int roll = player.getRoll();
        return roll;
    }
    public int getPlayerPosition(String name) {
        int playerId = getPlayerID(name);
        Player player = players.get(playerId);
        return player.getPosition();
    }

    public void setPlayerPosition(String name, int position) {
        int playerId = getPlayerID(name);
        Player player = players.get(playerId);
        player.setPosition(position);
    }
 
    private String displayPlayerList() {
        String playerList = "Players: ";
        for (int player = 0; player < players.size(); player++) {
            playerList += getPlayerName(player);
            if (player != players.size() - 1) {
                playerList += ", ";
            }
        }
        this.message = playerList;
        System.out.println(playerList);
        return playerList;
    }

    private String errorMessage(String errorMessage) {
        System.err.println(errorMessage);
        this.message = errorMessage;
        return errorMessage;
    }

    private String alreadyExistingNameMessage(String name) {
        this.message = "Player " + name + " already exists. Please insert a new player.";
        System.out.println(message);
        return message;
    }

    private String countSubMessage(Player player) {
        String countSubMessage = "";
        String name = player.getName();
        countSubMessage = "Player " + name + " rolls " + player.getPlayerDice(0, player) + ","
                + player.getPlayerDice(1, player) + ". ";
        countSubMessage += name + " moves from ";
        if (player.getPosition() == 0) {
            countSubMessage += "Start";
        } else {
            countSubMessage += player.getPosition();
        }
        this.message = countSubMessage;
        return countSubMessage;
    }

    private String moveMessage(Player player) {
        String moveMessage = "";
        String name = player.getName();
        moveMessage = message + board.getMessage();
        moveMessage = moveMessage.replaceAll("NAME", name);
        this.message = moveMessage;
        // board.setMessage("");
        System.out.println(moveMessage);
        return moveMessage;
    }

    public String getMessage() {
        return message;
    }

    public boolean isEnded() {
        return ended;
    }
}