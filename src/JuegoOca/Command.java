package JuegoOca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Command {
    int action = 0;
    String name = "";
    private List<Player> players = new ArrayList<>();
    String[]splitted = new String[]{};

    void insertCommand() {
        Scanner scanner = new Scanner(System.in);
        String newCommand = scanner.nextLine();
        extractCommand(newCommand);
    }

    public int extractCommand(String command) {
        String lowerCase = "";
        String addPrefix = "add player";
        String movePrefix = "move";
        command = command.replaceAll("[\\.\\\\(\\)]|^ +", "");
        command = command.replaceAll(",|( )+", " ");
        lowerCase = command.toLowerCase();
        String[] splitted = command.split("( )+");
        this.splitted = splitted;   

        if (lowerCase.startsWith(addPrefix)) {
            if (splitted.length == 3) {
                name = splitted[2];
                action = 1;     
            } else{
                action = 3;
            }
        } else if (lowerCase.startsWith(movePrefix)) {
            if (searchName(name)) {
            action = 2;
            name = splitted[1];
            } else{
                action = 3;
            }
        } 

        return action;
    }

boolean searchName(String nameSearched) {
        String nameStored = "";
        boolean found = false;
        
        for (int player = 0; player < players.size(); player++) {
            nameStored = players.get(player).getName();
            if (nameStored.equalsIgnoreCase(nameSearched)) {
                found = true;
                break;
            } else {
                found = false;
            }
        }
        return found;
    }

     int determineMove() {
        int rollType = 0;
        if (splitted.length == 4) {
            rollType = 1;
        } else
            rollType = 0;
            return rollType;
    }

    int[] extractCommandDice(int sides) {
        int tempValue = 0;
        int[]dices = new int[2];

        for (int i = 0; i < 2; i++) {
            tempValue = Integer.parseInt(splitted[i + 2]);

            if ((tempValue <= sides) && (tempValue >= 1)) {
                dices[i] = tempValue;
            } else
                dices[i] = 1;
        }
        return dices;

    }

    void retrievePlayers(List<Player> players){
        this.players = players;
    }

    public int getAction() {
        return action;
    }
    public String getName() {
        return name;
    }
    
}
