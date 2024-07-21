package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.CellCoordinates;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.ROOK;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.github.coderodde.game.chess.UnderAttackCheck;

public final class WhiteUnderAttackCheckByBlackRookTest {
    
    private static final Piece whiteRook = new Piece(WHITE, ROOK);
    private static final Piece blackRook = new Piece(BLACK, ROOK);
    
    private static final Piece whitePawn = new Piece(WHITE, PAWN);
    private static final Piece blackPawn = new Piece(BLACK, PAWN);
    
    private static final UnderAttackCheck ROOK_CHECK = 
            new WhiteUnderAttackCheckByBlackRook();
    
    private final ChessBoardState state = new ChessBoardState();
    
    private static final CellCoordinates CELL_COORDS =
            UnderAttackCheck.ATTACKER_COORDINATES;
    
    @Before
    public void before() {
        state.clear();
        state.set(3, 3, whitePawn);
    }
    
    @Test
    public void blackRookNorth() {
        state.set(3, 1, blackRook);
        assertCheck();
        assertCellCoords(3, 1);
    }
    
    @Test
    public void blackRookSouth() {
        state.set(3, 7, blackRook);
        assertCheck();
        assertCellCoords(3, 7);
    }
    
    @Test
    public void blackRookWest() {
        state.set(0, 3, blackRook);
        assertCheck();
        assertCellCoords(0, 3);
    }
    
    @Test
    public void blackRookEast() {
        state.set(6, 3, blackRook);
        assertCheck();
        assertCellCoords(6, 3);
    }
    
    @Test
    public void whiteRookNorth() {
        state.set(3, 1, whiteRook);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whiteRookSouth() {
        state.set(3, 7, whiteRook);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whiteRookWest() {
        state.set(0, 3, whiteRook);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whiteRookEast() {
        state.set(6, 3, whiteRook);
        assertNotCheck();
        assertNoCell();
    }
        
    @Test
    public void blackPawnNorth() {
        state.set(3, 1, blackPawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blackPawnSouth() {
        state.set(3, 7, blackPawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blackPawnWest() {
        state.set(0, 3, blackPawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blackPawnEast() {
        state.set(6, 3, blackPawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void threatenedInAllDirection() {
        state.set(3, 3, new Piece(WHITE, BISHOP));
        
        // Straight queens:
        state.set(3, 5, blackRook);
        state.set(3, 0, blackRook);
        state.set(0, 3, blackRook);
        state.set(7, 3, blackRook);
        
        assertTrue(ROOK_CHECK.check(state, 3, 3));
    }
    
    private void assertCheck() {
        assertTrue(ROOK_CHECK.check(state, 3, 3));
    }
    
    private void assertNotCheck() {
        assertFalse(ROOK_CHECK.check(state, 3, 3));
    }
    
    private void assertCellCoords(final int file, final int rank) {
        assertEquals(file, CELL_COORDS.file);
        assertEquals(rank, CELL_COORDS.rank);
    }
    
    private void assertNoCell() {
        assertEquals(CellCoordinates.NO_ATTACK_FILE, CELL_COORDS.file);
    }
}
