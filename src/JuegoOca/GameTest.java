package JuegoOca;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GameTest {

    @Test
    public void extracts_name_from_add_player_command() {
        // arrange
        final String NAME = "Sara";
        String actualName = "";
        Game game = new Game();

        // act
        actualName = game.extractName("add player Sara");

        // assert
        assertEquals(NAME, actualName);
    }

    @Test
    public void adds_a_new_player() {
        // arrange
        Game game = new Game();

        // act
        int oldsize = game.players.size() + 1;
        game.addPlayer("Sara");
        int newsize = game.players.size();

        // assert
        assertEquals(oldsize, newsize);
    }

    @Test
    public void gets_playerlist_with_add_player_command() {
        // arrange
        final String LIST = "Players: Sara";
        String actualList = "";
        Game game = new Game();

        // act
        actualList = game.useAddPlayerCommand("add player Sara");

        // assert
        assertEquals(LIST, actualList);
    }

    @Test
    public void gets_playerlist_with_more_players_command() {
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
    }

    @Test
    public void searches_name() {
        // arrange
        final boolean FOUND = true;
        boolean actuallyFound = false;
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        actuallyFound = game.searchName("Sara");

        // assert
        assertEquals(FOUND, actuallyFound);
    }

    @Test
    public void cant_add_same_playerName_twice() {
        // arrange
        final String MESSAGE = "Player Sara already exists. Please insert a new player.";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.useAddPlayerCommand("add player Sara");
        actualMessage = game.useAddPlayerCommand("add player Sara");

        // assert
        assertEquals(MESSAGE, actualMessage);
    }

    @Test
    public void extracts_name_from_move_command() {
        // arrange
        final String NAME = "Sara";
        String actualName = "";
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        actualName = game.extractName("move Sara 4,2");

        // assert
        assertEquals(NAME, actualName);
    }

    @Test
    public void gets_player_index_from_name() {
        // arrange
        final int INDEX = 0;
        int actualIndex = -1;
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        actualIndex = game.getPlayer("Sara");

        // assert
        assertEquals(INDEX, actualIndex);
    }

    @Test
    public void extracts_dice_roll_from_command() {
        // arrange
        final int DICES_VALUE = 6;
        int actualDicesValue = 0;
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        game.extractDices("42");
        actualDicesValue = game.getDicesValue();

        // assert
        assertEquals(DICES_VALUE, actualDicesValue);
    }

    @Test
    public void gets_random_dice_values_between_2_and_12_command() {
        // arrange
        final boolean VALUE_IS_IN_RANGE = true;
        boolean actualValueIsInRange = false;
        int actualDicesValue = 0;
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        game.determineDiceType("move Sara");
        actualDicesValue = game.getDicesValue();
        actualValueIsInRange = ((actualDicesValue > 1) && (actualDicesValue < 13));

        // assert
        assertEquals(VALUE_IS_IN_RANGE, actualValueIsInRange);
    }

    @Test
    public void gets_random_dice_values_between_2_and_12() {
        // arrange
        final boolean VALUE_IS_IN_RANGE = true;
        boolean actualValueIsInRange = false;
        int actualDicesValue = 0;
        Game game = new Game();

        // act
        game.rollDices();
        actualDicesValue = game.getDicesValue();
        actualValueIsInRange = ((actualDicesValue > 1) && (actualDicesValue < 13));

        // assert
        assertEquals(VALUE_IS_IN_RANGE, actualValueIsInRange);
    }

    @Test
    public void determines_dice_type_by_reading_command() {
        // arrange
        final int DICES_VALUE = 6;
        int actualDicesValue = 0;
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        game.determineDiceType("move Sara 4,2");
        actualDicesValue = game.getDicesValue();

        // assert
        assertEquals(DICES_VALUE, actualDicesValue);
    }

    @Test
    public void adds_dice_values() {
        // arrange
        final int DICES_VALUE = 6;
        int actualDicesValue = 0;
        Game game = new Game();

        // act
        int[] dices = { 4, 2 };

        actualDicesValue = game.addDicesValues(dices);

        // assert
        assertEquals(DICES_VALUE, actualDicesValue);
    }

    @Test
    public void moves_player() {
        // arrange
        final int POSITION = 5;
        int actualPosition = 0;
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        game.makeAMove(0, 5);
        actualPosition = game.getPlayerPosition(0);

        // assert
        assertEquals(POSITION, actualPosition);
    }

    @Test
    public void moves_player_same_number_of_boxes_as_dice_value() {
        // arrange
        final int DICES_VALUE = 5;
        int actualDicesValue = 0;
        int actualPosition = 0;
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        game.useRollDiceCommand("move Sara 3,2");
        actualDicesValue = game.getDicesValue();
        actualPosition = game.getPlayerPosition(0);

        // assert
        assertEquals(DICES_VALUE, actualDicesValue);
        assertEquals(actualDicesValue, actualPosition);
    }

    @Test
    public void board_bounce() {
        // arrange
        final int POSITION = 58;
        final int DICES_VALUE = 6;
        int actualDicesValue = 0;
        int actualPosition = 0;
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        game.setPlayerPosition(0, 62);
        game.useRollDiceCommand("move Sara 4,2");
        actualDicesValue = game.getDicesValue();
        actualPosition = game.getPlayerPosition(0);

        // assert
        assertEquals(DICES_VALUE, actualDicesValue);
        assertEquals(POSITION, actualPosition);
    }

    @Test
    public void board_bounce_message() {
        // arrange
        final String MESSAGE = "Player Sara rolls 4,2. Sara moves from 62 to 63. Sara bounces! Sara Returns to 58";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        game.setPlayerPosition(0, 62);
        game.useRollDiceCommand("move Sara 4,2");
        actualMessage = game.boxMessage(0);

        // assert
        assertEquals(MESSAGE, actualMessage);
    }

    @Test
    public void end_box_board_message() {
        // arrange
        final String MESSAGE = "Player Sara rolls 1,2. Sara moves from 60 to 63. Sara Wins!!";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        game.setPlayerPosition(0, 60);
        game.useRollDiceCommand("move Sara 1,2");
        actualMessage = game.boxMessage(0);

        // assert
        assertEquals(MESSAGE, actualMessage);
    }

    @Test
    public void bridge_box_message() {
        // arrange
        final String MESSAGE = "Player Sara rolls 4,2. Sara moves from Start to The Bridge! Sara jumps to 12";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        game.useRollDiceCommand("move Sara 4,2");
        actualMessage = game.boxMessage(0);

        // assert
        assertEquals(MESSAGE, actualMessage);
    }

    @Test
    public void game_endGame() {
        // arrange
        final boolean ENDED = true;
        boolean actuallyEnded = false;
        Game game = new Game();

        // act
        game.addPlayer("Sara");
        game.setPlayerPosition(0, 60);
        game.useRollDiceCommand("move Sara 1,2");
        actuallyEnded = game.isEnded();

        // assert
        assertEquals(ENDED, actuallyEnded);
    }

    @Test
    public void automated_PC_vs_PC_game_ends() {
        // arrange
        final boolean ENDED = true;
        boolean actuallyEnded = false;
        Game game = new Game();

        // act
        game.changeSettings(2,2,true);
        game.startAutomatedGame();
        actuallyEnded = game.isEnded();

        // assert
        assertEquals(ENDED, actuallyEnded);
    }

    @Test
    public void automated_PC_vs_PC_game_end_message() {
        // arrange
        final String MESSAGE = " to 63. NAME Wins!!";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.changeSettings(1,2,true);
        game.startAutomatedGame();
        actualMessage = game.board.getMessage();

        // assert
        assertEquals(MESSAGE, actualMessage);

    }

    @Test
    public void automated_PC_vs_PC_game_with_10_player_and_4_dice_ends() {
        // arrange
        final boolean ENDED = true;
        boolean actuallyEnded = false;
        Game game = new Game();

        // act
        // act
        game.changeSettings(10,4,true);
        game.startAutomatedGame();
        actuallyEnded = game.isEnded();

        // assert
        assertEquals(ENDED, actuallyEnded);
    }
}