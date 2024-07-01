package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public final class WhiteBishopExpanderTest {

    private final AbstractChessBoardStateExpander expander = 
            new WhiteBishopExpander();
    
    private final AbstractChessBoardStateExpander dummyExpander = 
            new TestDummyExpander();
    
    @Test
    public void testExpand() {
        ChessBoardState state = new ChessBoardState();
        state.clear();
        
        state.set(6, 6, new Piece(WHITE, BISHOP, expander));
        state.set(7, 7, new Piece(BLACK, PAWN));
        state.set(4, 4, new Piece(WHITE, PAWN, dummyExpander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(4, children.size());
    }
}
