package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *@author g55019 / Cotton Ian
 *The board class defines a game board on which the player will place his Tiles
 *This Tile board will have a 4x4 size and any empty square will be indicated by a null value
 */

public class Board {
    private Tile[][] plateau;

    /**
     * Constructor, initialize the plate attribute -> 2-dimensional array of size 4x4
     * Adding an easily implementable size attribute
     * 4 random tiles are generated and are placed on the descending diagonal from the smallest to the largest
     */
    public Board() {
        Tile[][] plateaudeTuile = new Tile[4][4];
        List<Tile> randomTile = fourRandomTile();
        for(int i =0; i < randomTile.size();i++){
            plateaudeTuile[i][i] = randomTile.get(i);
        }
        this.plateau = plateaudeTuile;
    }

    /**
     * constructor for test class
     * @param test int | test is uselss, it's just to have another constructor for test
     */
    public Board(int test){
        Tile[][] plateaudeTuile = new Tile[4][4];
        this.plateau = plateaudeTuile;
    }

    /**
     * getter
     * @return Tile[][];
     */
    public Tile[][] getPlateau() {
        return plateau;
    }

    /**
    *Allows you to know the size of the tile board and returns it.
    *@return Integer the size of the array
    */
    public int getSize(){
        int taille = this.plateau.length;
        return taille;
    }

    /**
     *Used to find out if a given position is in the board returns a boolean.
     *@return boolean, true if in tray otherwise false
     */

    public boolean isInside(Position pos){
        int ligne = pos.getRow();
        int colonne = pos.getColumn();
        if( (ligne < getSize() && ligne >= 0) && (colonne < getSize() && colonne >= 0)){
            return true;
        }
        return false;
    }

    /**
     *Returns the board tile at a given position.
     *@param pos Position,  The position of the tile we want
     *@return Tile, The tile at the given position
     */
    public Tile getTile(Position pos){

        int ligne  = pos.getRow();
        int colonne = pos.getColumn();
        return this.plateau[ligne][colonne];
    }

    /**
     *Go through a row of the board and return the value of the first element on the left, if there is none: returns null
     *@param pos Position | position of the tile that we want to put on the board
     *@return Integer, null | value of the first tile met
     */
    private Integer parcourirLigneVersGauche(Position pos){
        //if on the left side (column = 0) will return null to avoid Out of bound exception
        if(pos.getColumn() == 0){
            return null;
        }
        /**
         *Check the line starting from the given position to the left,
         *return the first value found, if there is none return null (no value)
         **/
        Integer VG = 0;
        for (int i = 1; i < this.plateau.length -1; i++) {
            if(pos.getColumn()  - i >= 0){
                if(plateau[pos.getRow()][pos.getColumn()  - i] != null){
                    VG = plateau[pos.getRow()][pos.getColumn() - i].getValue();
                    return VG;
                }
            }
        }
        //If no tile on the left return null
        return null;
    }

    /**
     *Go through a row of the board and return the value of the first element on the right, if there is none: returns null
     *@param pos Position| position of the tile that we want to put on the board
     *@return Integer, null | value of the first tile met
     */
    private Integer parcourirLigneVersDroite(Position pos){
        //If on the right side (column = board.Length - 1), return null to avoid out of bound Exeption
        if(pos.getColumn() == this.plateau[0].length-1){
            return null;
        }
        Integer VD = 0;
        //Check the line starting from the given position to the right, return the first value found,
        // if there is none return null (no value)
        for (int i = 1; i < this.plateau.length -1; i++) {
            if(pos.getColumn() + i <= this.plateau[0].length -1){
                if(plateau[pos.getRow()][pos.getColumn() + i] != null){
                    VD = plateau[pos.getRow()][pos.getColumn() + i].getValue();
                    return VD;
                }
            }
        }
        return null;
    }

    /**
     *Go through a column of the board and return the value of the first element on the top, if there is none: returns null
     *@param pos Position| position of the tile that we want to put on the board
     *@return Integer, null | value of the first tile met
     */
    private Integer parcourirColonneVersHaut(Position pos){
        //If we're on the first line return null to avoir Out of bound Exception.
        if(pos.getRow() == 0){
            return null;
        }
        //Go through the column starting from the given position upwards,
        // return the first value found, if there is none return null (no value)
        Integer VH = 0;
        for (int i = 1; i < this.plateau[0].length - 1; i++) {
            if(pos.getRow() - i >= 0){
                if(plateau[pos.getRow() - i][pos.getColumn()] != null){
                    VH = plateau[pos.getRow() - i][pos.getColumn()].getValue();
                    return VH;
                }
            }
        }
        return null;
    }
    /**
     * Go through a column of the board and return the value of the first element on the bottom,
     * if there is none: returns null
     * @param pos Position | position of the tile that we want to put on the board
     * @return Integer, null | value of the first tile met
     */
    private Integer parcourirColonneVersBas(Position pos){
        //If on the last line, return null to avoir oob
        if(pos.getRow() == this.plateau.length - 1){
            return null;
        }
        //Go through the column starting from the given position down,
        // return the first value found, if there is none return null (no value)
        Integer VB =0 ;
        for (int i = 1; i < this.plateau[0].length - 1; i++) {
            if(pos.getRow() + i <= this.plateau.length - 1){
                if(plateau[pos.getRow() + i][pos.getColumn()] != null){
                    VB = plateau[pos.getRow() + i][pos.getColumn()].getValue();
                    return VB;
                }
            }
        }
        //no value
        return null;
    }

    /**
     * Return a boolean if the tile at the given position is Bigger or equals to the value of the top and left tile
     *     the methode && bot and right < tile given return true else false
     *
     *     @param tuile Tile , Position | a Tile and a Position if the position is OOB -> exception is thrown
     *     @return Boolean |true if the tile can be put at the given position if not -> false
     */
    public boolean canBePut(Tile tuile, Position pos){
        int ligne = pos.getRow();
        int colonne = pos.getColumn();
        int valTuile = tuile.getValue();

        /**
         *Get the value of the tile above / to the left / to the right / to the bottom of the given tile position
         *If their value does not hinder the fact of being able to place the tile returns true,
         *we can well place the tile otherwise returns false impossible
         *(null -> no tile on this side.)
         **/
        if((parcourirColonneVersHaut(pos) == null || parcourirColonneVersHaut(pos) < valTuile) &&
                (parcourirColonneVersBas(pos) == null || parcourirColonneVersBas(pos) > valTuile) &&
                (parcourirLigneVersGauche(pos) == null || parcourirLigneVersGauche(pos) < valTuile) &&
                (parcourirLigneVersDroite(pos) == null || parcourirLigneVersDroite(pos) > valTuile)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *Add a tile in the array at a given position, it is assumed that the position is on the board
     *@param tuile Tile, pos Position | the position is on the board
     **/
    public void put(Tile tuile, Position pos){
        this.plateau[pos.getRow()][pos.getColumn()] = tuile;
    }

    /**
     *Say if the board if full or not, if it's the case return true else false
     *@return boolean | if there is no null return true else false
     **/
    public boolean isFull(){
        for(int ligne = 0; ligne < this.plateau.length;ligne++){
            for (int colonne = 0; colonne < this.plateau[0].length; colonne++){
                if (this.plateau[ligne][colonne] == null){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Create a list of random tiles sorted in ascending order
     * @return List<Tile>
     */
    private List<Tile> fourRandomTile(){
        List<Integer> randomValue = new ArrayList<>();
        List<Tile> randomTile = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            randomValue.add(random());
        }
        Collections.sort(randomValue);
        for (int i = 0; i <randomValue.size(); i++) {
            randomTile.add(new Tile(randomValue.get(i)));
        }
        return randomTile;
    }

    /**
     * return a random number between 1 and 20
     * @return int
     */
    private int random(){
        int min = 1;
        int max = 20;
        Random r = new Random();
        int nb =  r.nextInt((max - min) + 1) + min;
        return nb;
    }

}
