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
    private int[] dices = new int[numberOfDices];
    List<Player> players = new ArrayList<>();

    Dice dice = new Dice();
    Board board = new Board();
    Scanner scanner = new Scanner(System.in);
    Message messages = new Message();
    Command command = new Command();

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
            command.insertCommand();
        } while (!ended);
        startManualGame();
    }

    private void turn() {
        for (int player = 0; player < players.size(); player++) {
            if (ended) {
                break;
            } else {
                rollRandomDice();
                makeAMove(player);
            }
        }
    }

    void executeCommand(String newCommand) {
        int action = command.extractCommand(newCommand);
        if (action == 1) {
            createPlayer(command.getName());
        } else if (action == 2) {
            movePlayer(command.getName());
        } else if (action == 3) {
            messages.badSyntaxMessage();
        }
    }

    private void createPlayer(String name) {
        boolean nameFound = command.searchName(name);
        if (!nameFound) {
            players.add(new Player(name));
            messages.displayPlayerList(players);
            command.retrievePlayers(players);
        } else {
            messages.alreadyExistingNameMessage(name);
        }
    }

    private void createPlayersAutomated() {
        String name;
        for (int player = 0; player < numberOfPlayers; player++) {
            name = String.valueOf("PC " + (player + 1));
            players.add(new Player(name));
        }
        messages.displayPlayerList(players);
        command.retrievePlayers(players);
    }

    private void movePlayer(String name) {
        int player = getPlayer(name);
        int rollType = command.determineMove();
        if (rollType == 1) {
            rollCommandDice();
        } else {
            rollRandomDice();
        }
        makeAMove(player);
    }

    private void rollCommandDice() {
        dices = command.extractCommandDice(dice.getSides());
        addValues(dices);
    }

    private void rollRandomDice() {
        for (numberOfDices = 0; numberOfDices < dices.length; numberOfDices++) {
            dices[numberOfDices] = dice.randomRoll();
        }
        addValues(dices);
    }

    private void makeAMove(int player) {
        int possibleBox = countBoxes(player);
        movePlayer(player, possibleBox);
    }

    private int addValues(int[] dices) {
        int entireRoll = 0;
        for (int diceNum = 0; diceNum < numberOfDices; diceNum++) {
            entireRoll += dices[diceNum];
        }
        return entireRoll;
    }

    private int countBoxes(int player) {
        int actualPosition = getPlayerPosition(player);
        int possibleBox = actualPosition + addValues(dices);
        messages.countSubMessage(player, dices, actualPosition);
        return possibleBox;
    }

    private void movePlayer(int player, int possibleBox) {
        int box = board.determineMoveResult(player, possibleBox);
        String boardMessage = "";
        setPlayerPosition(player, box);
        ended = (box == 63);
        boardMessage = board.getOutputMessage();
        board.setMessage("");
        messages.moveMessage(player, boardMessage);
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

    // GETTERS & SETTERS

    public int getPlayerPosition(int player) {
        return players.get(player).getPosition();
    }

    public void setPlayerPosition(int player, int position) {
        players.get(player).setPosition(position);
    }

    public String getMessage() {
        return messages.getMessage();
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

    public List<Player> getPlayers() {
        return players;
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
                + Arrays.toString(dices) + ", ended=" + ended + ", numberOfDices="
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