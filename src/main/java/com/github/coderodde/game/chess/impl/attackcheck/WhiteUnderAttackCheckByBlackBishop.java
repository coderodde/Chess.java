package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.PieceType;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 * This class is responsible for checking whether a white piece is threatened by
 * a black bishop.
 * 
 * @version 1.0.0 (Jul 8, 2024)
 * @since 1.0.0 (Jul 8, 2024)
 */
public class WhiteUnderAttackCheckByBlackBishop implements UnderAttackCheck {

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
    
        if (whiteCellIsUnderAttackByBlackBishopNorthWest(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackBishopNorthEast(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackBishopSouthEast(state, file, rank)) {
            return true;
        }
        
        return whiteCellIsUnderAttackByBlackBishopSouthWest(state, file, rank);
    }
    
    /**
     * Checks that a white piece is under attack by a black bishop on the top
     * left.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackBishopNorthWest(
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
            
            if (piece.isWhite()) {
                // Blocked by a piece with the same color (white):
                return false;
            }
            
            if (piece.getPieceType() == PieceType.BISHOP) {
                // Match! The black queen threatens the piece:
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black bishop on the top
     * right.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackBishopNorthEast(
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
            
            if (piece.isWhite()) {
                // Blocked by a piece with the same color:
                return false;
            }
            
            if (piece.getPieceType() == PieceType.BISHOP) {
                // Match! The black queen threatens this position:
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black bishop on the bottom
     * left.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackBishopSouthWest(
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
            
            if (piece.isWhite()) {
                // Blocked by the piece of the same color (white):
                return false;
            }
            
            if (piece.getPieceType() == PieceType.BISHOP) {
                // Match! The black queen threatens the current position:
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black bishop on the bottom
     * right.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackBishopSouthEast(
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
            
            if (piece.isWhite()) {
                // Blocked by a piece with the same color (white):
                return false;
            }
        
            return piece.getPieceType() == PieceType.BISHOP;
        }
        
        return false;
    }
}
