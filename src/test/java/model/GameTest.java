package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author MCD <mcodutti@he2b.be>
 */
public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    /* =====================
         Tests for start()
       ===================== */

    /* --- test related to the state --- */
    @Test
    public void start_when_game_not_started_ok() {
        game.start(4);
    }

    @Test
    public void start_when_game_over_ok() {
        fullPlay();
        game.start(2);
    }

    /* Play a game till the end */
    private void fullPlay() {
        game.startTest();
        int value = 1;
        int line = 0;
        int col = 0;
        for (int turn = 1; turn < game.getBoardSize() * game.getBoardSize(); turn++) {
            for (int player = 0; player < game.getPlayerCount(); player++) {
                if(game.getState() != State.GAME_OVER){
                    game.pickFaceUpTile(new Tile(value));
                    game.putTile(new Position(line, col));
                    game.nextPlayer();
                }
            }
            value++;
            col++;
            if (col == game.getBoardSize()) {
                col = 0;
                line++;
            }
        }
        if(game.getState() != State.GAME_OVER){
            game.pickFaceUpTile(new Tile(20));
            game.putTile(new Position(line, col));
        }
    }

    @Test
    public void start_when_game_in_progress_ISE() {
        game.start(4);
        assertThrows(IllegalStateException.class,
                () -> game.start(1));
    }

    @Test
    public void start_state_changed_to_PICK_TILE() {
        game.start(3);
        assertEquals(State.PICK_TILE, game.getState());
    }

    /* --- tests related to the parameter --- */
    @Test
    public void start_playerCount_too_small_Exception() {
        assertThrows(IllegalArgumentException.class,
                () -> game.start(1));
    }

    @Test
    public void start_playerCount_minimum_accepted() {
        game.start(2);
    }

    @Test
    public void start_playerCount_maximum_accepted() {
        game.start(4);
    }

    @Test
    public void start_playerCount_too_big_Exception() {
        assertThrows(IllegalArgumentException.class,
                () -> game.start(5));
    }

    /* -- tests related to fields initialization --- */
    @Test
    public void start_playerCount_initialized() {
        game.start(4);
        assertEquals(4, game.getPlayerCount());
    }

    @Test
    public void start_current_player_is_player_0() {
        game.start(4);
        assertEquals(0, game.getCurrentPlayerNumber());
    }

    @Test
    public void get_board_size_4(){
        game.start(2);
        assertEquals(4, game.getBoardSize());
    }

    @Test
    public void pick_face_down_when_state_incorrect_Exception(){
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalStateException.class,
                () -> game.pickFaceDownTile());
    }
    @Test
    public void pick_face_up_when_state_incorrect_Exception(){
        game.startTest();
        game.pickFaceUpTile(new Tile(5));
        assertThrows(IllegalStateException.class,
                () -> game.pickFaceUpTile(new Tile(5)));
    }

    @Test
    public void pick_face_down_when_state_ok(){
        game.start(2);
        game.pickFaceDownTile();
    }

    @Test
    public void pick_face_up_when_state_ok(){
        game.startTest();
        game.pickFaceUpTile(new Tile(5));
    }

    @Test
    public void put_Tile_StateExeption(){
        game.start(2);
        assertThrows(IllegalStateException.class,
                () -> game.putTile(new Position(0,0)));
    }

    @Test
    public void put_Tile_ok(){
        game.start(2);
        game.pickFaceDownTile();
        game.putTile(new Position(2,2));
    }

    @Test
    public void put_Tile_wrong_position(){
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> game.putTile(new Position(-1,0)));
    }

    @Test
    public void next_player_ok(){
        game.start(2);
        game.pickFaceDownTile();
        game.putTile(new Position(2,2));
        game.nextPlayer();
    }

    @Test
    public void next_player_State_Exception(){
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalStateException.class,
                () -> game.nextPlayer());
    }

    @Test
    public void get_current_player_number_ok(){
        game.start(2);
        game.pickFaceDownTile();
        game.getCurrentPlayerNumber();
    }

    @Test
    public void get_current_player_number_State_Exception(){
        Game jeu = new Game();
        assertThrows(IllegalStateException.class,
                () -> jeu.getCurrentPlayerNumber());
    }

    @Test
    public void is_Inside_true_00(){
        game.start(2);
        assertTrue(game.isInside(new Position(0,0)));
    }

    @Test
    public void is_Inside_true_03(){
        game.start(2);
        assertTrue(game.isInside(new Position(0,3)));
    }

    @Test
    public void is_Inside_true_30(){
        game.start(2);
        assertTrue(game.isInside(new Position(3,0)));
    }

    @Test
    public void is_Inside_true_33(){
        game.start(2);
        assertTrue(game.isInside(new Position(3,3)));
    }

    @Test
    public void is_Inside_false_503(){
        game.start(2);
        assertFalse(game.isInside(new Position(50,3)));
    }
    @Test
    public void is_Inside_false_negatif3(){
        game.start(2);
        assertFalse(game.isInside(new Position(-50,3)));
    }
    @Test
    public void is_Inside_false_50negatif3(){
        game.start(2);
        assertFalse(game.isInside(new Position(50,-3)));
    }
    @Test
    public void is_Inside_false_5050(){
        game.start(2);
        assertFalse(game.isInside(new Position(-50,50)));
    }
    @Test
    public void is_Inside_false_44(){
        game.start(2);
        assertFalse(game.isInside(new Position(4,4)));
    }

    @Test
    public void can_tile_be_put_state_exception(){
        game.start(2);
        assertThrows(IllegalStateException.class,
            () -> game.canTileBePut(new Position(0,0)));
    }

    @Test
    public void can_tile_be_put_wrong_position_exception(){
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalArgumentException.class,
                () -> game.canTileBePut(new Position(-1,0)));
    }
    @Test
    public void can_tile_be_put_wrong_position2_exception(){
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalArgumentException.class,
                () -> game.canTileBePut(new Position(0,-1)));
    }
    @Test
    public void can_tile_be_put_wrong_position3_exception(){
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalArgumentException.class,
                () -> game.canTileBePut(new Position(4,0)));
    }
    @Test
    public void can_tile_be_put_wrong_position4_exception(){
        game.start(2);
        game.pickFaceDownTile();
        assertThrows(IllegalArgumentException.class,
                () -> game.canTileBePut(new Position(-1,4)));
    }

    @Test
    public void get_tile_state_exception(){
        Game jeu = new Game();
        assertThrows(IllegalStateException.class,
                () -> jeu.getTile(0, new Position(0,0)));
    }

    @Test
    public void get_tile_wrong_player_number_Exception(){
        game.start(2);
        assertThrows(IllegalArgumentException.class,
                () -> game.getTile(3, new Position(0,0)));
    }

    @Test
    public void get_tile_wrong_position_Exception(){
        game.start(2);
        assertThrows(IllegalArgumentException.class,
                () -> game.getTile(1, new Position(-1,-2)));
    }

    @Test
    public void get_winners_state_Exception(){
        game.start(2);
        assertThrows(IllegalStateException.class,
                () -> game.getWinners());
    }

    @Test
    public void get_winners_ok(){
        fullPlay();
        game.getWinners();
    }
    @Test
    public void drop_tile_ok(){
        game.start(2);
        game.pickFaceDownTile();
        game.dropTile();
    }

    @Test
    public void drop_tile_state_exception(){
        game.start(2);
        assertThrows(IllegalStateException.class, () -> game.dropTile());
    }

    @Test
    public void face_down_count_full(){
        game.start(2);
        game.faceDownTileCount();
    }

    @Test
    public void face_down_count_empty(){
        game.startTest();
        game.faceDownTileCount();
    }

    @Test
    public void face_up_count_full(){
        game.startTest();
        game.faceUpTileCount();
    }

    @Test
    public void face_up_count_empty(){
        game.start(2);
        game.faceUpTileCount();
    }

    @Test
    public void get_all_face_up(){
        game.start(2);
        game.faceUpTileCount();
    }
    
}
