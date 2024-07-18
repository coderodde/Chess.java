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
        
        return     tryHideWest      (state, kingFile, kingRank) 
                && tryHideEast      (state, kingFile, kingRank)
                && tryHideNorth     (state, kingFile, kingRank)
                && tryHideNorthWest (state, kingFile, kingRank) 
                && tryHideNorthEast (state, kingFile, kingRank)
                && tryHideSouth     (state, kingFile, kingRank)
                && tryHideSouthWest (state, kingFile, kingRank) 
                && tryHideSouthEast (state, kingFile, kingRank);
    }
    
    /**
     * Tries to hide the white king to the north (towards smaller rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the white king cannot hide by moving
     *         to the north.
     */
    private boolean tryHideNorth(final ChessBoardState state, 
                                 final int kingFile, 
                                 final int kingRank) {
        if (kingRank == 0) {
            return false;
        }
        
        final Piece northPiece = state.get(kingFile, kingRank - 1);
        
        if (northPiece == null) {
            // Can hide only if not under attack:
            return WHITE_ATTACK_CHECKER.check(state, 
                                              kingFile, 
                                              kingRank - 1);
        } 
        
        if (northPiece.isWhite()) {
            // Blocked by a white piece:
            return true;
        }
        
        // Once here, the northPiece is a black piece, check whether capturing 
        // it is safe:
        return WHITE_ATTACK_CHECKER.check(state,
                                          kingFile, 
                                          kingRank - 1);
    }
    
    /**
     * Tries to hide the white king to the south (towards larger rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the white king cannot hide by moving
     *         to the south. 
     */
    private boolean tryHideSouth(final ChessBoardState state, 
                                 final int kingFile, 
                                 final int kingRank) {
        if (kingRank == N - 1) {
            return true;
        }
        
        final Piece southPiece = state.get(kingFile, kingRank + 1);
        
        if (southPiece == null) {
            // Can hide only if not under attack:
            return WHITE_ATTACK_CHECKER.check(state,
                                              kingFile, 
                                              kingRank + 1);
        }
        
        if (southPiece.isWhite()) {
            // Blocked by a white piece:
            return true;
        }
        
        // Once here, the southPiece is a black piece, check whether capturing 
        // it is safe:
        return WHITE_ATTACK_CHECKER.check(state,
                                          kingFile, 
                                          kingRank + 1);
    }
    
    /**
     * Tries to hide the white king to the west (towards smaller file indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the white king cannot hide by moving
     *         to the west.
     */
    private boolean tryHideWest(final ChessBoardState state, 
                                final int kingFile, 
                                final int kingRank) {
        if (kingFile == 0) {
            return true;
        }
        
        final Piece westPiece = state.get(kingFile - 1, kingRank);
        
        if (westPiece == null) {
            // Can hide only if not under attack:
            return WHITE_ATTACK_CHECKER.check(state, 
                                              kingFile - 1, 
                                              kingRank);
        }
        
        if (westPiece.isWhite()) {
            // Blocked by a white piece:
            return true;
        }
        
        // Once here, the westPiece is a black piece, check whether capturing 
        // it is safe:
        return WHITE_ATTACK_CHECKER.check(state,
                                          kingFile - 1, 
                                          kingRank);
    }
    
    /**
     * Tries to hide the white king to the east (towards larger file indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the white king cannot hide by moving
     *         to the east.
     */
    private boolean tryHideEast(final ChessBoardState state, 
                                final int kingFile, 
                                final int kingRank) {
        if (kingFile == N - 1) {
            return true;
        }
        
        final Piece eastPiece = state.get(kingFile + 1, kingRank);
        
        if (eastPiece == null) { 
            // Can hide only if not under attack:
            return WHITE_ATTACK_CHECKER.check(state, 
                                              kingFile + 1, 
                                              kingRank);
            
        }
        
        if (eastPiece.isWhite()) {
            // Blocked by a white piece:
            return true;
        }
        
        // Once here, the eastPiece is a black piece, check whether capturing 
        // it is safe:
        return WHITE_ATTACK_CHECKER.check(state,
                                          kingFile + 1, 
                                          kingRank);
    }
    
    /**
     * Tries to hide the white king to the north west (towards smaller file and
     * smaller rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the white king cannot hide by moving
     *         to the north west.
     */
    private boolean tryHideNorthWest(final ChessBoardState state, 
                                     final int kingFile, 
                                     final int kingRank) {
        if (kingFile == 0) {
            return true;
        }
        
        if (kingRank == 0) {
            return true;
        }
        
        final Piece northWestPiece = state.get(kingFile - 1, 
                                               kingRank - 1);
        
        if (northWestPiece == null) {
            // Can hide only if not under attack:
            return WHITE_ATTACK_CHECKER.check(state, 
                                              kingFile - 1,
                                              kingFile - 1);
        }
        
        if (northWestPiece.isWhite()) {
            // Blocked by a white piece:
            return true;
        }
        
        // Once here, the northWestPiece is a black piece, check whether 
        // capturing it is safe:
        return WHITE_ATTACK_CHECKER.check(state,
                                          kingFile - 1, 
                                          kingRank - 1);
    }
    
    /**
     * Tries to hide the white king to the east west (towards larger file and
     * smaller rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the white king cannot hide by moving
     *         to the north east.
     */
    private boolean tryHideNorthEast(final ChessBoardState state, 
                                     final int kingFile, 
                                     final int kingRank) {
        if (kingFile == N - 1) {
            return true;
        }
        
        if (kingRank == 0) {
            return true;
        }
        
        final Piece northEastPiece = state.get(kingFile + 1, 
                                               kingRank - 1);
        
        if (northEastPiece == null) {
            // Can hide only if not under attack:
            return WHITE_ATTACK_CHECKER.check(state, 
                                              kingFile + 1, 
                                              kingRank - 1);
        }
        
        if (northEastPiece.isWhite()) {
            // Blocked by a white piece:
            return true;
        }
        
        // Once here, the northEastPiece is a black piece, check whether 
        // capturing it is safe:
        return WHITE_ATTACK_CHECKER.check(state,
                                          kingFile + 1, 
                                          kingRank - 1);
    }
    
    /**
     * Tries to hide the white king to the south west (towards smaller file and
     * larger rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the white king cannot hide by moving
     *         to the south west.
     */
    private boolean tryHideSouthWest(final ChessBoardState state, 
                                     final int kingFile, 
                                     final int kingRank) {
        if (kingFile == 0) {
            return true;
        }
        
        if (kingRank == N - 1) {
            return true;
        }
        
        final Piece southWestPiece = state.get(kingFile - 1, 
                                               kingRank + 1);
        
        if (southWestPiece == null) {
            // Can hide only if not under attack:
            return WHITE_ATTACK_CHECKER.check(state, 
                                              kingFile - 1, 
                                              kingRank + 1);
        }
        
        if (southWestPiece.isWhite()) {
            // Blocked by a white piece:
            return true;
        }
        
        // Once here, the northPiece is a black piece, check whether capturing 
        // it is safe:
        return WHITE_ATTACK_CHECKER.check(state,
                                          kingFile - 1, 
                                          kingRank + 1);
    }
    
    /**
     * Tries to hide the white king to the south east (towards larger file and
     * larger rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return {@code true} if and only if the white king cannot hide by moving
     *         to the south east.
     */
    private boolean tryHideSouthEast(final ChessBoardState state, 
                                     final int kingFile, 
                                     final int kingRank) {
        if (kingFile == N - 1) {
            return true;
        }
        
        if (kingRank == N - 1) {
            return true;
        }
        
        final Piece southEastPiece = state.get(kingFile + 1,
                                               kingRank + 1);
        
        if (southEastPiece == null) {
            // Can hide only if not under attack:
            return WHITE_ATTACK_CHECKER.check(state, 
                                              kingFile + 1, 
                                              kingRank + 1);
        }
        
        if (southEastPiece.isWhite()) {
            // Blocked by a white piece:
            return true;
        }
        
        // Once here, the northPiece is a black piece, check whether capturing 
        // it is safe:
        return WHITE_ATTACK_CHECKER.check(state,
                                          kingFile + 1, 
                                          kingRank + 1);
    }
}
