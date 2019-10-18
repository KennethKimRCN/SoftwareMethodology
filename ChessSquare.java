public class ChessSquare {
	boolean isOccupied;
	ChessPiece piece;
	
	public ChessSquare() {
		isOccupied = false;
	}
	
	public ChessSquare(ChessPiece x) {
		isOccupied = true;
		piece = x;
	}

}