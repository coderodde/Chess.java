package com.github.coderodde.game.chess.impl.expanders;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import java.util.List;

/**
 * This class implements a dummy expander returning empty child lists.
 * 
 * @version 1.0.0 (Jul 1, 2024)
 * @since 1.0.0 (Jul 1, 2024)
 */
public final class TestDummyExpander extends AbstractChessBoardStateExpander {

    @Override
    public void expand(final ChessBoardState state, 
                       final Piece piece, 
                       final int file, 
                       final int rank, 
                       final List<ChessBoardState> children) {
    
    }
}
