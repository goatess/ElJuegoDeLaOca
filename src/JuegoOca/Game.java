package JuegoOca;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    private int numberOfPlayers = 5;
    private int numberOfDices = 2;
    private boolean ended = false;
    private boolean automated = false;
    private int[] dices = new int[numberOfDices];
    List<Player> players = new ArrayList<>();
    
    private int roll = 0;
    private String moveMessage = "";
    
    Dice dice = new Dice();
    Board board = new Board();
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.changeSettings(4, 2, true);
        game.start();
        game.changeSettings(99, 2, false);
        game.start();
    }

    Game() {
        initialSetup();
        changeSettings(5,2,false);
    }

    void start() {
        initialSetup();
        if (automated) {
            startAutomatedGame();
        } else
            startManualGame();
    }

    private void initialSetup() {
        players.clear();
        ended = false;
        roll = 0;
        moveMessage = "";
    }

    public void changeSettings(int numberOfPlayers, int numberOfDices, boolean auto) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfDices = numberOfDices;
        this.automated = auto;
    }

    private void startManualGame() {
        do {
            insertCommand();
        } while (!ended);
        initialSetup();
        startManualGame();
    }

    private void insertCommand() {
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

    private void addPlayer(String name) {
        int player = players.size();
        players.add(player, new Player());
        players.get(player).setName(name);
    }

    private boolean searchName(String nameTemp) {
        String name = "";
        boolean nameFound = false;
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

    private void movePlayer(int player) {
        moveMessage(player);
        int possiblePosition = countBoxes(player);
        makeAMove(player, possiblePosition);
    }

    private int countBoxes(int player) {
        int possiblePosition = getPlayerPosition(player);
        possiblePosition += roll;
        return possiblePosition;
    }

    private void makeAMove(int player, int possiblePosition) {
        int position = board.determineMoveResult(player, possiblePosition);
        setPlayerPosition(player, position);
        boxMessage(player);
        ended = (position == 63);
    }

    private void determineDiceType(String command) {
        String onlyNumbers = command;
        onlyNumbers = onlyNumbers.replaceAll("[^0-9]", "");
        if (onlyNumbers.length() > 0) {
            extractDices(onlyNumbers);
        } else
            rollDices();
    }

    private void rollDices() {
        for (numberOfDices = 0; numberOfDices < dices.length; numberOfDices++) {
            dices[numberOfDices] = dice.rollDice();
        }
        addDicesValues(dices);
    }

    private int addDicesValues(int[] dices) {
        roll = 0;
        for (int diceNum = 0; diceNum < numberOfDices; diceNum++) {
            roll += dices[diceNum];
        }
        return roll;
    }

    private void extractDices(String command) {
        String onlyNumbers = command;
        for (int i = 0; i < 2; i++) {
            dices[i] = Integer.parseInt(onlyNumbers.substring(i, i + 1));
        }
        addDicesValues(dices);
    }

    private String extractName(String command) {
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

    private void addPlayersAutomated() {
        String[] names = { "if", "you", "can", "read", "this", "players", "are", "sorted", "ascending", "when",
                "turned" };
        for (int index = 0; index < numberOfPlayers; index++) {
            players.add(new Player());
            players.get(index).setName(names[index]);
        }
        System.out.println(displayPlayerList());
    }

    private void gameLoop() {
        do {
            turn();
        } while (!ended);

    }

    private void turn() {
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

    private String displayPlayerList() {
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

    private String moveMessage(int player) {
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
        return roll;
    }
    
    public String getMoveMessage() {
        return moveMessage;
    }
    
    public boolean isEnded() {
        return ended;
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
        result = prime * result + ((moveMessage == null) ? 0 : moveMessage.hashCode());
        result = prime * result + numberOfDices;
        result = prime * result + numberOfPlayers;
        result = prime * result + ((players == null) ? 0 : players.hashCode());
        result = prime * result + roll;
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
        if (moveMessage == null) {
            if (other.moveMessage != null)
                return false;
        } else if (!moveMessage.equals(other.moveMessage))
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
        if (roll != other.roll)
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
                + Arrays.toString(dices) + ", ended=" + ended + ", moveMessage=" + moveMessage + ", numberOfDices=" + numberOfDices + ", numberOfPlayers=" + numberOfPlayers + ", players="
                + players + ", roll=" + roll + ", scanner=" + scanner + "]";
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