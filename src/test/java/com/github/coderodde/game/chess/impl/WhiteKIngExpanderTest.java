package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.KING;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public final class WhiteKIngExpanderTest {
    
    private final AbstractChessBoardStateExpander expander = 
            new WhiteKingExpander();
    
    private final AbstractChessBoardStateExpander dummyExpander = 
            new TestDummyExpander();
    
    @Test
    public void expandAll() {
        final ChessBoardState state = new ChessBoardState();
        state.clear();
    
        state.set(6, 6, new Piece(WHITE, KING, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        final Set<ChessBoardState> filter = new HashSet<>();
        
        assertEquals(8, children.size());
        
        ChessBoardState move = new ChessBoardState(state);
        
        assertTrue(children.contains(getMove(5, 5, state)));
        assertTrue(children.contains(getMove(6, 5, state)));
        assertTrue(children.contains(getMove(7, 5, state)));
        
        assertTrue(children.contains(getMove(5, 7, state)));
        assertTrue(children.contains(getMove(6, 7, state)));
        assertTrue(children.contains(getMove(7, 7, state)));
        
        assertTrue(children.contains(getMove(5, 6, state)));
        assertTrue(children.contains(getMove(5, 7, state)));
    }
    
    private static ChessBoardState getMove(final int file,
                                           final int rank, 
                                           final ChessBoardState move) {
        
        final ChessBoardState child = new ChessBoardState(move);
        child.clear(6, 6);
        child.set(file, rank, new Piece(WHITE, KING));
        
        return child;
    }
}
