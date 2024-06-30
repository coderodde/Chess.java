package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.ROOK;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public final class WhiteRookExpanderTest {
    
    private final AbstractChessBoardStateExpander expander = 
            new WhiteRookExpander();
    
    @Test
    public void expand() {
        final ChessBoardState state = new ChessBoardState();
        
        state.clear();
        
        state.set(3, 3, new Piece(WHITE, ROOK, expander));
        state.set(3, 2, new Piece(BLACK, PAWN));
        state.set(3, 6, new Piece(BLACK, PAWN));
        state.set(1, 3, new Piece(BLACK, BISHOP));
        state.set(6, 3, new Piece(BLACK, BISHOP));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(9, children.size());
    }
    
    @Test
    public void expandAtCorner() {
        final ChessBoardState state = new ChessBoardState();
        
        state.clear();
        
        state.set(6, 5, new Piece(WHITE, ROOK, expander));
        state.set(6, 4, new Piece(BLACK, PAWN));
        state.set(5, 5, new Piece(BLACK, BISHOP));
        
        ChessBoardState move1 = new ChessBoardState();
        ChessBoardState move2 = new ChessBoardState();
        ChessBoardState move3 = new ChessBoardState();
        ChessBoardState move4 = new ChessBoardState();
        ChessBoardState move5 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        move3.clear();
        move4.clear();
        move5.clear();
        
        // North:
        move1.set(6, 4, new Piece(WHITE, ROOK));
        move1.set(5, 5, new Piece(BLACK, BISHOP));
        
        // West:
        move2.set(5, 5, new Piece(WHITE, ROOK));
        move2.set(6, 4, new Piece(BLACK, PAWN));
        
        // East:
        move3.set(7, 5, new Piece(WHITE, ROOK));
        move3.set(6, 4, new Piece(BLACK, PAWN));
        move3.set(5, 5, new Piece(BLACK, BISHOP));
        
        // South first time:
        move4.set(6, 6, new Piece(WHITE, ROOK));
        move4.set(6, 4, new Piece(BLACK, PAWN));
        move4.set(5, 5, new Piece(BLACK, BISHOP));
        
        // South second time:
        move5.set(6, 7, new Piece(WHITE, ROOK));
        move5.set(6, 4, new Piece(BLACK, PAWN));
        move5.set(5, 5, new Piece(BLACK, BISHOP));
        
        System.out.println(state);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(5, children.size());
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
        
//        System.out.println("yes: ");
//        System.out.println(move3);
        
        assertTrue(children.contains(move3));
        assertTrue(children.contains(move4));
        assertTrue(children.contains(move5));
        
        final Set<ChessBoardState> filter = new HashSet<>();
        
        filter.add(move1);
        filter.add(move2);
        filter.add(move3);
        filter.add(move4);
        filter.add(move5);
        
        assertEquals(5, filter.size());
    }
}
