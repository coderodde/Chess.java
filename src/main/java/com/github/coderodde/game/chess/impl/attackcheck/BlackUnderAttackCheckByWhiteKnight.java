package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.PieceColor;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 * This class is responsible for checking whether a black piece is threatened by
 * a white knight.
 * 
 * @version 1.0.0 (Jul 9, 2024)
 * @since 1.0.0 (Jul 9, 2024)
 */
public final class BlackUnderAttackCheckByWhiteKnight
        implements UnderAttackCheck {

    @Override
    public boolean check(final ChessBoardState state, 
                         final int file, 
                         final int rank) {
        
        if (blackCellIsUnderAttackByWhiteKnightNorthLeft(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteKnightNorthRight(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteKnightSouthLeft(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteKnightSouthRight(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteKnightWestUp(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteKnightWestDown(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteKnightEastUp(state, file, rank)) {
            return true;
        }
        
        return blackCellIsUnderAttackByWhiteKnightEastDown(state, file, rank);
    }
    
    /**
     * Checks that the white knight threatens a black piece up north left.
     * 
     * @param state the state to check.
     * @param file  the file of the white knight.
     * @param rank  the rank of the white knight.
     * 
     * @return {@code true} if and only if the input white knight threatens a
     *         black piece up north to the left.
     */
    private boolean blackCellIsUnderAttackByWhiteKnightNorthLeft(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file == 0) {
            // Can't go to left:
            return false;
        }
        
        if (rank < 2) {
            // Can't go up north:
            return false;
        }
        
        final Piece piece = state.get(file - 1, rank - 2);
        
        if (piece == null) {
            // Nothing to threat:
            return false;
        }
        
        return piece.isBlack();
    }
    
    /**
     * Checks that the white knight threatens a black piece up north right.
     * 
     * @param state the state to check.
     * @param file  the file of the white knight.
     * @param rank  the rank of the white knight.
     * 
     * @return {@code true} if and only if the input white knight threatens a
     *         black piece up north to the right.
     */
    private boolean blackCellIsUnderAttackByWhiteKnightNorthRight(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file == N - 1) {
            // Can't go to right:
            return false;
        }
        
        if (rank < 2) {
            // Can't go up north:
            return false;
        }
        
        final Piece piece = state.get(file + 1, rank - 2);
        
        if (piece == null) {
            // Nothing to threat:
            return false;
        }
        
        return piece.isBlack();
    }
    
    /**
     * Checks that the white knight threatens a black piece up south left.
     * 
     * @param state the state to check.
     * @param file  the file of the white knight.
     * @param rank  the rank of the white knight.
     * 
     * @return {@code true} if and only if the input white knight threatens a
     *         black piece up south to the left.
     */
    private boolean blackCellIsUnderAttackByWhiteKnightSouthLeft(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file == 0) {
            // Can't go to the left:
            return false;
        }
        
        if (rank > 5) {
            // Can't go up south:
            return false;
        }
        
        final Piece piece = state.get(file - 1, rank + 2);
        
        if (piece == null) {
            // Nothting to threat:
            return false;
        }
        
        return piece.isBlack();
    }
    
    /**
     * Checks that the white knight threatens a black piece up south right.
     * 
     * @param state the state to check.
     * @param file  the file of the white knight.
     * @param rank  the rank of the white knight.
     * 
     * @return {@code true} if and only if the input white knight threatens a
     *         black piece up south to the right.
     */
    private boolean blackCellIsUnderAttackByWhiteKnightSouthRight(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file == N - 1) {
            // Can't go to the right:
            return false;
        }
        
        if (rank > 5) {
            // Can't go up south:
            return false;
        }
        
        final Piece piece = state.get(file + 1, rank + 2);
        
        if (piece == null) {
            // Nothing to threat:
            return false;
        }
        
        return piece.isBlack();
    }
    
    /**
     * Checks that the white knight threatens a black piece to the west up.
     * 
     * @param state the state to check.
     * @param file  the file of the white knight.
     * @param rank  the rank of the white knight.
     * 
     * @return {@code true} if and only if the input white knight threatens a
     *         black piece to the west up.
     */
    private boolean blackCellIsUnderAttackByWhiteKnightWestUp(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file < 2) {
            // Can't go to west:
            return false;
        }
        
        if (rank == 0) {
            // Can't go upwards:
            return false;
        }
        
        final Piece piece = state.get(file - 2, rank - 1);
        
        if (piece == null) {
            // Nothing to threat:
            return false;
        }
        
        return piece.isBlack();
    }
    
    /**
     * Checks that the white knight threatens a black piece to the west down.
     * 
     * @param state the state to check.
     * @param file  the file of the white knight.
     * @param rank  the rank of the white knight.
     * 
     * @return {@code true} if and only if the input white knight threatens a
     *         black piece to the west down.
     */
    private boolean blackCellIsUnderAttackByWhiteKnightWestDown(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file < 2) {
            // Can't go to west:
            return false;
        }
        
        if (rank == N - 1) {
            // Can't go downwards:
            return false;
        }
        
        final Piece piece = state.get(file - 2, rank + 1);
        
        if (piece == null) {
            // Nothing to threat:
            return false;
        }
        
        return piece.isBlack();
    }
    
    /**
     * Checks that the white knight threatens a black piece to the east up.
     * 
     * @param state the state to check.
     * @param file  the file of the white knight.
     * @param rank  the rank of the white knight.
     * 
     * @return {@code true} if and only if the input white knight threatens a
     *         black piece to the east up.
     */
    private boolean blackCellIsUnderAttackByWhiteKnightEastUp(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file > N - 3) {
            // Can't go to east:
            return false;
        }
        
        if (rank == 0) {
            // Can't go upwards:
            return false;
        }
        
        final Piece piece = state.get(file + 2, rank - 1);
        
        if (piece == null) {
            // Nothing to threat:
            return false;
        }
        
        return piece.isBlack();
    }
    
    /**
     * Checks that the white knight threatens a black piece to the east down.
     * 
     * @param state the state to check.
     * @param file  the file of the white knight.
     * @param rank  the rank of the white knight.
     * 
     * @return {@code true} if and only if the input white knight threatens a
     *         black piece to the east down.
     */
    private boolean blackCellIsUnderAttackByWhiteKnightEastDown(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file > N - 3) {
            // Can't go to east:
            return false;
        }
        
        if (rank == N - 1) {
            // Can't go downwards:
            return false;
        }
        
        final Piece piece = state.get(file + 2, rank + 1);
        
        if (piece == null) {
            // Nothing to threat:
            return false;
        }
        
        return piece.isBlack();
    }
}
