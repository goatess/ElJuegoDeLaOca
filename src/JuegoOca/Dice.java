package JuegoOca;

public class Dice {
    private int sides = 6;
    private int value = 1;

    Dice() {
        sides = 6;
        value = 1;
    }

    public Dice(int sides) {
        this.sides = sides;
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