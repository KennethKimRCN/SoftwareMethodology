package chess;

import piece.*;

/**
 * 8 by 8 tile board object that can hold chess pieces
 *
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class Board {

    /**
     * 8 by 8 2D array board
     */
    public Tile[][] tile;

    /**
     * Each tile of the board holds a chess piece [false = Black / true = White]
     */
    public Board() {

        tile = new Tile[8][8]; // Creates an 8 by 8 board

        // Populate tiles
        
        tile[0][0] = new Tile(new Rook(), false);
        tile[0][1] = new Tile(new Knight(), false);
        tile[0][2] = new Tile(new Bishop(), false);
        tile[0][3] = new Tile(new Queen(), false);
        tile[0][4] = new Tile(new King(), false);
        tile[0][5] = new Tile(new Bishop(), false);
        tile[0][6] = new Tile(new Knight(), false);
        tile[0][7] = new Tile(new Rook(), false);

        for(int i = 0; i < 8; i++) {
            tile[1][i] = new Tile(new Pawn(), false);
        }

        tile[7][0] = new Tile(new Rook(), true);
        tile[7][1] = new Tile(new Knight(), true);
        tile[7][2] = new Tile(new Bishop(), true);
        tile[7][3] = new Tile(new Queen(), true);
        tile[7][4] = new Tile(new King(), true);
        tile[7][5] = new Tile(new Bishop(), true);
        tile[7][6] = new Tile(new Knight(), true);
        tile[7][7] = new Tile(new Rook(), true);

        for(int i = 0; i < 8; i++) {
            tile[6][i] = new Tile(new Pawn(), true);
        }
        
        //Custom map to test
        /*
	        bK ##    ##    ##    ## 8
	        bR    ##    ##    ##    7
	           bp    ##    ##    ## 6
	        ##    ##    ##    ##    5
	        wN ##    ##    ##    ## 4
	        wK    ##    ##    ##    3
	           ##    ##    ##    ## 2
	        ##    ##    ##    ##    1
	         a  b  c  d  e  f  g  h 
         
        tile[0][0] = new Tile(new King(), false);
        tile[1][0] = new Tile(new Rook(), false);
        tile[2][1] = new Tile(new Pawn(), false);
        tile[4][0] = new Tile(new Knight(), true);
        tile[5][0] = new Tile(new King(), true);
		*/

    }

    /**
     * Prints out board
     */
    public void printBoard() {
    	
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                
            	/**
            	 * If tile is empty print blank or ##
            	 */
                if (tile[i][j] == null) {
                	if(i % 2 == 0) {
                		if (j % 2 == 0) 
                			System.out.print("   ");
                		else
                			System.out.print("## ");
                	}
                	else if(i % 2 == 1) {
                		if (j % 2 == 0)
                			System.out.print("## ");
                		else
                			System.out.print("   ");
                	}
                } else // Prints out piece
                	System.out.print(tile[i][j] + " ");
                if (j == 7) // Prints number to the right
                	System.out.print(8 - i);
            }
            System.out.println();
        }
        // Prints alphabets to the bottom
        char abc = 'a';
        for (int i = 0; i < 8; i++) {
        	System.out.print(" " + abc + " ");
        	abc++;
        }
        System.out.println();
    }
}
