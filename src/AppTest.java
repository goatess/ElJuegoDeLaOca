import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AppTest {
    @Test
    public void a_player_gets_added() {

        // arrange
        final String EXPECTED_PLAYER = "Sara";
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        String actualPlayer = newGame.getPlayer(0);

        // assert
        assertEquals(EXPECTED_PLAYER, actualPlayer);
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

    @Test
    public void two_players_get_added_message() {

        // arrange
        final String EXPECTED_MESSAGE = "Players: Sara, Juan";
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        String actualMessage = newGame.player.addPlayer("add player Juan");

        // assert
        assertEquals(EXPECTED_MESSAGE, actualMessage);
    }

    @Test
    public void cant_add_same_player_again() {

        // arrange
        final int EXPECTED_SIZE = 2;
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.player.addPlayer("add player Juan");
        newGame.player.addPlayer("add player Juan");
        int actualSize = newGame.player.playerList.size();

        // assert
        assertEquals(EXPECTED_SIZE, actualSize);
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
    public void positions_of_players_are_overwritten() {

        // arrange
        final int EXPECTED_POSITION = 6;
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.setPosition(0, 6);
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(EXPECTED_POSITION, actualPosition);
    }

    @Test
    public void move_players_without_command() {

        // arrange
        final int EXPECTED_POSITION = 8;
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.gameBoard.manageBoxes(0, 8);
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(EXPECTED_POSITION, actualPosition);
    }

    @Test
    public void move_players_without_command_message() {

        // arrange
        final String EXPECTED_MESSAGE = "NOMBRE moves from Start to 8";
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        String actualMessage = newGame.gameBoard.manageBoxes(0, 8);

        // assert
        assertEquals(EXPECTED_MESSAGE, actualMessage);
    }

    @Test
    public void move_players_with_no_dice_command() {

        // arrange
        final int EXPECTED_POSITION = 7;
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");

        newGame.manualRoll("move Sara 4, 3");
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(EXPECTED_POSITION, actualPosition);
    }

    @Test
    public void move_players_with_no_dice_command_MESSAGE() {

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
    public void player_moves_past_final_position_and_bounces() {

        // arrange
        final int EXPECTED_POSITION = 60;
        Game newGame = new Game();

        // act
        newGame.player.addPlayer("add player Sara");
        newGame.setPosition(0, 58);
        newGame.manualRoll("move Sara 6, 2");
        int actualPosition = newGame.getPosition(0);

        // assert
        assertEquals(EXPECTED_POSITION, actualPosition);
    }

    @Test
    public void player_moves_past_final_position_and_bounces_message() {

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
    public void player_moves_to_bridge_and_goes_to_box_num_12() {

        // arrange
        final int EXPECTED_POSITION = 12;
        Game game = new Game();

        // act
        game.player.addPlayer("add player Sara");
        game.manualRoll("move Sara 4, 2");
        int actualPosition = game.getPosition(0);

        // assert
        assertEquals(EXPECTED_POSITION, actualPosition);
    }

    @Test
    public void player_moves_to_bridge_and_goes_to_box_num_12_message() {

        // arrange
        final String EXPECTED_MESSAGE = "Player Sara rolls 4,2. Sara moves from Start to The Bridge. Sara jumps to 12";
        Game game = new Game();

        // act
        game.player.addPlayer("add player Sara");
        String actualMessage = game.manualRoll("move Sara 4, 2");

        // assert
        assertEquals(EXPECTED_MESSAGE, actualMessage);
    }

    @Test
    public void player_moves_to_finish_box_and_wins() {

        // arrange
        final int EXPECTED_POSITION = 63;
        Game game = new Game();

        // act
        game.player.addPlayer("add player Sara");
        game.setPosition(0, 58);
        game.manualRoll("move Sara 3, 2");
        int actualPosition = game.getPosition(0);

        // assert
        assertEquals(EXPECTED_POSITION, actualPosition);
    }

    @Test
    public void player_moves_to_finish_box_and_wins_message() {

        // arrange
        final String EXPECTED_MESSAGE = "Player Sara rolls 3,2. Sara moves from 58 to 63. Sara Wins!!";
        Game game = new Game();

        // act
        game.player.addPlayer("add player Sara");
        game.setPosition(0, 58);
        String actualMessage = game.manualRoll("move Sara 3, 2");

        // assert
        assertEquals(EXPECTED_MESSAGE, actualMessage);
    }

    @Test
    public void move_players_with_random_dice_roll_command() {

        // arrange
        Game game = new Game();

        // act
        game.player.addPlayer("add player Sara");
        game.diceRoll("move Sara");
        int actualPosition = game.getPosition(0);
        boolean movedPosition = actualPosition > 0;

        // assert
        assertEquals(true, movedPosition);
    }

    @Test
    public void one_player_wins_on_automatic_full_game() {

        // arrange
        Game game = new Game();

        // act
        game.player.addPlayer("add player Sara");
        game.player.addPlayer("add player Juan");
        game.turn();
        int player1Position = game.getPosition(0);
        int player2Position = game.getPosition(1);
        boolean aPlayerWon = player1Position == 63 || player2Position == 63;

        // assert
        assertEquals(true, aPlayerWon);
    }

    @Test
    public void only_one_player_wins_on_automatic_full_game() {

        // arrange
        Game game = new Game();

        // act
        game.player.addPlayer("add player Sara");
        game.player.addPlayer("add player Juan");
        game.turn();
        int player1Position = game.getPosition(0);
        int player2Position = game.getPosition(1);
        boolean onlyOneWon = player1Position == 63 && player2Position == 63;

        // assert
        assertEquals(false, onlyOneWon);
    }
}