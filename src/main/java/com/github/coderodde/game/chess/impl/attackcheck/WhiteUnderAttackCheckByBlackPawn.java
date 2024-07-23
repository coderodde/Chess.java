package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.CellCoordinates;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.impl.expanders.WhitePawnExpander.EN_PASSANT_SOURCE_RANK;
import com.github.coderodde.game.chess.UnderAttackCheck;
import static com.github.coderodde.game.chess.UnderAttackCheck.ATTACKER_COORDINATES;

/**
 * This class is responsible for checking whether a black piece is threatened by
 * a white pawn.
 * 
 * @version 1.0.0 (Jul 15, 2024)
 * @since 1.0.0 (Jul 15, 2024)
 */
public final class WhiteUnderAttackCheckByBlackPawn 
        extends UnderAttackCheck {

    @Override
    public boolean check(final ChessBoardState state,
                         final int file,
                         final int rank) {
        
        if (threatensFromLeft(state, file, rank)) {
            return true;
        }
        
        if (threatensFromRight(state, file, rank)) {
            return true;
        }
        
        if (threatensFromLeftEnPassant(state, file, rank)) {
            return true;
        }
        
        return threatensFromRightEnPassant(state, file, rank);
    }
    
    private boolean threatensFromLeft(final ChessBoardState state,
                                      final int file,
                                      final int rank) {
        if (file == 0) {
            // Cannot capture from the left:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (rank == N - 1) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file - 1, rank + 1);
        
        if (piece == null || piece.isWhite()) {
            // Nothing to threat:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == PAWN) {
            ATTACKER_COORDINATES.file = file - 1;
            ATTACKER_COORDINATES.rank = rank + 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    private boolean threatensFromRight(final ChessBoardState state,
                                       final int file, 
                                       final int rank) {
        
        if (file == N - 1) {
            // Cannot capture from right:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (rank == N - 1) {
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        final Piece piece = state.get(file + 1, rank + 1);
        
        if (piece == null || piece.isWhite()) {
            // Nothing to threat:
            ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
            return false;
        }
        
        if (piece.getPieceType() == PAWN) {
            ATTACKER_COORDINATES.file = file + 1;
            ATTACKER_COORDINATES.rank = rank + 1;
            return true;
        }
        
        ATTACKER_COORDINATES.file = CellCoordinates.NO_ATTACK_FILE;
        return false;
    }
    
    private boolean threatensFromLeftEnPassant(final ChessBoardState state,
                                               final int file, 
                                               final int rank) {
        
        if (rank != EN_PASSANT_SOURCE_RANK) {
            // Cannot do en passant to begin with:
            return false;
        }
        
        if (file == 0) {
            // Cannot capture from left:
            return false;
        }
        
        if (state.getBlackIsPreviouslyDoubleMoved()[file] == false) {
            // The previous black move was not a double move of a pawn:
            return false;
        }
        
        final Piece piece = state.get(file - 1, rank);
        
        if (piece == null) {
            // Nothing to threat:
            return false;
        }
        
        return piece.isBlack() && piece.getPieceType() == PAWN;
    }
    
    private boolean threatensFromRightEnPassant(final ChessBoardState state,
                                                final int file, 
                                                final int rank) {
        
        if (rank != EN_PASSANT_SOURCE_RANK) {
            // Cannot do en passant to begin with:
            return false;
        }
        
        if (file == N - 1) {
            // Cannot capture from right:
            return false;
        }
        
        if (state.getBlackIsPreviouslyDoubleMoved()[file] == false) {
            // The previous black move was not a double move of a pawn:
            return false;
        }
        
        final Piece piece = state.get(file + 1, rank);
        
        if (piece == null) {
            // Nothing to threat:
            return false;
        }
        
        return piece.isBlack() && piece.getPieceType() == PAWN;
    }
}
