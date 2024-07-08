package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.PieceType;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 * This class is responsible for checking whether a black piece is threatened by
 * a white bishop.
 * 
 * @version 1.0.0 (Jul 8, 2024)
 * @since 1.0.0 (Jul 8, 2024)
 */
public class BlackUnderAttackCheckByWhiteBishop implements UnderAttackCheck {

    /**
     * Checks that the piece at file {@code file} and rank {@code rank} is 
     * threatened by a black bishop.
     * 
     * @param state the board state to consider.
     * @param file  the file of the possibly threatened piece.
     * @param rank  the rank of the possibly threatened piece.
     * 
     * @return {@code true} if and only if the piece in question is threatened.
     */
    @Override
    public boolean check(final ChessBoardState state, 
                         final int file, 
                         final int rank) {
    
        if (blackCellIsUnderAttackByWhiteBishopNorthWest(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteBishopNorthEast(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteBishopSouthEast(state, file, rank)) {
            return true;
        }
        
        return blackCellIsUnderAttackByWhiteBishopSouthWest(state, file, rank);
    }
    
    /**
     * Checks that a black piece is under attack by a white bishop on the top
     * left.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteBishopNorthWest(
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
            
            return piece.getPieceType() == PieceType.BISHOP;
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a black piece is under attack by a white bishop on the top
     * right.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteBishopNorthEast(
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
            
            return piece.getPieceType() == PieceType.BISHOP;
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a black piece is under attack by a white bishop on the bottom
     * left.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteBishopSouthWest(
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
            
            return piece.getPieceType() == PieceType.BISHOP;
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a black piece is under attack by a white bishop on the bottom
     * right.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteBishopSouthEast(
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
            
            return piece.getPieceType() == PieceType.BISHOP;
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
}
