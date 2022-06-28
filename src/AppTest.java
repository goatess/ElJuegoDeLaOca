import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertFalse;
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
        final String expected_sentence = "Player juan already exists, please add player 2: ";
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
        assertEquals(expected_sentence, "Player " + player2 + " already exists, please add player 2: ");
    }
}
