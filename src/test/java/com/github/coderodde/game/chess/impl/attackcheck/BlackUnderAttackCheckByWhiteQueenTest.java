package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.QUEEN;
import com.github.coderodde.game.chess.UnderAttackCheck;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class BlackUnderAttackCheckByWhiteQueenTest {
    
    private static final Piece whiteQueen = new Piece(WHITE, QUEEN);
    private static final Piece blackQueen = new Piece(BLACK, QUEEN);
    
    private static final Piece whitePawn = new Piece(WHITE, PAWN);
    private static final Piece blackPawn = new Piece(BLACK, PAWN);
    
    private static final UnderAttackCheck QUEEN_CHECK = 
            new BlackUnderAttackCheckByWhiteQueen();
    
    private final ChessBoardState state = new ChessBoardState();
    
    @Before
    public void before() {
        state.clear();
        state.set(3, 3, blackPawn);
    }
    
    @Test
    public void whiteQueenNorth() {
        state.set(3, 1, whiteQueen);
        assertCheck();
    }
    
    @Test
    public void whiteQueenSouth() {
        state.set(3, 7, whiteQueen);
        assertCheck();
    }
    
    @Test
    public void whiteQueenWest() {
        state.set(0, 3, whiteQueen);
        assertCheck();
    }
    
    @Test
    public void whiteQueenEast() {
        state.set(6, 3, whiteQueen);
        assertCheck();
    }
    
    @Test
    public void whiteQueenNorthEast() {
        state.set(6, 0, whiteQueen);
        assertCheck();
    }
    
    @Test
    public void whiteQueenNorthWest() {
        state.set(0, 0, whiteQueen);
        assertCheck();
    }
    
    @Test
    public void whiteQueenSouthEast() {
        state.set(6, 6, whiteQueen);
        assertCheck();
    }
    
    @Test
    public void whiteQueenSouthWest() {
        state.set(0, 6, whiteQueen);
        assertCheck();
    }
    
    @Test
    public void blackQueenNorth() {
        state.set(3, 1, blackQueen);
        assertNotCheck();
    }
    
    @Test
    public void blackQueenSouth() {
        state.set(3, 7, blackQueen);
        assertNotCheck();
    }
    
    @Test
    public void blackQueenWest() {
        state.set(0, 3, blackQueen);
        assertNotCheck();
    }
    
    @Test
    public void blackQueenEast() {
        state.set(6, 3, blackQueen);
        assertNotCheck();
    }
    
    @Test
    public void blackQueenNorthEast() {
        state.set(6, 0, blackQueen);
        assertNotCheck();
    }
    
    @Test
    public void blackQueenNorthWest() {
        state.set(0, 0, blackQueen);
        assertNotCheck();
    }
    
    @Test
    public void blackQueenSouthEast() {
        state.set(6, 6, blackQueen);
        assertNotCheck();
    }
    
    @Test
    public void blackQueenSouthWest() {
        state.set(0, 6, blackQueen);
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
    public void blackPawnNorthEast() {
        state.set(6, 0, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnNorthWest() {
        state.set(0, 0, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnSouthEast() {
        state.set(6, 6, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnSouthWest() {
        state.set(0, 6, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void threatenedInAllDirection() {
        state.set(3, 3, new Piece(WHITE, BISHOP));
        
        // Diagonal queens:
        state.set(0, 0, whiteQueen);
        state.set(6, 6, whiteQueen);
        state.set(1, 5, whiteQueen);
        state.set(5, 1, whiteQueen);
        
        // Straight queens:
        state.set(3, 5, whiteQueen);
        state.set(3, 0, whiteQueen);
        state.set(0, 3, whiteQueen);
        state.set(7, 3, whiteQueen);
        
        assertTrue(QUEEN_CHECK.check(state, 3, 3));
    }
    
    @Test
    public void limitNorthWestFile() {
        state.set(2, 3, blackPawn);
        assertNotCheck(2, 3);
    }
    
    @Test
    public void limitNorthWestRank() {
        state.set(3, 2, blackPawn);
        assertNotCheck(3, 2);
    }
    
    @Test
    public void limitNorthWestBoth() {
        state.set(2, 2, blackPawn);
        assertNotCheck(2, 2);
    }
    
    @Test
    public void limitNorthEastFile() {
        state.set(6, 3, blackPawn);
        assertNotCheck(6, 3);
    }
    
    @Test
    public void limitNorthEastRank() {
        state.set(3, 6, blackQueen);
        assertNotCheck(3, 6);
    }
    
    private void assertNotCheck(final int file, 
                                final int rank) {
        
        assertFalse(QUEEN_CHECK.check(state, file, rank));
    }
    
    private void assertCheck() {
        assertTrue(QUEEN_CHECK.check(state, 3, 3));
    }
    
    private void assertNotCheck() {
        assertFalse(QUEEN_CHECK.check(state, 3, 3));
    }
}
