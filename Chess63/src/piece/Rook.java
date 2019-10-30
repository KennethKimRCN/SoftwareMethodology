package piece;

import chess.*;

/**
 * Rook chess piece, subclass of Piece
 *
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class Rook extends Piece {

    public Rook() {
        firstMove = true;
    }

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

        //Can't move both left / right and up / down in a single move
        if(numSpacesX > 0 && numSpacesY > 0)
            return false;

        
        //Moving up
        if(numSpacesX == 0 && numSpacesY > 0){
            if(start.getY() > end.getY()) {
                for (int i = start.getY() - 1; i > end.getY(); i--) {
                    if (board.tile[i][start.getX()] != null)
                        return false;
                }
                
                firstMove = false; // Disables castling

                return true;
            }
        }


        //Moving down
        if(numSpacesX == 0 && numSpacesY > 0){
            if(start.getY() < end.getY()) {
                for (int i = start.getY() + 1; i < end.getY(); i++) {
                    if (board.tile[i][start.getX()] != null)
                        return false;
                }

                firstMove = false;
                
                return true;
            }
        }

        //Moving left
        if(numSpacesX > 0 && numSpacesY == 0){
            if(start.getX() > end.getX()) {
                for (int i = start.getX() - 1; i > end.getX(); i--) {
                    if (board.tile[start.getY()][i] != null)
                        return false;
                }
                
                firstMove = false;

                return true;
            }
        }

        //Moving right
        if(numSpacesX > 0 && numSpacesY == 0){
            if(start.getX() < end.getX()) {
                for (int i = start.getX() + 1; i < end.getX(); i++) {
                    if (board.tile[start.getY()][i] != null)
                        return false;
                }

                firstMove = false;
                
                return true;
            }
        }

        return false;
    }
    
}