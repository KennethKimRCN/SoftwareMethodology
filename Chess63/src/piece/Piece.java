package piece;

import chess.*;

/**
 * The basis for a standard chess piece
 *
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public abstract class Piece {
    // First move tracker
    public boolean firstMove;
    // Promotion tracker for pawn
    public char promotion;
    // En passant tracker for pawn
    public boolean enpassant;
    // Castling tracker for Rook and King
    public char castling;

    /**
     * Check if move is legal
     *
     * @param start Current location of piece
     * @param end Destination of the piece
     * @param board
     * @return True if move is legal, false otherwise
     */
    public abstract boolean legalMove(Coord start, Coord end, Board board);
}
