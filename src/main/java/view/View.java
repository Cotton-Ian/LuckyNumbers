package view;

/**
 * Interface for the myView model.
 *
 * @author Cotton Ian|g55019
 */

import model.Position;

public interface View {

    /**
     * Displays a message at the start of the game: game name, author, version.
     */
    void displayWelcome();

    /**
     * displays the state of the game, the current player and his board as well as the tile he must place
     */
    void displayGame();

    /**
     * displays the number of the winning player.
     */
    void displayWinner();

    /**
     * asks how many players are in the game (from 2 to 4).
     * @return Integer, the amount of player (from 2 to 4).
     */
    int askPlayerCount();

    /**
     * asks the user to enter a row number and a column number and returns them as a position.
     * She makes sure that this position is valid.
     * @return Position, a position in the board
     */
    Position askPosition();

    /**
     * Display a message if there is an error
     * @param message
     */
    void displayError(String message);

    /**
     * Display the number of face down tile
     * @param nb
     */
    void displayFaceDown(int nb);

    /**
     * Display the face up tile
     */
    void displayFaceUp();

    /**
     *Asks the user if they want to choose a hidden tile (they must enter 1)
     * or choose a visible tile (2)
     * @return int | value 1 or 2
     */
    int askFaceUpOrDown();

    /**
     *If choice == 1 the user receives a random tile, he must choose between
     * the placed (1) or the drop (2). Rep = 1 or 2
     * If choice != 1 the user must select a visible tile and rep = 1
     * @param choix int | 2 value -> 1 or 2
     * @return rep int | value 1 | 2
     */
    int chooseTile(int choix);
}
