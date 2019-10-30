package chess;

import piece.*;

/**
 * A tile contains a player's [black/white] piece
 *
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class Tile {

    public Piece piece;
    
    /**
     * true = White, false == Black
     */
    public boolean player;

    /**
     * Constructor
     *
     * @param p Piece
     * @param pl Player color
     */
    public Tile(Piece piece, boolean player) {
        this.piece = piece;
        this.player = player;
    }

    /**
     *
     * @return Prints out the string
     */
    
    public String toString() {
        if(piece instanceof King) {
        	if(player)
        		return "wK";
        	else
        		return "bK";
        }
        if(piece instanceof Queen) {
        	if(player)
        		return "wQ";
        	else
        		return "bQ";
        }
        if(piece instanceof Rook) {
        	if(player) {
        		return "wR";
        	}
        	else
        		return "bR";
        }
        if(piece instanceof Knight) {
        	if(player)
        		return "wN";
        	else
        		return "bN";
        }
        if(piece instanceof Bishop) {
        	if(player)
        		return "wB";
        	else
        		return "bB";
        }
        if(piece instanceof Pawn) {
        	if(player)
        		return "wp";
        	else
        		return "bp";
        }
        else return "  ";
    }
}
