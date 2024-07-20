package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.KING;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import com.github.coderodde.game.chess.UnderAttackCheck;

public final class BlackUnderAttackCheckByWhiteKingTest {
    
    private static final Piece whiteKing = new Piece(WHITE, KING);
    private static final Piece blackKing = new Piece(BLACK, KING);
    private static final Piece whitePawn = new Piece(WHITE, PAWN);
    private static final Piece blackPawn = new Piece(BLACK, PAWN);
    
    private static final UnderAttackCheck KING_CHECK = 
            new BlackUnderAttackCheckByWhiteKing();
    
    private final ChessBoardState state = new ChessBoardState();
    
    @Before
    public void before() {
        state.clear();
        state.set(3, 3, blackPawn);
    }
    
    @Test
    public void whiteKingNorthThreatens() {
        state.set(3, 2, whiteKing);
        assertCheck();
    }
    
    @Test
    public void whiteKingSouthThreatens() {
        state.set(3, 4, whiteKing);
        assertCheck();
    }
    
    @Test
    public void whiteKingWestThreatens() {
        state.set(2, 3, whiteKing);
        assertCheck();
    }
    
    @Test
    public void whiteKingEastThreatens() {
        state.set(4, 3, whiteKing);
        assertCheck();
    }
    
    @Test
    public void whiteKingNorthWestThreatens() {
        state.set(2, 2, whiteKing);
        assertCheck();
    }
    
    @Test
    public void whiteKingNorthEastThreatens() {
        state.set(4, 2, whiteKing);
        assertCheck();
    }
    
    @Test
    public void whiteKingSouthWestThreatens() {
        state.set(2, 4, whiteKing);
        assertCheck();
    }
    
    @Test
    public void whiteKingSouthEastThreatens() {
        state.set(4, 4, whiteKing);
        assertCheck();
    }
    
    @Test
    public void blockKingNorthThreatens() {
        state.set(3, 2, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blockKingSouthThreatens() {
        state.set(3, 4, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blockKingWestThreatens() {
        state.set(2, 3, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blockKingEastThreatens() {
        state.set(4, 3, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blockKingNorthWestThreatens() {
        state.set(2, 2, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blockKingNorthEastThreatens() {
        state.set(4, 2, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blockKingSouthWestThreatens() {
        state.set(2, 4, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blockKingSouthEastThreatens() {
        state.set(4, 4, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blockPawnNorthThreatens() {
        state.set(3, 2, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blockPawnSouthThreatens() {
        state.set(3, 4, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blockPawnWestThreatens() {
        state.set(2, 3, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blockPawnEastThreatens() {
        state.set(4, 3, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blockPawnNorthWestThreatens() {
        state.set(2, 2, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blockPawnNorthEastThreatens() {
        state.set(4, 2, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blockPawnSouthWestThreatens() {
        state.set(2, 4, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blockPawnSouthEastThreatens() {
        state.set(4, 4, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blackPawnNorthNotThreatens() {
        state.set(3, 2, whitePawn);
        assertNotCheck();
    }
    
    @Test
    public void blackKingSouthNotThreatens() {
        state.set(3, 4, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blackKingWestNotThreatens() {
        state.set(2, 3, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blackKingEastNotThreatens() {
        state.set(4, 3, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blackKingNorthWestNotThreatens() {
        state.set(2, 2, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blackKingNorthEastNotThreatens() {
        state.set(4, 2, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blackKingSouthWestNotThreatens() {
        state.set(2, 4, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void blackKingSouthEastNotThreatens() {
        state.set(4, 4, blackKing);
        assertNotCheck();
    }
    
    @Test
    public void northWestCorner() {
        state.set(0, 0, whiteKing);
        assertNotCheck();
    }
    
    @Test
    public void northEastCorner() {
        state.set(7, 0, whiteKing);
        assertNotCheck();
    }
    
    @Test
    public void southWestCorner() {
        state.set(0, 7, whiteKing);
        assertNotCheck();
    }
    
    @Test
    public void soutEastCorner() {
        state.set(7, 7, whiteKing);
        assertNotCheck();
    }
    
    @Test
    public void allSurroundingKings() {
        state.set(2, 2, blackKing);
        state.set(3, 2, blackKing);
        state.set(4, 2, blackKing);
        state.set(2, 3, blackKing);
        state.set(4, 3, blackKing);
        state.set(2, 4, blackKing);
        state.set(3, 4, blackKing);
        state.set(4, 4, blackKing);
        
        assertNotCheck();
    }
    
    @Test
    public void rank0() {
        state.set(3, 0, whiteKing);
        assertNotCheck();
    }
    
    @Test
    public void rank7() {
        state.set(3, 7, whiteKing);
        assertNotCheck();
    }
    
    @Test
    public void file0() {
        state.set(0, 2, whiteKing);
        assertNotCheck();
    }
    
    @Test
    public void file7() {
        state.set(7, 2, whiteKing);
        assertNotCheck();
    }
    
    @Test
    public void bottomCenterWhiteKing() {
        state.set(2, 7, whiteKing);
        assertNotCheck();
    }
    
    @Test
    public void cornerCase1() {
        assertNotCheck(1, 0, blackPawn);
    }
    
    @Test
    public void cornerCase2() {
        assertNotCheck(1, 7, blackPawn);
    }
    
    @Test
    public void cornerCase3() {
        assertNotCheck(0, 1, blackPawn);
    }
    
    @Test
    public void cornerCase4() {
        assertNotCheck(7, 1, blackPawn);
    }
    
    private void assertCheck() {
        assertTrue(KING_CHECK.check(state, 3, 3));
    }
    
    private void assertNotCheck() {
        assertFalse(KING_CHECK.check(state, 3, 3));
    }
    
    private void assertNotCheck(final int file, 
                                final int rank, 
                                final Piece piece) {
        
        state.set(file, 
                  rank, 
                  piece);
        
        assertFalse(KING_CHECK.check(state, file, rank));
    }
}
