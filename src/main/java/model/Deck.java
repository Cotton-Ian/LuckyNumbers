package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class contains all the tiles available in the center of the table, whether face-up or not.
 * 2 lists are created one for the face down tiles and one for the face up tiles
 */

public class Deck {
    private ArrayList<Tile> faceUpTiles;
    private ArrayList<Tile> faceDownTile;

    /**
     * Constructor
     * @param playerCount
     */
    public Deck(int playerCount) {

        ArrayList<Tile> faceD = new ArrayList<>();

        for(int i = 0; i < playerCount; i++){
            for(int j = 1; j <= 20; j++){
                faceD.add(new Tile(j));
            }
        }

        this.faceUpTiles = new ArrayList<Tile>();
        this.faceDownTile = faceD;
    }

    /**
     * Constructor for test
     */
    public Deck() {
        ArrayList<Tile> faceUp = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            for(int j = 1; j <= 20; j++){
                faceUp.add(new Tile(j));
            }
        }
        this.faceUpTiles = faceUp;
        this.faceDownTile = new ArrayList<>();
    }

    /**
     * Stretch a face down tile and turn it over.
     * @Return Tile | a random tile from the face down tile list
     * @throws IllegalArgumentException if the list is empty
     */
    public Tile pickFaceDown(){
        int taille = this.faceDownTile.size() - 1;
        if(taille < 0){
            throw new IllegalArgumentException("plus de tuile");
        }
        int index;
        if(taille == 0){
            index = 0;
        }
        else{
            index = getRandomNumberInRange(taille);
        }

        Tile pickedTile = this.faceDownTile.get(index);
        faceDownTile.remove(index);
        pickedTile.flipFaceUp();
        return pickedTile;
    }

    /**
     * Return a random number between 0 and the 20* number of player
     * @param max | 20* the number of player
     * @return random position between 0 and max
     * @throws IllegalArgumentException if max < min
     */
    private int getRandomNumberInRange(int max) {
        int min = 0;
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        int index =  r.nextInt((max - min) + 1) + min;
        return index;
    }

    /**
     * gives the number of cards face down in the draw pile
     * @return int | the number of cards face down in the draw pile
     */
    public int faceDownCount(){
        return this.faceDownTile.size();
    }

    /**
     * gives the number of cards face up in the draw pile
     * @return int | the number of cards face up in the draw pile
     */
    public int faceUpCount(){
        return this.faceUpTiles.size();
    }

    /**
     * checks if the parameter tile exists and is visible in the stack.
     * @param tuile
     * @return boolean | true if the parameter tile exists
     */
    public boolean hasFaceUp(Tile tuile){
        return this.faceUpTiles.contains(tuile);
    }

    /**
     * returns the list of tiles face up.
     * @return returns the list of tiles face up.
     */
    public List<Tile> getAllFaceUp(){
        return this.faceUpTiles;
    }

    /**
     * remove the indicated tile from the draw pile.
     * @param tuile given tile
     */
    public void pickFaceUp(Tile tuile){
        this.faceUpTiles.remove(tuile);
    }

    /**
     * replace the given tile face up in the draw pile.
     * @param tuile given tile
     */
    public void putBack(Tile tuile){
        this.faceUpTiles.add(tuile);
    }
}
