package com.github.coderodde.game.chess;

import static com.github.coderodde.game.chess.ChessBoardState.BLACK_BISHOP;
import static com.github.coderodde.game.chess.ChessBoardState.BLACK_KNIGHT;
import static com.github.coderodde.game.chess.ChessBoardState.BLACK_PAWN;
import static com.github.coderodde.game.chess.ChessBoardState.BLACK_ROOK;
import static com.github.coderodde.game.chess.ChessBoardState.WHITE_BISHOP;
import static com.github.coderodde.game.chess.ChessBoardState.WHITE_KING;
import static com.github.coderodde.game.chess.ChessBoardState.WHITE_PAWN;
import static com.github.coderodde.game.chess.ChessBoardState.WHITE_QUEEN;
import static com.github.coderodde.game.chess.ChessBoardState.WHITE_ROOK;
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
        state.set(0, 6, WHITE_PAWN);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(2, children.size());
        
        final ChessBoardState child1 = children.get(0);
        final ChessBoardState child2 = children.get(1);
        
        assertTrue(children.contains(child1));
        assertTrue(children.contains(child2));
        
        final Set<Integer> indexSet = new HashSet<>();
        
        indexSet.add(children.indexOf(child1));
        indexSet.add(children.indexOf(child2));
        
        assertEquals(2, indexSet.size());
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        
        move1.set(0, 5, WHITE_PAWN);
        move2.set(0, 4, WHITE_PAWN);
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
    }
    
    @Test
    public void whitePawnCannotMoveForward() {
        state.set(4, 5, WHITE_PAWN);
        state.set(4, 4, BLACK_ROOK);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertTrue(children.isEmpty());
    }
    
    @Test
    public void whitePawnCanEatBothDirectionsAndMoveForward() {
        state.set(5, 4, WHITE_PAWN);
        state.set(4, 3, BLACK_KNIGHT);
        state.set(6, 3, BLACK_ROOK);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(3, children.size());
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        final ChessBoardState move3 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        move3.clear();
        
        // Eat to the left:
        move1.set(4, 3, WHITE_PAWN);
        move1.set(6, 3, BLACK_ROOK);
        
        // Move forward:
        move2.set(5, 3, WHITE_PAWN);
        move2.set(4, 3, BLACK_KNIGHT);
        move2.set(6, 3, BLACK_ROOK);
        
        // Eat to the right:
        move3.set(6, 3, WHITE_PAWN);
        move3.set(4, 3, BLACK_KNIGHT);
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
        assertTrue(children.contains(move3));
    }
    
    @Test
    public void whitePawnCannotMakeFirstDoubleMoveDueToObstruction() {
        state.set(6, 6, WHITE_PAWN);
        state.set(6, 5, WHITE_BISHOP);
        
        assertTrue(state.expand(PlayerTurn.WHITE).isEmpty());
        
        state.clear();
        state.set(4, 6, WHITE_PAWN);
        state.set(4, 5, BLACK_ROOK);
        
        assertTrue(state.expand(PlayerTurn.WHITE).isEmpty());
    }
    
    @Test
    public void whitePawnPromotion() {
        state.set(3, 1, WHITE_PAWN);
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(1, children.size());
        
        final ChessBoardState move = new ChessBoardState();
        
        move.clear();
        move.set(3, 0, WHITE_QUEEN);
        
        assertEquals(move, children.get(0));
    }
    
    @Test
    public void whitePawnPromotionCaptureBoth() {
        state.set(5, 1, WHITE_PAWN);
        state.set(4, 0, BLACK_BISHOP);
        state.set(6, 0, BLACK_PAWN);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(3, children.size());
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        final ChessBoardState move3 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        move3.clear();
        
        // Queen forward:
        move1.set(4, 0, BLACK_BISHOP);
        move1.set(5, 0, WHITE_QUEEN);
        move1.set(6, 0, BLACK_PAWN);
        
        // Queen left:
        move2.set(4, 0, WHITE_QUEEN);
        move2.set(6, 0, BLACK_PAWN);
        
        // Queen right:
        move3.set(6, 0, WHITE_QUEEN);
        move3.set(4, 0, BLACK_BISHOP);
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
        assertTrue(children.contains(move3));
        
        final Set<Integer> indexSet = new HashSet<>();
        
        indexSet.add(children.indexOf(move1));
        indexSet.add(children.indexOf(move2));
        indexSet.add(children.indexOf(move3));
        
        assertEquals(3, indexSet.size());
    }
    
    @Test
    public void whitePawnEnPassantToLeft() {
        state.set(0, 3, BLACK_PAWN);
        state.set(1, 2, BLACK_ROOK);
        state.set(1, 3, WHITE_PAWN);
        state.markBlackPawnInitialDoubleMove(0);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(1, children.size());
        
        final ChessBoardState move = new ChessBoardState();
        
        move.clear();
        move.set(1, 2, BLACK_ROOK);
        move.set(0, 2, WHITE_PAWN);
        
        assertTrue(children.contains(move));
    }
    
    @Test
    public void whitePawnEnPassantToRight() {
        state.set(7, 3, BLACK_PAWN);
        state.set(6, 2, BLACK_ROOK);
        state.set(6, 3, WHITE_PAWN);
        state.markBlackPawnInitialDoubleMove(7);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(1, children.size());
        
        final ChessBoardState move = new ChessBoardState();
        
        move.clear();
        move.set(6, 2, BLACK_ROOK);
        move.set(7, 2, WHITE_PAWN);
        
        assertTrue(children.contains(move));
    }
    
    @Test
    public void moveBlackPawnInitialDoubleMove() {
        state.set(3, 1, BLACK_PAWN);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(2, children.size());
        
        final ChessBoardState child1 = children.get(0);
        final ChessBoardState child2 = children.get(1);
        
        assertTrue(children.contains(child1));
        assertTrue(children.contains(child2));
        
        final Set<Integer> indexSet = new HashSet<>();
        
        indexSet.add(children.indexOf(child1));
        indexSet.add(children.indexOf(child2));
        
        assertEquals(2, indexSet.size());
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        
        move1.set(3, 2, BLACK_PAWN);
        move2.set(3, 3, BLACK_PAWN);
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
    }
    
    @Test
    public void blackPawnCannotMoveForward() {
        state.set(4, 2, BLACK_PAWN);
        state.set(4, 3, WHITE_ROOK);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertTrue(children.isEmpty());
    }
    
    @Test
    public void blackPawnCanEatBothDirectionsAndMoveForward() {
        state.set(5, 3, BLACK_PAWN);
        state.set(4, 4, WHITE_ROOK);
        state.set(6, 4, WHITE_KING);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(3, children.size());
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        final ChessBoardState move3 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        move3.clear();
        
        // Capture to the left:
        move1.set(4, 4, BLACK_PAWN);
        move1.set(6, 4, WHITE_KING);
        
        // Move forward:
        move2.set(4, 4, WHITE_ROOK);
        move2.set(5, 4, BLACK_PAWN);
        move2.set(6, 4, WHITE_KING);
        
        // Capture to the right:
        move3.set(6, 4, BLACK_PAWN);
        move3.set(4, 4, WHITE_ROOK);
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
        assertTrue(children.contains(move3));
    }
}
