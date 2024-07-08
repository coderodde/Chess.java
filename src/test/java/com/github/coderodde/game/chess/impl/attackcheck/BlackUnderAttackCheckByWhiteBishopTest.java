package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import com.github.coderodde.game.chess.UnderAttackCheck;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class BlackUnderAttackCheckByWhiteBishopTest {
    
    private static final Piece whiteBishop = new Piece(WHITE, BISHOP);
    private static final Piece blackBishop = new Piece(BLACK, BISHOP);
    
    private static final Piece whitePawn = new Piece(WHITE, PAWN);
    private static final Piece blackPawn = new Piece(BLACK, PAWN);
    
    private static final UnderAttackCheck BISHOP_CHECK = 
            new BlackUnderAttackCheckByWhiteBishop();
    
    private final ChessBoardState state = new ChessBoardState();
    
    @Before
    public void before() {
        state.clear();
        state.set(3, 3, blackPawn);
    }
    
    @Test
    public void blackBishopNorthEast() {
        state.set(6, 0, whiteBishop);
        assertCheck();
    }
    
    @Test
    public void blackBishopNorthWest() {
        state.set(0, 0, whiteBishop);
        assertCheck();
    }
    
    @Test
    public void blackBishopSouthEast() {
        state.set(6, 6, whiteBishop);
        assertCheck();
    }
    
    @Test
    public void blackBishopSouthWest() {
        state.set(0, 6, whiteBishop);
        assertCheck();
    }
    
    @Test
    public void blockedBishopNorthEast() {
        state.set(6, 0, blackBishop);
        assertNotCheck();
    }
    
    @Test
    public void blockedBishopNorthWest() {
        state.set(0, 0, blackBishop);
        assertNotCheck();
    }
    
    @Test
    public void blockedBishopSouthEast() {
        state.set(6, 6, blackBishop);
        assertNotCheck();
    }
    
    @Test
    public void blockedBishopSouthWest() {
        state.set(0, 6, blackBishop);
        assertNotCheck();
    }
        
    @Test
    public void blackPawnNorth() {
        state.set(3, 1, blackPawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnSouth() {
        state.set(3, 7, blackPawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnWest() {
        state.set(0, 3, blackPawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnEast() {
        state.set(6, 3, blackPawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnNorthEast() {
        state.set(6, 0, blackPawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnNorthWest() {
        state.set(0, 0, blackPawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnSouthEast() {
        state.set(6, 6, blackPawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnSouthWest() {
        state.set(0, 6, blackPawn);
        assertNotCheck();
    }
    
    @Test
    public void threatenedInAllDirection() {
        state.set(3, 3, new Piece(WHITE, BISHOP));
        
        // Diagonal bishops:
        state.set(0, 0, whiteBishop);
        state.set(6, 6, whiteBishop);
        state.set(1, 5, whiteBishop);
        state.set(5, 1, whiteBishop);
        
        assertTrue(BISHOP_CHECK.check(state, 3, 3));
    }
    
    @Test
    public void limitNorthWestFile() {
        state.set(2, 3, whitePawn);
        assertNotCheck(2, 3);
    }
    
    @Test
    public void limitNorthWestRank() {
        state.set(3, 2, whitePawn);
        assertNotCheck(3, 2);
    }
    
    @Test
    public void limitNorthWestBoth() {
        state.set(2, 2, whitePawn);
        assertNotCheck(2, 2);
    }
    
    @Test
    public void limitNorthEastFile() {
        state.set(6, 3, whitePawn);
        assertNotCheck(6, 3);
    }
    
    @Test
    public void limitNorthEastRank() {
        state.set(3, 6, whiteBishop);
        assertNotCheck(3, 6);
    }
    
    @Test
    public void coverIf1() {
        // Don't match the black bishop:
        state.set(5, 5, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void coverIf2() {
        // Don't match the black bishop:
        state.set(0, 0, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void coverIf3() {
        // Don't match the black bishop:
        state.set(6, 0, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void coverIf4() {
        // Don't match the black bishop:
        state.set(0, 6, whitePawn);
        assertNotCheck();
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
}
