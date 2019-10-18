public class ChessSquare {
	boolean isOccupied;
	ChessPiece piece;
	
	public ChessSquare() {
		isOccupied = false;
		piece = null;
	}
	
	public ChessSquare(ChessPiece x) {
		isOccupied = true;
		piece = x;
	}
	
	public boolean occupied() {
		return isOccupied;
	}
	
	public void unoccupy() {
		piece = null;
		isOccupied = false;
	}
	
	public void occupy(ChessPiece x) {
		piece = x;
		isOccupied = true;
	}
	
	public ChessPiece getPiece() {
		return piece;
	}

}