package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.CheckMateInspector;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.UnderAttackCheck;
import com.github.coderodde.game.chess.impl.attackcheck.WhiteUnderAttackCheck;

/**
 * This class implements the API for checking for checkmate for the white king.
 * 
 * @version 1.0.0 (Jul 16, 2024)
 * @since 1.0.0 (Jul 16, 2024)
 */
public final class WhiteCheckMateInspector implements CheckMateInspector {
    
    private static final UnderAttackCheck WHITE_ATTACK_CHECKER = 
            new WhiteUnderAttackCheck();

    /**
     * This method is responsible of finding out whether there is a checkmate 
     * for the white player.
     * 
     * @param state the state to check.
     * 
     * @return {@code true} if and only if the input state is in checkmate for
     *         the white player.
     */
    @Override
    public boolean isInCheckMate(final ChessBoardState state) {
    
        final int kingFile = state.getWhiteKingFile();
        final int kingRank = state.getWhiteKingRank();
        
        if (WHITE_ATTACK_CHECKER.check(state, kingFile, kingRank) == false) {
            // Easy case: the white king is not under attack.
            return false;
        }
        
        if (tryHideWest(state, kingFile, kingRank)) {
            return true;
        }
        
        if (tryHideEast(state, kingFile, kingRank)) {
            return true;
        }
        
        if (tryHideNorth(state, kingFile, kingRank)) {
            return true;
        }
        
        if (tryHideNorthWest(state, kingFile, kingRank)) {
            return true;
        }
        
        if (tryHideNorthEast(state, kingFile, kingRank)) {
            return true;
        }
        
        if (tryHideSouth(state, kingFile, kingRank)) {
            return true;
        }
        
        if (tryHideSouthWest(state, kingFile, kingRank)) {
            return true;
        }
        
        if (tryHideSouthEast(state, kingFile, kingRank)) {
            return true;
        }
        
        throw new UnsupportedOperationException();
    }
    
    /**
     * Tries to hide the white king to the north (towards smaller rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the input state is in checkmate for
     *         the white player.
     */
    private boolean tryHideNorth(final ChessBoardState state, 
                                 final int kingFile, 
                                 final int kingRank) {
        if (kingRank == 0) {
            return false;
        }
        
        final Piece northPiece = state.get(kingFile, kingRank - 1);
        
        if (northPiece == null || northPiece.isBlack()) {
            return !WHITE_ATTACK_CHECKER.check(state,
                                               kingFile, 
                                               kingRank - 1);
        } else {
            // North cell is blocked by a white piece:
            return false;
        }
    }
    
    /**
     * Tries to hide the white king to the south (towards larger rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the input state is in checkmate for
     *         the white player.
     */
    private boolean tryHideSouth(final ChessBoardState state, 
                                 final int kingFile, 
                                 final int kingRank) {
        if (kingRank == N - 1) {
            return false;
        }
        
        final Piece southPiece = state.get(kingFile, kingRank + 1);
        
        if (southPiece == null || southPiece.isBlack()) {
            return !WHITE_ATTACK_CHECKER.check(state,
                                               kingFile, 
                                               kingRank + 1);
        } else {
            // North cell is blocked by a white piece:
            return false;
        }
    }
    
    /**
     * Tries to hide the white king to the west (towards smaller file indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the input state is in checkmate for
     *         the white player.
     */
    private boolean tryHideWest(final ChessBoardState state, 
                                final int kingFile, 
                                final int kingRank) {
        if (kingFile == 0) {
            return false;
        }
        
        final Piece westPiece = state.get(kingFile - 1, kingRank);
        
        if (westPiece == null || westPiece.isBlack()) {
            return !WHITE_ATTACK_CHECKER.check(state,
                                               kingFile - 1, 
                                               kingRank);
        } else {
            // North cell is blocked by a white piece:
            return false;
        }
    }
    
    /**
     * Tries to hide the white king to the east (towards larger file indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the input state is in checkmate for
     *         the white player.
     */
    private boolean tryHideEast(final ChessBoardState state, 
                                final int kingFile, 
                                final int kingRank) {
        if (kingFile == N - 1) {
            return false;
        }
        
        final Piece westPiece = state.get(kingFile + 1, kingRank);
        
        if (westPiece == null || westPiece.isBlack()) {
            return !WHITE_ATTACK_CHECKER.check(state,
                                               kingFile + 1, 
                                               kingRank);
        } else {
            // North cell is blocked by a white piece:
            return false;
        }
    }
    
    /**
     * Tries to hide the white king to the north west (towards smaller file and
     * smaller rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the input state is in checkmate for
     *         the white player.
     */
    private boolean tryHideNorthWest(final ChessBoardState state, 
                                     final int kingFile, 
                                     final int kingRank) {
        if (kingFile == 0) {
            return false;
        }
        
        if (kingRank == 0) {
            return false;
        }
        
        final Piece westPiece = state.get(kingFile - 1, kingRank - 1);
        
        if (westPiece == null || westPiece.isBlack()) {
            return !WHITE_ATTACK_CHECKER.check(state,
                                               kingFile - 1, 
                                               kingRank - 1);
        } else {
            // North cell is blocked by a white piece:
            return false;
        }
    }
    
    /**
     * Tries to hide the white king to the east west (towards larger file and
     * smaller rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the input state is in checkmate for
     *         the white player.
     */
    private boolean tryHideNorthEast(final ChessBoardState state, 
                                     final int kingFile, 
                                     final int kingRank) {
        if (kingFile == N - 1) {
            return false;
        }
        
        if (kingRank == 0) {
            return false;
        }
        
        final Piece westPiece = state.get(kingFile + 1, kingRank - 1);
        
        if (westPiece == null || westPiece.isBlack()) {
            return !WHITE_ATTACK_CHECKER.check(state,
                                               kingFile + 1, 
                                               kingRank - 1);
        } else {
            // North cell is blocked by a white piece:
            return false;
        }
    }
    
    /**
     * Tries to hide the white king to the south west (towards smaller file and
     * larger rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the input state is in checkmate for
     *         the white player.
     */
    private boolean tryHideSouthWest(final ChessBoardState state, 
                                     final int kingFile, 
                                     final int kingRank) {
        if (kingFile == 0) {
            return false;
        }
        
        if (kingRank == N - 1) {
            return false;
        }
        
        final Piece westPiece = state.get(kingFile - 1, kingRank + 1);
        
        if (westPiece == null || westPiece.isBlack()) {
            return !WHITE_ATTACK_CHECKER.check(state,
                                               kingFile - 1, 
                                               kingRank + 1);
        } else {
            // North cell is blocked by a white piece:
            return false;
        }
    }
    
    /**
     * Tries to hide the white king to the south east (towards larger file and
     * larger rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the input state is in checkmate for
     *         the white player.
     */
    private boolean tryHideSouthEast(final ChessBoardState state, 
                                     final int kingFile, 
                                     final int kingRank) {
        if (kingFile == N - 1) {
            return false;
        }
        
        if (kingRank == N - 1) {
            return false;
        }
        
        final Piece westPiece = state.get(kingFile + 1, kingRank + 1);
        
        if (westPiece == null || westPiece.isBlack()) {
            return !WHITE_ATTACK_CHECKER.check(state,
                                               kingFile + 1, 
                                               kingRank + 1);
        } else {
            // North cell is blocked by a white piece:
            return false;
        }
    }
}
