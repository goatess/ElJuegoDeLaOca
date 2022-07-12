package JuegoOca;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.ObjectInputStream.GetField;

public class GameTest {

    @Test
    public void adds_players_with_command_and_retrieves_a_player_list() {
        // arrange
        final String MESSAGE = "Players: Sara";
        String actualMessage = "";
        Game game = new Game();

        // act
        actualMessage = game.useAddPlayerCommand("add player Sara");

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(1, game.players.size());
        assertEquals("Sara", game.getPlayerName(0));
    }

    @Test
    public void gets_playerlist_correctly_when_adding_more_players() {
        // arrange
        final String MESSAGE = "Players: Sara, Juan, Pepe";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.useAddPlayerCommand("add player Sara");
        game.useAddPlayerCommand("add player Juan");
        actualMessage = game.useAddPlayerCommand("add player Pepe");

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(3, game.players.size());
    }

    @Test
    public void retrieve_an_error_message_when_trying_to_add_same_player_name_twice() {
        // arrange
        final String MESSAGE = "Player Sara already exists. Please insert a new player.";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.useAddPlayerCommand("add player Sara");
        actualMessage = game.useAddPlayerCommand("add player Sara");

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(1, game.players.size());
    }

    @Test
    public void extract_name_and_numbers_and_retrieve_a_message() {
        // arrange
        final String MESSAGE = "Player Sara rolls 3,2. Sara moves from Start";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.useAddPlayerCommand("add player Sara");
        game.useRollDiceCommand("move Sara 3,2");
        actualMessage = game.getMoveMessage();
        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(5, game.getDicesValue());
    }

    @Test
    public void extract_name_and_roll_dices_and_retrieve_a_message() {
        // arrange
        final boolean VALUE_IS_IN_RANGE = true;
        boolean actualValueIsInRange = false;
        int actualRoll = 0;
        Game game = new Game();

        // act
        game.useAddPlayerCommand("add player Sara");
        game.useRollDiceCommand("move Sara");
        actualRoll = game.getDicesValue();
        actualValueIsInRange = ((actualRoll > 1) && (actualRoll < 13));

        // assert
        assertEquals(VALUE_IS_IN_RANGE, actualValueIsInRange);
    }

    @Test
    public void gets_a_player_from_the_playerlist_by_searching_a_name() {
        // arrange
        final int INDEX = 0;
        int actualIndex = -1;
        Game game = new Game();

        // act
        game.useAddPlayerCommand("add player Sara");
        actualIndex = game.getPlayer("Sara");

        // assert
        assertEquals(INDEX, actualIndex);
    }

    @Test
    public void move_player_the_same_number_of_boxes_as_dice_value() {
        // arrange
        final int DICES_VALUE = 5;
        int actualDicesValue = 0;
        int actualPosition = 0;
        Game game = new Game();

        // act
        game.useAddPlayerCommand("add player Sara");
        game.useRollDiceCommand("move Sara 3,2");
        actualDicesValue = game.getDicesValue();
        actualPosition = game.getPlayerPosition(0);

        // assert
        assertEquals(DICES_VALUE, actualDicesValue);
        assertEquals(actualDicesValue, actualPosition);
    }

    @Test
    public void retrieve_message_when_player_has_to_bounce() {
        // arrange
        final String MESSAGE = "Player Sara rolls 4,2. Sara moves from 62 to 63. Sara bounces! Sara Returns to 58";
        final int POSITION = 58;
        final int DICES_VALUE = 6;
        final boolean ENDED = false;
        String actualMessage = "";
        int actualDicesValue = 0;
        int actualPosition = 0;
        boolean actuallyEnded = false;
        Game game = new Game();

        // act
        game.useAddPlayerCommand("add player Sara");
        game.setPlayerPosition(0, 62);
        game.useRollDiceCommand("move Sara 4,2");
        actualMessage = game.boxMessage(0);
        actualDicesValue = game.getDicesValue();
        actualPosition = game.getPlayerPosition(0);
        actuallyEnded = game.isEnded();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(DICES_VALUE, actualDicesValue);
        assertEquals(POSITION, actualPosition);
        assertEquals(ENDED, actuallyEnded);
    }

    @Test
    public void retrieve_message_when_player_is_at_end_box() {
        // arrange
        final String MESSAGE = "Player Sara rolls 1,2. Sara moves from 60 to 63. Sara Wins!!";
        final int POSITION = 63;
        final int DICES_VALUE = 3;
        final boolean ENDED = true;
        String actualMessage = "";
        int actualDicesValue = 0;
        int actualPosition = 0;
        boolean actuallyEnded = false;
        Game game = new Game();

        // act
        game.useAddPlayerCommand("add player Sara");
        game.setPlayerPosition(0, 60);
        game.useRollDiceCommand("move Sara 1,2");
        actualMessage = game.boxMessage(0);
        actualDicesValue = game.getDicesValue();
        actualPosition = game.getPlayerPosition(0);
        actuallyEnded = game.isEnded();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(DICES_VALUE, actualDicesValue);
        assertEquals(POSITION, actualPosition);
        assertEquals(ENDED, actuallyEnded);
    }

    @Test
    public void retrieve_message_when_player_is_at_bridge_box() {
        // arrange
        final String MESSAGE = "Player Sara rolls 4,2. Sara moves from Start to The Bridge! Sara jumps to 12";
        final int POSITION = 12;
        final int DICES_VALUE = 6;
        String actualMessage = "";
        int actualDicesValue = 0;
        int actualPosition = 0;
        Game game = new Game();

        // act
        game.useAddPlayerCommand("add player Sara");
        game.useRollDiceCommand("move Sara 4,2");
        actualMessage = game.boxMessage(0);
        actualDicesValue = game.getDicesValue();
        actualPosition = game.getPlayerPosition(0);

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(DICES_VALUE, actualDicesValue);
        assertEquals(POSITION, actualPosition);
    }

    @Test
    public void retrieve_message_when_automated_PC_vs_PC_game_has_ended_succsessfully() {
        // arrange
        final boolean ENDED = true;
        final String MESSAGE = " to 63. NAME Wins!!";
        String actualMessage = "";
        boolean actuallyEnded = false;
        Game game = new Game();

        // act
        game.changeSettings(1, 2, true);
        game.startAutomatedGame();
        actualMessage = game.board.getMessage();
        actuallyEnded = game.isEnded();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(ENDED, actuallyEnded);

    }

    @Test
    public void run_an_automated_PC_vs_PC_game_with_10_player_and_4_dice_ends() {
        // arrange
        final boolean ENDED = true;
        boolean actuallyEnded = false;
        Game game = new Game();

        // act
        // act
        game.changeSettings(10, 4, true);
        game.startAutomatedGame();
        actuallyEnded = game.isEnded();

        // assert
        assertEquals(ENDED, actuallyEnded);
    }
}