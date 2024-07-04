package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.ROOK;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public final class WhiteRookExpanderTest {
    
    private final AbstractChessBoardStateExpander expander = 
            new WhiteRookExpander();
    
    private final AbstractChessBoardStateExpander dummyExpander = 
            new TestDummyExpander();
    
    private final ChessBoardState state = new ChessBoardState();
    
    @Before
    public void before() {
        state.clear();
    }
    
    @Test
    public void expand() {
        final ChessBoardState state = new ChessBoardState();
        
        state.clear();
        
        state.set(3, 3, new Piece(WHITE, ROOK, expander));
        state.set(3, 2, new Piece(BLACK, PAWN));
        state.set(3, 6, new Piece(BLACK, PAWN));
        state.set(1, 3, new Piece(BLACK, BISHOP));
        state.set(6, 3, new Piece(BLACK, BISHOP));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(9, children.size());
    }
    
    @Test
    public void expandAtCorner() {
        final ChessBoardState state = new ChessBoardState();
        
        state.clear();
        
        state.set(6, 5, new Piece(WHITE, ROOK, expander));
        state.set(6, 4, new Piece(BLACK, PAWN));
        state.set(5, 5, new Piece(BLACK, BISHOP));
        
        ChessBoardState move1 = new ChessBoardState();
        ChessBoardState move2 = new ChessBoardState();
        ChessBoardState move3 = new ChessBoardState();
        ChessBoardState move4 = new ChessBoardState();
        ChessBoardState move5 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        move3.clear();
        move4.clear();
        move5.clear();
        
        // North:
        move1.set(6, 4, new Piece(WHITE, ROOK));
        move1.set(5, 5, new Piece(BLACK, BISHOP));
        
        // West:
        move2.set(5, 5, new Piece(WHITE, ROOK));
        move2.set(6, 4, new Piece(BLACK, PAWN));
        
        // East:
        move3.set(7, 5, new Piece(WHITE, ROOK));
        move3.set(6, 4, new Piece(BLACK, PAWN));
        move3.set(5, 5, new Piece(BLACK, BISHOP));
        
        // South first time:
        move4.set(6, 6, new Piece(WHITE, ROOK));
        move4.set(6, 4, new Piece(BLACK, PAWN));
        move4.set(5, 5, new Piece(BLACK, BISHOP));
        
        // South second time:
        move5.set(6, 7, new Piece(WHITE, ROOK));
        move5.set(6, 4, new Piece(BLACK, PAWN));
        move5.set(5, 5, new Piece(BLACK, BISHOP));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(5, children.size());
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
        
        assertTrue(children.contains(move3));
        assertTrue(children.contains(move4));
        assertTrue(children.contains(move5));
        
        final Set<ChessBoardState> filter = new HashSet<>();
        
        filter.add(move1);
        filter.add(move2);
        filter.add(move3);
        filter.add(move4);
        filter.add(move5);
        
        assertEquals(5, filter.size());
    }
    
    @Test
    public void expandAll() {
        ChessBoardState state = new ChessBoardState();
        state.clear();
        state.set(5, 3, new Piece(WHITE, ROOK, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        final Set<ChessBoardState> filter = new HashSet<>();
        
        assertEquals(14, children.size());
        
        int f;
        int r;
        
        // To north:
        f = 5;
        r = 2;
        
        while (r >= 0) {
            ChessBoardState child = new ChessBoardState(state);
            child.set(f, r, state.get(5, 3));
            child.clear(5, 3);
            filter.add(child);
            
            assertTrue(children.contains(child));
            
            r--;
        }
        
        // To south:
        f = 5;
        r = 4;
        
        while (r < N) {
            ChessBoardState child = new ChessBoardState(state);
            child.set(f, r, state.get(5, 3));
            child.clear(5, 3);
            filter.add(child);
            
            assertTrue(children.contains(child));
            
            r++;
        }
        
        // To west:
        f = 4;
        r = 3;
        
        while (f >= 0) {
            ChessBoardState child = new ChessBoardState(state);
            child.set(f, r, state.get(5, 3));
            child.clear(5, 3);
            filter.add(child);
            
            assertTrue(children.contains(child));
            
            f--;
        }
        
        // To east:
        f = 6;
        r = 3;
        
        while (f < N) {
            ChessBoardState child = new ChessBoardState(state);
            child.set(f, r, state.get(5, 3));
            child.clear(5, 3);
            filter.add(child);
            
            assertTrue(children.contains(child));
            
            f++;
        }
        
        assertEquals(14, filter.size());
    }
    
    @Test
    public void obstructionAtNorth() {
        state.set(7, 2, new Piece(WHITE, ROOK, expander));
        state.set(7, 0, new Piece(WHITE, PAWN, dummyExpander));
        state.set(6, 2, new Piece(WHITE, PAWN, dummyExpander));
        state.set(7, 3, new Piece(WHITE, PAWN, dummyExpander));
        
        System.out.println(state);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(1, children.size());
        
        final ChessBoardState move = new ChessBoardState(state);
        
        move.set(7, 1, state.get(7, 2));
        move.clear(7, 2);
    }
    
    @Test
    public void obstructionOnEast() {
        state.set(0, 0, new Piece(WHITE, ROOK, expander));
        state.set(0, 1, new Piece(WHITE, PAWN, dummyExpander));
        state.set(2, 0, new Piece(WHITE, PAWN, dummyExpander));

        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(1, children.size());
        
        final ChessBoardState move = new ChessBoardState(state);
        move.set(1, 0, state.get(0, 0));
        move.clear(0, 0);
        
        assertTrue(children.contains(move));
    }
}
