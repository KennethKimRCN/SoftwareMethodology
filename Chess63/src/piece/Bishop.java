package piece;

import chess.*;

/**
 * Bishop chess piece, subclass of Piece
 *
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class Bishop extends Piece{

    public Bishop() {}

    @Override
    public boolean legalMove(Coord start, Coord end, Board board) {

        Tile startSquare = board.tile[start.getY()][start.getX()];
        Tile endSquare = board.tile[end.getY()][end.getX()];

        //Number of spaces moved in X and Y direction
        int numSpacesX = Math.abs(start.getX() - end.getX());
        int numSpacesY = Math.abs(start.getY() - end.getY());

        //Check to make sure not landing on their own piece
        if(endSquare != null && startSquare.player == endSquare.player)
            return false;


        //Up and left diagonal
        if(numSpacesX == numSpacesY) {
            if (start.getX() > end.getX() && start.getY() > end.getY()) {
                for (int i = start.getY() - 1, j = start.getX() - 1; i > end.getY() && j > end.getX(); i--, j--) {
                    if (board.tile[i][j] != null)
                        return false;
                }
                return true;
            }
        }

        //Up and right diagonal
        if(numSpacesX == numSpacesY) {
            if (start.getX() < end.getX() && start.getY() > end.getY()) {
                for (int i = start.getY() - 1, j = start.getX() + 1; i > end.getY() && j < end.getX(); i--, j++) {
                    if (board.tile[i][j] != null)
                        return false;
                }
                return true;
            }
        }

        //Bottom and left diagonal
        if(numSpacesX == numSpacesY) {
            if (start.getX() > end.getX() && start.getY() < end.getY()) {
                for (int i = start.getY() + 1, j = start.getX() - 1; i < end.getY() && j > end.getX(); i++, j--) {
                    if (board.tile[i][j] != null)
                        return false;
                }
                return true;
            }
        }

        //Bottom and right diagonal
        if(numSpacesX == numSpacesY) {
            if (start.getX() < end.getX() && start.getY() < end.getY()) {
                for (int i = start.getY() + 1, j = start.getX() + 1; i < end.getY() && j < end.getX(); i++, j++) {
                    if (board.tile[i][j] != null)
                        return false;
                }
                return true;
            }
        }
        	

        return false;
    }
}