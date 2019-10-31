package piece;

import chess.*;

/**
 * Pawn chess piece, subclass of Piece
 *
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class Pawn extends Piece{

    //Tracks if last move made was 2 spaces
    public boolean doubleJump;
    //public boolean firstMove;

    //Which piece to promote the pawn to
    public Pawn() {
        firstMove = true;
        doubleJump = false;
    }

    @Override
    public boolean legalMove(Coord start, Coord end, Board board) {

        Tile startSquare = board.tile[start.getY()][start.getX()];
        Tile endSquare = board.tile[end.getY()][end.getX()];


        //Number of spaces moved in X and Y direction
        int numSpacesX = Math.abs(start.getX() - end.getX());
        int numSpacesY;

        //Check to make sure not landing on their own piece
        if(endSquare != null && startSquare.player == endSquare.player) {
            enpassant = false;
            return false;
        }
        
        //Whites piece, moving up
        if(startSquare.player == true)
            numSpacesY = start.getY() - end.getY();

        //Blacks piece, moving down
        else
            numSpacesY = end.getY() - start.getY();

        //Trying to move up/down, check if space is empty
        if(numSpacesX == 0 && endSquare == null) {

            //Moving up/down 1 space
            if(numSpacesY == 1) {
                firstMove = false;

                //Check for promotion
                if(startSquare.player == true && end.getY() == 0 ||
                   startSquare.player == false && end.getY() == 7)
                    board.tile[start.getY()][start.getX()] = new Tile(promotion(), startSquare.player);

                enpassant = false;
                return true;
            }

            //Moving up/down 2 spaces, check if first move
            if(numSpacesY == 2 && firstMove == true) {

                //Check if path is clear
                int midY = (start.getY() + end.getY()) / 2;
                if(board.tile[midY][start.getX()] == null) {
                    firstMove = false;
                    doubleJump = true;
                    enpassant = false;
                    return true;
                }

            }
        }


        //Diagonal capture
        if(numSpacesX == 1 && numSpacesY == 1) {

            //Check to make sure trying to capture opponents piece and not landing on their own
            if(endSquare != null && !startSquare.player == endSquare.player) {

                //Check for promotion
                if(startSquare.player == true && end.getY() == 0 ||
                   startSquare.player == false && end.getY() == 7)
                    board.tile[start.getY()][start.getX()] = new Tile(promotion(), startSquare.player);

                enpassant = false;
                return true;
            }

            //En passant
            Tile adjacentSquare = board.tile[start.getY()][end.getX()];
            if(adjacentSquare != null &&
               !adjacentSquare.player == startSquare.player &&
               adjacentSquare.piece instanceof Pawn &&
                    ((Pawn) adjacentSquare.piece).doubleJump) {

                enpassant = true;
                return true;
            }
        }


        enpassant = false;
        return false;
    }



    /**
     * Method that promotes pawn piece to [Rook / Knight / Bishop/ Queen]
     *
     * @return Promoted piece
     */
    public Piece promotion() {

        switch(promotion) {
            case 'R': return new Rook();
            case 'N': return new Knight();
            case 'B': return new Bishop();
            default: return new Queen();
        }
    }
    

}