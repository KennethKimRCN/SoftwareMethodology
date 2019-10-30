package piece;

import chess.*;

/**
 * Queen chess piece, subclass of Piece
 *
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class Queen extends Piece {
	//Constructor for Queen
    public Queen() {
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

		//Moving up
		if(numSpacesX == 0 && numSpacesY > 0){
			if(start.getY() > end.getY()) {
				for (int i = start.getY() - 1; i > end.getY(); i--) {
					if (board.tile[i][start.getX()] != null)
						return false;
				}

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

				return true;
			}
		}

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