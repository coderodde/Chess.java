package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.CellCoordinates;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.PieceType;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 * This class is responsible for checking whether a black piece is threatened by
 * a white rook.
 * 
 * @version 1.0.1 (Jul 21, 2024)
 * @since 1.0.0 (Jul 7, 2024)
 */
public final class BlackUnderAttackCheckByWhiteRook 
        extends UnderAttackCheck {

    @Override
    public boolean check(final ChessBoardState state,
                         final int file, 
                         final int rank) {
        
        if (blackCellIsUnderAttackByWhiteRookNorth(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteRookSouth(state, file, rank)) {
            return true;
        }
        
        if (blackCellIsUnderAttackByWhiteRookWest(state, file, rank)) {
            return true;
        }
        
        return blackCellIsUnderAttackByWhiteRookEast(state, file, rank);
    }
    
    /**
     * Checks that a black piece is under attack by a white rook on top of the
     * piece.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteRookNorth(
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
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                return false;
            }
            
            if (piece.getPieceType() == PieceType.ROOK) {
                ATTACKER_COORDINATES.file = file;
                ATTACKER_COORDINATES.rank = r;
                return true;
            }
        }
        
        // Once here, there is no black queen above the piece:
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a black piece is under attack by a white rook below the
     * piece.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteRookSouth(
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
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                return false;
            }
            
            if (piece.getPieceType() == PieceType.ROOK) {
                ATTACKER_COORDINATES.file = file;
                ATTACKER_COORDINATES.rank = r;
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a black piece is under attack by a white rook on the left.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteRookWest(
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
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                return false;
            }
            
            if (piece.getPieceType() == PieceType.ROOK) {
                ATTACKER_COORDINATES.file = f;
                ATTACKER_COORDINATES.rank = rank;
                return true;
            }
        }

        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a black piece is under attack by a white rook on the right.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean blackCellIsUnderAttackByWhiteRookEast(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int f = file + 1; f < N; f++) {
            
            final Piece piece = state.get(f, rank);
            
            if (piece == null) {
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                continue;
            }
            
            if (piece.isBlack()) {
                // Blocked by the same color piece (black):
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                return false;
            }
            
            if (piece.getPieceType() == PieceType.ROOK) {
                ATTACKER_COORDINATES.file = f;
                ATTACKER_COORDINATES.rank = rank;
                return true;
            }
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
}
