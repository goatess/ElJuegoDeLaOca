package JuegoOca;

public class Board {
    final int END_POSITION = 63;
    final int BRIDGE_POSITION = 6;

    boolean ended = false;
    String message = "";

    int makeAMove(int player, int position) {
        if (position == BRIDGE_POSITION) {
            position = 12;
            message = " to The Bridge! NAME jumps to 12";
        } 
            if (position > END_POSITION) {
                position = END_POSITION - (position - END_POSITION);
                message += " to " + END_POSITION + ". NAME bounces! NAME Returns to POSITION";
            } else {
                message += " to POSITION";
            }
            if (position == END_POSITION) {
                message += ". NAME Wins!!";
                ended = true;
            }
        
        return position;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEnded() {
        return ended;
    }
}
