package view;

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
    Integer askPlayerCount();

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

}