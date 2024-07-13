package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.KING;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import com.github.coderodde.game.chess.UnderAttackCheck;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public final class BlackUnderAttackCheckByWhiteKingTest {
    
    private static final Piece whiteKing = new Piece(WHITE, KING);
    private static final Piece blackKing = new Piece(BLACK, KING);
    private static final Piece whitePawn = new Piece(WHITE, PAWN);
    private static final Piece blackPawn = new Piece(BLACK, PAWN);
    
    private static final UnderAttackCheck KING_CHECK = 
            new BlackUnderAttackCheckByWhiteKing();
    
    private final ChessBoardState state = new ChessBoardState();
    
    @Before
    public void before() {
        state.clear();
        state.set(3, 3, blackPawn);
    }
    
    @Test
    public void whiteKingNorthThreatens() {
        state.set(3, 2, whiteKing);
        assertCheck();
    }
    
    @Test
    public void whiteKingNorthNotThreatens() {
        assertNotCheck();
    }
    
    private void assertCheck() {
        assertTrue(KING_CHECK.check(state, 3, 3));
    }
    
    private void assertNotCheck() {
        assertFalse(KING_CHECK.check(state, 3, 3));
    }
}
