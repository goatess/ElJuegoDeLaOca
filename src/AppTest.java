import static org.junit.Assert.assertEquals; 
import org.junit.Test;

public class AppTest {
    @Test
    public void checkPlayerNameStoring(){
        //arrange
        final String expected_player_name = "Juanito";
        App app = new App(); 
        Player player = new Player();
        
        //act
        player.setPlayer1("Juanito");

        //assert
        assertEquals(expected_player_name , player.getPlayer1());
    }

    @Test
    public void checkDuplicatePlayerNames(){
        //arrange
        final String expected_name_error_sentence = "Player juan already exists, please add player 2: ";
        //String actual_sentence ="";
        Player player = new Player();
        App app = new App(); 
        
        //act
        player.setPlayer1("juan");
        player.setPlayer2("juan");
        String player1 = player.getPlayer1();
        String player2 = player.getPlayer2();
    
        //assert
        assertEquals(player1,player2);
        assertEquals(expected_name_error_sentence, "Player " + player2 + " already exists, please add player 2: ");
    }

    @Test
    public void checkPlayerMovement(){

        //arrange
        App app = new App(); 
        Player player = new Player();
        Dice dice = new Dice();
        final String expected_movement_sentence = "Juanito (2,3) Se mueve hasta la casilla: 30"; 
       
        //act
        String actual_player = "Juanito";
        dice.setDiceValue1(2);
        dice.setDiceValue2(3);
        player.setPosition(30);

        //assert
        assertEquals(expected_movement_sentence, actual_player + " (" + dice.d1 + "," + dice.d2 +") Se mueve hasta la casilla: " + player.getPosition());
    }
}
