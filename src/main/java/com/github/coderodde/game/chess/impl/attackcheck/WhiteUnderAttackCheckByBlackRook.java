package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.PieceType;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 * This class is responsible for checking whether a white piece is threatened by
 * a black rook.
 * 
 * @version 1.0.0 (Jul 7, 2024)
 * @since 1.0.0 (Jul 7, 2024)
 */
public final class WhiteUnderAttackCheckByBlackRook 
        extends UnderAttackCheck {

    @Override
    public boolean check(final ChessBoardState state,
                         final int file, 
                         final int rank) {
        
        if (whiteCellIsUnderAttackByBlackRookNorth(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackRookSouth(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackRookWest(state, file, rank)) {
            return true;
        }
        
        return whiteCellIsUnderAttackByBlackRookEast(state, file, rank);
    }
    
    /**
     * Checks that a white piece is under attack by a black rook on top of the
     * piece.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackRookNorth(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int r = rank - 1; r >= 0; r--) {
            
            final Piece piece = state.get(file, r);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                // Blocked by the same color piece (white):
                return false;
            }
            
            return piece.getPieceType() == PieceType.ROOK;
        }
        
        // Once here, there is no black queen above the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black rook below the
     * piece.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackRookSouth(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int r = rank + 1; r < N; r++) {
            final Piece piece = state.get(file, r);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                // Blocked by the same color piece (white):
                return false;
            }
            
            return piece.getPieceType() == PieceType.ROOK;
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black rook on the left.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackRookWest(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int f = file - 1; f >= 0; f--) {
            
            final Piece piece = state.get(f, rank);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                // Blocked by the same color piece (white):
                return false;
            }
            
            return piece.getPieceType() == PieceType.ROOK;
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black rook on the right.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackRookEast(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int f = file + 1; f < N; f++) {
            
            final Piece piece = state.get(f, rank);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                // Blocked by the same color piece (white):
                return false;
            }
            
            return piece.getPieceType() == PieceType.ROOK;
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
}
