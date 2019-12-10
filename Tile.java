package com.example.chessproject;


/**
 * A tile contains a player's [black/white] piece
 *
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class Tile {

    public static Piece piece;

    /**
     * true = White, false == Black
     */
    public static boolean player;

    public Tile(Piece piece, boolean player) {
        this.piece = piece;
        this.player = player;
    }

    /**
     *
     * @return Prints out the string
     */

    public static String tosString(Tile t) {
        if(t.piece instanceof King) {
            if(t.player)
                return "wK";
            else
                return "bK";
        }
        if(t.piece instanceof Queen) {
            if(t.player)
                return "wQ";
            else
                return "bQ";
        }
        if(t.piece instanceof Rook) {
            if(t.player) {
                return "wR";
            }
            else
                return "bR";
        }
        if(t.piece instanceof Knight) {
            if(t.player)
                return "wN";
            else
                return "bN";
        }
        if(t.piece instanceof Bishop) {
            if(t.player)
                return "wB";
            else
                return "bB";
        }
        if(t.piece instanceof Pawn) {
            if(t.player)
                return "wp";
            else
                return "bp";
        }
        else return "  ";
    }

    public static String tosString() {
        if(piece instanceof King) {
            if(player)
                return "wK";
            else
                return "bK";
        }
        if(piece instanceof Queen) {
            if(player)
                return "wQ";
            else
                return "bQ";
        }
        if(piece instanceof Rook) {
            if(player) {
                return "wR";
            }
            else
                return "bR";
        }
        if(piece instanceof Knight) {
            if(player)
                return "wN";
            else
                return "bN";
        }
        if(piece instanceof Bishop) {
            if(player)
                return "wB";
            else
                return "bB";
        }
        if(piece instanceof Pawn) {
            if(player)
                return "wp";
            else
                return "bp";
        }
        else return "  ";
    }
}