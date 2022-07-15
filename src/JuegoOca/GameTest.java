package JuegoOca;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GameTest {

    @Test
    public void display_a_playerlist_when_adding_succsessfully_a_new_player_with_command() {
        // arrange
        final String MESSAGE = "Players: Sara";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        actualMessage = game.getMessage();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(1, game.players.size());
        assertEquals("Sara", game.getPlayerName(0));
    }

    @Test
    public void display_playerlist_sorted_correctly_when_adding_more_players() {
        // arrange
        final String MESSAGE = "Players: Sara, Juan, Pepe";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        game.executeCommand("add player Juan");
        game.executeCommand("add player Pepe");
        actualMessage = game.getMessage();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(3, game.players.size());
    }

    @Test
    public void display_an_error_message_when_trying_to_add_same_player_name_twice() {
        // arrange
        final String MESSAGE = "Player Sara already exists. Please insert a new player.";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        game.executeCommand("add player Sara");
        actualMessage = game.getMessage();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(1, game.players.size());
    }

    @Test
    public void move_player_the_same_number_of_boxes_as_dice_value() {
        // arrange
        final int DICES_VALUE = 5;
        final String MESSAGE = "Player Sara rolls 3,2. Sara moves from Start to 5";
        String actualMessage = "";
        int actualPosition = 0;
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        game.executeCommand("move Sara 3,2");
        actualPosition = game.getPlayerPosition("Sara");
        actualMessage = game.getMessage();

        // assert
        assertEquals(DICES_VALUE, actualPosition);
        assertEquals(MESSAGE, actualMessage);
    }

    @Test
    public void store_position_of_a_player_and_add_it_to_dice_roll_next_move() {
        // arrange
        final String MESSAGE = "Player Sara rolls 5,5. Sara moves from 20 to 30";
        final int POSITION = 30;
        String actualMessage = "";
        int actualPosition = 0;
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        game.executeCommand("move Sara 5,5");
        game.executeCommand("move Sara 5,5");
        game.executeCommand("move Sara 5,5");

        actualMessage = game.getMessage();
        actualPosition = game.getPlayerPosition("Sara");
        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(POSITION, actualPosition);
    }

    @Test
    public void store_positions_of_each_player() {
        // arrange
        final String LAST_MESSAGE = "Player Juan rolls 2,3. Juan moves from 20 to 25";
        final int PLAYER_0_POSITION = 50;
        final int PLAYER_1_POSITION = 25;
        String actualMessage = "";
        int actualPlayer0Position = 0;
        int actualPlayer1Position = 0;
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        game.executeCommand("add player Juan");
        game.executeCommand("move Sara 5,5");
        game.executeCommand("move Juan 2,3");
        game.executeCommand("move Sara 5,5");
        game.executeCommand("move Juan 2,3");
        game.executeCommand("move Sara 5,5");
        game.executeCommand("move Juan 2,3");
        game.executeCommand("move Sara 5,5");
        game.executeCommand("move Juan 2,3");
        game.executeCommand("move Sara 5,5");
        game.executeCommand("move Juan 2,3");
        actualMessage = game.getMessage();
        actualPlayer0Position = game.getPlayerPosition("Sara");
        actualPlayer1Position = game.getPlayerPosition("Juan");
        // assert
        assertEquals(LAST_MESSAGE, actualMessage);
        assertEquals(PLAYER_0_POSITION, actualPlayer0Position);
        assertEquals(PLAYER_1_POSITION, actualPlayer1Position);
    }

    @Test
    public void roll_is_in_range_when_moving_player_with_random_roll() {
        // arrange
        final boolean DICES_ROLL_IS_IN_RANGE = true;
        boolean actualDicesRollIsInRange = false;
        int actualRoll = 0;
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        game.executeCommand("move Sara");
        actualRoll = game.getRoll(0);
        actualDicesRollIsInRange = ((actualRoll > 1) && (actualRoll < 13));
       
        // assert
        assertEquals(DICES_ROLL_IS_IN_RANGE, actualDicesRollIsInRange);
    }

    @Test
    public void retrieve_players_index_by_serching_a_name() {
        // arrange
        final int INDEX = 0;
        int actualIndex = -1;
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        actualIndex = game.getPlayerID("Sara");

        // assert
        assertEquals(INDEX, actualIndex);
    }

    @Test
    public void display_message_when_player_has_to_bounce() {
        // arrange
        final String MESSAGE = "Player Sara rolls 4,2. Sara moves from 62 to 63. Sara bounces! Sara Returns to 58";
        final int POSITION = 58;
        final boolean ENDED = false;
        String actualMessage = "";
        int actualPosition = 0;
        boolean actuallyEnded = false;
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        game.setPlayerPosition("Sara", 62);
        game.executeCommand("move Sara 4,2");
        actualMessage = game.getMessage();
        actualPosition = game.getPlayerPosition("Sara");
        actuallyEnded = game.isEnded();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(POSITION, actualPosition);
        assertEquals(ENDED, actuallyEnded);
    }

    @Test
    public void display_message_when_player_is_at_end_box() {
        // arrange
        final String MESSAGE = "Player Sara rolls 1,2. Sara moves from 60 to 63. Sara Wins!!";
        final int POSITION = 63;
        final boolean ENDED = true;
        String actualMessage = "";
        int actualPosition = 0;
        boolean actuallyEnded = false;
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        game.setPlayerPosition("Sara", 60);
        game.executeCommand("move Sara 1,2");
        actualMessage = game.getMessage();
        actualPosition = game.getPlayerPosition("Sara");
        actuallyEnded = game.isEnded();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(POSITION, actualPosition);
        assertEquals(ENDED, actuallyEnded);
    }

    @Test
    public void display_message_when_player_is_at_bridge_box() {
        // arrange
        final String MESSAGE = "Player Sara rolls 4,2. Sara moves from Start to The Bridge! Sara jumps to 12";
        final int POSITION = 12;
        String actualMessage = "";
        int actualPosition = 0;
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        game.executeCommand("move Sara 4,2");
        actualMessage = game.getMessage();
        actualPosition = game.getPlayerPosition("Sara");

        // assert
        assertEquals(POSITION, actualPosition);
        assertEquals(MESSAGE, actualMessage);
    }

    @Test
    public void run_an_automated_PC_vs_PC_game_succsessfully() {
        // arrange
        final boolean ENDED = true;
        final boolean FINAL_MESSAGE = true;
        boolean isFinalMessage = false;
        boolean actuallyEnded = false;
        Game game = new Game();
        
        // act
        
        game.selectGameToStart(2, true);
        isFinalMessage = game.getMessage().contains(" Wins!!");
        actuallyEnded = game.isEnded();
        
        // assert
        assertEquals(FINAL_MESSAGE, isFinalMessage);
        assertEquals(ENDED, actuallyEnded);
        
    }

    
    @Test
    public void run_an_automated_PC_vs_PC_game_with_4_dice_of_10_sides() {
        // arrange
        final boolean ENDED = true;
        boolean actuallyEnded = false;
        Game game = new Game();

        // act
        game.selectGameToStart(2, true);
        actuallyEnded = game.isEnded();

        // assert
        assertEquals(ENDED, actuallyEnded);
    }
 
 
    @Test
    public void ignore_caps_when_adding_player() {
        // arrange
        final String MESSAGE = "Player SARA already exists. Please insert a new player.";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        game.executeCommand("add player SARA");
        actualMessage = game.getMessage();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(1, game.players.size());
    }

    @Test
    public void ignore_caps_when_moving_player() {
        // arrange
        final String MESSAGE = "Player Sara rolls 3,2. Sara moves from Start to 5";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        game.executeCommand("move sArA 3,2");
        actualMessage = game.getMessage();
        // assert
        assertEquals(MESSAGE, actualMessage);
    }

    @Test
    public void ignore_caps_when_searching_players() {
        // arrange
        final int INDEX = 0;
        int actualIndex = -1;
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        actualIndex = game.getPlayerID("SARA");

        // assert
        assertEquals(INDEX, actualIndex);
    }

    @Test
    public void ignore_caps_in_add_player_command_reserved_words() {
        // arrange
        final String MESSAGE = "Players: Sara";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add PLAYER Sara");
        actualMessage = game.getMessage();
        // assert
        assertEquals(MESSAGE, actualMessage);
    }

    @Test
    public void ignore_caps_in_move_player_command_reserved_words() {
        // arrange
        final String MESSAGE = "Player Sara rolls 1,4. Sara moves from Start to 5";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add PLAYER Sara");
        game.executeCommand("mOVe Sara 1,4");
        actualMessage = game.getMessage();
        // assert
        assertEquals(MESSAGE, actualMessage);
    }

    @Test
    public void assign_a_value_of_1__to_dice_from_command_dices_out_of_dice_range() {
        // arrange
        final String MESSAGE = "Player Sara rolls 1,1. Sara moves from Start to 2";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add player Sara");
        game.executeCommand("move sArA 10, 53");
        actualMessage = game.getMessage();
        // assert
        assertEquals(MESSAGE, actualMessage);
    }

    @Test
    public void add_player_with_numbers_in_name() {
        // arrange
        final String MESSAGE = "Players: 0010";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add player 0010");
        actualMessage = game.getMessage();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(1, game.players.size());
        assertEquals("0010", game.getPlayerName(0));
    }

    @Test
    public void move_player_with_numbers_in_name() {
        // arrange
        final String MESSAGE = "Player 0010 rolls 1,1. 0010 moves from Start to 2";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add player 0010");
        game.executeCommand("move 0010 1,1");
        actualMessage = game.getMessage();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(1, game.players.size());
        assertEquals("0010", game.getPlayerName(0));
    }

    @Test
    public void add_and_move_player_with_NAME_in_name() {
        // arrange
        final String MESSAGE = "Player NAME rolls 1,1. NAME moves from Start to 2";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add player NAME");
        game.executeCommand("move NAME 1,1");
        actualMessage = game.getMessage();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(1, game.players.size());
        assertEquals("NAME", game.getPlayerName(0));
    }

        @Test
    public void ignore_multiple_spaces_anywhere_in_command() {
        // arrange
        final String MESSAGE = "Player Player rolls 1,1. Player moves from Start to 2";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("  add    Player    Player  ");
        game.executeCommand("  move    Player   1 ,  1  ");
        actualMessage = game.getMessage();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(1, game.players.size());
        assertEquals("Player", game.getPlayerName(0));
    }

        @Test
    public void manage_bad_syntax_in_commands() {
        // arrange
        final String MESSAGE = "Bad syntax in command 'move'";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add Player");
        game.executeCommand("move 1,1");
        actualMessage = game.getMessage();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(0, game.players.size());
    }
            @Test
    public void manage_empty_command() {
        // arrange
        final String MESSAGE = "Command not found";
        String actualMessage = "";
        Game game = new Game();

        // act
        game.executeCommand("add Player");
        game.executeCommand("");
        actualMessage = game.getMessage();

        // assert
        assertEquals(MESSAGE, actualMessage);
        assertEquals(0, game.players.size());
    }
}