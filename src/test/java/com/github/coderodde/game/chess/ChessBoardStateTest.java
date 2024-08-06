package com.github.coderodde.game.chess;

import com.github.coderodde.game.chess.impl.ShannonHeuristicFunction;
import com.github.coderodde.game.chess.impl.engine.AlphaBetaPruningGameEngine;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class ChessBoardStateTest {
    
    private final ChessBoardState state = new ChessBoardState();
    private final ShannonHeuristicFunction heuristicFunction = 
            new ShannonHeuristicFunction();
    
    private final AbstractGameEngine engine = 
            new AlphaBetaPruningGameEngine(heuristicFunction);
    
    private static final String[] NAUGHTY_DESCRIPTION = new String[] {
        "r#bqkb.r",
        "ppppp.p.",
        ".#.N.n.#",
        "#.#.#Q#p",
        "P#.#.#.#",
        "#.#.#.#.",
        ".P.PPPPP",
        "R.B.KBNR",
    };
    
    @Before
    public void before() {
        state.clear();
    }
    
    @Test
    public void debugKingDisappearing() {
        final ChessBoardState naughtyState = 
                new ChessBoardState(NAUGHTY_DESCRIPTION);
        
        System.out.println(
                "Score of the naughty state: " +
                        heuristicFunction.evaluate(naughtyState, 2));
        
        // Can protect the black king from a white knight:
        assertFalse(naughtyState.isCheckMate(PlayerTurn.BLACK));
        
        System.out.println("Naughty state:\n" + naughtyState);
        
        try {
            // Must eat the white knight with a black pawn:
            final ChessBoardState nextState = 
                    engine.search(naughtyState, 
                                  2,
                                  PlayerTurn.BLACK);
            
            System.out.println("Next state:\n" + nextState);
        
            System.out.println(
                    "Score of the next state: " +
                            heuristicFunction.evaluate(nextState, 2));
            
        } catch (final ThreeFoldRepetionRuleDrawException ex) {
            fail("Should not get here.");
        }
    }
}
