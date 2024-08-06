package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractHeuristicFunction;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.KNIGHT;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class ShannonHeuristicFunctionTest {
    
    private static final ChessBoardState state = new ChessBoardState();

    private final AbstractHeuristicFunction heuristicFunction = 
            null;
    
    @Before
    public void before() {
        state.clear();
    }
    
    @Test
    public void stateZeroScore() {
        state.set(3, 3, new Piece(WHITE, BISHOP));
        state.set(5, 5, new Piece(BLACK, BISHOP));
        
        assertEquals(0, heuristicFunction.evaluate(state, -1));
    }
    
    @Test
    public void avoidBlackAttack() {
        state.set(3, 3, new Piece(WHITE, PAWN));
        state.set(6, 6, new Piece(BLACK, KNIGHT));
        
        assertEquals(4, heuristicFunction.evaluate(state, 0));
    }
    
    @Test
    public void avoidWhiteAttack() {
        state.set(3, 3, new Piece(BLACK, PAWN));
        state.set(6, 6, new Piece(WHITE, KNIGHT));
        
        assertEquals(-4, heuristicFunction.evaluate(state, 0));
    }
}
