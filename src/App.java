import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // Initialize game basic parameters
        String actual_player = "";
        int turn = 2;
        boolean isFinished = false;
        int finishLine = 63;
        int puente = 6;
        int new_position = 0;
        int position1 = 0;
        int position2 = 0;
        // Add players and create dice
        Dice dice = new Dice();
        Roll roll = new Roll();
        Player player = new Player();
        System.out.println("Add player 1: ");
        String player1 = player.addPlayer();
        System.out.println("add player 2 : ");
        String player2 = player.addPlayer();

        //Checks if 2 different players are inserted
        if (player1.equals(player2)){
            System.out.println("Player " + player1 + " already exists, please add player 2 : ");
            player2 = player.addPlayer();
        }
        System.out.println("Players: " + player1 + " and " + player2);
        
        //Main loop until game finishes
        for (isFinished = false; isFinished == false;){
            
            // Turn loop : player iteration and storing variables before next move
            if (turn == 1){
                actual_player = player2;
                //guardo la posicion del otro jugador antes de perderla
                position2 = player.getPosition();
                //llamo a la posicion de este
                new_position = position1;
                //cambio el turno para la proxima vez que llegue aqui el proceso
                turn = 2;
            } 
            else {
                actual_player = player1;
                position1 = player.getPosition();
                new_position = position2;
                turn = 1;
            }  

            // Rolling dices
            roll.pressEnterKeyToContinue();
            int d1 = dice.diceRoll();
            dice.setDiceValue1(d1);
            int d2 = dice.diceRoll();
            dice.setDiceValue2(d2);            
            new_position += dice.getDiceAdding();

            // Moving players
            if (new_position < finishLine){
                if (new_position == puente){
                    player.setPosition(new_position + puente);
                    System.out.println(actual_player + " (" + d1 + ", " + d2 + ") De puente a puente, se mueve donde le lleva la corriente, hasta la casilla: " + player.getPosition());
                }else {
                    player.setPosition(new_position);
                    System.out.println(actual_player + " (" + d1 + ", " + d2 + ") Se mueve hasta la casilla: " + player.getPosition());
                }
            }else if (new_position > finishLine) {
                new_position = finishLine - (new_position - finishLine);
                player.setPosition(new_position);
                System.out.println(actual_player + " (" + d1 + ", " + d2 + ") ¡REBOTE! Se mueve hasta la casilla: " + player.getPosition());
            }else {
                System.out.println(actual_player + "(" + d1 + ", " + d2 + ") Casilla 63! " + actual_player + " WINS!");
                isFinished = true;
            }
        }         
    } 
}
    class Player {
    String newName = "";
    String player1;
    String player2;
    int position_temp = 0;

    String addPlayer(){
        Scanner scanner = new Scanner(System.in);
        newName = scanner.nextLine();
        return newName;    
    }
    public String getPlayer1(){
        return player1;
    }
    public void setPlayer1(String player1){
        this.player1 = player1;
    }
     public String getPlayer2(){
        return player2;
    }
    public void setPlayer2(String player2){
        this.player2 = player2;
    }
    public void setPosition(int position){
        this.position_temp = position;
    }
    public int getPosition(){
        return position_temp;
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
class Roll {
    public void pressEnterKeyToContinue(){ 
        System.out.println("Press Enter key to continue...");
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }
}

