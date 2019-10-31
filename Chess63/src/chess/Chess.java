package chess;
import piece.*;
import java.util.Scanner;

public class Chess {

    /**
     * Main method
     *
     * @author Khangnyon Kim
     * @author Whitney Poh
     */
	public static boolean player = true; // Global variable to keep track on whose turn it is. (true = white, false = black)
	static Board board; // Initialize board
	static Board backup;
    public static void main(String[] args) {
        board = new Board();
         
        Scanner reader = new Scanner(System.in);
        String input;
        
        boolean gameover = false;
        boolean lastMoveLegal = true;
        boolean drawOffered = false;
        boolean stalemate = false;

        while(!gameover) {
        	backup = board; //Backup board allows to easily undo illegal moves
            //Only print board if a move was executed
            if(lastMoveLegal)
                board.printBoard();
            
            //Draw offer pop-up
            if(drawOffered) {
            	if (!player) 
            		System.out.println("White offered to draw.");
            	else
            		System.out.println("Black offered to draw.");
            }
            
            //White's turn
            if(player)
                System.out.print("White's move: ");

            //Black's turn
            else
                System.out.print("Black's move: ");
            
            
            input = reader.nextLine();

            //Gameover with a draw
            if(input.equals("draw") && drawOffered)
                break;
            
            //Player x forfeits
            if(input.equals("resign")) {
            	gameover = true;
            	player = !player; // This allows opponent to win
            	break;
            }
            
            //Stalemate
            if(stalemate) {
            	break;
            }

            //Checks if the move is legal
            if(movePiece(input, player)) {
                if(player) {
                    player = !player;
                    resetEPvalue(false);
                }
                else {
                    player = true;
                    resetEPvalue(true);
                }

                lastMoveLegal = true;

                //Offers draw
                if(input.length() == 11 && input.substring(6, 11).equals("draw?"))
                    drawOffered = true;
                else
                    drawOffered = false;
                
                if(player == false && whiteCheck() == true) { //Currently white's turn, check if black pieces can check white King
                	if(stalemate(player)) { //Check if stalemate applies
                		drawOffered = true;
                		break;
                	}
                	lastMoveLegal = false; //Cannot put yourself into check
                    undo(input, player);
                	//backup.printBoard();
                    board = backup;
                    
                    System.out.println();
                    System.out.println("Illegal move, try again");
                    player = !player;
                }
                
                else if(player == true && blackCheck() == true) { //Currently black's turn, check if white pieces can check black King
                	lastMoveLegal = false; //Cannot put yourself into check
                    undo(input, player);
                	//backup.printBoard();
                    board = backup;
                    
                    System.out.println();
                    System.out.println("Illegal move, try again");
                    player = !player;
                }
                //Check if player is in check
                else if(detectCheck() && lastMoveLegal == true) {
                    System.out.println("\nCheck");


                    //Check if player is in checkmate, if so they lose
                    boolean checkMate = false;
                    if(player)
                        checkMate = detectMate(true);
                    else
                        checkMate = detectMate(false);

                    if(checkMate) {
                        if(player)
                            player = !player;
                        else
                            player = !player;
                        gameover = true;
                        System.out.println("Checkmate");
                        System.out.println();
                        board.printBoard();

                    }

                }
            //Invalid move
            } else {
                lastMoveLegal = false;
                System.out.println();
                System.out.println("Illegal move, try again");
            }

            System.out.println();
        }
        
        /**
         * Game is over, and the result is a...
         */
        printWinner(player,drawOffered);
    }
    
    private static void printWinner(boolean player, boolean draw) {
    	if(player && draw == false)
    		System.out.println("White wins");
    	else if((player == false) && (draw == false)) {
    		System.out.println("Black wins");
    	}
    	else
    		System.out.println("Draw");
    }

    /**
     * Undo's any illegal move made by player
     * 
     * @param s user input
     * @param player
     */
    private static void undo(String s, boolean player) {
    	Coord end = getPosition(s.substring(0,2));
        Coord start = getPosition(s.substring(3,5));
        board.tile[end.getY()][end.getX()] = board.tile[start.getY()][start.getX()];
        board.tile[start.getY()][start.getX()] = null;
    }
    
    /**
     * Depending on which players turn it is, detects if they are
     * requesting a valid move
     *
     * @param s The user's input in FileRank (start) FileRank (end) (IE a2 a4)
     * @param turn The current players turn, either white or black
     * @return True if the move was valid for the chess piece or false otherwise
     */
    private static boolean movePiece(String s, boolean player) {

        /**
         * Only accepts regular input (+ draw?)
         * Anything else is considered invalid input (except resign or draw)
         */
        if(!(s.length() == 5 || s.length() == 11))
            return false;
        
        /**
         * "Spell checking" for regular input
         */
        if(s.charAt(0) > 'h' || s.charAt(0) < 'a')
        	return false;
        if(s.charAt(1) > '8' || s.charAt(1) < '1')
        	return false;
        
        if(s.charAt(2) != ' ')
        	return false;
        if(s.charAt(3) > 'h' || s.charAt(3) < 'a')
        	return false;
        if(s.charAt(4) > '8' || s.charAt(4) < '1')
        	return false;
        
        Coord start = getPosition(s.substring(0,2));
        Coord end = getPosition(s.substring(3,5));
        

        //Check to make sure square is not blank
        if(board.tile[start.getY()][start.getX()] == null)
            return false;

        Tile startSquare = board.tile[start.getY()][start.getX()];

        //Check to make sure player is moving their own piece
        if((startSquare.player == false && player == true) ||
           (startSquare.player == true && player == false)) {

            return false;
        }

        //Check input included a promotion character
        if(s.length() >= 7) {
            startSquare.piece.promotion = s.charAt(6);
        }

        //Check to make sure the specific piece (pawn, bishop, etc) is allowed to move in that direction
        if(!startSquare.piece.legalMove(start, end, board))
            return false;

        //Player executed en passant
        if(startSquare.piece.enpassant) {
            board.tile[start.getY()][end.getX()] = null;
        }

        //Move Rook piece according to direction
        if(startSquare.piece.castling == '1') {
        	board.tile[0][3] = board.tile[0][0];
        	board.tile[0][0] = null;
        }
        if(startSquare.piece.castling == '2') {
        	board.tile[0][5] = board.tile[0][7];
        	board.tile[0][7] = null;
        }
        if(startSquare.piece.castling == '3') {
        	board.tile[7][3] = board.tile[7][0];
        	board.tile[7][0] = null;
        }
        if(startSquare.piece.castling == '4') {
        	board.tile[7][5] = board.tile[7][7];
        	board.tile[7][7] = null;
        }


        //Move the piece
        board.tile[end.getY()][end.getX()] = board.tile[start.getY()][start.getX()];
        board.tile[start.getY()][start.getX()] = null;

        return true;

    }

    /**
     * Detects if a player is currently in check
     *
     * @return True if a player is in check, false otherwise
     */
    private static boolean detectCheck() {
    	/**
    	 * Step 1) Find the position of Kings (This way it
    	 * Step 2) Traverse the board to find all pieces
    	 * Step 3) Find distance to current piece to enemy's King piece
    	 * Step 4) Test if piece can reach ememy's King piece 
    	 */
        //Holds the location of each player's king, and the piece to compare
        Coord wK_pos = null, bK_pos = null, test;

        //Step 1)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }
                else if((s.piece instanceof King) && s.player == true)
                    wK_pos = new Coord(j , i);
                else if((s.piece instanceof  King) && s.player == false)
                    bK_pos = new Coord(j , i);
                
            }
           
        }

        //Step 2 and 3)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {
                    //Skip null spot
                } else if(s.player == true) {
                    test = new Coord(j , i);
                    if(s.piece.legalMove(test, bK_pos, board)) {
                    	return true;    //Black in check

                    }
                } else if(s.player == false) {
                    test = new Coord(j , i);
                    if(s.piece.legalMove(test, wK_pos, board)) {
                        return true;    //White in check

                    }
                }
            }
        }

        return false;
    }
    
    /**
     * Detects if white's king is in check
     *
     * @return True if a player is in check, false otherwise
     */
    private static boolean whiteCheck() {
    	/**
    	 * Step 1) Find the position of white's king
    	 * Step 2) Traverse the board to find all black pieces
    	 * Step 3) Find distance to current piece to white's King piece
    	 * Step 4) Test if piece can reach white's King piece 
    	 */
        //Holds the location of each player's king, and the piece to compare
        Coord wK_pos = null, test;

        //Step 1)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }
                else if((s.piece instanceof King) && s.player == true)
                    wK_pos = new Coord(j , i);
                               
            }
           
        }

        //Step 2 and 3)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }  else if(s.player == false) {
                    test = new Coord(j , i);
                    if(s.piece.legalMove(test, wK_pos, board)) 
                        return true; //White in check
                }
            }
        }

        return false;
    }
    
    /**
     * Detects if white's king is in check
     *
     * @return True if a player is in check, false otherwise
     */
    private static boolean blackCheck() {
    	/**
    	 * Step 1) Find the position of white's king
    	 * Step 2) Traverse the board to find all black pieces
    	 * Step 3) Find distance to current piece to white's King piece
    	 * Step 4) Test if piece can reach white's King piece 
    	 */
        //Holds the location of each player's king, and the piece to compare
        Coord bK_pos = null, test;

        //Step 1)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }
                else if((s.piece instanceof King) && s.player == false)
                    bK_pos = new Coord(j , i);
                               
            }
           
        }

        //Step 2 and 3)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }  else if(s.player == true) {
                    test = new Coord(j , i);
                    if(s.piece.legalMove(test, bK_pos, board)) 
                        return true; //Black in check
                }
            }
        }

        return false;
    }

    /**
     * Detects if a player is in Checkmate
     *
     * @param p The player to check for checkmate (w or b)
     * @return True if player is in checkmate, false otherwise
     */
    private static boolean detectMate(boolean p) {

        //Saves the firstMove variable
        boolean firstMove;

        //Loop through board and find white's pieces
        for(int a = 0; a < 8; a++) {
            for(int b = 0; b < 8; b++) {
                if(board.tile[a][b] != null && board.tile[a][b].player == p) {
                    Coord start = new Coord(b , a);

                    //Check every piece on the board to see if it can move there
                    for(int c = 0; c < 8; c++) {
                        for(int d = 0; d < 8; d++) {
                            Coord end = new Coord(d, c);

                            //Save firstMove variable
                            firstMove = board.tile[a][b].piece.firstMove;

                            //It can move to given piece
                            if(board.tile[a][b].piece.legalMove(start, end, board)) {

                                //Move the piece
                                Tile startSquare = board.tile[a][b];
                                Tile endSquare = board.tile[end.getY()][end.getX()];
                                board.tile[end.getY()][end.getX()] = board.tile[a][b];
                                board.tile[a][b] = null;

                                //Found a situation where player can move piece and not be in check anymore
                                if(!detectCheck()) {
                                    board.tile[end.getY()][end.getX()] = endSquare;
                                    board.tile[a][b] = startSquare;
                                    return false;
                                }

                                //Put the board back
                                board.tile[end.getY()][end.getX()] = endSquare;
                                board.tile[a][b] = startSquare;

                                //Reset the firstMove variable
                                board.tile[a][b].piece.firstMove = firstMove;

                            }
                        }
                    }
                }
            }
        }


        return true;
    }

    /**
     * Parses an input into a x and y coordinate (IE: a1 gets converted to (0, 7))
     *
     * @param s FileRank (IE a1)
     * @return A new Point object with the x and y coordinates pertaining to the location on a chessboard
     */
    public static Coord getPosition(String s) {

        char c1 = s.charAt(0);
        char c2 = s.charAt(1);

        /**
         *  x-position (ascii value for 'a' is 97)
         */
        int x = c1 - 97;
        if(x < 0 || x > 7) // Out of bound
        	return null;
        
        /** 
         * y-position (ascii value for '1' is 49)
         */
        int y = 56 - c2;
        if(y < 0 || y > 7) // Out of bound
            return null;

        return new Coord(x, y);
    }

    /**
     * Reset en passant value
     */
    private static void resetEPvalue(boolean player) {

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {

                }
                else if(s.piece instanceof Pawn && s.player == player)
                    ((Pawn) board.tile[i][j].piece).doubleJump = false;
            }
        }
    }
    
    /**
     * Rule for stalemate:
     * 1. No pieces are able to move except for King piece
     * 2. King piece is in a position where if it moves then it is in check
     * 
     * @return true if stalemate applies, false otherwise
     */
    private static boolean stalemate(boolean player) {
        Coord wK_pos = null, bK_pos = null, test;
        if (!player) {
        	for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    Tile s = board.tile[i][j];
                    if(s == null) {
                        //Skip null spot
                    }
                    else if((s.piece instanceof King) && s.player == true)
                        wK_pos = new Coord(j , i);
                                   
                }
               
            }

            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    Tile s = board.tile[i][j];
                    if(s == null) {
                        //Skip null spot
                    }  else if(s.player == false) {
                        test = new Coord(j , i);
                        if(s.piece.legalMove(test, wK_pos, board)) 
                            return true; //White in check
                    }
                }
            }
        }
        else if(player) {
	        for(int i = 0; i < 8; i++) {
	            for(int j = 0; j < 8; j++) {
	                Tile s = board.tile[i][j];
	                if(s == null) {
	                    //Skip null spot
	                }
	                else if((s.piece instanceof King) && s.player == false)
	                    bK_pos = new Coord(j , i);
	                               
	            }
	           
	        }
	
	        for(int i = 0; i < 8; i++) {
	            for(int j = 0; j < 8; j++) {
	                Tile s = board.tile[i][j];
	                if(s == null) {
	                    //Skip null spot
	                }  else if(s.player == true) {
	                    test = new Coord(j , i);
	                    if(s.piece.legalMove(test, bK_pos, board)) 
	                        return true; //Black in check
	                }
	            }
	        }
        }
    	return false;
    }
}


