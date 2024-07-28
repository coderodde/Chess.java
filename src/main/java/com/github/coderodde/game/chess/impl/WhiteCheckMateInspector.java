package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.CellCoordinates;
import com.github.coderodde.game.chess.CheckMateInspector;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.impl.attackcheck.WhiteUnderAttackCheck;
import com.github.coderodde.game.chess.UnderAttackCheck;
import com.github.coderodde.game.chess.impl.attackcheck.BlackUnderAttackCheck;
import java.util.Arrays;

/**
 * This class implements the API for checking for checkmate for the white king.
 * 
 * @version 1.0.0 (Jul 16, 2024)
 * @since 1.0.0 (Jul 16, 2024)
 */
public final class WhiteCheckMateInspector implements CheckMateInspector {
    
    private static final int ATTACKER_CELLS_LENGTH = 8;
    private static final CellCoordinates[] ATTACKER_CELLS = 
            new CellCoordinates[ATTACKER_CELLS_LENGTH];
    
    private static final UnderAttackCheck WHITE_PIECE_UNDER_ATTACK_CHECKER = 
            new WhiteUnderAttackCheck();
    
    private static final UnderAttackCheck BLACK_PIECE_UNDER_ATTACK_CHECKER = 
            new BlackUnderAttackCheck();

    static {
        for (int i = 0; i < ATTACKER_CELLS.length; i++) {
            ATTACKER_CELLS[i] = new CellCoordinates();
        }
    }
    
    private int attackerCellsSize;
    
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
        
        if (WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile, 
                                                   kingRank) == false) {
            // Easy case: the white king is not under attack.
            return false;
        }
        
        attackerCellsSize = 0;
        
        if (canHideWest(state, kingFile, kingRank)) {
            return false;
        }
        
        if (canHideEast(state, kingFile, kingRank)) {
            return false;
        }
            
        if (canHideNorth(state, kingFile, kingRank)) {
            return false;
        }
        
        if (canHideNorthWest(state, kingFile, kingRank)) {
            return false;
        }
        
        if (canHideNorthEast(state, kingFile, kingRank)) {
            return false;
        }
        
        if (canHideSouth(state, kingFile, kingRank)) {
            return false;
        }
        
        if (canHideSouthWest(state, kingFile, kingRank)) {
            return false;
        }
        
        if (canHideSouthEast(state, kingFile, kingRank)) {
            return false;
        }
        
        Arrays.sort(ATTACKER_CELLS, 0, attackerCellsSize);
        
        if (ATTACKER_CELLS[0].equals(ATTACKER_CELLS[attackerCellsSize - 1])) {
            return cannotDefend(state);
        }
        
        // Once here, we have more than one offending black pieces. Since we can
        // hope to eliminate at most one of them, the game is lost for the white
        // player and we have a checkmate:
        return true;
    }
    
    private boolean cannotDefend(final ChessBoardState state) {
        final CellCoordinates attackerCellCoordinates = ATTACKER_CELLS[0];
        
        return !BLACK_PIECE_UNDER_ATTACK_CHECKER.check(
                state, 
                attackerCellCoordinates.file, 
                attackerCellCoordinates.rank);
    }
    
    /**
     * Tries to hide the white king to the north (towards smaller rank indices).
     * 
     * @param state    the state to investigate.
     * @param kingFile the file of the white king.
     * @param kingRank the rank of the black king.
     * 
     * @return the coordinates of the offending black piece.
     */
    private boolean canHideNorth(final ChessBoardState state, 
                                 final int kingFile, 
                                 final int kingRank) {
        if (kingRank == 0) {
            return false;
        }
        
        final Piece northPiece = state.get(kingFile, kingRank - 1);
        
        if (northPiece == null || northPiece.isBlack()) {
            // Once here, the north slot is either empty or is occupied by 
            // a black piece which can be possibly captured:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile, 
                                                   kingRank - 1);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file ==
                    CellCoordinates.NO_ATTACK_FILE) {
                
                // It is safe to move to north:
                return true;
            }
            
            // Once here, we have a north offender:
            addCopyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            // ..., that is, we cannot hide:
            return false;
        } 
        
        // Once here, northPiece is a white piece. King is blocked: 
        return false;
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
    private boolean canHideSouth(final ChessBoardState state, 
                                 final int kingFile, 
                                 final int kingRank) {
        if (kingRank == N - 1) {
            return false;
        }
        
        final Piece southPiece = state.get(kingFile, kingRank + 1);
        
        if (southPiece == null || southPiece.isBlack()) {
            // Once here, the south slot is either empty or is occupied by a
            // black piece which can be possibly captured:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state,
                                                   kingFile,
                                                   kingRank + 1);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file == 
                    CellCoordinates.NO_ATTACK_FILE) {
                
                // It is safe to move to south:
                return true;
            } 
            
            // Once here, we have a south offender:
            addCopyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            // ..., that is, we cannot hide:
            return false;
        }
        
        // Once here, southPiece is a white piece. King is blocked: 
        return false;
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
    private boolean canHideWest(final ChessBoardState state, 
                                final int kingFile, 
                                final int kingRank) {
        if (kingFile == 0) {
            return false;
        }
        
        final Piece westPiece = state.get(kingFile - 1, kingRank);
        
        if (westPiece == null || westPiece.isBlack()) {
            // Once here, the west slot is either empty or is occupied by a
            // black piece which can be possibly captured:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile - 1, 
                                                   kingRank);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file == 
                    CellCoordinates.NO_ATTACK_FILE) {
                
                // It is safe to move to the west:
                return true;
            }
            
            // Once here, we have a west offender:
            addCopyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            // ..., that is, we cannot hide:
            return false;
        }
        
        // Once here, westPiece is a white piece. King is blocked:
        return false;
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
    private boolean canHideEast(final ChessBoardState state, 
                                final int kingFile, 
                                final int kingRank) {
        if (kingFile == N - 1) {
            return false;
        }
        
        final Piece eastPiece = state.get(kingFile + 1, kingRank);
        
        if (eastPiece == null || eastPiece.isBlack()) { 
            // Once here, the east slot is either empty or is occupied by a
            // black piece which can be possibly captured:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile + 1, 
                                                   kingRank);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file
                    == CellCoordinates.NO_ATTACK_FILE) {
                
                // It is safe to move to the east:
                return true;
            }
            
            // Once here, we have an east offender:
            addCopyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            // ..., that is, we cannot hide:
            return false;
        }
        
        // Once here, eastPiece is a white piece. King is blocked:
        return false;
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
    private boolean canHideNorthWest(final ChessBoardState state, 
                                     final int kingFile, 
                                     final int kingRank) {
        if (kingFile == 0) {
            return false;
        }
        
        if (kingRank == 0) {
            return false;
        }
        
        final Piece northWestPiece = state.get(kingFile - 1, 
                                               kingRank - 1);
        
        if (northWestPiece == null || northWestPiece.isBlack()) {
            // Once here, the north west slot is either empty or is occupied by 
            // a black piece which can be possibly captured:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile - 1,
                                                   kingRank - 1);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file == 
                    CellCoordinates.NO_ATTACK_FILE) {
                
                // It is safe to move to the west:
                return true;
            }
            
            // Once here, we have a north west offender:
            addCopyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            // ..., that is, we cannot hide:
            return false;
        }
        
        // Once here, northWestPiece is a white piece. King is blocked:
        return false;
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
    private boolean canHideNorthEast(final ChessBoardState state, 
                                     final int kingFile, 
                                     final int kingRank) {
        if (kingFile == N - 1) {
            return false;
        }
        
        if (kingRank == 0) {
            return false;
        }
        
        final Piece northEastPiece = state.get(kingFile + 1, 
                                               kingRank - 1);
        
        if (northEastPiece == null || northEastPiece.isBlack()) {
            // Once here, the north east slot is either empty or is occupied by 
            // a black piece which can be possibly captured:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile + 1, 
                                                   kingRank - 1);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file == 
                    CellCoordinates.NO_ATTACK_FILE) {
                
                // It is safe to move to the west:
                return true;
            }
            
            // Once here, we have a west offender:
            addCopyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            // ..., that is, we cannot hide:
            return false;
        }
        
        // Once here, northEastPiece is a white piece. King is blocked:
        return false;
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
    private boolean canHideSouthWest(final ChessBoardState state, 
                                     final int kingFile, 
                                     final int kingRank) {
        if (kingFile == 0) {
            return false;
        }
        
        if (kingRank == N - 1) {
            return false;
        }
        
        final Piece southWestPiece = state.get(kingFile - 1, 
                                               kingRank + 1);
        
        if (southWestPiece == null || southWestPiece.isBlack()) {
            // Once here, the south west slot is either empty or is occupied by
            // a black piece which can be possibly captured:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile - 1, 
                                                   kingRank + 1);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file == 
                    CellCoordinates.NO_ATTACK_FILE) {
                
                // It is safe to move to the west:
                return true;
            }
            
            // Once here, we have a west offender:
            addCopyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            // ..., that is, we cannot hide:
            return false;
        }
        
        // Once here, southWestPiece is a white piece. King is blocked:
        return false;
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
    private boolean canHideSouthEast(final ChessBoardState state, 
                                     final int kingFile, 
                                     final int kingRank) {
        if (kingFile == N - 1) {
            return false;
        }
        
        if (kingRank == N - 1) {
            return false;
        }
        
        final Piece southEastPiece = state.get(kingFile + 1,
                                               kingRank + 1);
        
        if (southEastPiece == null || southEastPiece.isBlack()) {
            // Once here, the south east slot is either empty or is occupied by 
            // a black piece which can be possibly captured:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile + 1, 
                                                   kingRank + 1);
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file == 
                    CellCoordinates.NO_ATTACK_FILE) {
                
                // It is safe to move to south east:
                return true;
            }
            
            // Once here, we have a west offender:
            addCopyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            // ..., that is, we cannot hide:
            return false;
        }
           
        // Once here, southEastPiece is a white piece. King is blocked: 
        return false;
    }
    
    private void addCopyCoordinateCells(final CellCoordinates cellCoordinates) {
        ATTACKER_CELLS[attackerCellsSize].file = cellCoordinates.file;
        ATTACKER_CELLS[attackerCellsSize].rank = cellCoordinates.rank;
        attackerCellsSize++;
    }
}
