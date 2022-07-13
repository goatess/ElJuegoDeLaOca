package JuegoOca;

public class Dice {
    private int sides = 6;
    private int value = 1;

    Dice() {
        int value = randomRoll();
        this.value = value;
    }
        Dice(int value) {
        this.value = value;

    }

    public int randomRoll() {
        value = (int) Math.floor(Math.random() * sides + 1);
        return value;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    public int getSides() {
        return sides;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int diceValue) {
        this.value = diceValue;
    }
}