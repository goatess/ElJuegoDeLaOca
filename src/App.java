import java.util.Scanner;
import java.math.*;

public class App {
    public static void main(String[] args) throws Exception {
        //Add players
        System.out.println("Add player 1: ");
        Player player = new Player();
        String player1 = player.addPlayer();
        System.out.println("add player 2 : ");
        String player2 = player.addPlayer();
        System.out.println("Players: " + player1 + " and " + player2);
        
        int finishLine = 63;
        boolean turn_change = false;
        String current_player = player1;
        
        // Rolling dice loop and storing positions game loop
        Dice dice = new Dice();
        for (int actualPosition = 1; actualPosition != finishLine;){
            int d1 = dice.diceRoll();
            dice.setDiceValue1(d1);
            int d2 = dice.diceRoll();
            dice.setDiceValue2(d2);
            System.out.println("Current Player: " + current_player);
            
            // Turn check system
            if (current_player == player1 && turn_change == true){
                current_player = player2;
                turn_change = false;
            }else if (current_player == player2 && turn_change == true){
                current_player = player1;
                turn_change = false;
            }
            
            // Game checks if you win or you move
            if (actualPosition < finishLine){
                actualPosition = player.getActualPosition();
                player.setActualPosition(actualPosition + dice.getDiceAdding());
                System.out.println("(" + d1 + ", " + d2 + ") Se mueve hasta la casilla: " + actualPosition);
                turn_change = true;
            }else if (actualPosition > finishLine) {
                actualPosition = player.getActualPosition();
                player.setActualPosition(actualPosition + dice.getDiceAdding());
                actualPosition = finishLine - (actualPosition - finishLine);
                player.setActualPosition(actualPosition);
                System.out.println("(" + d1 + ", " + d2 + ") Se mueve hasta la casilla: " + actualPosition);
                turn_change = true;
            }
            else System.out.println("(" + d1 + ", " + d2 + ") Casilla 63! " + current_player + " WIN!");
        }   
    }
}

class Player {
    String newName = "";
    int actualPosition = 1;
    String addPlayer(){
        Scanner scanner = new Scanner(System.in);
        newName = scanner.nextLine();
        return newName;
    }
    public void setActualPosition(int actualPosition){
        this.actualPosition = actualPosition;
    }
    public int getActualPosition(){
        return actualPosition;
    }
    public String getNewName(){
        return newName;
    }
}

class Dice {
    int max = 6;
    int min = 1;
    int d1 = 1;
    int d2 = 1;
    
    int diceRoll(){
        //Generate random int value from 1 to 6 
        int random_int = (int)Math.floor(Math.random()*(max - min + 1) + min);
        return random_int;
    }
    public void setDiceValue1(int d1){
        this.d1 = d1;
    }
    public void setDiceValue2(int d2){
        this.d2 = d2;
    }
    public int getDiceAdding(){
        int diceAdding = d1 + d2;
        return diceAdding;
    }
}