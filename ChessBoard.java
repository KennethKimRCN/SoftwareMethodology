public class ChessBoard {
	public ChessSquare[][] board;
	public boolean draw;
	
	public ChessBoard() {
		draw = false;
		board = new ChessSquare[8][8];
		
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				board[i][j]= new ChessSquare();
			}
		}
		
		ChessPiece a = new ChessPiece(-1,-1,'x');
		
		for(int i=0;i<8;i++) {
			if((i==0)||(i==7)) {
				a = new Rook(0,i,'b');
				board[0][i].occupy(a);
			}
			else if((i==1)||(i==6)) {
				a = new Knight(0,i,'b');
				board[0][i].occupy(a);
			}
			else if((i==2)||(i==5)) {
				a = new Bishop(0,i,'b');
				board[0][i].occupy(a);
			}
			else if(i==3) {
				a = new Queen(0,i,'b');
				board[0][i].occupy(a);
			}
			else {
				a = new King(0,i,'b');
				board[0][i].occupy(a);
			}
		}
		
		for(int i=0;i<8;i++) {
			a = new pawn(1,i,'b');
			board[1][i].occupy(a);
		}
		
		for(int i=0;i<8;i++) {
			if((i==0)||(i==7)) {
				a = new Rook(7,i,'w');
				board[7][i].occupy(a);
			}
			else if((i==1)||(i==6)) {
				a = new Knight(7,i,'w');
				board[7][i].occupy(a);
			}
			else if((i==2)||(i==5)) {
				a = new Bishop(7,i,'w');
				board[7][i].occupy(a);
			}
			else if(i==3) {
				a = new Queen(7,i,'w');
				board[7][i].occupy(a);
			}
			else {
				a = new King(7,i,'w');
				board[7][i].occupy(a);
			}
		}
		
		for(int i=0;i<8;i++) {
			a = new pawn(6,i,'w');
			board[6][i].occupy(a);
		}
	}
	
	public String getDisplay(int x, int y) {
		if (board[x][y].occupied()) {
			return board[x][y].getPiece().getDisplay();
		}
		
		else if((x+y)%2==0){
			return "   ";
		}
		else {
			return "## ";
		}
	}
	
	public void getDisplay() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				System.out.print(getDisplay(i,j));
			}
			System.out.println(8-i);
		}
		System.out.println(" a  b  c  d  e  f  g  h ");
	}
	
	public void falseDraw() {
		draw = false;
	}
	
	public void trueDraw() {
		draw = true;
	}
	
	public boolean stalemate(char toMove) {
		ChessSquare[][] board2 = new ChessSquare[8][8];
		
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(board[i][j].getPiece() instanceof pawn) {
					ChessPiece u = new pawn(i,j,board[i][j].getPiece().getTeam());
					ChessSquare v = new ChessSquare(u);
					board2[i][j]=v;
				}
				else if(board[i][j].getPiece() instanceof Knight) {
					ChessPiece u = new Knight(i,j,board[i][j].getPiece().getTeam());
					ChessSquare v = new ChessSquare(u);
					board2[i][j]=v;
				}
				else if(board[i][j].getPiece() instanceof Rook) {
					ChessPiece u = new Rook(i,j,board[i][j].getPiece().getTeam());
					ChessSquare v = new ChessSquare(u);
					board2[i][j]=v;
				}
				else if(board[i][j].getPiece() instanceof Bishop) {
					ChessPiece u = new Bishop(i,j,board[i][j].getPiece().getTeam());
					ChessSquare v = new ChessSquare(u);
					board2[i][j]=v;
				}
				else if(board[i][j].getPiece() instanceof Queen) {
					ChessPiece u = new Queen(i,j,board[i][j].getPiece().getTeam());
					ChessSquare v = new ChessSquare(u);
					board2[i][j]=v;
				}
				else {
					ChessPiece u = new King(i,j,board[i][j].getPiece().getTeam());
					ChessSquare v = new ChessSquare(u);
					board2[i][j]=v;
				}
			}
			
		}
		
		boolean isStalemate = true;
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(board2[i][j].getPiece().getTeam()==toMove) {
					if(board2[i][j].getPiece().canMove(board2)) {
						isStalemate = false;
					}
				}
			}
		}
		return isStalemate;
	}
}