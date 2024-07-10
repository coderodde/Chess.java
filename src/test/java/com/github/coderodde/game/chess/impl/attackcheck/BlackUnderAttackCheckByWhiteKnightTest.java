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
        checkThreatImpl(3, 1);
    }
    
    @Test
    public void threatNorthRight() {
        checkThreatImpl(5, 1);
    }
    
    @Test
    public void threatSouthLeft() {
        checkThreatImpl(3, 5);
    }
    
    @Test
    public void threatSouthRight() {
        checkThreatImpl(5, 5);
    }
    
    @Test
    public void threatWestUp() {
        checkThreatImpl(2, 2);
    }
    
    @Test
    public void threatWestDown() {
        checkThreatImpl(2, 4);
    }
    
    @Test
    public void threatEastUp() {
        checkThreatImpl(6, 2);
    }
    
    @Test
    public void threatEastDown() {
        checkThreatImpl(6, 4);
    }
    
    @Test
    public void northWestCorner() {
        checkNoThreatImpl(0, 0);
    }
    
    @Test
    public void southWestCorner() {
        checkNoThreatImpl(0, 7);
    }
    
    @Test
    public void northEastCorner() {
        checkNoThreatImpl(7, 0);
    }
    
    @Test
    public void southEastCorner() {
        checkNoThreatImpl(7, 7);
    }
    
    private void checkThreatImpl(final int blackPawnFile,
                                 final int blackPawnRank) {
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
    
    private void checkNoThreatImpl(final int whiteKnightFile,
                                   final int whiteKnightRank) {
        state.set(whiteKnightFile, 
                  whiteKnightRank,
                  whiteKnight);
        
        assertFalse(KNIGHT_CHECK.check(state, 
                                       whiteKnightFile, 
                                       whiteKnightRank));
    }
}
