package piece;

import chess.*;

/**
 * King chess piece, subclass of Piece
 *
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class King extends Piece {

    public King() {
        firstMove = true;
        castling = '0';
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

        //Move to adjacent square
        if((numSpacesX == 1 && numSpacesY == 0) ||
           (numSpacesX == 0 && numSpacesY == 1) ||
           (numSpacesX == 1 && numSpacesY == 1)) {
            firstMove = false;
            castling = '0';
            return true;
        }
        
        //My castle
        
        if(numSpacesX == 2 && numSpacesY == 0 && firstMove) {
        	Tile topleftRook = board.tile[0][0];
        	Tile toprightRook = board.tile[0][7];
        	Tile bottomleftRook = board.tile[7][0];
        	Tile bottomrightRook = board.tile[7][7];
        	
        	//Top-Left castling
        	if(board.tile[0][0] != null) {
	        	if(start.getX() > end.getX() && topleftRook.piece instanceof Rook && topleftRook.piece.firstMove && board.tile[start.getY()][start.getX()].player == topleftRook.player) {
	        		if(board.tile[0][1] != null || board.tile[0][2] != null || board.tile[0][3] != null) {
	        			return false; //Cannot castle when pieces are in the way
	        		}
	        		else
	        			//Move Rook piece
	        			//board.tile[0][3] = board.tile[0][0];
	                	//board.tile[0][0] = null;
	                	//firstMove = false;
	                	castling = '1';
	                	return true;
	        	}
	        }
        	
        	//Top-Right castling
        	if(board.tile[0][7] != null) {
	        	if(start.getX() < end.getX() && toprightRook.piece instanceof Rook && toprightRook.piece.firstMove && board.tile[start.getY()][start.getX()].player == toprightRook.player) {
	        		if(board.tile[0][5] != null || board.tile[0][6] != null) {
	        			return false; //Cannot castle when pieces are in the way
	        		}
	        		else
	        			//Move Rook piece
	        			
	                	//firstMove = false;
	                	castling = '2';
	                	return true;
	        	}
        	}
        	
        	//Top-Left castling
        	if(board.tile[7][0] != null) {
	        	if(start.getX() > end.getX() && bottomleftRook.piece instanceof Rook && bottomleftRook.piece.firstMove && board.tile[start.getY()][start.getX()].player == bottomleftRook.player) {
	        		if(board.tile[7][1] != null || board.tile[7][2] != null || board.tile[7][3] != null) {
	        			return false; //Cannot castle when pieces are in the way
	        		}
	        		else
	        			//Move Rook piece
	        			
	                	//firstMove = false;
	                	castling = '3';
	                	return true;
	        	}
        	}
        	
        	//Bottom-Right castling
        	if(board.tile[7][7] != null) {
	        	if(start.getX() < end.getX() && bottomrightRook.piece instanceof Rook && bottomrightRook.piece.firstMove && board.tile[start.getY()][start.getX()].player == bottomrightRook.player) {
	        		if(board.tile[7][5] != null || board.tile[7][6] != null) {
	        			return false; //Cannot castle when pieces are in the way
	        		}
	        		else
	        			//Move Rook piece
	        			
	                	//firstMove = false;
	                	castling = '4';
	                	return true;
	        	}
	        }
        }

        return false;
  }
}
