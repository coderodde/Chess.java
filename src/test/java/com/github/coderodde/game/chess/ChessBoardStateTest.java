package com.github.coderodde.game.chess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class ChessBoardStateTest {
    
    private ChessBoardState state;
    
    @Before
    public void before() {
        state = new ChessBoardState();
        state.clear();
    }
    
    @Test
    public void moveWhitePawnInitialDoubleMove() {
        state.set(0, 6, ChessBoardState.WHITE_PAWN);
        
        final List<ChessBoardState> children = state.expand();
        
        assertEquals(2, children.size());
        
        final ChessBoardState child1 = children.get(0);
        final ChessBoardState child2 = children.get(1);
        
        assertTrue(children.contains(child1));
        assertTrue(children.contains(child2));
        
        final Set<Integer> indexSet = new HashSet<>();
        
        indexSet.add(children.indexOf(child1));
        indexSet.add(children.indexOf(child2));
        
        assertEquals(2, indexSet.size());
    }
}
