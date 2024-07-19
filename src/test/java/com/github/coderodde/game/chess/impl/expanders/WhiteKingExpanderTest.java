package com.github.coderodde.game.chess.impl.expanders;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.KING;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.QUEEN;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public final class WhiteKingExpanderTest {
    
    private static final ChessBoardState state = new ChessBoardState();
    
    private static final AbstractChessBoardStateExpander EXPANDER = 
            new WhiteKingExpander();
    
    private static final AbstractChessBoardStateExpander DUMMY_EXPANDER = 
            new TestDummyExpander();
    
    private static final Piece blackQueen = new Piece(BLACK, QUEEN);
    private static final Piece blackPawn = new Piece(WHITE, 
                                                     PAWN, 
                                                     DUMMY_EXPANDER);
    private static final Piece whiteKing = new Piece(WHITE, KING, EXPANDER);
    private static final Piece whitePawn = new Piece(WHITE,
                                                     PAWN, 
                                                     DUMMY_EXPANDER);
    @Before
    public void before() {
        state.clear();
    }
    
    @Test
    public void expandAll() {
        final ChessBoardState state = new ChessBoardState();
        state.clear();
    
        state.set(6, 6, new Piece(WHITE, KING, EXPANDER));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        final Set<ChessBoardState> filter = new HashSet<>();
        
        assertEquals(8, children.size());
        
        ChessBoardState move;
        
        assertTrue(children.contains(move = getMove(5, 5, state)));
        filter.add(move);
        
        assertTrue(children.contains(move = getMove(6, 5, state)));
        filter.add(move);
        
        assertTrue(children.contains(move = getMove(7, 5, state)));
        filter.add(move);
        
        assertTrue(children.contains(move = getMove(5, 7, state)));
        filter.add(move);
        
        assertTrue(children.contains(move = getMove(6, 7, state)));
        filter.add(move);
        
        assertTrue(children.contains(move = getMove(7, 7, state)));
        filter.add(move);
        
        assertTrue(children.contains(move = getMove(5, 6, state)));
        filter.add(move);
        
        assertTrue(children.contains(move = getMove(7, 6, state)));
        filter.add(move);
        
        assertEquals(8, filter.size());
    }
    
    @Test
    public void smallExpand() {
        final ChessBoardState state = new ChessBoardState();
        state.clear();
    
        state.set(7, 7, new Piece(WHITE, KING, EXPANDER));
        state.set(6, 6, new Piece(WHITE, PAWN, DUMMY_EXPANDER));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(2, children.size());
        
        ChessBoardState move = new ChessBoardState(state);
        move.set(7, 6, new Piece(WHITE, KING));
        move.clear(7, 7);
        
        assertTrue(children.contains(move));
        
        move = new ChessBoardState(state);
        move.set(6, 7, new Piece(WHITE, KING));
        move.clear(7, 7);
        
        assertTrue(children.contains(move));
    }
    
    @Test
    public void canMoveNorthWest() {
        state.set(2, 2, new Piece(WHITE, KING, EXPANDER));
        state.set(1, 5, blackQueen);
            
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(4, children.size());
        
        assertChildrenContains(children,
                               state, 
                               2, 
                               2, 
                               2, 
                               1, 
                               whiteKing);
        
        assertChildrenContains(children,
                               state, 
                               2, 
                               2, 
                               2, 
                               3, 
                               whiteKing);
        
        assertChildrenContains(children,
                               state, 
                               2, 
                               2, 
                               3, 
                               1, 
                               whiteKing);
        
        assertChildrenContains(children,
                               state, 
                               2, 
                               2, 
                               3, 
                               2, 
                               whiteKing);
    }
    
    @Test
    public void rightUpwardsNotSafe() {
        state.set(5, 1, whiteKing);
        // Try attack (6, 2)
        state.set(2, 5, blackQueen);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(6, children.size());
    }
    
    @Test
    public void file0rank0() {
        state.set(0, 0, new Piece(WHITE, KING, EXPANDER));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(3, children.size());
        
        ChessBoardState move = new ChessBoardState();
        move.clear();
        
        move.set(0, 1, new Piece(WHITE, KING));
        
        assertTrue(children.contains(move));
        
        move.clear();
        
        move.set(1, 0, new Piece(WHITE, KING));
        
        assertTrue(children.contains(move));
        
        move.clear();
        
        move.set(1, 1, new Piece(WHITE, KING));
        
        assertTrue(children.contains(move));
    }
    
    @Test
    public void cannotMoveInAnyDiriection() {
        state.set(2, 5, new Piece(BLACK, KING, EXPANDER));
        state.set(1, 5, new Piece(BLACK, PAWN, DUMMY_EXPANDER));
        state.set(3, 5, new Piece(BLACK, PAWN, DUMMY_EXPANDER));
        state.set(1, 6, new Piece(BLACK, PAWN, DUMMY_EXPANDER));
        state.set(2, 6, new Piece(BLACK, PAWN, DUMMY_EXPANDER));
        state.set(3, 6, new Piece(BLACK, PAWN, DUMMY_EXPANDER));
        state.set(1, 4, new Piece(BLACK, PAWN, DUMMY_EXPANDER));
        state.set(2, 4, new Piece(BLACK, PAWN, DUMMY_EXPANDER));
        state.set(3, 4, new Piece(BLACK, PAWN, DUMMY_EXPANDER));
        
        assertTrue(state.expand(PlayerTurn.WHITE).isEmpty());
    }
    
    @Test
    public void cannotMoveRightDownwards() {
        state.set(2, 5, new Piece(WHITE, KING, EXPANDER));
        state.set(1, 5, new Piece(WHITE, PAWN, DUMMY_EXPANDER));
        state.set(3, 5, new Piece(WHITE, PAWN, DUMMY_EXPANDER));
        state.set(1, 6, new Piece(WHITE, PAWN, DUMMY_EXPANDER));
        state.set(2, 6, new Piece(WHITE, PAWN, DUMMY_EXPANDER));
        state.set(3, 6, new Piece(BLACK, PAWN, DUMMY_EXPANDER));
        state.set(1, 4, new Piece(WHITE, PAWN, DUMMY_EXPANDER));
        state.set(2, 4, new Piece(WHITE, PAWN, DUMMY_EXPANDER));
        state.set(3, 4, new Piece(WHITE, PAWN, DUMMY_EXPANDER));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(1, children.size());
        
        ChessBoardState move = new ChessBoardState(state);
       
        move.clear(2, 5);
        move.set(3, 6, new Piece(WHITE, KING));
        
        assertTrue(children.contains(move));
    }
    
    @Test
    public void file1rank0() {
        final Piece p = new Piece(WHITE, PAWN, DUMMY_EXPANDER);
        
        state.set(1, 0, new Piece(WHITE, KING, EXPANDER));
        state.set(0, 0, p);
        state.set(2, 0, p);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(3, children.size());
        
        assertTrue(children.contains(move(state, 1, 0, 0, 1)));
        assertTrue(children.contains(move(state, 1, 0, 1, 1)));
        assertTrue(children.contains(move(state, 1, 0, 2, 1)));
    }
    
    @Test
    public void file6rank7() {
        final Piece p = new Piece(WHITE, PAWN, DUMMY_EXPANDER);
        
        state.set(6, 7, new Piece(WHITE , KING, EXPANDER));
        state.set(5, 7, p);
        state.set(7, 7, p);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(3, children.size());
        
        assertTrue(children.contains(move(state, 6, 7, 5, 6)));
        assertTrue(children.contains(move(state, 6, 7, 6, 6)));
        assertTrue(children.contains(move(state, 6, 7, 7, 6)));
    }
    
    @Test
    public void northIsUnderAttack() {
        state.set(6, 3, whiteKing);
        state.set(5, 2, blackPawn);
        state.set(5, 3, blackPawn);
        state.set(5, 4, blackPawn);
        
        state.set(7, 2, blackPawn);
        state.set(6, 4, blackPawn);
        state.set(7, 3, blackPawn);
        
        state.set(6, 0, blackQueen);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(1, children.size());
        assertChildrenContains(children,
                               state, 
                               6, 
                               3, 
                               7,
                               4, 
                               whiteKing);
    }
    
    @Test
    public void northEastIsUnderAttack() {
        state.set(6, 3, whiteKing);
        state.set(5, 2, blackPawn);
        state.set(5, 3, blackPawn);
        state.set(5, 4, blackPawn);
        
        state.set(6, 4, blackPawn);
        state.set(7, 3, blackPawn);
        state.set(6, 2, blackPawn);
        
        state.set(7, 0, blackQueen);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        
        assertEquals(1, children.size());
        
        assertChildrenContains(children,
                               state,
                               6, 
                               3, 
                               7,
                               4,
                               whiteKing);
    }
    
    @Test
    public void cannotMoveToRightDownwards() {
        state.set(6, 6, whiteKing);
        
        state.set(7, 7, whitePawn);
        state.set(7, 6, whitePawn);
        state.set(7, 5, whitePawn);
        state.set(6, 5, whitePawn);
        state.set(6, 7, whitePawn);
        state.set(5, 5, whitePawn);
        state.set(5, 6, whitePawn);
        state.set(5, 7, whitePawn);
        
        assertTrue(state.expand(PlayerTurn.WHITE).isEmpty());
    }
    
    private static ChessBoardState move(final ChessBoardState state,
                                        final int sourceFile,
                                        final int sourceRank,
                                        final int targetFile,
                                        final int targetRank) {
        
        final ChessBoardState move = new ChessBoardState(state);
    
        move.set(targetFile, 
                 targetRank, 
                 state.get(sourceFile,
                           sourceRank));
        
        move.clear(sourceFile,
                   sourceRank);
        
        return move;
    }
    
    private static ChessBoardState getMove(final int file,
                                           final int rank, 
                                           final ChessBoardState move) {
        
        final ChessBoardState child = new ChessBoardState(move);
        child.clear(6, 6);
        child.set(file, rank, new Piece(WHITE, KING));
        
        return child;
    }
    
    private static ChessBoardState getMove2(final int file,
                                            final int rank, 
                                            final ChessBoardState move) {
        
        final ChessBoardState child = new ChessBoardState(move);
        child.clear(6, 6);
        child.set(file, rank, new Piece(BLACK, KING));
        
        return child;
    }
    
    private void assertChildrenContains(final List<ChessBoardState> children,
                                        final ChessBoardState state,
                                        final int sourceFile, 
                                        final int sourceRank,
                                        final int targetFile, 
                                        final int targetRank,
                                        final Piece piece) {
        
        final ChessBoardState move = new ChessBoardState(state);
        
        move.clear(sourceFile,
                   sourceRank);
        
        move.set(targetFile, 
                 targetRank,
                 piece);
        
        assertTrue(children.contains(move));
    }
}
