package JuegoOca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {

    private int numberOfPlayers = 4;
    private int numberOfDices = 2;
    private boolean ended = false;
    private boolean automated = false;
    private String message = "";
    private int[] dices = new int[numberOfDices];
    List<Player> players = new ArrayList<>();

    Dice dice = new Dice();
    Board board = new Board();
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.selectGameToStart(4, 2, true, 6);
        game.selectGameToStart(99, 2, false, 6);
    }

    Game() {
        clearValues();
        changeSettings(4, 2, false, 6);
    }

    public Game(int numberOfPlayers, int numberOfDices, boolean automated, int sides) {
        clearValues();
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfDices = numberOfDices;
        this.automated = automated;
        this.dice.setSides(sides);
    }

    private void clearValues() {
        players.clear();
        ended = false;
        message = "";
    }

    public void changeSettings(int numberOfPlayers, int numberOfDices, boolean auto, int sides) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfDices = numberOfDices;
        this.automated = auto;
        this.dice.setSides(sides);

    }

    void selectGameToStart(int numberOfPlayers, int numberOfDices, boolean auto, int sides) {
        clearValues();
        changeSettings(numberOfPlayers, numberOfDices, auto, sides);
        if (automated) {
            startAutomatedGame();
        } else
            startManualGame();
    }

    void startAutomatedGame() {
        createPlayersAutomated();
        do {
            turn();
        } while (!ended);
    }

    private void startManualGame() {
        do {
            insertCommand();
        } while (!ended);
        startManualGame();
    }

    private void turn() {
        for (int player = 0; player < players.size(); player++) {
            if (ended) {
                break;
            } else {
                rollRandomValues();
                makeAMove(player);
            }
        }
    }

    private void insertCommand() {
        String command = scanner.nextLine();
        executeCommand(command);
    }

    public String[] executeCommand(String command) {
        String[] splitted = new String[4];
        String lowerCase = "";
        String name = "";

        command = command.replaceAll("[\\.\\\\(\\)]|^ +", "");
        command = command.replaceAll(",|( )+", " ");
        lowerCase = command.toLowerCase();
        splitted = command.split("( )+");
        
        if (lowerCase.startsWith("add player")) {
            name = splitted[2];
            createPlayer(name);
        } else if (lowerCase.startsWith("move")) {
            name = splitted[1];
            useMoveCommand(splitted, name);
        } else
           insertCommand();
    
        return splitted;
    }

    private void useMoveCommand(String[] splitted, String name) {
        int player = getPlayer(name);
        determineRoll(splitted);
        makeAMove(player);
    }

    private String createPlayer(String name) {
        boolean nameFound = searchName(name);
        if (!nameFound) {
            players.add(new Player(name));
            message = displayPlayerList();
        } else {
            message = alreadyExistingNameMessage(name);
        }
        return message;
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

    private void determineRoll(String[] splittedCommand) {
        if (splittedCommand.length > 2) {
            extractCommandValues(splittedCommand);
        } else
            rollRandomValues();
    }

    private void extractCommandValues(String[] splittedCommand) {
        int tempValue = 0;

        for (int i = 0; i < 2; i++) {
            tempValue = Integer.parseInt(splittedCommand[i+2]);

            if ((tempValue <= dice.getSides()) && (tempValue >= 1)) {
                dices[i] = tempValue;
            } else
                dices[i] = 1;
        }
        addValues(dices);
    }

    private void rollRandomValues() {
        for (numberOfDices = 0; numberOfDices < dices.length; numberOfDices++) {
            dices[numberOfDices] = dice.randomRoll();
        }
        addValues(dices);
    }

    private int addValues(int[] dices) {
        int entireRoll = 0;
        for (int diceNum = 0; diceNum < numberOfDices; diceNum++) {
            entireRoll += dices[diceNum];
        }
        return entireRoll;
    }

    private void makeAMove(int player) {
        int possibleBox = countBoxes(player);
        movePlayer(player, possibleBox);
    }

    private int countBoxes(int player) {
        int possibleBox = getPlayerPosition(player);
        possibleBox += addValues(dices);
        countSubMessage(player);
        return possibleBox;
    }

    private void movePlayer(int player, int possibleBox) {
        int box = board.determineMoveResult(player, possibleBox);
        setPlayerPosition(player, box);
        ended = (box == 63);
        moveMessage(player);
    }

    public String getPlayerName(int player) {
        String name = players.get(player).getName();
        return name;
    }

    public int getPlayer(String name) {
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
    // MESSAGES

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

    private String alreadyExistingNameMessage(String name) {
        this.message = "Player " + name + " already exists. Please insert a new player.";
        System.out.println(message);
        return message;
    }

    private String countSubMessage(int player) {
        String countSubMessage = "";
        countSubMessage = "Player NAME rolls " + dices[0] + "," + dices[1] + ". ";
        countSubMessage += "NAME moves from ";
        if (getPlayerPosition(player) == 0) {
            countSubMessage += "Start";
        } else {
            countSubMessage += getPlayerPosition(player);
        }
        countSubMessage = countSubMessage.replaceAll("NAME", getPlayerName(player));
        this.message = countSubMessage;
        return countSubMessage;
    }

    private String moveMessage(int player) {
        String movePlayerMessage = "";
        String name = getPlayerName(player);
        movePlayerMessage = message + board.getOutputMessage();
        this.message = movePlayerMessage.replaceAll("NAME", name);
        board.setMessage("");
        System.out.println(movePlayerMessage);
        return movePlayerMessage;
    }

    // GETTERS & SETTERS

    public int getPlayerPosition(int player) {
        return players.get(player).getPosition();
    }

    public void setPlayerPosition(int player, int position) {
        players.get(player).setPosition(position);
    }

    public String getMessage() {
        return message;
    }

    public boolean isEnded() {
        return ended;
    }

    public int getDice0() {
        return dices[0];
    }

    public int getDice1() {
        return dices[1];
    }

    // OVERRIDE - HASH CODE & EQUALS

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (automated ? 1231 : 1237);
        result = prime * result + ((board == null) ? 0 : board.hashCode());
        result = prime * result + ((dice == null) ? 0 : dice.hashCode());
        result = prime * result + Arrays.hashCode(dices);
        result = prime * result + (ended ? 1231 : 1237);
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + numberOfDices;
        result = prime * result + numberOfPlayers;
        result = prime * result + ((players == null) ? 0 : players.hashCode());
        result = prime * result + ((scanner == null) ? 0 : scanner.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Game other = (Game) obj;
        if (automated != other.automated)
            return false;
        if (board == null) {
            if (other.board != null)
                return false;
        } else if (!board.equals(other.board))
            return false;
        if (dice == null) {
            if (other.dice != null)
                return false;
        } else if (!dice.equals(other.dice))
            return false;
        if (!Arrays.equals(dices, other.dices))
            return false;
        if (ended != other.ended)
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (numberOfDices != other.numberOfDices)
            return false;
        if (numberOfPlayers != other.numberOfPlayers)
            return false;
        if (players == null) {
            if (other.players != null)
                return false;
        } else if (!players.equals(other.players))
            return false;
        if (scanner == null) {
            if (other.scanner != null)
                return false;
        } else if (!scanner.equals(other.scanner))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Game [automated=" + automated + ", board=" + board + ", dice=" + dice + ", dices="
                + Arrays.toString(dices) + ", ended=" + ended + ", message=" + message + ", numberOfDices="
                + numberOfDices + ", numberOfPlayers=" + numberOfPlayers + ", players="
                + players + ", scanner=" + scanner + "]";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }
}