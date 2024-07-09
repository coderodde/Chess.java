package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.KNIGHT;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import com.github.coderodde.game.chess.UnderAttackCheck;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class BlackUnderAttackCheckByWhiteKnightTest {
    
    private final ChessBoardState state = new ChessBoardState();
    private final Piece whiteKnight = new Piece(WHITE, KNIGHT);
    private final Piece blackPawn = new Piece(BLACK, PAWN); 
    
    private static final UnderAttackCheck KNIGHT_CHECK = 
            new BlackUnderAttackCheckByWhiteKnight();
    
    @Before
    public void before() {
        state.clear();
    }
    
    @Test
    public void threatNorthLeft() {
        checkImpl(3, 1);
    }
    
    @Test
    public void threatNorthRight() {
        checkImpl(5, 1);
    }
    
    @Test
    public void threatSouthLeft() {
        checkImpl(3, 5);
    }
    
    @Test
    public void threatSouthRight() {
        checkImpl(5, 5);
    }
    
    @Test
    public void threatWestUp() {
        checkImpl(2, 2);
    }
    
    @Test
    public void threatWestDown() {
        checkImpl(2, 4);
    }
    
    @Test
    public void threatEastUp() {
        checkImpl(6, 2);
    }
    
    @Test
    public void threatEastDown() {
        checkImpl(6, 4);
    }
    
    private void checkImpl(final int blackPawnFile, final int blackPawnRank) {
        state.set(4, 
                  3,
                  whiteKnight);
        
        state.set(blackPawnFile, 
                  blackPawnRank, 
                  blackPawn);
        
        assertTrue(KNIGHT_CHECK.check(state, 
                                      4, 
                                      3));
    }
}
