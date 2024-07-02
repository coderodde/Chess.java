package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.QUEEN;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public final class WhiteQueenExpanderTest {
    
    private final AbstractChessBoardStateExpander expander = 
            new WhiteQueenExpander();
    
    private final AbstractChessBoardStateExpander dummyExpander = 
            new TestDummyExpander();
    
    @Test
    public void expand1() {
        final ChessBoardState state = new ChessBoardState();
        
        state.clear();
        
        state.set(2, 2, new Piece(WHITE, QUEEN, expander));
        state.set(4, 4, new Piece(BLACK, PAWN));
        state.set(2, 4, new Piece(BLACK, BISHOP));
        state.set(6, 6, new Piece(WHITE, BISHOP, dummyExpander));
        state.set(4, 2, new Piece(BLACK, PAWN));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(16, children.size());
        
        // To the left:
        final ChessBoardState move1 = move(state, 1, 2);
        final ChessBoardState move2 = move(state, 0, 2);
        
        // To the right:
        final ChessBoardState move3 = move(state, 3, 2);
        final ChessBoardState move4 = move(state, 4, 2);
        
        // Upwards:
        final ChessBoardState move5 = move(state, 2, 1);
        final ChessBoardState move6 = move(state, 2, 0);
        
        // Downwards:
        final ChessBoardState move7 = move(state, 2, 3);
        final ChessBoardState move8 = move(state, 2, 4);
        
        // North west
        final ChessBoardState move9 = move(state, 1, 1);
        final ChessBoardState move10 = move(state, 0, 0);
        
        // North east:
        final ChessBoardState move11 = move(state, 3, 1);
        final ChessBoardState move12 = move(state, 4, 0);
        
        // South west:
        final ChessBoardState move13 = move(state, 1, 3);
        final ChessBoardState move14 = move(state, 0, 4);
        
        // South east:
        final ChessBoardState move15 = move(state, 3, 3);
        final ChessBoardState move16 = move(state, 4, 4);
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
        assertTrue(children.contains(move3));
        assertTrue(children.contains(move4));
        assertTrue(children.contains(move5));
        assertTrue(children.contains(move6));
        assertTrue(children.contains(move7));
        assertTrue(children.contains(move8));
        assertTrue(children.contains(move9));
        assertTrue(children.contains(move10));
        assertTrue(children.contains(move11));
        assertTrue(children.contains(move12));
        assertTrue(children.contains(move13));
        assertTrue(children.contains(move14));
        assertTrue(children.contains(move15));
        assertTrue(children.contains(move16));
        
        final Set<ChessBoardState> filter = new HashSet<>(children);
        
        assertEquals(16, filter.size());
    }
    
    @Test
    public void expandAll() {
        final ChessBoardState state = new ChessBoardState();
        state.clear();
        
        state.set(3, 4, new Piece(WHITE, QUEEN, expander));
        System.out.println(state);
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        final Set<ChessBoardState> filter = new HashSet<>();
        
        assertEquals(27, children.size());
        
        children.forEach(System.out::println);
        
        int f;
        int r;
        
        // To north:
        f = 3;
        r = 3;
        
        ChessBoardState child;
        
        while (r >= 0) {
            child = move2(state, f, r);
            filter.add(child);
            assertTrue(children.contains(child));
            
            r--;
        }
        
        // To south:
        f = 3;
        r = 5;
        
        while (r < N) {
            child = move2(state, f, r);
            filter.add(child);
            assertTrue(children.contains(child));
            
            r++;
        }
        
        // To west:
        f = 2;
        r = 4;
        
        while (f >= 0) {
            child = move2(state, f, r);
            filter.add(child);
            assertTrue(children.contains(child));
            
            f--;
        }
        
        // To east:
        f = 4;
        r = 4;
        
        while (f < N) {
            child = move2(state, f, r);
            filter.add(child);
            assertTrue(children.contains(child));
            
            f++;
        }
        
        // Check north west:
        f = 2;
        r = 3;
        
        while (f >= 0 && r >= 0) {
            child = move2(state, f, r);
            filter.add(child);
            assertTrue(children.contains(child));
            
            f--;
            r--;
        }
        
        // Check north east:
        f = 4;
        r = 3;
        
        while (f < N && r >= 0) {
            child = move2(state, f, r);
            filter.add(child);
            assertTrue(children.contains(child));

            assertTrue(children.contains(child));
        
            f++;
            r--;
        }
        
        // Check south west:
        f = 2;
        r = 5;
        
        while (f >= 0 && r < N) {
            child = move2(state, f, r);
            filter.add(child);
            assertTrue(children.contains(child));
        
            f--;
            r++;
        }
        
        // Check south east:
        f = 4;
        r = 5;
        
        while (f < N && r < N) {
            child = move2(state, f, r);
            filter.add(child);
            assertTrue(children.contains(child));
            
            f++;
            r++;
        }
        
        assertEquals(27, filter.size());
    }
    
    private static ChessBoardState move(final ChessBoardState state,
                                        final int file,
                                        final int rank) {
        
        final ChessBoardState move = new ChessBoardState(state);
        
        move.set(file, rank, state.get(2, 2));
        move.clear(2, 2);
        
        return move;
    }
    
    private static ChessBoardState move2(final ChessBoardState state,
                                         final int file,
                                         final int rank) {
        
        final ChessBoardState move = new ChessBoardState(state);
        
        move.set(file, rank, state.get(3, 4));
        move.clear(3, 4);
        
        return move;
    }
}
