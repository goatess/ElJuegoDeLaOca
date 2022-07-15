package JuegoOca;

public class Player {
    private String name = "";
    private int box = 0;
    private int count;
    private int rollResult;
    // DICE ATTRIBUTES
    private int diceNumber = 2;
    private int diceSides = 6;
    private Dice dice = new Dice(diceNumber,diceSides);

    public Player(String name) {
        this.name = name;
        this.box = 0;
        this.count = 0;
        this.rollResult = 0;
    }

       public Player(String name, int number, int sides) {
        this.name = name;
        this.box = 0;
        this.count = 0;
        this.rollResult = 0;
        this.diceNumber = number;
        this.diceSides = sides;
    }

    int rollDice() {
        rollResult = dice.roll();
        return rollResult;
    }

    int insertDice(int[] commandNumbers) {
        rollResult = dice.checkDice(commandNumbers);
        return rollResult;
    }

    int countBoxes() {
        int boxCounted = box + rollResult;
        this.count = boxCounted;
        return boxCounted;
    }


    public int getPlayerDice(int index, Player player) {
        int value = player.dice.getValue(index);
        return value;
    }

    public int getDiceNumber() {
        int NumberOfDice = dice.getNumberOfDie();
        return NumberOfDice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(int position) {
        this.box = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return box;
    }

    public int getCount() {
        return count;
    }

    public int getRoll() {
        return rollResult;
    }

}