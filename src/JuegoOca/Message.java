package JuegoOca;

import java.util.List;

public class Message {
    String message = "";
    private List<Player> players;

    Message(String error) {
        System.err.println(error);
    }

    Message() {
        this.message = "";
    }

    public String displayPlayerList(List<Player> players) {
        this.players = players;
        String playerList = "Players: ";
        for (int player = 0; player < players.size(); player++) {
            playerList += players.get(player).getName();
            if (player != players.size() - 1) {
                playerList += ", ";
            }
        }
        this.message = playerList;
        System.out.println(playerList);
        return playerList;
    }

    public String alreadyExistingNameMessage(String name) {
        this.message = "Player " + name + " already exists. Please insert a new player.";
        System.out.println(message);
        return message;
    }

    String countSubMessage(int player, int[] dices, int position) {
        String countSubMessage = "";
        String name = players.get(player).getName();
        countSubMessage = "Player NAME rolls " + dices[0] + "," + dices[1] + ". ";
        countSubMessage += "NAME moves from ";
        if (position == 0) {
            countSubMessage += "Start";
        } else {
            countSubMessage += position;
        }
        countSubMessage = countSubMessage.replaceAll("NAME", name);
        this.message = countSubMessage;
        return countSubMessage;
    }

    String moveMessage(int player, String boardMessage) {
        String movePlayerMessage = "";

        String name = players.get(player).getName();
        movePlayerMessage = message + boardMessage;
        this.message = movePlayerMessage.replaceAll("NAME", name);
        // board.setMessage("");
        System.out.println(movePlayerMessage);
        return movePlayerMessage;
    }

    String badSyntaxMessage() {
        String badSyntaxMessage = "Bad syntax";
        this.message = badSyntaxMessage;
        System.err.println(message);
        return message;
    }

    public String getMessage() {
        return message;
    }

    public void setPrintError(String message) {
        this.message = message;
        System.err.println(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
