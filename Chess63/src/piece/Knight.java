package piece;

import chess.*;

/**
 * Knight chess piece, subclass of Piece
 *
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class Knight extends Piece {

    public Knight() {
    }

    @Override
    public boolean legalMove(Coord start, Coord end, Board board) {

        Tile startSquare = board.tile[start.getY()][start.getX()];
        Tile endSquare = board.tile[end.getY()][end.getX()];

        //Number of spaces moved in X and Y direction
        int numSpacesX = Math.abs(start.getX() - end.getX());
        int numSpacesY = Math.abs(start.getY() - end.getY());

        //Prevent friendly fire
        if(endSquare != null && startSquare.player == endSquare.player)
            return false;

        //Left or right 1, up or down 2
        if(numSpacesX == 1 && numSpacesY == 2)
            return true;

        //Left or right 2, up or down 1
        if(numSpacesX == 2 && numSpacesY == 1)
            return true;


        return false;
    }
}
