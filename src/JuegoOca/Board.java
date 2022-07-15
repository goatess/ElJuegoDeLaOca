package JuegoOca;

public class Board {
    final int END_BOX = 63;
    final int BRIDGE_BOX = 6;
    final String END_MESSAGE = ". NAME Wins!!";
    final String BOUNCE_MESSAGE = " to " + END_BOX + ". NAME bounces! NAME Returns";
    final String BRIDGE_MESSAGE = " to The Bridge! NAME jumps";
    final String TO = " to ";
    private int box;
    private boolean ended;
    private String outputMessage;

    Board() {
        this.ended = false;
}

    int applyRules(Player player) {
        outputMessage = "";
        int count = player.getCount();
        if (count == BRIDGE_BOX) {
            box = 12;
            outputMessage = BRIDGE_MESSAGE;
        } else if (count > END_BOX) {
            box = END_BOX - (count - END_BOX);
            outputMessage = BOUNCE_MESSAGE;
        } else {
            box = count;
        }
        outputMessage += TO + box;
        if (count == END_BOX) {
            outputMessage += END_MESSAGE;
            ended = true;
        }

        return box;

    }

    public String getMessage() {
        return outputMessage;
    }

    public boolean isEnded() {
        return ended;
    }

}
