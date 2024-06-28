package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import com.github.coderodde.game.chess.PieceType;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.KNIGHT;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.QUEEN;
import static com.github.coderodde.game.chess.PieceType.ROOK;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class WhitePawnExpanderTest {
    
    private ChessBoardState state;
    private final AbstractChessBoardStateExpander expander = 
            new WhitePawnExpander();
    
    @Before
    public void before() {
        state = new ChessBoardState();
        state.clear();
    }
    
    @Test
    public void moveWhitePawnInitialDoubleMove() {
        state.set(new Piece(WHITE, PAWN, 0, 6, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(2, children.size());
        
        final ChessBoardState child1 = children.get(0);
        final ChessBoardState child2 = children.get(1);
        
        assertTrue(children.contains(child1));
        assertTrue(children.contains(child2));
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        
        move1.set(0, 5, new Piece(WHITE, PAWN, 0, 5, expander));
        move2.set(0, 4, new Piece(WHITE, PAWN, 0, 4, expander));
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
    }
    
    @Test
    public void whitePawnCannotMoveForward() {
        state.set(4, 5, new Piece(WHITE, PAWN, 4, 5, expander));
        state.set(4, 4, new Piece(BLACK, ROOK, 4, 4, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertTrue(children.isEmpty());
    }
    
    @Test
    public void whitePawnCanEatBothDirectionsAndMoveForward() {
        state.set(5, 4, new Piece(WHITE, PAWN, expander));
        state.set(4, 3, new Piece(BLACK, KNIGHT, expander));
        state.set(6, 3, new Piece(BLACK, ROOK, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(3, children.size());
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        final ChessBoardState move3 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        move3.clear();
        
        // Capture to the left:
        move1.set(4, 3, new Piece(WHITE, PAWN, expander));
        move1.set(6, 3, new Piece(BLACK, ROOK, expander));
        
        // Move forward:
        move2.set(5, 3, new Piece(WHITE, PAWN, expander));
        move2.set(4, 3, new Piece(BLACK, KNIGHT, expander));
        move2.set(6, 3, new Piece(BLACK, ROOK, expander));
        
        // Caupture to the right:
        move3.set(6, 3, new Piece(WHITE, PAWN, expander));
        move3.set(4, 3, new Piece(BLACK, KNIGHT, expander));
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
        assertTrue(children.contains(move3));
    }
    
    @Test
    public void whitePawnCannotMakeFirstDoubleMoveDueToObstruction() {
        state.set(6, 6, new Piece(WHITE, PAWN, expander));
        state.set(6, 5, new Piece(WHITE, BISHOP, expander));
        
        assertTrue(state.expand(PlayerTurn.WHITE).isEmpty());
        
        state.clear();
        state.set(4, 6, new Piece(WHITE, PAWN));
        state.set(4, 5, new Piece(BLACK, ROOK));
        
        assertTrue(state.expand(PlayerTurn.WHITE).isEmpty());
    }
    
    @Test
    public void whitePawnPromotion() {
        state.set(3, 1, new Piece(WHITE, PAWN));
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(4, children.size());
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        final ChessBoardState move3 = new ChessBoardState();
        final ChessBoardState move4 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        move3.clear();
        move4.clear();
        
        move1.set(3, 0, new Piece(WHITE, QUEEN));
        move2.set(3, 0, new Piece(WHITE, ROOK));
        move3.set(3, 0, new Piece(WHITE, KNIGHT));
        move4.set(3, 0, new Piece(WHITE, BISHOP));
       
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move1));
        
        final Set<ChessBoardState> stateSet = new HashSet<>();
        
        stateSet.addAll(Arrays.asList(move1, move2, move3, move4));
        
        assertEquals(4, stateSet.size());
    }
    
    @Test
    public void whitePawnPromotionCaptureBoth() {
        state.set(5, 1, new Piece(WHITE, PAWN));
        state.set(4, 0, new Piece(BLACK, BISHOP));
        state.set(6, 0, new Piece(BLACK, PAWN));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(12, children.size());
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        final ChessBoardState move3 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        move3.clear();
        
        // Promote forward:
        move1.set(4, 0, new Piece(BLACK, BISHOP));
        move1.set(6, 0, new Piece(BLACK, PAWN));
        
        for (final PieceType pieceType :
                AbstractChessBoardStateExpander.PROMOTION_PIECE_TYPES) {
            
            move1.set(5, 0, new Piece(WHITE, pieceType));
            assertTrue(children.contains(move1));
        }
        
        // Promote left:
        move2.set(6, 0, new Piece(BLACK, PAWN));
        
        for (final PieceType pieceType :
                AbstractChessBoardStateExpander.PROMOTION_PIECE_TYPES) {
            
            move2.set(4, 0, new Piece(WHITE, pieceType));
            assertTrue(children.contains(move2));
        }
        
        // Promote right:
        move3.set(4, 0, new Piece(BLACK, BISHOP));
        
        for (final PieceType pieceType :
                AbstractChessBoardStateExpander.PROMOTION_PIECE_TYPES) {
            
            move3.set(6, 0, new Piece(WHITE, pieceType));
            assertTrue(children.contains(move3));
        }
    }
    
    @Test
    public void whitePawnEnPassantToLeft() {
        state.set(0, 3, new Piece(BLACK, PAWN));
        state.set(1, 2, new Piece(BLACK, ROOK));
        state.set(1, 3, new Piece(WHITE, PAWN));
        
        state.markBlackPawnInitialDoubleMove(0);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(1, children.size());
        
        final ChessBoardState move = new ChessBoardState();
        
        move.clear();
        move.set(1, 2, new Piece(BLACK, ROOK));
        move.set(0, 2, new Piece(WHITE, PAWN));
        
        assertTrue(children.contains(move));
    }
    
    @Test
    public void whitePawnEnPassantToRight() {
        state.set(7, 3, new Piece(BLACK, PAWN));
        state.set(6, 2, new Piece(BLACK, ROOK));
        state.set(6, 3, new Piece(WHITE, PAWN));
        
        state.markBlackPawnInitialDoubleMove(7);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(1, children.size());
        
        final ChessBoardState move = new ChessBoardState();
        
        move.clear();
        move.set(6, 2, new Piece(BLACK, ROOK));
        move.set(7, 2, new Piece(WHITE, PAWN));
        
        assertTrue(children.contains(move));
    }
}
