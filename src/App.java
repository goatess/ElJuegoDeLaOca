import java.util.Scanner;
import java.math.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Add players
        Player player = new Player();
        System.out.println("Add player 1: ");
        String player1 = player.addPlayer();
        System.out.println("add player 2 : ");
        String player2 = player.addPlayer();
        if (player1.equals(player2)){
            System.out.println("Player " + player1 + " already exists, please add player 2 : ");
            player2 = player.addPlayer();
        }
        System.out.println("Players: " + player1 + " and " + player2);

        // Basic Rules
        
        // Rolling dice MAIN loop 
        int finishLine = 63;
        int current_player = 1;
        boolean turn_change = true;
        Dice dice = new Dice();
        for (int actualPosition = 1; actualPosition != finishLine;){
            int position1 = 1;
            int position2 = 1; 
            int d1 = dice.diceRoll();
            dice.setDiceValue1(d1);
            int d2 = dice.diceRoll();
            dice.setDiceValue2(d2);
            int totalRoll = dice.getDiceAdding();
            
            // Turn check system
            if (current_player == 1 && turn_change == true){
                current_player = 2;
                position2 = actualPosition += totalRoll;
                turn_change = false;
            }else if (current_player == 2 && turn_change == true){
                current_player = 1;
                position1 = actualPosition += totalRoll;
                turn_change = false;
            }

            // Game checks if your roll wins or you move and stores position 
            if (actualPosition < finishLine){
                System.out.println(player.newName + " (" + d1 + ", " + d2 + ") Se mueve hasta la casilla: " + actualPosition);
                turn_change = true;
            }else if (actualPosition > finishLine) {
                actualPosition = finishLine - (actualPosition - finishLine);
                System.out.println(player.newName + " (" + d1 + ", " + d2 + ") Se mueve hasta la casilla: " + actualPosition);
                turn_change = true;
            }else System.out.println("(" + d1 + ", " + d2 + ") Casilla 63! " + current_player + " WIN!");
        }   
    }
}

class Player {
    String newName = "";
    String addPlayer(){
        Scanner scanner = new Scanner(System.in);
        newName = scanner.nextLine();
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