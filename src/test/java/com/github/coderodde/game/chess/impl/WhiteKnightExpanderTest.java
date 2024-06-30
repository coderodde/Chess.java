package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.KNIGHT;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class WhiteKnightExpanderTest {
    
    private final AbstractChessBoardStateExpander expander = 
            new WhiteKnightExpander();
    
    @Test
    public void expand8() {
        ChessBoardState state = new ChessBoardState();
        state.clear();
        state.set(4, 4, new Piece(WHITE, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
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
        move1.set(3, 2, new Piece(WHITE, KNIGHT));
        
        // North right:
        move2.set(5, 2, new Piece(WHITE, KNIGHT));
        
        // South left:
        move3.set(3, 6, new Piece(WHITE, KNIGHT));
        
        // South right:
        move4.set(5, 6, new Piece(WHITE, KNIGHT));
        
        // West up:
        move5.set(2, 3, new Piece(WHITE, KNIGHT));
        
        // West down:
        move6.set(2, 5, new Piece(WHITE, KNIGHT));
        
        // East up:
        move7.set(6, 3, new Piece(WHITE, KNIGHT));
        
        // East down:
        move8.set(6, 5, new Piece(WHITE, KNIGHT));
        
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
        
        state.set(1, 1, new Piece(WHITE, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(4, children.size());
    }
    
    @Test
    public void topRightCorner() {
        final ChessBoardState state = new ChessBoardState();
        state.clear();
        
        state.set(6, 1, new Piece(WHITE, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(4, children.size());
    }
    
    @Test
    public void bottomLeftCorner() {
        final ChessBoardState state = new ChessBoardState();
        state.clear();
        
        state.set(1, 6, new Piece(WHITE, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(4, children.size());
    }
    
    @Test
    public void bottomRightCorner() {
        final ChessBoardState state = new ChessBoardState();
        state.clear();
        
        state.set(6, 6, new Piece(WHITE, KNIGHT, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(4, children.size());
    }
}
