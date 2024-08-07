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
    
    private static final String[] WHITE_CHECKMATE_DESCRIPTION = new String[] {
       "r#.#.bnr", 
       "pp#.p.pp", 
       ".#.k.#.#", 
       "#.#.#.#.", 
       "P#.#.#PP", 
       "#.#n#.#.", 
       ".q.#.#.#", 
       "#K#.#.#.",       
    };
    
    private static final String[] BLACK_CHECKMATE_DESCRIPTION = new String[] {
        ".n.k.#B#", 
        "#rp.#.p.", 
        ".pq#.#.#", 
        "p.#p#.#.", 
        "P#.P.#.#", 
        "#.N.PP#.", 
        ".PPB.#.R", 
        "RQ#K#.N.", 
    };
    
    @Before
    public void before() {
        state.clear();
    }
    
//    @Test
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
                                  1,
                                  PlayerTurn.BLACK);
            
            System.out.println("Next state:\n" + nextState);
        
            System.out.println(
                    "Score of the next state: " +
                            heuristicFunction.evaluate(nextState, 2));
            
        } catch (final ThreeFoldRepetionRuleDrawException ex) {
            fail("Should not get here.");
        }
        
        final ChessBoardState expectedState = new ChessBoardState(naughtyState);
        
        expectedState.move(4, 1, 3, 2);
        
        System.out.println("Expected state:");
        System.out.println(expectedState);
        
        System.out.println(
                "Score of the expected state: " + 
                        heuristicFunction.evaluate(expectedState, 2));
    }
    
    @Test
    public void checkMateWhite() {
        final ChessBoardState state =
                new ChessBoardState(WHITE_CHECKMATE_DESCRIPTION);
        
        state.setWhiteKingFile(1);
        state.setWhiteKingRank(7);
        
        System.out.println("checkMateWhite(): Check mate board:");
        System.out.println(state);
        
        boolean checkMate = state.isCheckMate(PlayerTurn.WHITE);
        
        assertTrue(checkMate);
    }
    
    @Test
    public void checkMateBlack() {
        final ChessBoardState state =
                new ChessBoardState(BLACK_CHECKMATE_DESCRIPTION);
        
        state.setWhiteKingFile(3);
        state.setWhiteKingRank(0);
        
        System.out.println("checkMateBlack(): Check mate board:");
        System.out.println(state);
        
        boolean checkMate = state.isCheckMate(PlayerTurn.BLACK);
        
        assertFalse(checkMate);
    }
}
