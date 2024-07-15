package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.KING;
import static com.github.coderodde.game.chess.PieceType.KNIGHT;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.QUEEN;
import static com.github.coderodde.game.chess.PieceType.ROOK;
import com.github.coderodde.game.chess.UnderAttackCheck;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public final class BlackUnderAttackCheckTest {
    
    private static final ChessBoardState state = new ChessBoardState();
    private static final UnderAttackCheck CHECKER = new BlackUnderAttackCheck();
    private static final Piece pawn = new Piece(BLACK, PAWN);
    
    @Before
    public void before() {
        state.clear();
    }
    
    @Test
    public void mainTest() {
        final ChessBoardState state = new ChessBoardState();
        state.clear();
        
        state.set(3, 3, new Piece(WHITE, PAWN));
        
        assertFalse(CHECKER.check(state, 3, 3));
    }
    
    @Test
    public void catchQueenAttack() {
        state.set(1, 1, pawn);
        state.set(4, 4, new Piece(BLACK, QUEEN));
        assertTrue(CHECKER.check(state, 1, 1));
    }
    
    @Test
    public void catchRookAttack() {
        state.set(1, 1, pawn);
        state.set(1, 4, new Piece(BLACK, ROOK));
        assertTrue(CHECKER.check(state, 1, 1));
    }
    
    @Test
    public void catchBishopAttack() {
        state.set(1, 1, pawn);
        state.set(5, 5, new Piece(BLACK, BISHOP));
        assertTrue(CHECKER.check(state, 1, 1));
    }
    
    @Test
    public void catchKnightAttack() {
        state.set(1, 1, pawn);
        state.set(2, 3, new Piece(BLACK, KNIGHT));
        assertTrue(CHECKER.check(state, 1, 1));
    }
    
    @Test
    public void catchPawnAttack() {
        state.set(1, 1, pawn);
        state.set(2, 2, new Piece(BLACK, PAWN));
        assertTrue(CHECKER.check(state, 1, 1));
    }
    
    @Test
    public void catchKingAttack() {
        state.set(1, 1, pawn);
        state.set(0, 1, new Piece(BLACK, KING));
        assertTrue(CHECKER.check(state, 1, 1));
    }
}
