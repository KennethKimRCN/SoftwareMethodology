package piece;
import chess.*;
public abstract class Piece {
	int x, y, turn1, p;
		
	public int getX(int x) {
		return this.x;
	}
	
	public int getY(int y) {
		return this.y;
	}
	
	public int getP() {
		return this.p;
	}
	
	public int isOpponent(Piece x) {
		int p1 = this.p;
		int p2 = x.p;
		if (p1 == p2) {
			return 0;
		}
		return 1;
	}
}
