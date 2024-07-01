package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.KING;
import static com.github.coderodde.game.chess.PieceType.KNIGHT;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public final class WhiteBishopExpanderTest {

    private final AbstractChessBoardStateExpander expander = 
            new WhiteBishopExpander();
    
    private final AbstractChessBoardStateExpander dummyExpander = 
            new TestDummyExpander();
    
    @Test
    public void expand1() {
        ChessBoardState state = new ChessBoardState();
        state.clear();
        
        state.set(6, 6, new Piece(WHITE, BISHOP, expander));
        state.set(7, 7, new Piece(BLACK, PAWN));
        state.set(4, 4, new Piece(WHITE, PAWN, dummyExpander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(4, children.size());
    }
    
    @Test
    public void expand2() {
        ChessBoardState state = new ChessBoardState();
        state.clear();
        
        state.set(4, 4, new Piece(WHITE, BISHOP, expander));
        state.set(2, 2, new Piece(WHITE, KING, dummyExpander));
        state.set(5, 5, new Piece(BLACK, PAWN));
        state.set(3, 5, new Piece(BLACK, KNIGHT));
        
        System.out.println("---");
        System.out.println(state);
        System.out.println("---");
       
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(6, children.size());
        
        ChessBoardState move1 = new ChessBoardState(state);
        ChessBoardState move2 = new ChessBoardState(state);
        ChessBoardState move3 = new ChessBoardState(state);
        ChessBoardState move4 = new ChessBoardState(state);
        ChessBoardState move5 = new ChessBoardState(state);
        ChessBoardState move6 = new ChessBoardState(state);
        
        // North west:
        move1.set(3, 3, new Piece(WHITE, BISHOP));
        move1.clear(4, 4);
        
        assertTrue(children.contains(move1));
        
        // North east:
        move2.clear(4, 4);
        move3.clear(4, 4);
        move4.clear(4, 4);
        
        move2.set(5, 3, new Piece(WHITE, BISHOP));
        move3.set(6, 2, new Piece(WHITE, BISHOP));
        move4.set(7, 1, new Piece(WHITE, BISHOP));
        
        // South west:
        move5.clear(4, 4);
        move5.set(3, 5, new Piece(WHITE, BISHOP));
        
        // South east:
        move6.clear(4, 4);
        move6.set(5, 5, new Piece(WHITE, BISHOP));
        
        assertTrue(children.contains(move2));
        assertTrue(children.contains(move3));
        assertTrue(children.contains(move4));
        assertTrue(children.contains(move5));
        assertTrue(children.contains(move6));
        
        final Set<ChessBoardState> filter = new HashSet<>();
        
        filter.addAll(Arrays.asList(move1,
                                    move2,
                                    move3,
                                    move4,
                                    move5,
                                    move6));
        
        assertEquals(6, filter.size());
    }
}
