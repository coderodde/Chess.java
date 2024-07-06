package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.KNIGHT;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class BlackKnightExpanderTest {
    
    private final AbstractChessBoardStateExpander expander = 
            new BlackKnightExpander();
    
    private final AbstractChessBoardStateExpander dummyExpander = 
            new TestDummyExpander();
    
    private final ChessBoardState state = new ChessBoardState();
    
    @Before
    public void before() {
        state.clear();
    }
    
    @Test
    public void expand8() {
        ChessBoardState state = new ChessBoardState();
        state.clear();
        state.set(4, 4, new Piece(BLACK, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(8, children.size());
        
        ChessBoardState move1 = new ChessBoardState();
        ChessBoardState move2 = new ChessBoardState();
        ChessBoardState move3 = new ChessBoardState();
        ChessBoardState move4 = new ChessBoardState();
        ChessBoardState move5 = new ChessBoardState();
        ChessBoardState move6 = new ChessBoardState();
        ChessBoardState move7 = new ChessBoardState();
        ChessBoardState move8 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        move3.clear();
        move4.clear();
        move5.clear();
        move6.clear();
        move7.clear();
        move8.clear();
        
        // North left:
        move1.set(3, 2, new Piece(BLACK, KNIGHT));
        
        // North right:
        move2.set(5, 2, new Piece(BLACK, KNIGHT));
        
        // South left:
        move3.set(3, 6, new Piece(BLACK, KNIGHT));
        
        // South right:
        move4.set(5, 6, new Piece(BLACK, KNIGHT));
        
        // West up:
        move5.set(2, 3, new Piece(BLACK, KNIGHT));
        
        // West down:
        move6.set(2, 5, new Piece(BLACK, KNIGHT));
        
        // East up:
        move7.set(6, 3, new Piece(BLACK, KNIGHT));
        
        // East down:
        move8.set(6, 5, new Piece(BLACK, KNIGHT));
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
        assertTrue(children.contains(move3));
        assertTrue(children.contains(move4));
        assertTrue(children.contains(move5));
        assertTrue(children.contains(move6));
        assertTrue(children.contains(move7));
        assertTrue(children.contains(move8));
        
        final Set<ChessBoardState> filter = new HashSet<>();
        
        filter.add(move1);
        filter.add(move2);
        filter.add(move3);
        filter.add(move4);
        filter.add(move5);
        filter.add(move6);
        filter.add(move7);
        filter.add(move8);
        
        assertEquals(8, filter.size());
    }
    
    @Test
    public void topLeftCorner() {
        final ChessBoardState state = new ChessBoardState();
        state.clear();
        
        state.set(1, 1, new Piece(BLACK, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(4, children.size());
    }
    
    @Test
    public void topRightCorner() {
        final ChessBoardState state = new ChessBoardState();
        state.clear();
        
        state.set(6, 1, new Piece(BLACK, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(4, children.size());
    }
    
    @Test
    public void bottomLeftCorner() {
        final ChessBoardState state = new ChessBoardState();
        state.clear();
        
        state.set(1, 6, new Piece(BLACK, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(4, children.size());
    }
    
    @Test
    public void bottomRightCorner() {
        final ChessBoardState state = new ChessBoardState();
        state.clear();
        
        state.set(6, 6, new Piece(BLACK, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(4, children.size());
    }
    
    @Test
    public void cannotMoveNorthLeft() {
        state.set(0, 2, new Piece(BLACK, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(4, children.size());
        
        assertTrue(children.contains(getMove(state, 0, 2, 1, 0)));
        assertTrue(children.contains(getMove(state, 0, 2, 1, 4)));
        assertTrue(children.contains(getMove(state, 0, 2, 2, 1)));
        assertTrue(children.contains(getMove(state, 0, 2, 2, 3)));
    }
    
    @Test
    public void obstructionNorthLeft() {
        state.set(2, 2, new Piece(BLACK, KNIGHT, expander));
        state.set(1, 0, new Piece(BLACK, PAWN, dummyExpander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        final ChessBoardState obstructedState = getMove(state,
                                                        1,
                                                        0,
                                                        2, 
                                                        2);
        
        
        assertFalse(children.contains(obstructedState));
    }
    
    @Test
    public void cannotGenerateNorthRight() {
        state.set(N - 1, 2, new Piece(BLACK, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(4, children.size());
        assertTrue(children.contains(getMove(state, N - 1, 2, 6, 0)));
        assertTrue(children.contains(getMove(state, N - 1, 2, 6, 4)));
        assertTrue(children.contains(getMove(state, N - 1, 2, 5, 1)));
        assertTrue(children.contains(getMove(state, N - 1, 2, 5, 3)));
    }
    
    @Test
    public void cannotGenerateWestUp() {
        state.set(2, 0, new Piece(BLACK, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(4, children.size());
        assertTrue(children.contains(getMove(state, 2, 0, 1, 2)));
        assertTrue(children.contains(getMove(state, 2, 0, 3, 2)));
        assertTrue(children.contains(getMove(state, 2, 0, 0, 1)));
        assertTrue(children.contains(getMove(state, 2, 0, 4, 1)));
    }
    
    @Test
    public void cannotGenerateWestDown() {
        state.set(2, N - 1, new Piece(BLACK, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(4, children.size());
        
        assertTrue(children.contains(getMove(state, 2, N - 1, 0, 6)));
        assertTrue(children.contains(getMove(state, 2, N - 1, 4, 6)));
        assertTrue(children.contains(getMove(state, 2, N - 1, 1, 5)));
        assertTrue(children.contains(getMove(state, 2, N - 1, 3, 5)));
    }
    
    @Test
    public void allObstructions() {
        final Piece p = new Piece(BLACK, PAWN, dummyExpander);
        state.set(4, 4, new Piece(BLACK, KNIGHT, expander));
        
        // North left:
        state.set(3, 2, p);
        
        // North east:
        state.set(5, 2, p);
                
        // South left:
        state.set(3, 6, p);
        
        // South right:
        state.set(5, 6, p);
        
        // West up:
        state.set(2, 3, p);
        
        // West down:
        state.set(2, 5, p);
        
        // East up:
        state.set(6, 3, p);
        
        // East down:
        state.set(6, 5, p);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertTrue(children.isEmpty());
    }
    
    /**
     * Makes a move in a {@code state}.
     * 
     * @param state the source state.
     * @param file1 the source state file.
     * @param rank1 the source state rank.
     * @param file2 the target state file.
     * @param rank2 the target state rank.
     * 
     * @return a new move. 
     */
    private static ChessBoardState 
        getMove(final ChessBoardState state,
                final int file1,
                final int rank1, 
                final int file2, 
                final int rank2) {
            
        final ChessBoardState move = new ChessBoardState(state);
            
        move.set(file2, 
                 rank2, 
                 move.get(file1,
                          rank1));
        
        move.clear(file1,
                   rank1);
        
        return move;
    }
}
