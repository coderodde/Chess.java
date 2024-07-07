package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.QUEEN;
import com.github.coderodde.game.chess.UnderAttackCheck;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public final class WhiteUnderAttackCheckByBlackQueenTest {
    
    private static final Piece queen = new Piece(BLACK, QUEEN);
    private static final UnderAttackCheck QUEEN_CHECK = 
            new WhiteUnderAttackCheckByBlackQueen();
    
    private final ChessBoardState state = new ChessBoardState();
    
    @Before
    public void before() {
        state.clear();
        state.set(3, 3, new Piece(WHITE, PAWN));
    }
    
    @Test
    public void blackQueenNorth() {
        state.set(3, 1, queen);
        assertCheck();
    }
    
    @Test
    public void blackQueenSouth() {
        state.set(3, 7, queen);
        assertCheck();
    }
    
    @Test
    public void blackQueenWest() {
        state.set(0, 3, queen);
        assertCheck();
    }
    
    @Test
    public void blackQueenEast() {
        state.set(6, 3, queen);
        assertCheck();
    }
    
    @Test
    public void blackQueenNorthEast() {
        state.set(6, 0, queen);
        assertCheck();
        System.out.println("northeast");
    }
    
    @Test
    public void blackQueenNorthWest() {
        state.set(0, 0, queen);
        assertCheck();
        System.out.println("northwest");
    }
    
    @Test
    public void threatenedInAllDirection() {
        state.set(3, 3, new Piece(WHITE, BISHOP));
        
        // Diagonal queens:
        state.set(0, 0, queen);
        state.set(6, 6, queen);
        state.set(1, 5, queen);
        state.set(5, 1, queen);
        
        // Straight queens:
        state.set(3, 5, queen);
        state.set(3, 0, queen);
        state.set(0, 3, queen);
        state.set(7, 3, queen);
        
        assertTrue(QUEEN_CHECK.check(state, 3, 3));
    }
    
    private void assertCheck() {
        assertTrue(QUEEN_CHECK.check(state, 3, 3));
    }
}
