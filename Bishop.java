
public class Bishop extends ChessPiece{
	
	public Bishop(int x, int y, char t) {
		super(x,y,t);
	}
	
	public boolean move(int x, int y, ChessSquare[][] chessBoard) {
		if(((x>=8)||(x<0))||((y>=8)||(y<0))) {
			return false;
		}
		
		if(((x-this.xpos)==(y-this.ypos))||((x-this.xpos)==(Math.negateExact(y-this.ypos)))) {
			if(chessBoard[x][y].occupied()) {
				if((x<this.xpos)&&(y>this.ypos)) {
					for(int i=1;i<Math.abs(this.xpos-x);i++) {
						if(chessBoard[x+i][y-i].occupied()) {
							return false;
						}
					}
					
				}
				
				else if((x<this.xpos)&&(y<this.ypos)) {
					for(int i=1;i<Math.abs(this.xpos-x);i++) {
						if(chessBoard[x+i][y+i].occupied()) {
							return false;
						}
					}
					
				}
				
				else if((x>this.xpos)&&(y<this.ypos)) {
					for(int i=1;i<Math.abs(this.xpos-x);i++) {
						if(chessBoard[x-i][y+i].occupied()) {
							return false;
						}
					}
					
				}
				
				else if((x>this.xpos)&&(y>this.ypos)) {
					for(int i=1;i<Math.abs(this.xpos-x);i++) {
						if(chessBoard[x-i][y-i].occupied()) {
							return false;
						}
					}
					
				}
				
				else {
					return false;
				}
				
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
				if((x<this.xpos)&&(y>this.ypos)) {
				for(int i=1;i<Math.abs(this.xpos-x);i++) {
					if(chessBoard[x+i][y-i].occupied()) {
						return false;
					}
				}
				
			}
			
			else if((x<this.xpos)&&(y<this.ypos)) {
				for(int i=1;i<Math.abs(this.xpos-x);i++) {
					if(chessBoard[x+i][y+i].occupied()) {
						return false;
					}
				}
				
			}
			
			else if((x>this.xpos)&&(y<this.ypos)) {
				for(int i=1;i<Math.abs(this.xpos-x);i++) {
					if(chessBoard[x-i][y+i].occupied()) {
						return false;
					}
				}
				
			}
			
			else if((x>this.xpos)&&(y>this.ypos)) {
				for(int i=1;i<Math.abs(this.xpos-x);i++) {
					if(chessBoard[x-i][y-i].occupied()) {
						return false;
					}
				}
				
			}
			
			else {
				return false;
			}
				
				chessBoard[this.xpos][this.ypos].unoccupy();
				this.xpos = x;
				this.ypos = y;
				chessBoard[x][y].occupy(this);
				return true;
			}
		}
		return false;
	}

	public String getDisplay() {
		return team+"B ";
	}
}