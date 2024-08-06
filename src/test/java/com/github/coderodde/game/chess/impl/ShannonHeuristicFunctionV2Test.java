package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.ROOK;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public final class ShannonHeuristicFunctionV2Test {
    
    private static final ShannonHeuristicFunctionV2 heuristicFunction = 
            new ShannonHeuristicFunctionV2();
    
    private final ChessBoardState state = new ChessBoardState();
    
    private static final double EPSILON = 0.00001;
    private static final Piece blackRook = new Piece(BLACK, ROOK);
    private static final Piece whitePawn = new Piece(WHITE, PAWN);
    private static final Piece blackPawn = new Piece(BLACK, PAWN);
    
    @Before
    public void beforeEach() {
        state.clear();
    }
    
    @Test
    public void mobility1() {
        double mobility = heuristicFunction.mobility(state);
        
        assertEquals(0.0, mobility, EPSILON);
        
        state.set(4, 4, blackRook);
        state.set(2, 6, whitePawn);
        
        mobility = heuristicFunction.mobility(state);
        
        assertEquals(1.2, mobility, EPSILON);
    }
    
    @Test
    public void countBlockedPawnWhite() {
        state.set(3, 5, whitePawn);
        state.set(3, 4, whitePawn);
        state.set(3, 3, blackRook);
        
        state.set(1, 4, whitePawn);
        state.set(1, 3, whitePawn);
        state.set(1, 2, whitePawn);
        state.set(1, 1, whitePawn);
        
        // Noise:
        state.set(5, 4, blackPawn);
        state.set(5, 4, blackPawn);
        
        assertEquals(2, heuristicFunction.countBlockedPawnWhite(state, 3));
        assertEquals(3, heuristicFunction.countBlockedPawnWhite(state, 1));
    }
    
    @Test
    public void countBlockedPawnBlack() {
        state.set(3, 5, blackPawn);
        state.set(3, 4, blackPawn);
        state.set(3, 3, blackRook);
        
        state.set(1, 4, blackPawn);
        state.set(1, 3, blackPawn);
        state.set(1, 2, blackPawn);
        state.set(1, 1, blackPawn);
        
        // Noise:
        state.set(5, 4, whitePawn);
        state.set(5, 4, whitePawn);
        
        assertEquals(2, heuristicFunction.countBlockedPawnBlack(state, 3));
        assertEquals(3, heuristicFunction.countBlockedPawnBlack(state, 1));
    }
    
    @Test
    public void countDoubledPawnsWhiteNoDoublePawns() {
        state.set(2, 2, whitePawn);
        
        assertEquals(0, heuristicFunction.countDoubledPawnsWhite(state, 2));
        assertEquals(0, heuristicFunction.countDoubledPawnsWhite(state, 1));
        assertEquals(0, heuristicFunction.countDoubledPawnsWhite(state, 3));
    }
    
    @Test
    public void countDoubledPawnsBlackNoDoublePawns() {
        state.set(2, 2, blackPawn);
        
        assertEquals(0, heuristicFunction.countDoubledPawnsBlack(state, 2));
        assertEquals(0, heuristicFunction.countDoubledPawnsBlack(state, 1));
        assertEquals(0, heuristicFunction.countDoubledPawnsBlack(state, 3));
    }
    
    @Test
    public void countIsolatedPawnsWhiteFile0() {
        state.set(0, 3, whitePawn);
        
        assertEquals(1, heuristicFunction.countIsolatedPawnsWhite(state, 0));
    }
    
    @Test
    public void countIsolatedPawnsWhiteFile7() {
        state.set(7, 3, whitePawn);
        
        assertEquals(1, heuristicFunction.countIsolatedPawnsWhite(state, 7));
    }
    
    @Test
    public void countIsolatedPawnsWhiteFile0OnEmpty() {
        assertEquals(0, heuristicFunction.countIsolatedPawnsWhite(state, 0));
    }
    
    @Test
    public void countIsolatedPawnsWhiteFile7OnEmpty() {
        assertEquals(0, heuristicFunction.countIsolatedPawnsWhite(state, 7));
    }
}
