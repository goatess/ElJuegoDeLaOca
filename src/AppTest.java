import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AppTest {
    @Test
    public void a_player_gets_added() {

        // arrange
        final String expectedPlayer = "Sara";
        NewGame newGame = new NewGame();

        // act
        newGame.player.addPlayer("add player Sara");
        String actualPlayer = newGame.getPlayer(0);

        // assert
        assertEquals(expectedPlayer, actualPlayer);
    }
    // message player

    @Test
    public void cant_add_same_player_again() {

        // arrange
        final int expectedSize = 2;
        NewGame newGame = new NewGame();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.player.addPlayer("add player Juan");
        newGame.player.addPlayer("add player Juan");
        int actualSize = newGame.player.playerList.size();

        // assert
        assertEquals(expectedSize, actualSize);
    }
    // message error

    @Test
    public void initial_positions_are_set_up() {

        // arrange
        final int expectedPosition = 0;
        NewGame newGame = new NewGame();

        // act
        newGame.player.addPlayer("add player Sara");
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(expectedPosition, actualPosition);
    }

    @Test
    public void positions_are_overwritten() {

        // arrange
        final int expectedPosition = 6;
        NewGame newGame = new NewGame();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.setPosition(0, 6);
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(expectedPosition, actualPosition);
    }

    @Test
    public void move_players() {

        // arrange
        final int expectedPosition = 8;
        NewGame newGame = new NewGame();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.gameBoard.makeAMove(0, 8);
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(expectedPosition, actualPosition);
    }

    @Test
    public void move_players_with_command() {

        // arrange
        final int expectedPosition = 7;
        NewGame newGame = new NewGame();

        // act
        newGame.player.addPlayer("add player Sara");

        newGame.commandRoll("move Sara 4, 3");
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(expectedPosition, actualPosition);
    }

    @Test
    public void moves_and_bounces() {

        // arrange
        final int expectedPosition = 60;
        NewGame newGame = new NewGame();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.setPosition(0, 58);
        newGame.commandRoll("move Sara 6, 2");
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(expectedPosition, actualPosition);
    }

    @Test
    public void moves_to_bridge() {

        // arrange
        final int expectedPosition = 12;
        NewGame newGame = new NewGame();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.commandRoll("move Sara 4, 2");
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(expectedPosition, actualPosition);
    }

    @Test
    public void dice_roll() {

        // arrange
        NewGame newGame = new NewGame();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.diceRoll("move Sara");
        int actualPosition = newGame.getPosition(0);
        boolean actualBool = actualPosition > 0;

        // assert
        assertEquals(true, actualBool);
    }
}