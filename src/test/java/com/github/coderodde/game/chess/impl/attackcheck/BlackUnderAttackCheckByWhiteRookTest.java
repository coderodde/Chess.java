package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.ROOK;
import com.github.coderodde.game.chess.UnderAttackCheck;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class BlackUnderAttackCheckByWhiteRookTest {
    
    private static final Piece whiteRook = new Piece(WHITE, ROOK);
    private static final Piece blackRook = new Piece(BLACK, ROOK);
    
    private static final Piece whitePawn = new Piece(WHITE, PAWN);
    private static final Piece blackPawn = new Piece(BLACK, PAWN);
    
    private static final UnderAttackCheck ROOK_CHECK = 
            new BlackUnderAttackCheckByWhiteRook();
    
    private final ChessBoardState state = new ChessBoardState();
    
    @Before
    public void before() {
        state.clear();
        state.set(3, 3, blackPawn);
    }
    
    @Test
    public void whiteRookNorth() {
        state.set(3, 1, whiteRook);
        assertCheck();
    }
    
    @Test
    public void whiteRookSouth() {
        state.set(3, 7, whiteRook);
        assertCheck();
    }
    
    @Test
    public void whiteRookWest() {
        state.set(0, 3, whiteRook);
        assertCheck();
    }
    
    @Test
    public void whiteRookEast() {
        state.set(6, 3, whiteRook);
        assertCheck();
    }
    
    @Test
    public void blackRookNorth() {
        state.set(3, 1, blackRook);
        assertNotCheck();
    }
    
    @Test
    public void blackRookSouth() {
        state.set(3, 7, blackRook);
        assertNotCheck();
    }
    
    @Test
    public void blackRookWest() {
        state.set(0, 3, blackRook);
        assertNotCheck();
    }
    
    @Test
    public void blackRookEast() {
        state.set(6, 3, blackRook);
        assertNotCheck();
    }
        
    @Test
    public void blackPawnNorth() {
        state.set(3, 1, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnSouth() {
        state.set(3, 7, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnWest() {
        state.set(0, 3, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnEast() {
        state.set(6, 3, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void threatenedInAllDirection() {
        state.set(3, 3, new Piece(WHITE, BISHOP));
        
        // Straight queens:
        state.set(3, 5, whiteRook);
        state.set(3, 0, whiteRook);
        state.set(0, 3, whiteRook);
        state.set(7, 3, whiteRook);
        
        assertTrue(ROOK_CHECK.check(state, 3, 3));
    }
    
    private void assertCheck() {
        assertTrue(ROOK_CHECK.check(state, 3, 3));
    }
    
    private void assertNotCheck() {
        assertFalse(ROOK_CHECK.check(state, 3, 3));
    }
}
