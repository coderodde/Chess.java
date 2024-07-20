package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.CellCoordinates;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceType.KING;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 * This class is responsible for checking whether a black piece is threatened by
 * a white king.
 * 
 * @version 1.0.0 (Jul 12, 2024)
 * @since 1.0.0 (Jul 12, 2024)
 */
public final class BlackUnderAttackCheckByWhiteKing 
        extends UnderAttackCheck {

    /**
     * Checks that a black piece at the file {@code file} and the rank 
     * {@code rank} is threatened by the white king.
     * 
     * @param state the state to check in.
     * @param file  the file of the black piece.
     * @param rank  the rank of the black piece.
     * 
     * @return {@code true} if and only if the specified black piece is 
     *         threatened by the white king.
     */
    @Override
    public boolean check(final ChessBoardState state,
                         final int file, 
                         final int rank) {
        if (blackPieceUnderAttackByWhiteKingNorth(state, file, rank)) {
            return true;
        }
        
        if (blackPieceUnderAttackByWhiteKingSouth(state, file, rank)) {
            return true;
        }
        
        if (blackPieceUnderAttackByWhiteKingWest(state, file, rank)) {
            return true;
        }
        
        if (blackPieceUnderAttackByWhiteKingEast(state, file, rank)) {
            return true;
        }
        
        if (blackPieceUnderAttackByWhiteKingNorthEast(state, file, rank)) {
            return true;
        }
        
        if (blackPieceUnderAttackByWhiteKingSouthEast(state, file, rank)) {
            return true;
        }
        
        if (blackPieceUnderAttackByWhiteKingSouthWest(state, file, rank)) {
            return true;
        }
        
        return blackPieceUnderAttackByWhiteKingNorthWest(state, file, rank);
    }
    
    /**
     * Checks that a black piece is threatened by a white king at north.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a black piece is threatened by a 
     *         white king.
     */
    private boolean blackPieceUnderAttackByWhiteKingNorth(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        if (rank == 0) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file, rank - 1);
        
        if (piece == null || piece.isBlack()) {
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
     * Checks that a black piece is threatened by a white king at south.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a black piece is threatened by a 
     *         white king.
     */
    private boolean blackPieceUnderAttackByWhiteKingSouth(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        if (rank == N - 1) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file, rank + 1);
        
        if (piece == null || piece.isBlack()) {
            // Nothing to threat:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KING) {
            ATTACKER_COORDINATES.file = file;
            ATTACKER_COORDINATES.rank = rank + 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a black piece is threatened by a white king at west.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a black piece is threatened by a 
     *         white king.
     */
    private boolean blackPieceUnderAttackByWhiteKingWest(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        if (file == 0) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file - 1, rank);
        
        if (piece == null || piece.isBlack()) {
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
     * Checks that a black piece is threatened by a white king at east.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a black piece is threatened by a 
     *         white king.
     */
    private boolean blackPieceUnderAttackByWhiteKingEast(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        if (file == N - 1) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file + 1, rank);
        
        if (piece == null || piece.isBlack()) {
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
     * Checks that a black piece is threatened by a white king at north east.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a black piece is threatened by a 
     *         white king.
     */
    private boolean blackPieceUnderAttackByWhiteKingNorthEast(
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
        
        if (piece == null || piece.isBlack()) {
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
     * Checks that a black piece is threatened by a white king at south east.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a black piece is threatened by a 
     *         white king.
     */
    private boolean blackPieceUnderAttackByWhiteKingSouthEast(
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
        
        if (piece == null || piece.isBlack()) {
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
     * Checks that a black piece is threatened by a white king at south west.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a black piece is threatened by a 
     *         white king.
     */
    private boolean blackPieceUnderAttackByWhiteKingSouthWest(
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
        
        if (piece == null || piece.isBlack()) {
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
     * Checks that a black piece is threatened by a white king at north west.
     * 
     * @param state the state to check.
     * @param file  the file of a black piece to test.
     * @param rank  the rank of a black piece to test.
     * 
     * @return {@code true} if and only if a black piece is threatened by a 
     *         white king.
     */
    private boolean blackPieceUnderAttackByWhiteKingNorthWest(
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
        
        if (piece == null || piece.isBlack()) {
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
