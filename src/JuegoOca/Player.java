package JuegoOca;

public class Player {
    String name = "";
    int position = 0;
    
    Player(){
    }   

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}