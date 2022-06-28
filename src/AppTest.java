import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class AppTest {
    @Test
    public void checkPlayerNames(){
        //arrange
        String expected_player_name = "Juanito";
        App app = new App(); 
        Player player = new Player();
        
        //act
        player.setPlayer1("Juanito");

        //assert
        assertEquals(expected_player_name , player.getPlayer1());
    }

        @Test
    public void checkMainLoop(){
        //arrange
        String expected_sentence = "Player juan already exists, please add player 2 :";
        Player player = new Player();
        App app = new App(); 
        
        //act
        player.setPlayer1("juan");
        player.setPlayer2("juan");
    
        //assert
        assertEquals(expected_sentence, "Player juan already exists, please add player 2 :");
    }
}
