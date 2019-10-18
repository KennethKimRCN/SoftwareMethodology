public class ChessPiece {
	static ChessSquare[][] chessBoard;
	
	int xpos;
	int ypos;
	char team;
	
	public boolean move() {
		return false;
	}
	
	public char getTeam() {
		return team;
	}
}