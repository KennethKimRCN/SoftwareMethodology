//needs implement: en passant

public class pawn extends ChessPiece{
	boolean hasMoved = false;
	
	public pawn(int x, int y,char t) {
		super(x,y,t);
	}
	
	public boolean move(int x, int y, ChessSquare[][] chessBoard) {
		
		if(((x>=8)||(x<0))||((y>=8)||(y<0))) {
			return false;
		}
		
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
				
				if(y==7) {
					promote(chessBoard);
				}
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
				
				if(y==7) {
					promote(chessBoard);
				}
				return true;
			}
			else {
				return false;
			}
		}
		

		
		return false;
	}
	
	public String getDisplay() {
		return team+"p"+" ";
	}
	
	public boolean promote(ChessSquare[][] chessBoard) {
		ChessPiece x = new Queen(xpos, ypos, team);
		chessBoard[xpos][ypos].unoccupy();
		chessBoard[xpos][ypos].occupy(x);
		return true;
	}
	
	public boolean promote(ChessSquare[][] chessBoard,char type) {
		if(type=='N') {
			ChessPiece x = new Knight(xpos, ypos, team);
			chessBoard[xpos][ypos].unoccupy();
			chessBoard[xpos][ypos].occupy(x);
			return true;
		}
		else if(type=='B') {
			ChessPiece x = new Bishop(xpos, ypos, team);
			chessBoard[xpos][ypos].unoccupy();
			chessBoard[xpos][ypos].occupy(x);
			return true;
		}
		else if(type=='R') {
			ChessPiece x = new Rook(xpos, ypos, team);
			chessBoard[xpos][ypos].unoccupy();
			chessBoard[xpos][ypos].occupy(x);
			return true;
		}
		
		
		else {
		ChessPiece x = new Queen(xpos, ypos, team);
		chessBoard[xpos][ypos].unoccupy();
		chessBoard[xpos][ypos].occupy(x);
		return true;
		}
	}
	
}