public class ChessPiece {
	static ChessSquare[][] chessBoard;
	
	int xpos;
	int ypos;
	char team;
	
	public ChessPiece(int x, int y, char t) {
		xpos = x;
		ypos = y;
		team = t;
	}
	
	public boolean move() {
		return false;
	}
	
	public char getTeam() {
		return team;
	}
	
	public String getDisplay() {
		return "";
	}
}