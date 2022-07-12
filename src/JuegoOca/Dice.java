package JuegoOca;

public class Dice {
    final int MIN = 1;
    final int MAX = 6;

    public int rollDice() {
        int diceValue = (int) Math.floor(Math.random() * (MAX - MIN + 1) + MIN);
        return diceValue;
    }
}