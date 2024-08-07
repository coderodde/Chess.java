package com.github.coderodde.game.chess.impl.expanders;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
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
import org.junit.Before;
import org.junit.Test;

public final class BlackBishopExpanderTest {

    private final AbstractChessBoardStateExpander expander = 
            new BlackBishopExpander();
    
    private final AbstractChessBoardStateExpander dummyExpander = 
            new TestDummyExpander();
    
    private final ChessBoardState state = new ChessBoardState();
    
    @Before
    public void before() {
        state.clear();
    }
    
    @Test
    public void expand1() {
        state.set(6, 6, new Piece(BLACK, BISHOP, expander));
        state.set(7, 7, new Piece(WHITE, PAWN));
        state.set(4, 4, new Piece(BLACK, PAWN, dummyExpander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(4, children.size());
    }
    
    @Test
    public void expand2() {
        state.set(4, 4, new Piece(BLACK, BISHOP, expander));
        state.set(2, 2, new Piece(BLACK, KING, dummyExpander));
        state.set(5, 5, new Piece(WHITE, PAWN));
        state.set(3, 5, new Piece(WHITE, KNIGHT));
       
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(6, children.size());
        
        ChessBoardState move1 = new ChessBoardState(state);
        ChessBoardState move2 = new ChessBoardState(state);
        ChessBoardState move3 = new ChessBoardState(state);
        ChessBoardState move4 = new ChessBoardState(state);
        ChessBoardState move5 = new ChessBoardState(state);
        ChessBoardState move6 = new ChessBoardState(state);
        
        // North west:
        move1.set(3, 3, new Piece(BLACK, BISHOP));
        move1.clear(4, 4);
        
        assertTrue(children.contains(move1));
        
        // North east:
        move2.clear(4, 4);
        move3.clear(4, 4);
        move4.clear(4, 4);
        
        move2.set(5, 3, new Piece(BLACK, BISHOP));
        move3.set(6, 2, new Piece(BLACK, BISHOP));
        move4.set(7, 1, new Piece(BLACK, BISHOP));
        
        // South west:
        move5.clear(4, 4);
        move5.set(3, 5, new Piece(BLACK, BISHOP));
        
        // South east:
        move6.clear(4, 4);
        move6.set(5, 5, new Piece(BLACK, BISHOP));
        
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
    
    @Test
    public void expandAll() {
        state.set(4, 5, new Piece(BLACK, BISHOP, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        final Set<ChessBoardState> filter = new HashSet<>();
        
        assertEquals(11, children.size());
        
        int f = 3;
        int r = 4;
        
        // Check north west:
        while (f >= 0 && r >= 0) {
            ChessBoardState child = new ChessBoardState(state);
            child.set(f, r, state.get(4, 5));
            child.clear(4, 5);
            filter.add(child);
            
            assertTrue(children.contains(child));
            
            f--;
            r--;
        }
        
        // Check north east:
        f = 5;
        r = 4;
        
        while (f < N && r >= 0) {
            ChessBoardState child = new ChessBoardState(state);
            child.set(f, r, state.get(4, 5));
            child.clear(4, 5);
            filter.add(child);

            assertTrue(children.contains(child));
        
            f++;
            r--;
        }
        
        // Check south west:
        f = 3;
        r = 6;
        
        while (f >= 0 && r < N) {
            ChessBoardState child = new ChessBoardState(state);
            child.set(f, r, state.get(4, 5));
            child.clear(4, 5);
            filter.add(child);
            
            assertTrue(children.contains(child));
        
            f--;
            r++;
        }
        
        // Check south east:
        f = 5;
        r = 6;
        
        while (f < N && r < N) {
            ChessBoardState child = new ChessBoardState(state);
            child.set(f, r, state.get(4, 5));
            child.clear(4, 5);
            filter.add(child);
            
            assertTrue(children.contains(child));
        
            f++;
            r++;
        }
        
        assertEquals(11, filter.size());
    }
    
    @Test
    public void tryGenerateNorthWest() {
        state.set(7, 7, new Piece(BLACK, BISHOP, expander));
        state.set(5, 5, new Piece(WHITE, PAWN));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(2, children.size());
        
        ChessBoardState move = new ChessBoardState();
        
        move.clear();
        move.set(5, 5, new Piece(WHITE, PAWN));
        move.set(6, 6, state.get(7, 7));
        assertTrue(children.contains(move));
        
        move.clear();
        move.set(5, 5, state.get(7, 7));
        assertTrue(children.contains(move));
    }
    
    @Test
    public void tryGenerateSouthEast() {
        state.set(0, 0, new Piece(BLACK, BISHOP, expander));
        state.set(2, 2, new Piece(WHITE, PAWN));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(2, children.size());
        
        ChessBoardState move = new ChessBoardState();
        
        move.clear();
        move.set(1, 1, state.get(0, 0));
        move.set(2, 2, new Piece(WHITE, PAWN));
        assertTrue(children.contains(move));
        
        move.clear();
        move.set(2, 2, state.get(0, 0));
        assertTrue(children.contains(move));
    }
    
    @Test
    public void tryGenerateNorthEast() {
        state.set(0, 7, new Piece(BLACK, BISHOP, expander));
        state.set(2, 5, new Piece(WHITE, PAWN));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(2, children.size());
        
        ChessBoardState move = new ChessBoardState();
        
        move.clear();
        move.set(1, 6, state.get(0, 7));
        move.set(2, 5, new Piece(WHITE, PAWN));
        assertTrue(children.contains(move));
        
        move.clear();
        move.set(2, 5, state.get(0, 7));
        assertTrue(children.contains(move));
    }
    
    @Test
    public void obstructionSouthEast() {
        state.set(0, 0, new Piece(BLACK, BISHOP, expander));
        state.set(2, 2, new Piece(BLACK, PAWN, dummyExpander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(1, children.size());
        
        ChessBoardState move = new ChessBoardState();
        move.clear();
        move.set(1, 1, new Piece(BLACK, BISHOP));
        move.set(2, 2, new Piece(BLACK, PAWN));
        
        assertTrue(children.contains(move));
    }
    
    @Test
    public void obstructionSouthWest() {
        state.set(7, 0, new Piece(BLACK, BISHOP, expander));
        state.set(5, 2, new Piece(BLACK, PAWN, dummyExpander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(1, children.size());
        
        ChessBoardState move = new ChessBoardState();
        move.clear();
        move.set(6, 1, new Piece(BLACK, BISHOP));
        move.set(5, 2, new Piece(BLACK, PAWN));
        
        assertTrue(children.contains(move));
    }
    
    @Test
    public void obstructionNorthEast() {
        state.set(0, 7, new Piece(BLACK, BISHOP, expander));
        state.set(2, 5, new Piece(BLACK, PAWN, dummyExpander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(1, children.size());
        
        ChessBoardState move = new ChessBoardState();
        move.clear();
        move.set(1, 6, new Piece(BLACK, BISHOP));
        move.set(2, 5, new Piece(BLACK, PAWN));
        
        assertTrue(children.contains(move));
    }
}
