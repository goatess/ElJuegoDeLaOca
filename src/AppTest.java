import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class AppTest {
    @Test
    public void checkPlayerNames(){
        //arrange
        App app = new App(); 
        Player player = new Player();
 
       //act
        String jugador1 = player.getPlayer1();
        String jugador2 = player.getPlayer2();

        //assert
        assertFalse(jugador1 != jugador2);
    
    }

        @Test
    public void checkMainLoop(){
        //arrange
        App app = new App(); 
        // final String FULL_SONG; 
        //act
        //Player player = new Player();
    
        //assert
        // assertEquals(app.actualPosition, 63);
}
}
