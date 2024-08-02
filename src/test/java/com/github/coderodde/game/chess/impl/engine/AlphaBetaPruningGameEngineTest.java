package com.github.coderodde.game.chess.impl.engine;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.AbstractGameEngine;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import com.github.coderodde.game.chess.PlayerTurn;
import com.github.coderodde.game.chess.ThreeFoldRepetionRuleDrawException;
import com.github.coderodde.game.chess.impl.DefaultHeuristicFunction;
import com.github.coderodde.game.chess.impl.expanders.WhitePawnExpander;
import org.junit.Test;

public final class AlphaBetaPruningGameEngineTest {
    
    private static final AbstractChessBoardStateExpander WHITE_PAWN_EXPANDER = 
            new WhitePawnExpander();
    
    private final Piece whitePawn = new Piece(WHITE, 
                                              PAWN, 
                                              WHITE_PAWN_EXPANDER);
    
    private final AbstractGameEngine engine = 
            new AlphaBetaPruningGameEngine(new DefaultHeuristicFunction());
    
//    @Test
    public void debug1() {
        final ChessBoardState root = new ChessBoardState();
        
        root.set(3, 4, root.get(3, 6));
        root.clear(3, 6);
        
        System.out.println("root:\n" + root);
        
        try {
            final ChessBoardState next = 
                    engine.search(
                            root, 
                            2, 
                            PlayerTurn.BLACK);
            
            System.out.println("Next:\n");
            System.out.println(next);
            
        } catch (ThreeFoldRepetionRuleDrawException ex) {
            ex.printStackTrace();
        }
        
    }
    
//    @Test
    public void debugNoStateTransition()
            throws ThreeFoldRepetionRuleDrawException {
            
        final ChessBoardState state = new ChessBoardState();
        
        // Set:
        state.clear(3, 6);
        state.set(3, 4, whitePawn);
        
        System.out.println("Start state:");
        System.out.println(state);
        
        final ChessBoardState nextState = 
            engine.search(state,
                          2,
                          PlayerTurn.BLACK);
            
        System.out.println("Next state:");
        System.out.println(nextState);
    }
    
//    @Test
    public void debugNoStateTransition2()
            throws ThreeFoldRepetionRuleDrawException {
            
        final ChessBoardState state = new ChessBoardState();
        
        // Set:
        state.clear(3, 6);
        state.set(3, 4, whitePawn);
        
        System.out.println("Start state:");
        System.out.println(state);
        
        final ChessBoardState nextState = 
            engine.search(state,
                          3,
                          PlayerTurn.BLACK);
            
        System.out.println("Next state:");
        System.out.println(nextState);
    }
    
//    @Test
    public void debugReturnNullOnDepth4() {
        final ChessBoardState state = new ChessBoardState();
        state.set(3, 4, state.get(3, 6));
        state.clear(3, 6);
        
        try {
            final ChessBoardState next = engine.search(state, 4, PlayerTurn.BLACK);
            
            System.out.println("next:\n" + next);
        } catch (ThreeFoldRepetionRuleDrawException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("yewfdsa");
        System.out.println(state);
    }
    
    @Test
    public void debug10() {
        final ChessBoardState root = new ChessBoardState();
        
        // Move white queen:
        root.move(3, 7, 1, 5);
        
        // Move white pawn:
        root.move(2, 6, 2, 5);
        
        // Move white king:
        root.move(4, 7, 3, 7);
        
        System.out.println("HELLO");
        System.out.println(root);
        
        try {
            final ChessBoardState next = 
                    engine.search(
                            root,
                            4,
                            PlayerTurn.BLACK);
            
            System.out.println("NEXT:");
            System.out.println(next);
        } catch (ThreeFoldRepetionRuleDrawException ex) {
            ex.printStackTrace();
        }
    }
}

