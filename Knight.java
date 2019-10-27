public class Knight extends ChessPiece{

	public Knight(int x, int y, char t) {
		super(x,y,t);
	}
	
	public boolean move(int x, int y, ChessSquare[][] chessBoard) {
		if(((x>=8)||(x<0))||((y>=8)||(y<0))) {
			return false;
		}
		
		if(((x==xpos+2)&&(y==ypos+1))||((x==xpos+2)&&(y==ypos-1))||((x==xpos-2)&&(y==ypos+1))||((x==xpos-2)&&(y==ypos-1))||((x==xpos+1)&&(y==ypos+2))||((x==xpos+1)&&(y==ypos-2))||((x==xpos-1)&&(y==ypos+2))||((x==xpos-1)&&(y==ypos-2))) {
			if(chessBoard[x][y].occupied()) {
				if(chessBoard[x][y].getPiece().getTeam()!=this.team) {
					chessBoard[this.xpos][this.ypos].unoccupy();
					this.xpos = x;
					this.ypos = y;
					chessBoard[x][y].unoccupy();
					chessBoard[x][y].occupy(this);
					return true;
				}
				else {
					return false;
				}
			}
			else {
				chessBoard[this.xpos][this.ypos].unoccupy();
				this.xpos = x;
				this.ypos = y;
				chessBoard[x][y].occupy(this);
				return true;
			}
		}
		
		else {
			return false;
		}
	}
	
	public String getDisplay() {
		return team+"N"+" ";
	}

}