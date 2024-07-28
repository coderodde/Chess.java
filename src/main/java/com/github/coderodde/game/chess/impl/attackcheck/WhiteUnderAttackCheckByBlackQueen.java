package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.CellCoordinates;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.PieceType;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 * This class is responsible for checking whether a white piece is threatened by
 * the black queen.
 * 
 * @version 1.0.0 (Jul 7, 2024)
 * @since 1.0.0 (Jul 7, 2024)
 */
public final class WhiteUnderAttackCheckByBlackQueen 
        extends UnderAttackCheck {

    /**
     * Checks that the piece at file {@code file} and rank {@code rank} is 
     * threatened by a black queen.
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
        
        if (whiteCellIsUnderAttackByBlackQueenNorth(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackQueenSouth(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackQueenWest(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackQueenEast(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackQueenNorthEast(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackQueenNorthWest(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackQueenSouthEast(state, file, rank)) {
            return true;
        }
        
        return whiteCellIsUnderAttackByBlackQueenSouthWest(state, file, rank);
    }
    
    /**
     * Checks that a white piece is under attack by a black queen on top of the
     * queen.
     * 
     * @param file the file of the black queen to test for.
     * @param rank the rank of the black queen to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackQueenNorth(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int r = rank - 1; r >= 0; r--) {
            
            final Piece piece = state.get(file, r);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                // Blocked by the same color piece:
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! A black queen threatens the position:
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
     * Checks that a white piece is under attack by a black queen below the
     * piece.
     * 
     * @param file the file of the black queen to test for.
     * @param rank the rank of the black queen to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackQueenSouth(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int r = rank + 1; r < N; r++) {
            final Piece piece = state.get(file, r);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                // Blocked by the same color piece:
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! A black queen threatens the position:
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
     * Checks that a white piece is under attack by a black queen on the left.
     * 
     * @param file the file of the black queen to test for.
     * @param rank the rank of the black queen to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackQueenWest(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int f = file - 1; f >= 0; f--) {
            
            final Piece piece = state.get(f, rank);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                // Blocked by the same color piece:
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! A black queen threatens the position:
                ATTACKER_COORDINATES.file = f;
                ATTACKER_COORDINATES.rank = rank;
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black queen on the right.
     * 
     * @param file the file of the black queen to test for.
     * @param rank the rank of the black queen to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackQueenEast(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        for (int f = file + 1; f < N; f++) {
            
            final Piece piece = state.get(f, rank);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                // Blocked by the same color piece:
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! A black queen threatens the position:
                ATTACKER_COORDINATES.file = f;
                ATTACKER_COORDINATES.rank = rank;
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black queen on the top
     * left.
     * 
     * @param file the file of the black queen to test for.
     * @param rank the rank of the black queen to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackQueenNorthWest(
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
            
            if (piece.getPieceType() == PieceType.QUEEN) {
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
     * Checks that a white piece is under attack by a black queen on the top
     * right.
     * 
     * @param file the file of the black queen to test for.
     * @param rank the rank of the black queen to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackQueenNorthEast(
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
            
            if (piece.getPieceType() == PieceType.QUEEN) {
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
     * Checks that a white piece is under attack by a black queen on the bottom
     * left.
     * 
     * @param file the file of the black queen to test for.
     * @param rank the rank of the black queen to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackQueenSouthWest(
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
            
            if (piece.getPieceType() == PieceType.QUEEN) {
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
     * Checks that a white piece is under attack by a black queen on the bottom
     * right.
     * 
     * @param file the file of the black queen to test for.
     * @param rank the rank of the black queen to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean whiteCellIsUnderAttackByBlackQueenSouthEast(
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
                // Blocked by a piece with the same color (white):
                ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                // Match! A black queen threatens this piece:
                ATTACKER_COORDINATES.file = f;
                ATTACKER_COORDINATES.rank = r;
                return true;
            }
            
            f++;
            r++;
        }
        
        // Once here, there is no black queen below the piece:
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
}
