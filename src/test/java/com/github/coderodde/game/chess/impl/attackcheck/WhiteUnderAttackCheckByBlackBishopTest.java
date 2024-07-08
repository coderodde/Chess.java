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

public final class WhiteUnderAttackCheckByBlackBishopTest {
    
    private static final Piece whiteBishop = new Piece(WHITE, BISHOP);
    private static final Piece blackBishop = new Piece(BLACK, BISHOP);
    
    private static final Piece whitePawn = new Piece(WHITE, PAWN);
    private static final Piece blackPawn = new Piece(BLACK, PAWN);
    
    private static final UnderAttackCheck BISHOP_CHECK = 
            new WhiteUnderAttackByBlackBishop();
    
    private final ChessBoardState state = new ChessBoardState();
    
    @Before
    public void before() {
        state.clear();
        state.set(3, 3, whitePawn);
    }
    
    @Test
    public void blackQueenNorthEast() {
        state.set(6, 0, blackBishop);
        assertCheck();
    }
    
    @Test
    public void blackQueenNorthWest() {
        state.set(0, 0, blackBishop);
        assertCheck();
    }
    
    @Test
    public void blackQueenSouthEast() {
        state.set(6, 6, blackBishop);
        assertCheck();
    }
    
    @Test
    public void blackQueenSouthWest() {
        state.set(0, 6, blackBishop);
        assertCheck();
    }
    
    @Test
    public void whiteQueenNorthEast() {
        state.set(6, 0, whiteBishop);
        assertNotCheck();
    }
    
    @Test
    public void whiteQueenNorthWest() {
        state.set(0, 0, whiteBishop);
        assertNotCheck();
    }
    
    @Test
    public void whiteQueenSouthEast() {
        state.set(6, 6, whiteBishop);
        assertNotCheck();
    }
    
    @Test
    public void whiteQueenSouthWest() {
        state.set(0, 6, whiteBishop);
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
        
        // Diagonal queens:
        state.set(0, 0, blackBishop);
        state.set(6, 6, blackBishop);
        state.set(1, 5, blackBishop);
        state.set(5, 1, blackBishop);
        
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
