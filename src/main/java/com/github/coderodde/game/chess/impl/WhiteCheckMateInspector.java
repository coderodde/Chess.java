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
    
    private static final CellCoordinates PREVIOUS_CELL_COORDINATES = 
            new CellCoordinates();
    
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
//      
        final boolean cannotHide =  
                   cannotHideWest      (state, kingFile, kingRank) 
                && cannotHideEast      (state, kingFile, kingRank)
                && cannotHideNorth     (state, kingFile, kingRank)
                && cannotHideNorthWest (state, kingFile, kingRank) 
                && cannotHideNorthEast (state, kingFile, kingRank)
                && cannotHideSouth     (state, kingFile, kingRank)
                && cannotHideSouthWest (state, kingFile, kingRank) 
                && cannotHideSouthEast (state, kingFile, kingRank);
        
        if (cannotHide) {
            // The white king is threatend and cannot move to a safe location:
            return true;
        }
        
        if (attackerCellsSize == 0) {
            return true;
        }
        
        Arrays.sort(ATTACKER_CELLS, 0, attackerCellsSize);
        
        if (ATTACKER_CELLS[0].equals(ATTACKER_CELLS[attackerCellsSize - 1])) {
            return cannotDefend(state);
        }
        
        // Once here, more than one offending black pieces. Since we can hope to 
        // eliminate at most one of them, the game is lost for the white player
        // and we have a checkmate:
        return true;
    }
    
    private boolean cannotDefend(final ChessBoardState state) {
        final CellCoordinates attackerCellCoordinates = ATTACKER_CELLS[0];
        
        return BLACK_PIECE_UNDER_ATTACK_CHECKER.check(
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
    private boolean cannotHideNorth(final ChessBoardState state, 
                                    final int kingFile, 
                                    final int kingRank) {
        if (kingRank == 0) {
            return true;
        }
        
        final Piece northPiece = state.get(kingFile, kingRank - 1);
        
        if (northPiece == null || northPiece.isBlack()) {
            // Can hide only if not under attack:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile, 
                                                   kingRank - 1);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file ==
                    CellCoordinates.NO_ATTACK_FILE) {
                
                return false;
            }
            
            copyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            return true;
        } 
        
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
    private boolean cannotHideSouth(final ChessBoardState state, 
                                    final int kingFile, 
                                    final int kingRank) {
        if (kingRank == N - 1) {
            return true;
        }
        
        final Piece southPiece = state.get(kingFile, kingRank + 1);
        
        if (southPiece == null || southPiece.isBlack()) {
            // Can hide only if not under attack:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state,
                                                   kingFile,
                                                   kingRank + 1);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file == 
                    CellCoordinates.NO_ATTACK_FILE) {
                
                return false;
            } 
            
            copyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            return true;
        }
        
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
    private boolean cannotHideWest(final ChessBoardState state, 
                                   final int kingFile, 
                                   final int kingRank) {
        if (kingFile == 0) {
            return true;
        }
        
        final Piece westPiece = state.get(kingFile - 1, kingRank);
        
        if (westPiece == null || westPiece.isBlack()) {
            // Can hide only if not under attack:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile - 1, 
                                                   kingRank);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file == 
                    CellCoordinates.NO_ATTACK_FILE) {
                
                return false;
            }
            
            copyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            return true;
        }
        
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
    private boolean cannotHideEast(final ChessBoardState state, 
                                   final int kingFile, 
                                   final int kingRank) {
        if (kingFile == N - 1) {
            return true;
        }
        
        final Piece eastPiece = state.get(kingFile + 1, kingRank);
        
        if (eastPiece == null || eastPiece.isBlack()) { 
            // Can hide only if not under attack:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile + 1, 
                                                   kingRank);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file
                    == CellCoordinates.NO_ATTACK_FILE) {
                return false;
            }
            
            copyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            return true;
        }
        
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
    private boolean cannotHideNorthWest(final ChessBoardState state, 
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
        
        if (northWestPiece == null || northWestPiece.isBlack()) {
            // Can hide only if not under attack:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile - 1,
                                                   kingRank - 1);
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file == 
                    CellCoordinates.NO_ATTACK_FILE) {
                return false;
            }
            
            copyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            return true;
        }
        
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
    private boolean cannotHideNorthEast(final ChessBoardState state, 
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
        
        if (northEastPiece == null || northEastPiece.isBlack()) {
            // Can hide only if not under attack:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile + 1, 
                                                   kingRank - 1);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file == 
                    CellCoordinates.NO_ATTACK_FILE) {
                return false;
            }
            
            copyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            return true;
        }
        
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
    private boolean cannotHideSouthWest(final ChessBoardState state, 
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
        
        if (southWestPiece == null || southWestPiece.isBlack()) {
            // Can hide only if not under attack:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile - 1, 
                                                   kingRank + 1);
            
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file == 
                    CellCoordinates.NO_ATTACK_FILE) {
                return false;
            }
            
            copyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            return true;
        }
            
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
    private boolean cannotHideSouthEast(final ChessBoardState state, 
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
        
        if (southEastPiece == null || southEastPiece.isBlack()) {
            // Can hide only if not under attack:
            WHITE_PIECE_UNDER_ATTACK_CHECKER.check(state, 
                                                   kingFile + 1, 
                                                   kingRank + 1);
            if (WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES.file == 
                    CellCoordinates.NO_ATTACK_FILE) {
                
                return false;
            }
            
            copyCoordinateCells(
                    WHITE_PIECE_UNDER_ATTACK_CHECKER.ATTACKER_COORDINATES);
            
            return true;
        }
            
        return false;
    }
    
    private void copyCoordinateCells(final CellCoordinates cellCoordinates) {
        ATTACKER_CELLS[attackerCellsSize].file = cellCoordinates.file;
        ATTACKER_CELLS[attackerCellsSize].rank = cellCoordinates.rank;
        attackerCellsSize++;
    }
}
