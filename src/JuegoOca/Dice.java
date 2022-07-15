package JuegoOca;

public class Dice {
    private int sides = 6;
    private int numberOfDie = 2;
    private int[] dice = new int[numberOfDie];

    Dice() {
    }
    
    Dice(int number, int sides){
        this.sides = sides;
        this.numberOfDie = number;
    }

    private int randomRoll() {
        int dieResult = 0;
        dieResult = (int) Math.floor(Math.random() * sides + 1);
        return dieResult;
    }

    private int checkDieInRange(int value) {
        int dieResult = 0;
        if ((value <= sides) && (value >= 1)) {
            dieResult = value;
        } else {
            dieResult = 1;
        }
        return dieResult;
    }

    int roll() {
        int rollResult = 0;
        for (int num = 0; num < dice.length; num++) {
            dice[num] = randomRoll();
            rollResult +=dice[num];
        }
        return rollResult;
    }

    int checkDice(int[] commandDice) {
        int rollResult = 0;
        for (int num = 0; num < commandDice.length; num++) {
            dice[num] = checkDieInRange(commandDice[num]);
            rollResult +=dice[num];
        }
        return rollResult;
    }

    public int getNumberOfDie() {
        return numberOfDie;
    }

   

    public int[] getDiceValues() {
        return dice;
    }

    public int getSides() {
        return sides;
    }

    public int getValue(int index) {
        return dice[index];
    }

    public void setNumber(int numberOfDies) {
        this.numberOfDie = numberOfDies;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    public int[] getDice() {
        return dice;
    }
}