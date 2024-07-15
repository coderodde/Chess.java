package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.KNIGHT;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.ROOK;
import com.github.coderodde.game.chess.UnderAttackCheck;
import static com.github.coderodde.game.chess.impl.expanders.WhitePawnExpander.EN_PASSANT_SOURCE_RANK;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class BlackUnderAttackCheckByWhitePawnTest {
    
    private final ChessBoardState state = new ChessBoardState();
    private static final Piece whitePawn = new Piece(WHITE, PAWN);
    private static final Piece whiteRook = new Piece(WHITE, ROOK);
    private static final Piece blackPawn = new Piece(BLACK, PAWN);
    private static final Piece blackKnight = new Piece(BLACK, KNIGHT);
    
    private static final UnderAttackCheck PAWN_CHECK =
            new BlackUnderAttackCheckByWhitePawn();
    
    @Before
    public void before() {
        state.clear();
        state.clearBlackInitialDoubleMoveFlags();
        state.clearWhiteInitialDoubleMoveFlags();
    }
    
    @Test
    public void threatensFromLeft() {
        state.set(3, 3, blackPawn);
        state.set(2, 4, whitePawn);
        assertThreatens(3, 3);
    }
    
    @Test
    public void threatensFromRight() {
        state.set(3, 3, blackPawn);
        state.set(4, 4, whitePawn);
        assertThreatens(3, 3);
    }
    
    @Test
    public void cannotCaptureFromFile0() {
        state.set(0, 1, blackPawn);
        state.set(1, 2, whitePawn);
        assertThreatens(0, 1);
    }
    
    @Test
    public void threatensFromLeft2() {
        state.set(3, 3, blackPawn);
        state.set(2, 4, blackPawn);
        assertNotThreatens(3, 3);
    }
    
    @Test
    public void threatensFromLeft3() {
        state.set(3, 3, blackPawn);
        state.set(2, 4, whiteRook);
        assertNotThreatens(3, 3);
    }
    
    @Test
    public void threatensFromRight2() {
        state.set(3, 3, blackPawn);
        state.set(4, 4, blackPawn);
        assertNotThreatens(3, 3);
    }
    
    @Test
    public void threatensFromRight3() {
        state.set(3, 3, blackPawn);
        state.set(4, 4, whiteRook);
        assertNotThreatens(3, 3);
    }
    
    @Test
    public void cannotCaptureFromRigthFile7() {
        state.set(7, 1, blackPawn);
        assertNotThreatens(7, 1);
    }
    
    @Test
    public void sameColor1() {
        state.set(3, 3, whitePawn);
        state.set(2, 4, blackPawn);
        assertNotThreatens(3, 3);
    }
    
    @Test
    public void test1() {
        state.set(3, 3, blackPawn);
        state.set(2, 4, blackPawn);
        assertNotThreatens(3, 3);
    }
    
    @Test
    public void test2() {
        state.set(3, 3, blackPawn);
        state.set(2, 4, blackKnight);
        assertNotThreatens(3, 3);
    }
    
    @Test
    public void northWestCornerNoThreat() {
        state.set(0, 1, blackPawn);
        assertNotThreatens(0, 1);
    }
    
    @Test
    public void nortEastCornerNoThreat() {
        state.set(7, 1, blackPawn);
        assertNotThreatens(7, 1);
    }
    
    @Test
    public void southWestCornerNoThreat() {
        state.set(0, 6, blackPawn);
        assertNotThreatens(0, 6);
    }
    
    @Test
    public void southEasttCornerNoThreat() {
        state.set(7, 6, blackPawn);
        assertNotThreatens(7, 6);
    }
    
    // En passant tests:
    @Test 
    public void cannotEnPassantFromFile0() {
        state.set(0, EN_PASSANT_SOURCE_RANK, blackPawn);
        assertNotThreatens(0, EN_PASSANT_SOURCE_RANK);  
    } 
    
    @Test 
    public void cannotEnPassantFromFile7() {
        state.set(7, EN_PASSANT_SOURCE_RANK, blackPawn);
        assertNotThreatens(7, EN_PASSANT_SOURCE_RANK);  
    } 
    
    @Test
    public void enPassantToLeft() {
        state.set(4, EN_PASSANT_SOURCE_RANK, whitePawn);
        state.set(3, EN_PASSANT_SOURCE_RANK, blackPawn);
        state.getBlackIsPreviouslyDoubleMoved()[3] = true;
        assertThreatens(3, EN_PASSANT_SOURCE_RANK);
    }
    
    @Test
    public void enPassantToRight() {
        state.set(4, EN_PASSANT_SOURCE_RANK, whitePawn);
        state.set(5, EN_PASSANT_SOURCE_RANK, blackPawn);
        state.getBlackIsPreviouslyDoubleMoved()[5] = true;
        assertThreatens(5, EN_PASSANT_SOURCE_RANK);
    }
    
    @Test
    public void wrongPieceEnPassantToLeft() {
        state.set(4, EN_PASSANT_SOURCE_RANK, whiteRook);
        state.set(3, EN_PASSANT_SOURCE_RANK, blackPawn);
        state.getBlackIsPreviouslyDoubleMoved()[3] = true;
        assertNotThreatens(3, EN_PASSANT_SOURCE_RANK);
    }
    
    @Test
    public void wrongPieceEnPassantToRight() {
        state.set(4, EN_PASSANT_SOURCE_RANK, whiteRook);
        state.set(5, EN_PASSANT_SOURCE_RANK, blackPawn);
        state.getBlackIsPreviouslyDoubleMoved()[5] = true;
        assertNotThreatens(5, EN_PASSANT_SOURCE_RANK);
    }
    
    @Test
    public void wrongColorEnPassantToLeft() {
        state.set(4, EN_PASSANT_SOURCE_RANK, blackPawn);
        state.set(3, EN_PASSANT_SOURCE_RANK, blackPawn);
        state.getBlackIsPreviouslyDoubleMoved()[3] = true;
        assertNotThreatens(3, EN_PASSANT_SOURCE_RANK);
    }
    
    @Test
    public void wrongColorEnPassantToRight() {
        state.set(4, EN_PASSANT_SOURCE_RANK, blackPawn);
        state.set(5, EN_PASSANT_SOURCE_RANK, blackPawn);
        state.getBlackIsPreviouslyDoubleMoved()[5] = true;
        assertNotThreatens(5, EN_PASSANT_SOURCE_RANK);
    }
    
    @Test
    public void cannotEnPassantFromRightOnNullPiece() {
        state.set(3, EN_PASSANT_SOURCE_RANK, blackPawn);
        state.getBlackIsPreviouslyDoubleMoved()[3] = true;
        assertNotThreatens(3, EN_PASSANT_SOURCE_RANK);
    }
    
    @Test
    public void canDoEnPassantToLeft() {
        
    }
    
    @Test
    public void canDoEnPassantToRight() {
        
    }
    
    private void assertThreatens(final int file, 
                                 final int rank) {
        
        assertTrue(PAWN_CHECK.check(state, 
                                    file, 
                                    rank));
    }
    
    private void assertNotThreatens(final int file, 
                                    final int rank) {
        
        assertFalse(PAWN_CHECK.check(state, 
                                     file, 
                                     rank));
    }
}
