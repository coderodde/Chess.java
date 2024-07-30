package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.CheckMateInspector;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.KING;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.QUEEN;
import static com.github.coderodde.game.chess.PieceType.ROOK;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public final class BlackCheckMateInspectorTest {
    
    private static final ChessBoardState state = new ChessBoardState();
    private static final Piece blackKing = new Piece(BLACK, KING);
    private static final Piece blackPawn = new Piece(BLACK, PAWN);
    private static final Piece blackRook = new Piece(BLACK, ROOK);
    private static final Piece whiteRook = new Piece(WHITE, ROOK);
    private static final Piece whiteQueen = new Piece(WHITE, QUEEN);
    private static final CheckMateInspector CHECKMATE_INSPECTOR =
            new BlackCheckMateInspector();
    
    @Before
    public void before() {
        state.clear();
    }
    
    @Test
    public void isNotInCheckMateCenter() {
        prepareKing(4, 4);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void isNotInCheckMateCenter66() {
        prepareKing(4, 4);
        state.set(6, 6, whiteQueen);
        // The white king can hide from the black queen.
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void checkMateWithSecuredBlackQueen() {
        prepareKing(4, 4);
        
        state.set(3, 3, blackPawn);
        state.set(4, 3, blackPawn);
        state.set(5, 3, blackPawn);
        state.set(3, 4, blackPawn);
        state.set(5, 4, blackPawn);
        state.set(3, 5, blackPawn);
        state.set(5, 5, blackPawn);
        state.set(4, 5, whiteQueen);
        state.set(4, 7, whiteRook); // Secure the black queen at (4,5).
        
        assertTrue(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void king00() {
        prepareKing(0, 0);
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void king07() {
        prepareKing(0, 7);
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void king70() {
        prepareKing(7, 0);
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void king77() {
        prepareKing(7, 7);
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void checkMateWithAllBlackQueens() {
        prepareKing(4, 4);
        
        state.set(3, 3, whiteQueen);
        state.set(4, 3, whiteQueen);
        state.set(5, 3, whiteQueen);
        state.set(3, 4, whiteQueen);
        state.set(5, 4, whiteQueen);
        state.set(3, 5, whiteQueen);
        state.set(5, 5, whiteQueen);
        state.set(4, 5, whiteQueen);
        
        assertTrue(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void sameColorBlockNorthWest() {
        prepareKing(0, 0);
        
        state.set(1, 0, blackPawn);
        state.set(0, 1, blackPawn);
        state.set(1, 1, blackPawn);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void sameColorBlockNorthEast() {
        prepareKing(7, 0);
        
        state.set(6, 0, blackPawn);
        state.set(7, 1, blackPawn);
        state.set(6, 1, blackPawn);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void sameColorBlockSouthWest() {
        prepareKing(0, 7);
        
        state.set(0, 6, blackPawn);
        state.set(1, 6, blackPawn);
        state.set(1, 7, blackPawn);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void sameColorBlockSouthEast() {
        prepareKing(7, 7);
        
        state.set(6, 7, blackPawn);
        state.set(6, 6, blackPawn);
        state.set(7, 6, blackPawn);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void threatAtNorthWestCorner() {
        prepareKing(0, 0);
        state.set(4, 4, whiteQueen);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void threatAtNorthEastCorner() {
        prepareKing(7, 0);
        state.set(6, 1, whiteQueen);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void threatAtSouthWestCorner() {
        prepareKing(0, 7);
        state.set(1, 6, whiteQueen);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void threatAtSouthEastCorner() {
        prepareKing(7, 7);
        state.set(4, 4, whiteQueen);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void yeah() {
        prepareKing(0, 0);
        state.set(1, 0, blackPawn);
        state.set(4, 4, whiteQueen);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void tryHideEastEastPieceNull() {
        state.set(0, 1, blackPawn);
        prepareKing(1, 1);
        
        state.set(3, 3, whiteQueen);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void tryHideNorthRank1PieceNull() {
        state.set(0, 1, blackPawn);
        prepareKing(1, 1);
        state.set(2, 1, blackPawn);
        
        state.set(4, 4, whiteQueen);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void tryHideSouthRank7() {
        prepareKing(4, 7);
        state.set(3, 7, blackPawn);
        state.set(5, 7, blackPawn);
        state.set(3, 6, blackPawn);
        state.set(5, 6, blackPawn);
        
        state.set(4, 1, whiteQueen);
        
        assertTrue(CHECKMATE_INSPECTOR.isInCheckMate(state));  
    }
    
    @Test
    public void southPieceIsNull() {
        prepareKing(4, 6);
        
        state.set(3, 7, blackPawn);
        state.set(3, 6, blackPawn);
        state.set(3, 5, blackPawn);
        state.set(4, 5, blackPawn);
        state.set(5, 5, blackPawn);
        state.set(5, 7, blackPawn);
        state.set(7, 6, whiteRook);
        
        // Can hide to the south which is not occupied:
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void southPieceIsBlocking() {
        prepareKing(4, 6);
        
        state.set(3, 7, blackPawn);
        state.set(3, 6, blackPawn);
        state.set(3, 5, blackPawn);
        state.set(4, 5, blackPawn);
        state.set(5, 5, blackPawn);
        state.set(5, 7, blackPawn);
        state.set(4, 7, blackPawn);
        state.set(7, 6, whiteRook);
        
        // Can hide to the south which is not occupied:
        assertTrue(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void northWestRank0() {
        prepareKing(1, 0);
        
        state.set(0, 0, blackPawn);
        state.set(2, 0, blackPawn);
        state.set(0, 1, blackPawn);
        state.set(2, 1, blackPawn);
        
        state.set(1, 6, whiteQueen);
        
        assertTrue(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void northWestPieceIsNull() {
        prepareKing(3, 3);
        
        // bottom pawn border
        state.set(2, 4, blackPawn);
        state.set(3, 4, blackPawn);
        state.set(4, 4, blackPawn);
        
        // left pawn border
        state.set(2, 2, blackPawn);
        state.set(2, 3, blackPawn);
        
        state.set(4, 3, blackPawn);
        state.set(3, 0, whiteQueen);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void tryHideNorthWestPieceIsNull() {
        prepareKing(4, 5);
        
        state.set(3, 5, blackPawn);
        state.set(5, 5, blackPawn);
        state.set(4, 4, blackPawn);
        state.set(5, 4, blackPawn);
        state.set(3, 5, blackPawn);
        state.set(5, 5, blackPawn);
        
        state.set(4, 7, whiteQueen);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void tryHideSouthWestFile0() {
        prepareKing(0, 5);
        
        state.set(0, 4, blackPawn);
        state.set(0, 6, blackPawn);
        state.set(1, 4, blackPawn);
        state.set(1, 6, blackPawn);
        
        state.set(6, 5, whiteQueen);
        
        assertTrue(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void cannotHideToSouthEastFile7() {
        prepareKing(7, 3);
        
        state.set(7, 2, blackPawn);
        state.set(7, 4, blackPawn);
        state.set(6, 2, blackPawn);
        state.set(6, 4, blackPawn);
        
        state.set(2, 3, whiteQueen);
        
        assertTrue(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void southEastPieceIsNull() {
        prepareKing(6, 3);
        
        // top pawn border:
        state.set(5, 2, blackPawn);
        state.set(6, 2, blackPawn);
        state.set(7, 2, blackPawn);
        
        state.set(7, 3, blackPawn   );
        
        state.set(5, 4, blackPawn);
        state.set(6, 4, blackPawn);
        state.set(2, 3, whiteQueen);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void tryHideNorthRank0() {
        state.set(0, 0, blackPawn);
        prepareKing(1, 0);
        state.set(2, 0, blackPawn);
        state.set(1, 5, whiteQueen);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void blockedWithoutOffender() {
        prepareKing(2, 2);
        
        state.set(1, 1, blackPawn);
        state.set(2, 1, blackPawn);
        state.set(3, 1, blackPawn);
        
        state.set(1, 3, blackPawn);
        state.set(2, 3, blackPawn);
        state.set(3, 3, blackPawn);
        
        state.set(1, 2, blackPawn);
        state.set(3, 2, blackPawn);
        
        state.set(5, 5, whiteQueen);
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void threatenedEverywhere() {
        prepareKing(4, 5);
        
        state.set(1, 4, whiteQueen);
        state.set(1, 5, whiteQueen);
        state.set(1, 6, whiteQueen);
        
        assertTrue(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    @Test
    public void canCaptureOffendingBlackQueen() {
        prepareKing(3, 3);
        
        state.set(2, 2, blackPawn);
        state.set(3, 2, blackPawn);
        state.set(4, 2, blackPawn);
        
        state.set(2, 3, blackPawn);
        state.set(4, 3, blackPawn);
        
        state.set(2, 4, blackPawn);
        state.set(4, 4, blackPawn);
        
        state.set(3, 7, whiteQueen);
        state.set(7, 7, blackRook); // Black rook can capture the only offender.
        
        assertFalse(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    private static void prepareKing(final int file, final int rank) {
        state.setBlackKingFile(file);
        state.setBlackKingRank(rank);
        state.set(file, 
                  rank,
                  blackKing);
    }
}
