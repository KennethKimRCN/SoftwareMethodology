import java.util.Scanner;

public class Turn {
	public static boolean turn(ChessBoard chessBoard) {
		Scanner s = new Scanner(System.in);
		
		String move;
		boolean validmove = false;
		
		while(validmove==false) {
			if(chessBoard.stalemate('w')) {
				System.out.println("Stalemate");
				System.out.println("Draw");
				return true;
			}
			
			move = s.nextLine();
			
			if(move.equals("resign")) {
				System.out.println("Black wins");
				return true;
			}
			
			else if(chessBoard.draw) {
				if(move.equals("Draw")) {
					System.out.println("Draw");
					return true;
				}
			}
			
			//add in check here
			
			//create a copy of your piece you're trying to move, also copy the chessboard
			
			//if moving that copied piece on copied chessboard will result in the king not being in check
			
			//then move the real piece on the real chessboard
			
			//otherwise invalid move
			
			else {
				chessBoard.falseDraw();
				int x;
				int y = -1;
				switch (move.charAt(0)) {
				default: y = -1;
				case 'a': y=0; break;
				case 'b': y=1; break;
				case 'c': y=2; break;
				case 'd': y=3; break;
				case 'e': y=4; break;
				case 'f': y=5; break;
				case 'g': y=6; break;
				case 'h': y=7; break;
				}
				
				x = 8-((Character.getNumericValue(move.charAt(1))));
				
				int u;
				int v = -1;
				
				switch (move.charAt(3)) {
				default: v = -1;
				case 'a': v=0; break;
				case 'b': v=1; break;
				case 'c': v=2; break;
				case 'd': v=3; break;
				case 'e': v=4; break;
				case 'f': v=5; break;
				case 'g': v=6; break;
				case 'h': v=7; break;
				}
				
				u = 8-((Character.getNumericValue(move.charAt(4))));
				
				if((y==-1)||(v==-1)) {
					System.out.println("Invalid move, try again");
				}
				
				else if(!chessBoard.board[x][y].occupied()) {
					System.out.println("Invalid move, try again");
				}
				
				else if(chessBoard.board[x][y].getPiece().getTeam()!='w') {
					System.out.println("Invalid move, try again");
				}
				else if(chessBoard.board[x][y].getPiece().move(u,v,chessBoard.board)==false){
					
					System.out.println("Invalid move, try again");
				}
				else {
					chessBoard.board[x][y].getPiece().move(u,v,chessBoard.board);
					
					if((chessBoard.board[x][y].getPiece() instanceof pawn)&&(x==0)) {
						promote(u,v,move.charAt(5),chessBoard);
					}
					validmove=true;
					//insert check and checkmate methods from King class
					
					if(move.substring(move.length()-5).equals("Draw?")) {
						chessBoard.trueDraw();
					}
				}
				
			}
		}
		
		chessBoard.getDisplay();
		validmove = false;
		
		while(validmove==false) {
			
			if(chessBoard.stalemate('b')) {
				System.out.println("Stalemate");
				System.out.println("Draw");
				return true;
			}
			
			move = s.nextLine();
			
			if(move.equals("resign")) {
				System.out.println("White wins");
				return true;
			}
			
			else if(chessBoard.draw) {
				if(move.equals("Draw")) {
					System.out.println("Draw");
					return true;
				}
			}
			
			//add in check here
			
			else {
				chessBoard.falseDraw();
				int x;
				int y = -1;
				switch (move.charAt(0)) {
				default: y = -1;
				case 'a': y=0; break;
				case 'b': y=1; break;
				case 'c': y=2; break;
				case 'd': y=3; break;
				case 'e': y=4; break;
				case 'f': y=5; break;
				case 'g': y=6; break;
				case 'h': y=7; break;
				}
				
				x = 8-((Character.getNumericValue(move.charAt(1))));;
				
				int u;
				int v = -1;
				
				switch (move.charAt(3)) {
				default: v = -1;
				case 'a': v=0; break;
				case 'b': v=1; break;
				case 'c': v=2; break;
				case 'd': v=3; break;
				case 'e': v=4; break;
				case 'f': v=5; break;
				case 'g': v=6; break;
				case 'h': v=7; break;
				}
				
				u = 8-((Character.getNumericValue(move.charAt(4))));;
				
				if((y==-1)||(v==-1)) {
					System.out.println("Invalid move, try again");
				}
				
				else if(!chessBoard.board[x][y].occupied()) {
					System.out.println("Invalid move, try again");
				}
				
				else if(chessBoard.board[x][y].getPiece().getTeam()!='b') {
					System.out.println("Invalid move, try again");
				}
				else if(chessBoard.board[x][y].getPiece().move(u,v,chessBoard.board)==false){
					System.out.println("Invalid move, try again");
				}
				else {
					chessBoard.board[x][y].getPiece().move(u,v,chessBoard.board);
					if((chessBoard.board[u][v].getPiece() instanceof pawn)&&(x==7)) {
						promote(u,v,move.charAt(5),chessBoard);
						
						/*switch(move.charAt(5)) {
						case 'N': chessBoard.board[u][v].unoccupy();
						ChessPiece piece = new Knight(u,v,'b');
						chessBoard.board[u][v].occupy(piece); break;
						case 'R': chessBoard.board[u][v].unoccupy();
							piece = new Rook(u,v,'b');
							chessBoard.board[u][v].occupy(piece); break;
						case 'B': chessBoard.board[u][v].unoccupy();
						piece = new Bishop(u,v,'b');
						chessBoard.board[u][v].occupy(piece); break;
						default: chessBoard.board[u][v].unoccupy();
						piece = new Queen(u,v,'b');
						chessBoard.board[u][v].occupy(piece); break;
						}*/
					}
					
					validmove=true;
					//insert check and checkmate methods from King class
					
					if(move.substring(move.length()-5).equals("Draw?")) {
						chessBoard.trueDraw();
					}
					chessBoard.getDisplay();
					return false;
				}
				
			}
		}
		chessBoard.getDisplay();
		return false;
	}
	
	public static void promote(int u, int v, char c, ChessBoard chessBoard) {
		switch(c) {
		case 'N': chessBoard.board[u][v].unoccupy();
		ChessPiece piece = new Knight(u,v,chessBoard.board[u][v].getPiece().getTeam());
		chessBoard.board[u][v].occupy(piece); break;
		case 'R': chessBoard.board[u][v].unoccupy();
			piece = new Rook(u,v,chessBoard.board[u][v].getPiece().getTeam());
			chessBoard.board[u][v].occupy(piece); break;
		case 'B': chessBoard.board[u][v].unoccupy();
		piece = new Bishop(u,v,chessBoard.board[u][v].getPiece().getTeam());
		chessBoard.board[u][v].occupy(piece); break;
		default: chessBoard.board[u][v].unoccupy();
		piece = new Queen(u,v,chessBoard.board[u][v].getPiece().getTeam());
		chessBoard.board[u][v].occupy(piece); break;
		}
	}
}