package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.CellCoordinates;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceType.KING;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 * This class is responsible for checking whether a white piece is threatened by
 * a black king.
 * 
 * @version 1.0.1 (Jul 20, 2024)
 * @since 1.0.0 (Jul 12, 2024)
 */
public final class WhiteUnderAttackCheckByBlackKing
        extends UnderAttackCheck {

    /**
     * Checks that a white piece at the file {@code file} and the rank 
     * {@code rank} is threatened by the black king.
     * 
     * @param state the state to check in.
     * @param file  the file of the black piece.
     * @param rank  the rank of the black piece.
     * 
     * @return {@code true} if and only if the specified white piece is 
     *         threatened by the black king.
     */
    @Override
    public boolean check(final ChessBoardState state,
                         final int file, 
                         final int rank) {
        if (whitePieceUnderAttackByBlackKingNorth(state, file, rank)) {
            return true;
        }
        
        if (whitePieceUnderAttackByBlackKingSouth(state, file, rank)) {
            return true;
        }
        
        if (whitePieceUnderAttackByBlackKingWest(state, file, rank)) {
            return true;
        }
        
        if (whitePieceUnderAttackByBlackKingEast(state, file, rank)) {
            return true;
        }
        
        if (whitePieceUnderAttackByBlackKingNorthEast(state, file, rank)) {
            return true;
        }
        
        if (whitePieceUnderAttackByBlackKingSouthEast(state, file, rank)) {
            return true;
        }
        
        if (whitePieceUnderAttackByBlackKingSouthWest(state, file, rank)) {
            return true;
        }
        
        return whitePieceUnderAttackByBlackKingNorthWest(state, file, rank);
    }
    
    /**
     * Checks that a white piece is threatened by a black king at north.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a white piece is threatened by a 
     *         black king.
     */
    private boolean whitePieceUnderAttackByBlackKingNorth(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        if (rank == 0) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file, rank - 1);
        
        if (piece == null || piece.isWhite()) {
            // Nothing to threat:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KING) {
            ATTACKER_COORDINATES.file = file;
            ATTACKER_COORDINATES.rank = rank - 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a white piece is threatened by a black king at south.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a white piece is threatened by a 
     *         black king.
     */
    private boolean whitePieceUnderAttackByBlackKingSouth(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        if (rank == N - 1) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file, rank + 1);
        
        if (piece == null || piece.isWhite()) {
            // Nothing to threat:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KING) {
            ATTACKER_COORDINATES.file = file;
            ATTACKER_COORDINATES.file = rank + 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a white  piece is threatened by a black king at west.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a white piece is threatened by a 
     *         black king.
     */
    private boolean whitePieceUnderAttackByBlackKingWest(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        if (file == 0) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file - 1, rank);
        
        if (piece == null || piece.isWhite()) {
            // Nothing to threat:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KING) {
            ATTACKER_COORDINATES.file = file - 1;
            ATTACKER_COORDINATES.rank = rank;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a white piece is threatened by a black king at east.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a white piece is threatened by a 
     *         black king.
     */
    private boolean whitePieceUnderAttackByBlackKingEast(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        if (file == N - 1) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file + 1, rank);
        
        if (piece == null || piece.isWhite()) {
            // Nothing to threat:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KING) {
            ATTACKER_COORDINATES.file = file + 1;
            ATTACKER_COORDINATES.rank = rank;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a white piece is threatened by a black king at north east.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a white piece is threatened by a 
     *         black king.
     */
    private boolean whitePieceUnderAttackByBlackKingNorthEast(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        if (rank == 0) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (file == N - 1) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file + 1, rank - 1);
        
        if (piece == null || piece.isWhite()) {
            // Nothing to threat:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KING) {
            ATTACKER_COORDINATES.file = file + 1;
            ATTACKER_COORDINATES.rank = rank - 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a white piece is threatened by a black king at south east.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a white piece is threatened by a 
     *         black king.
     */
    private boolean whitePieceUnderAttackByBlackKingSouthEast(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        if (rank == N - 1) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (file == N - 1) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file + 1, rank + 1);
        
        if (piece == null || piece.isWhite()) {
            // Nothing to threat:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KING) {
            ATTACKER_COORDINATES.file = file + 1;
            ATTACKER_COORDINATES.rank = rank + 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a white piece is threatened by a black king at south west.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a white piece is threatened by a 
     *         black king.
     */
    private boolean whitePieceUnderAttackByBlackKingSouthWest(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        if (rank == N - 1) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (file == 0) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file - 1, rank + 1);
        
        if (piece == null || piece.isWhite()) {
            // Nothing to threat:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KING) {
            ATTACKER_COORDINATES.file = file - 1;
            ATTACKER_COORDINATES.rank = rank + 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a white piece is threatened by a black king at north west.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a white piece is threatened by a 
     *         black king.
     */
    private boolean whitePieceUnderAttackByBlackKingNorthWest(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        if (rank == 0) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (file == 0) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file - 1, rank - 1);
        
        if (piece == null || piece.isWhite()) {
            // Nothing to threat:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KING) {
            ATTACKER_COORDINATES.file = file - 1;
            ATTACKER_COORDINATES.rank = rank - 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
}
