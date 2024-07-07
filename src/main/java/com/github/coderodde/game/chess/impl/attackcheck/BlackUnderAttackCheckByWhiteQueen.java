package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.PieceType;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 * This class is responsible for checking whether a black piece is threatened by
 * the white queen..
 * 
 * @version 1.0.0 (Jul 7, 2024)
 * @since 1.0.0 (Jul 7, 2024)
 */
public final class BlackUnderAttackCheckByWhiteQueen 
        implements UnderAttackCheck {

    @Override
    public boolean check(final ChessBoardState state,
                         final int file, 
                         final int rank) {
        
        if (blackCellIsUnderAttackByWhiteQueenNorth(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteQueenSouth(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteQueenWest(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteQueenEast(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteQueenNorthEast(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteQueenNorthWest(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteQueenSouthEast(state, file, rank)) {
            return true;
        }
        
        return blackCellIsUnderAttackByWhiteQueenSouthWest(state, file, rank);
    }
    
    /**
     * Checks that a white piece is under attack by a black queen on top of the
     * piece.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteQueenNorth(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int r = rank - 1; r >= 0; r--) {
            
            final Piece piece = state.get(file, r);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isBlack()) {
                // Blocked by the same color piece (black):
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! A black queen threatens the position:
                return true;
            }
        }
        
        // Once here, there is no black queen above the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black queen below the
     * piece.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteQueenSouth(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int r = rank + 1; r < N; r++) {
            final Piece piece = state.get(file, r);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isBlack()) {
                // Blocked by the same color piece (black):
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! A black queen threatens the position:
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black queen on the left.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteQueenWest(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int f = file - 1; f >= 0; f--) {
            
            final Piece piece = state.get(f, rank);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isBlack()) {
                // Blocked by the same color piece (black):
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! A black queen threatens the position:
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black queen on the right.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteQueenEast(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int f = file + 1; f < N; f++) {
            
            final Piece piece = state.get(f, rank);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isBlack()) {
                // Blocked by the same color piece (black):
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! A black queen threatens the position:
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black queen on the top
     * left.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteQueenNorthWest(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        int f = file - 1;
        int r = rank - 1;
        
        while (f >= 0 && r >= 0) {
            
            final Piece piece = state.get(f, r);
            
            // Make sure that the counters are updated:
            f--;
            r--;
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isBlack()) {
                // Blocked by a piece with the same color (black):
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! The black queen threatens the piece:
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black queen on the top
     * right.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteQueenNorthEast(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        int f = file + 1;
        int r = rank - 1;
        
        while (f < N && r >= 0) {
            
            final Piece piece = state.get(f, r);
            
            // Make sure the indices are updated:
            f++;
            r--;
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isBlack()) {
                // Blocked by a piece with the same color (black):
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! The black queen threatens this position:
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black queen on the bottom
     * left.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteQueenSouthWest(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        int f = file - 1;
        int r = rank + 1;
        
        while (f >= 0 && r < N) {
            
            final Piece piece = state.get(f, r);
            
            // Make sure that the indices are updated:
            f--;
            r++;
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isBlack()) {
                // Blocked by the piece of the same color (black):
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! The black queen threatens the current position:
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black queen on the bottom
     * left.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteQueenSouthEast(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        int f = file + 1;
        int r = rank + 1;
        
        while (f < N && r < N) {
            
            final Piece piece = state.get(f, r);
            
            // Make sure that the indices are updated:
            f++;
            r++;
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isBlack()) {
                // Blocked by a piece with the same color (black):
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! A black queen threatens this piece:
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
}
