package piece;
import chess.*;

/**
 * The Class Piece.
 * @author Khangynon Kim
 * @author Whitney Poh
 */
public abstract class Piece {
	
	/** The p. */
	int x, y, turn1, p;
		
	/**
	 * Gets the x.
	 *
	 * @param x the x
	 * @return the x
	 */
	public int getX(int x) {
		return this.x;
	}
	
	/**
	 * Gets the y.
	 *
	 * @param y the y
	 * @return the y
	 */
	public int getY(int y) {
		return this.y;
	}
	
	/**
	 * Gets the p.
	 *
	 * @return the p
	 */
	public int getP() {
		return this.p;
	}
	
	/**
	 * Checks if is opponent.
	 *
	 * @param x the x
	 * @return the int
	 */
	public int isOpponent(Piece x) {
		int p1 = this.p;
		int p2 = x.p;
		if (p1 == p2) {
			return 0;
		}
		return 1;
	}
}
