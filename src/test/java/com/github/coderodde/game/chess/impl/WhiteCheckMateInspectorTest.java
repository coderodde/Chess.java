package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.CheckMateInspector;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.KING;
import static com.github.coderodde.game.chess.PieceType.QUEEN;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public final class WhiteCheckMateInspectorTest {
    
    private static final ChessBoardState state = new ChessBoardState();
    private static final Piece king = new Piece(WHITE, KING);
    private static final Piece blackQueen = new Piece(BLACK, QUEEN);
    private static final CheckMateInspector CHECKMATE_INSPECTOR = 
            new WhiteCheckMateInspector();
    
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
    public void isInCheckMateCenter() {
        prepareKing(4, 4);
        state.set(6, 6, blackQueen);
        assertTrue(CHECKMATE_INSPECTOR.isInCheckMate(state));
    }
    
    private static void prepareKing(final int file, final int rank) {
        state.setWhiteKingFile(file);
        state.setWhiteKingRank(rank);
    }
}
