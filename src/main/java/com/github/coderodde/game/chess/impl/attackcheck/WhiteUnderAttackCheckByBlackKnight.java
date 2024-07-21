package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.CellCoordinates;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceType.KNIGHT;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 * This class is responsible for checking whether a black piece is threatened by
 * a white knight.
 * 
 * @version 1.0.1 (Jul 21, 2024)
 * @since 1.0.0 (Jul 9, 2024)
 */
public final class WhiteUnderAttackCheckByBlackKnight
        extends UnderAttackCheck {

    @Override
    public boolean check(final ChessBoardState state, 
                         final int file, 
                         final int rank) {
        
        if (whiteCellIsUnderAttackByBlackKnightNorthLeft(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackKnightNorthRight(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackKnightSouthLeft(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackBlackSouthRight(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackKnightWestUp(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackKnightWestDown(state, file, rank)) {
            return true;
        }
        
        if (whiteCellIsUnderAttackByBlackKnightEastUp(state, file, rank)) {
            return true;
        }
        
        return whiteCellIsUnderAttackByBlackKnightEastDown(state, file, rank);
    }
    
    /**
     * Checks that a black knight threatens a white piece up north left.
     * 
     * @param state the state to check.
     * @param file  the file of the piece to test.
     * @param rank  the rank of the piece to test.
     * 
     * @return {@code true} if and only if the specified white piece is 
     *         threatened by a black knight from north left.
     */
    private boolean whiteCellIsUnderAttackByBlackKnightNorthLeft(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file == 0) {
            // Can't go to left:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (rank < 2) {
            // Can't go up north:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file - 1, rank - 2);
        
        if (piece == null || piece.isWhite()) {
            // Nothing is threating:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KNIGHT) {
            ATTACKER_COORDINATES.file = file - 1;
            ATTACKER_COORDINATES.rank = rank - 2;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a black knight threatens a white piece up north right.
     * 
     * @param state the state to check.
     * @param file  the file of the piece to test.
     * @param rank  the rank of the piece to test.
     * 
     * @return {@code true} if and only if the specified white piece is 
     *         threatened by a black knight from north right.
     */
    private boolean whiteCellIsUnderAttackByBlackKnightNorthRight(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file == N - 1) {
            // Can't go to right:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (rank < 2) {
            // Can't go up north:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file + 1, rank - 2);
        
        if (piece == null || piece.isWhite()) {
            // Nothing is threating:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KNIGHT) {
            ATTACKER_COORDINATES.file = file + 1;
            ATTACKER_COORDINATES.rank = rank - 2;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a black knight threatens a white piece up south left.
     * 
     * @param state the state to check.
     * @param file  the file of the piece to test.
     * @param rank  the rank of the piece to test.
     * 
     * @return {@code true} if and only if the specified white piece is 
     *         threatened by a black knight from south left.
     */
    private boolean whiteCellIsUnderAttackByBlackKnightSouthLeft(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file == 0) {
            // Can't go to the left:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (rank > 5) {
            // Can't go up south:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file - 1, rank + 2);
        
        if (piece == null || piece.isWhite()) {
            // Nothing is threatening:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KNIGHT) {
            ATTACKER_COORDINATES.file = file - 1;
            ATTACKER_COORDINATES.rank = rank + 2;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a black knight threatens a white piece up south right.
     * 
     * @param state the state to check.
     * @param file  the file of the piece to test.
     * @param rank  the rank of the piece to test.
     * 
     * @return {@code true} if and only if the specified white piece is 
     *         threatened by a black knight from south right.
     */
    private boolean whiteCellIsUnderAttackByBlackBlackSouthRight(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file == N - 1) {
            // Can't go to the right:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (rank > 5) {
            // Can't go up south:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file + 1, rank + 2);
        
        if (piece == null || piece.isWhite()) {
            // Nothing is threatening:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KNIGHT) {
            ATTACKER_COORDINATES.file = file + 1;
            ATTACKER_COORDINATES.rank = rank + 2;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a black knight threatens a white piece to the west up.
     * 
     * @param state the state to check.
     * @param file  the file of the piece to test.
     * @param rank  the rank of the piece to test.
     * 
     * @return {@code true} if and only if the specified white piece is 
     *         threatened by a black knight from west up.
     */
    private boolean whiteCellIsUnderAttackByBlackKnightWestUp(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file < 2) {
            // Can't go to west:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (rank == 0) {
            // Can't go upwards:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file - 2, rank - 1);
        
        if (piece == null || piece.isWhite()) {
            // Nothing is threatening
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KNIGHT) {
            ATTACKER_COORDINATES.file = file - 2;
            ATTACKER_COORDINATES.rank = rank - 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a black knight threatens a white piece to the west down.
     * 
     * @param state the state to check.
     * @param file  the file of the piece to test.
     * @param rank  the rank of the piece to test.
     * 
     * @return {@code true} if and only if the specified white piece is 
     *         threatened by a black knight from west down.
     */
    private boolean whiteCellIsUnderAttackByBlackKnightWestDown(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file < 2) {
            // Can't go to west:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (rank == N - 1) {
            // Can't go downwards:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file - 2, rank + 1);
        
        if (piece == null || piece.isWhite()) {
            // Nothing is threatening:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KNIGHT) {           
            ATTACKER_COORDINATES.file = file - 2;
            ATTACKER_COORDINATES.rank = rank + 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a black knight threatens a white piece to the east up.
     * 
     * @param state the state to check.
     * @param file  the file of the piece to test.
     * @param rank  the rank of the piece to test.
     * 
     * @return {@code true} if and only if the specified white piece is 
     *         threatened by a black knight from east up.
     */
    private boolean whiteCellIsUnderAttackByBlackKnightEastUp(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file > N - 3) {
            // Can't go to east:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (rank == 0) {
            // Can't go upwards:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file + 2, rank - 1);
        
        if (piece == null || piece.isWhite()) {
            // Nothing is threatening:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KNIGHT) {
            ATTACKER_COORDINATES.file = file + 2;
            ATTACKER_COORDINATES.rank = rank - 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    /**
     * Checks that a black knight threatens a white piece to the east down.
     * 
     * @param state the state to check.
     * @param file  the file of the piece to test.
     * @param rank  the rank of the piece to test.
     * 
     * @return {@code true} if and only if the specified white piece is 
     *         threatened by a black knight from east down.
     */
    private boolean whiteCellIsUnderAttackByBlackKnightEastDown(
            final ChessBoardState state,
            final int file, 
            final int rank) {
        
        // Check whether the move is outside of the board:
        if (file > N - 3) {
            // Can't go to east:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (rank == N - 1) {
            // Can't go downwards:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file + 2, rank + 1);
        
        if (piece == null || piece.isWhite()) {
            // Nothing is threating:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == KNIGHT) {
            ATTACKER_COORDINATES.file = file + 2;
            ATTACKER_COORDINATES.rank = rank + 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
}
