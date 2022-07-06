import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AppTest {
    @Test
    public void a_player_gets_added() {

        // arrange
        final String expectedPlayer = "Sara";
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        String actualPlayer = newGame.getPlayer(0);

        // assert
        assertEquals(expectedPlayer, actualPlayer);
    }

        @Test
    public void a_player_gets_added_message() {

        // arrange
        final String EXPECTED_MESSAGE = "Players: Sara";
        Game newGame = new Game();

        // act
        String actualMessage = newGame.player.addPlayer("add player Sara");

        // assert
        assertEquals(EXPECTED_MESSAGE, actualMessage);
    }
    // message player

    @Test
    public void cant_add_same_player_again() {

        // arrange
        final int expectedSize = 2;
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.player.addPlayer("add player Juan");
        newGame.player.addPlayer("add player Juan");
        int actualSize = newGame.player.playerList.size();

        // assert
        assertEquals(expectedSize, actualSize);
    }

       @Test
    public void cant_add_same_player_again_message() {

        // arrange
        final String EXPECTED_MESSAGE = "Player Juan already exists. Please insert a new player.";
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.player.addPlayer("add player Juan");
        String actualMessage = newGame.player.addPlayer("add player Juan");

        // assert
        assertEquals(EXPECTED_MESSAGE, actualMessage);
    }

          @Test
    public void player_list_message() {

        // arrange
        final String EXPECTED_MESSAGE = "Players: Sara, Juan, Pepito";
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.player.addPlayer("add player Juan");
        String actualMessage = newGame.player.addPlayer("add player Pepito");

        // assert
        assertEquals(EXPECTED_MESSAGE, actualMessage);
    }


    @Test
    public void positions_are_overwritten() {

        // arrange
        final int expectedPosition = 6;
        Game newGame = new Game();

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
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.gameBoard.manageBoxes(0, 8);
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(expectedPosition, actualPosition);
    }

    @Test
    public void move_players_message() {

        // arrange
        final String EXPECTED_MESSAGE = "NOMBRE moves from Start to 8";
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        String actualMessage= newGame.gameBoard.manageBoxes(0, 8);
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(EXPECTED_MESSAGE, actualMessage);
    }
    
    @Test
    public void move_players_manually_with_command() {

        // arrange
        final int expectedPosition = 7;
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");

        newGame.manualRoll("move Sara 4, 3");
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(expectedPosition, actualPosition);
    }

        @Test
    public void move_players_manually_with_command_MESSAGE() {

        // arrange
        final String EXPECTED_MESSAGE = "Player Sara rolls 4,3. Sara moves from Start to 7";
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");

        String actualMessage = newGame.manualRoll("move Sara 4, 3");

        // assert
        assertEquals(EXPECTED_MESSAGE, actualMessage);
    }

    @Test
    public void moves_past_final_position_and_bounces() {

        // arrange
        final int expectedPosition = 60;
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.setPosition(0, 58);
        newGame.manualRoll("move Sara 6, 2");
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(expectedPosition, actualPosition);
    }


    @Test
    public void moves_past_final_position_and_bounces_message() {

        // arrange
        final String EXPECTED_MESSAGE = "Player Sara rolls 6,2. Sara moves from 58 to 63. Sara bounces! Sara Returns to 60";
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.setPosition(0, 58);
        String actualMessage = newGame.manualRoll("move Sara 6, 2");

        // assert
        assertEquals(EXPECTED_MESSAGE, actualMessage);
    }

    @Test
    public void moves_to_bridge_and_goes_to_box_num_12() {

        // arrange
        final int expectedPosition = 12;
        Game game = new Game();

        // act
        game.player.addPlayer("add player Sara");
        game.manualRoll("move Sara 4, 2");
        int actualPosition = game.getPosition(0);

        // assert
        assertEquals(expectedPosition, actualPosition);
    }

        @Test
    public void moves_to_bridge_and_goes_to_box_num_12_message() {

        // arrange
        final String EXPECTED_MESSAGE = "Player Sara rolls 4,2. Sara moves from Start to The Bridge. Sara jumps to 12";
        Game game = new Game();

        // act
        game.player.addPlayer("add player Sara");
        String actualString= game.manualRoll("move Sara 4, 2");

        // assert
        assertEquals(EXPECTED_MESSAGE, actualString);
    }

        @Test
    public void moves_to_finish_box() {

        // arrange
        final int expectedPosition = 63;
        Game game = new Game();

        // act
        game.player.addPlayer("add player Sara");
        game.setPosition(0, 58);
        game.manualRoll("move Sara 3, 2");
        int actualPosition = game.getPosition(0);

        // assert
        assertEquals(expectedPosition, actualPosition);
    }

        @Test
    public void moves_to_finish_message() {

        // arrange
        final String EXPECTED_MESSAGE = "Player Sara rolls 3,2. Sara moves from 58 to 63. Sara Wins!!";
        Game game = new Game();

        // act
        game.player.addPlayer("add player Sara");
         game.setPosition(0, 58);
        String actualString= game.manualRoll("move Sara 3, 2");

        // assert
        assertEquals(EXPECTED_MESSAGE, actualString);
    }

    @Test
    public void dice_roll() {

        // arrange
        Game game = new Game();

        // act
        game.player.addPlayer("add player Sara");
        game.diceRoll("move Sara");
        int actualPosition = game.getPosition(0);
        boolean actualBool = actualPosition > 0;

        // assert
        assertEquals(true, actualBool);
    }
}