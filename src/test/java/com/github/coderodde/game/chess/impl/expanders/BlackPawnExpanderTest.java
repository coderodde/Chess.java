package com.github.coderodde.game.chess.impl.expanders;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
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
import static com.github.coderodde.game.chess.impl.expanders.BlackPawnExpander.EN_PASSANT_SOURCE_RANK;
import static com.github.coderodde.game.chess.impl.expanders.BlackPawnExpander.EN_PASSANT_TARGET_RANK;
import static com.github.coderodde.game.chess.impl.expanders.BlackPawnExpander.INITIAL_BLACK_PAWN_MOVE_1_RANK;
import static com.github.coderodde.game.chess.impl.expanders.BlackPawnExpander.INITIAL_BLACK_PAWN_MOVE_2_RANK;
import static com.github.coderodde.game.chess.impl.expanders.BlackPawnExpander.INITIAL_BLACK_PAWN_RANK;
import static com.github.coderodde.game.chess.impl.expanders.BlackPawnExpander.PROMOTION_SOURCE_RANK;
import static com.github.coderodde.game.chess.impl.expanders.BlackPawnExpander.PROMOTION_TARGET_RANK;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class BlackPawnExpanderTest {
    
    private ChessBoardState state;
    private final AbstractChessBoardStateExpander expander = 
            new BlackPawnExpander();
    
    private final AbstractChessBoardStateExpander dummyExpander = 
            new TestDummyExpander();
    
    @Before
    public void before() {
        state = new ChessBoardState();
        state.clear();
    }
    
    @Test
    public void cannotDoDoubleMoveFirstCellOccupied() {
        state.set(1, 1, new Piece(BLACK, PAWN, expander));
        state.set(1, 2, new Piece(WHITE, PAWN));
        
        assertTrue(state.expand(PlayerTurn.BLACK).isEmpty());
    }
    
    @Test
    public void file0CannotPromoteToLeft() {
        state.set(0, PROMOTION_SOURCE_RANK, new Piece(BLACK, PAWN, expander));
        state.set(0, PROMOTION_TARGET_RANK, new Piece(WHITE, PAWN));
            
        assertTrue(state.expand(PlayerTurn.BLACK).isEmpty());
    }
    
    @Test
    public void doubleMoveFurtherCellOccupied() {
        state.set(5, INITIAL_BLACK_PAWN_RANK, new Piece(BLACK, PAWN, expander));
        state.set(5, INITIAL_BLACK_PAWN_MOVE_2_RANK, new Piece(WHITE, PAWN));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(1, children.size());
        
        ChessBoardState move = new ChessBoardState(state);
        
        move.set(5, 
                 INITIAL_BLACK_PAWN_MOVE_1_RANK,
                 state.get(5, 
                           INITIAL_BLACK_PAWN_RANK));
        
        move.clear(5, INITIAL_BLACK_PAWN_RANK);
        
        assertTrue(children.contains(move));
    }
    
    @Test
    public void enPassantFile0() {
        state.set(0, EN_PASSANT_SOURCE_RANK, new Piece(BLACK, PAWN, expander));
        state.set(0, EN_PASSANT_TARGET_RANK, new Piece(WHITE, PAWN));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertTrue(children.isEmpty());
    }
    
    @Test
    public void enPassantFile7() {
        state.set(7, EN_PASSANT_SOURCE_RANK, new Piece(BLACK, PAWN, expander));
        state.set(7, EN_PASSANT_TARGET_RANK, new Piece(WHITE, PAWN));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertTrue(children.isEmpty());
    }
    
    @Test
    public void promotionFile0CannotCaptureToLeft() {
        state.set(0,
                  PROMOTION_SOURCE_RANK, 
                  new Piece(BLACK, 
                            PAWN, 
                            expander));
         
        state.set(1, 
                  PROMOTION_TARGET_RANK, 
                  new Piece(BLACK, 
                            PAWN,
                            dummyExpander));
        
        state.set(0, 
                  PROMOTION_TARGET_RANK, 
                  new Piece(BLACK, 
                            BISHOP, 
                            dummyExpander));
         
        assertTrue(state.expand(PlayerTurn.WHITE).isEmpty());
    }
    
    @Test
    public void promotionFile7CannotCaptureToRight() {
        state.set(7,
                  PROMOTION_SOURCE_RANK, 
                  new Piece(BLACK, 
                            PAWN, 
                            expander));
         
        state.set(6, 
                  PROMOTION_TARGET_RANK, 
                  new Piece(BLACK, 
                            PAWN,
                            dummyExpander));
        
        state.set(7, 
                  PROMOTION_TARGET_RANK, 
                  new Piece(BLACK, 
                            BISHOP, 
                            dummyExpander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
         
        assertTrue(children.isEmpty());
    }
    
    @Test
    public void promotionFile7CannotCaptureToLeft() {
         state.set(N - 1,
                   PROMOTION_SOURCE_RANK, 
                   new Piece(BLACK, 
                             PAWN, 
                             expander));
         
        state.set(N - 1, 
                  PROMOTION_TARGET_RANK, 
                  new Piece(BLACK, 
                            PAWN,
                            dummyExpander));
        state.set(N - 2, 
                  PROMOTION_TARGET_RANK, 
                  new Piece(BLACK, 
                            BISHOP, 
                            dummyExpander));
         
        assertTrue(state.expand(PlayerTurn.WHITE).isEmpty());
    }
    
    @Test
    public void cannotCaptureToLeft() {
        state.set(0, 4, new Piece(BLACK, PAWN, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(1, children.size());
        assertTrue(children.contains(getMove(state, 0, 4, 0, 5)));
    }
    
    @Test
    public void cannotCaptureToRight() {
        state.set(7, 4, new Piece(BLACK, PAWN, expander));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(1, children.size());
        assertTrue(children.contains(getMove(state, 7, 4, 7, 5)));
    }
     
    @Test
    public void cannotCaptureToRightOnPromotion() {
        state.set(7, 
                  PROMOTION_SOURCE_RANK, 
                  new Piece(BLACK, PAWN, expander));
        
        state.set(7, 
                  PROMOTION_TARGET_RANK, 
                  new Piece(BLACK, PAWN, dummyExpander));
        
        state.set(6, 
                  PROMOTION_TARGET_RANK, 
                  new Piece(BLACK, PAWN, dummyExpander));
        
        assertTrue(state.expand(PlayerTurn.BLACK).isEmpty());
    }
    
    @Test
    public void moveBlackPawnInitialDoubleMove() {
        state.set(0, 
                  INITIAL_BLACK_PAWN_RANK, 
                  new Piece(WHITE, PAWN, expander));
        
//        System.out.println("YEAH:\n" + state);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.WHITE);
        children.forEach(System.out::println);
        assertEquals(2, children.size());
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        
        move1.set(0, 
                  INITIAL_BLACK_PAWN_MOVE_1_RANK, 
                  new Piece(WHITE, PAWN, expander));
        
        move2.set(0, 
                  INITIAL_BLACK_PAWN_MOVE_2_RANK,
                  new Piece(WHITE, PAWN, expander));
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
    }
    
    @Test
    public void blackPawnCannotMoveForward() {
        state.set(4, 4, new Piece(BLACK, PAWN, expander));
        state.set(4, 5, new Piece(WHITE, ROOK));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertTrue(children.isEmpty());
    }
    
    @Test
    public void blackPawnCanEatBothDirectionsAndMoveForward() {
        state.set(4, 4, new Piece(BLACK, PAWN, expander));
        state.set(3, 5, new Piece(WHITE, KNIGHT));
        state.set(5, 5, new Piece(WHITE, ROOK));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(3, children.size());
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        final ChessBoardState move3 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        move3.clear();
        
        // Capture to the left:
        move1.set(3, 5, new Piece(BLACK, PAWN));
        move1.set(5, 5, new Piece(WHITE, ROOK));
        
        // Move forward:
        move2.set(4, 5, new Piece(BLACK, PAWN));
        move2.set(3, 5, new Piece(WHITE, KNIGHT));
        move2.set(5, 5, new Piece(WHITE, ROOK));
        
        // Capture to the right:
        move3.set(5, 5, new Piece(BLACK, PAWN));
        move3.set(3, 5, new Piece(WHITE, KNIGHT));
        
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move2));
        assertTrue(children.contains(move3));
    }
    
    @Test
    public void blackPawnCannotMakeFirstDoubleMoveDueToObstruction() {
        state.set(6, 6, new Piece(WHITE, PAWN, expander));
        state.set(6, 5, new Piece(BLACK, BISHOP, expander));
        
        assertTrue(state.expand(PlayerTurn.BLACK).isEmpty());
        
        state.clear();
        state.set(4, 6, new Piece(WHITE, PAWN, expander));
        state.set(4, 5, new Piece(BLACK, ROOK, expander));
        
        assertTrue(state.expand(PlayerTurn.BLACK).isEmpty());
    }
    
    @Test
    public void blackPawnPromotion() {
        state.set(3, PROMOTION_SOURCE_RANK, new Piece(BLACK, PAWN, expander));
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(4, children.size());
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        final ChessBoardState move3 = new ChessBoardState();
        final ChessBoardState move4 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        move3.clear();
        move4.clear();
        
        move1.set(3, PROMOTION_TARGET_RANK, new Piece(BLACK, QUEEN));
        move2.set(3, PROMOTION_TARGET_RANK, new Piece(BLACK, ROOK));
        move3.set(3, PROMOTION_TARGET_RANK, new Piece(BLACK, KNIGHT));
        move4.set(3, PROMOTION_TARGET_RANK, new Piece(BLACK, BISHOP));
       
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move1));
        assertTrue(children.contains(move1));
        
        final Set<ChessBoardState> stateSet = new HashSet<>();
        
        stateSet.addAll(Arrays.asList(move1, move2, move3, move4));
        
        assertEquals(4, stateSet.size());
    }
    
    @Test
    public void blackPawnPromotionCaptureBoth() {
        state.set(5, PROMOTION_SOURCE_RANK, new Piece(BLACK, PAWN, expander));
        state.set(4, PROMOTION_TARGET_RANK, new Piece(WHITE, BISHOP));
        state.set(6, PROMOTION_TARGET_RANK, new Piece(WHITE, PAWN));
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(12, children.size());
        
        final ChessBoardState move1 = new ChessBoardState();
        final ChessBoardState move2 = new ChessBoardState();
        final ChessBoardState move3 = new ChessBoardState();
        
        move1.clear();
        move2.clear();
        move3.clear();
        
        // Promote forward:
        move1.set(4, PROMOTION_TARGET_RANK, new Piece(WHITE, BISHOP));
        move1.set(6, PROMOTION_TARGET_RANK, new Piece(WHITE, PAWN));
        
        for (final PieceType pieceType :
                AbstractChessBoardStateExpander.PROMOTION_PIECE_TYPES) {
            
            move1.set(5, 
                      PROMOTION_TARGET_RANK, 
                      new Piece(BLACK, 
                                pieceType));
            
            assertTrue(children.contains(move1));
        }
        
        // Promote left:
        move2.set(6, PROMOTION_TARGET_RANK, new Piece(WHITE, PAWN));
        
        for (final PieceType pieceType :
                AbstractChessBoardStateExpander.PROMOTION_PIECE_TYPES) {
            
            move2.set(4, 
                      PROMOTION_TARGET_RANK, 
                      new Piece(BLACK, 
                                pieceType));
            
            assertTrue(children.contains(move2));
        }
        
        // Promote right:
        move3.set(4, PROMOTION_TARGET_RANK, new Piece(WHITE, BISHOP));
        
        for (final PieceType pieceType :
                AbstractChessBoardStateExpander.PROMOTION_PIECE_TYPES) {
            
            move3.set(6, 
                      PROMOTION_TARGET_RANK, 
                      new Piece(BLACK,
                                pieceType));
            
            assertTrue(children.contains(move3));
        }
    }
    
    @Test
    public void blackPawnEnPassantToLeft() {
        state.set(0, EN_PASSANT_SOURCE_RANK, new Piece(WHITE, PAWN));
        state.set(1, EN_PASSANT_TARGET_RANK, new Piece(WHITE, ROOK));
        state.set(1, EN_PASSANT_SOURCE_RANK, new Piece(BLACK, PAWN, expander));
        
        state.markWhitePawnInitialDoubleMove(0);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(1, children.size());
        
        final ChessBoardState move = new ChessBoardState();
        
        move.clear();
        move.set(1, EN_PASSANT_TARGET_RANK, new Piece(WHITE, ROOK));
        move.set(0, EN_PASSANT_TARGET_RANK, new Piece(BLACK, PAWN));
        
        assertTrue(children.contains(move));
    }
    
    @Test
    public void blackPawnEnPassantToRight() {
        state.set(3, EN_PASSANT_SOURCE_RANK, new Piece(BLACK, PAWN, expander));
        state.set(4, EN_PASSANT_SOURCE_RANK, new Piece(WHITE, PAWN));
        state.set(3, EN_PASSANT_TARGET_RANK, new Piece(WHITE, ROOK));
        
        state.markWhitePawnInitialDoubleMove(4);
        
        final List<ChessBoardState> children = state.expand(PlayerTurn.BLACK);
        
        assertEquals(1, children.size());
        
        final ChessBoardState move = new ChessBoardState();
        
        move.clear();
        
        move.set(3, EN_PASSANT_TARGET_RANK, new Piece(WHITE, ROOK));
        move.set(4, EN_PASSANT_TARGET_RANK, new Piece(BLACK, PAWN));
        
        boolean pass = children.contains(move);
        
        assertTrue(pass);
    }
    
    private static ChessBoardState getMove(final ChessBoardState state,
                                           final int sourceFile,
                                           final int sourceRank,
                                           final int targetFile,
                                           final int targetRank) {
        
        final ChessBoardState move = new ChessBoardState(state);
        
        move.set(targetFile, 
                 targetRank, 
                 move.get(sourceFile, 
                          sourceRank));
        
        move.clear(sourceFile,
                   sourceRank);
        
        return move;
    }
}
