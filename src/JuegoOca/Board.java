package JuegoOca;

public class Board {
    final int END_BOX = 63;
    final int BRIDGE_BOX = 6;
    final String END_MESSAGE = ". NAME Wins!!";
    final String BOUNCE_MESSAGE = " to " + END_BOX + ". NAME bounces! NAME Returns";
    final String BRIDGE_MESSAGE = " to The Bridge! NAME jumps";
    final String TO = " to ";
    private boolean ended = false;
    private String outputMessage = "";

    Board() {
    }

    int determineMoveResult(int player, int position) {
        if (position == BRIDGE_BOX) {
            position = 12;
            outputMessage = BRIDGE_MESSAGE;
        } else if (position > END_BOX) {
            position = END_BOX - (position - END_BOX);
            outputMessage = BOUNCE_MESSAGE;
        } 
        outputMessage += TO + position;
        if (position == END_BOX) {
            outputMessage += END_MESSAGE;
            ended = true;
        }
        return position;
    }

    public String getMessage() {
        return outputMessage;
    }

    public void setMessage(String message) {
        this.outputMessage = message;
    }

    public boolean isEnded() {
        return ended;
    }

}
