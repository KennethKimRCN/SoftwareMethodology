public class ChessPiece {
	
	int xpos;
	int ypos;
	char team;
	
	public boolean canMove(ChessSquare[][] chessBoard) {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(canMove(i,j,chessBoard)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean canMove(int x, int y, ChessSquare[][] chessBoard) {
		ChessPiece a = this;
		
		if(a.move(x,y,chessBoard)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public ChessPiece(int x, int y, char t) {
		xpos = x;
		ypos = y;
		team = t;
	}
	
	public boolean move(int x,int y,ChessSquare[][] chessBoard) {
		return false;
	}
	
	public char getTeam() {
		return team;
	}
	
	public String getDisplay() {
		return "";
	}
}