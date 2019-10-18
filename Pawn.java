public class Pawn extends ChessPiece{
	boolean hasMoved = false;
	
	public pawn(int x, int y, char c) {
		xpos = x;
		ypos = y;
		team = c;
	}
	
	public boolean move(int x, int y) {
		
		if(!chessBoard[x][y].occupied()) {
			//move 2 spaces in the beginning
			if((x==xpos)&&(y==ypos+2)) {
				if(!hasMoved) {
					chessBoard[xpos][ypos].unoccupy();
					
					xpos = x;
					ypos = y;
				
					chessBoard[xpos][ypos].occupy(this);
					
					hasMoved = true;
					return true;
				}
				else {
					return false;
				}
			}
		
			//move normally without capturing
			if((x==xpos)&&(y==ypos+1)) {
				chessBoard[x][y].occupy(this);
				xpos = x;
				ypos = y;
				hasMoved = true;
				return true;
			}
		}
		
		//capture pieces
		if(chessBoard[x][y].occupied()) {
			if(this.team == chessBoard[x][y].getPiece().getTeam()) {
				return false;
			}
			else if(((x==xpos+1)||(x==xpos-1))&&(y==ypos+1)) {
				chessBoard[x][y].unoccupy();
				xpos = x;
				ypos = y;
				chessBoard[x][y].occupy(this);
				
				hasMoved = true;
				return true;
			}
			else {
				return false;
			}
		}
		

		
		return false;
	}
	
}