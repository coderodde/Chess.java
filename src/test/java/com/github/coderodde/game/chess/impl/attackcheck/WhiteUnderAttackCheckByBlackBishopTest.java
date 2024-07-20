package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.CellCoordinates;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.github.coderodde.game.chess.UnderAttackCheck;

public final class WhiteUnderAttackCheckByBlackBishopTest {
    
    private static final Piece whiteBishop = new Piece(WHITE, BISHOP);
    private static final Piece blackBishop = new Piece(BLACK, BISHOP);
    
    private static final Piece whitePawn = new Piece(WHITE, PAWN);
    private static final Piece blackPawn = new Piece(BLACK, PAWN);
    
    private static final UnderAttackCheck BISHOP_CHECK = 
            new WhiteUnderAttackCheckByBlackBishop();
    
    private static final CellCoordinates CELL_COORDS =
            UnderAttackCheck.ATTACKER_COORDINATES;
    
    private final ChessBoardState state = new ChessBoardState();
    
    @Before
    public void before() {
        state.clear();
        state.set(3, 3, whitePawn);
    }
    
    @Test
    public void blackBishopNorthEast() {
        state.set(6, 0, blackBishop);
        assertCheck();
        assertCellCoords(6, 0);
    }
    
    @Test
    public void blackBishopNorthWest() {
        state.set(0, 0, blackBishop);
        assertCheck();
        assertCellCoords(0, 0);
    }
    
    @Test
    public void blackBishopSouthEast() {
        state.set(6, 6, blackBishop);
        assertCheck();
        assertCellCoords(6, 6);
    }
    
    @Test
    public void blackBishopSouthWest() {
        state.set(0, 6, blackBishop);
        assertCheck();
        assertCellCoords(0, 6);
    }
    
    @Test
    public void blockedBishopNorthEast() {
        state.set(6, 0, whiteBishop);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blockedBishopNorthWest() {
        state.set(0, 0, whiteBishop);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blockedBishopSouthEast() {
        state.set(6, 6, whiteBishop);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blockedBishopSouthWest() {
        state.set(0, 6, whiteBishop);
        assertNotCheck();
        assertNoCell();
    }
        
    @Test
    public void whitePawnNorth() {
        state.set(3, 1, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnSouth() {
        state.set(3, 7, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnWest() {
        state.set(0, 3, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnEast() {
        state.set(6, 3, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnNorthEast() {
        state.set(6, 0, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnNorthWest() {
        state.set(0, 0, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnSouthEast() {
        state.set(6, 6, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnSouthWest() {
        state.set(0, 6, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void threatenedInAllDirection() {
        state.set(3, 3, new Piece(BLACK, BISHOP));
        
        // Diagonal bishops:
        state.set(0, 0, blackBishop);
        state.set(6, 6, blackBishop);
        state.set(1, 5, blackBishop);
        state.set(5, 1, blackBishop);
        
        assertTrue(BISHOP_CHECK.check(state, 3, 3));
    }
    
    @Test
    public void limitNorthWestFile() {
        state.set(2, 3, blackPawn);
        assertNotCheck(2, 3);
        assertNoCell();
    }
    
    @Test
    public void limitNorthWestRank() {
        state.set(3, 2, blackPawn);
        assertNotCheck(3, 2);
        assertNoCell();
    }
    
    @Test
    public void limitNorthWestBoth() {
        state.set(2, 2, blackPawn);
        assertNotCheck(2, 2);
        assertNoCell();
    }
    
    @Test
    public void limitNorthEastFile() {
        state.set(6, 3, blackPawn);
        assertNotCheck(6, 3);
        assertNoCell();
    }
    
    @Test
    public void limitNorthEastRank() {
        state.set(3, 6, blackBishop);
        assertNotCheck(3, 6);
        assertNoCell();
    }
    
    @Test
    public void coverIf1() {
        // Don't match the black bishop:
        state.set(5, 5, blackPawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void coverIf2() {
        // Don't match the black bishop:
        state.set(0, 0, blackPawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void coverIf3() {
        // Don't match the black bishop:
        state.set(6, 0, blackPawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void coverIf4() {
        // Don't match the black bishop:
        state.set(0, 6, blackPawn);
        assertNotCheck();
        assertNoCell();
    }
    
    private void assertNotCheck(final int file, 
                                final int rank) {
        
        assertFalse(BISHOP_CHECK.check(state, file, rank));
    }
    
    private void assertCheck() {
        assertTrue(BISHOP_CHECK.check(state, 3, 3));
    }
    
    private void assertNotCheck() {
        assertFalse(BISHOP_CHECK.check(state, 3, 3));
    }
    
    private void assertCellCoords(final int file, final int rank) {
        assertEquals(file, CELL_COORDS.file);
        assertEquals(rank, CELL_COORDS.rank);
    }
    
    private void assertNoCell() {
        assertEquals(CellCoordinates.NO_ATTACK_FILE, CELL_COORDS.file);
    }
}
