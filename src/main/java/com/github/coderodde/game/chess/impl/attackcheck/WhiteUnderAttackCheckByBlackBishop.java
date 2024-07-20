package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.CellCoordinates;
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
public class WhiteUnderAttackCheckByBlackBishop 
        extends UnderAttackCheck {

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
            
            if (piece == null) {
                f--;
                r--;
                continue;
            }
            
            if (piece.isWhite()) {
                // Blocked by a piece with the same color (white):
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                return false;
            }
            
            if (piece.getPieceType() == PieceType.BISHOP) {
                // Match! The black queen threatens the piece:
                ATTACKER_COORDINATES.file = f;
                ATTACKER_COORDINATES.rank = r;
                return true;
            }
            
            f--;
            r--;
        }
        
        // Once here, there is no black queen below the piece:
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
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
            
            if (piece == null) {
                f++;
                r--;
                continue;
            }
            
            if (piece.isWhite()) {
                // Blocked by a piece with the same color:
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                return false;
            }
            
            if (piece.getPieceType() == PieceType.BISHOP) {
                // Match! The black queen threatens this position:
                ATTACKER_COORDINATES.file = f;
                ATTACKER_COORDINATES.rank = r;
                return true;
            }
            
            f++;
            r--;
        }
        
        // Once here, there is no black queen below the piece:
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
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
            
            if (piece == null) {
                f--;
                r++;
                continue;
            }
            
            if (piece.isWhite()) {
                // Blocked by the piece of the same color (white):
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                return false;
            }
            
            if (piece.getPieceType() == PieceType.BISHOP) {
                // Match! The black queen threatens the current position:
                ATTACKER_COORDINATES.file = f;
                ATTACKER_COORDINATES.rank = r;
                return true;
            }
            
            f--;
            r++;
        }
        
        // Once here, there is no black queen below the piece:
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
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
            
            if (piece == null) {
                f++;
                r++;
                continue;
            }
            
            if (piece.isWhite()) {
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                // Blocked by a piece with the same color (white):
                return false;
            }
        
            if (piece.getPieceType() == PieceType.BISHOP) {
                // Match! The black queen threatens the current position:
                ATTACKER_COORDINATES.file = f;
                ATTACKER_COORDINATES.rank = r;
                return true;
            }
            
            f++;
            r++;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
}
