package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import java.util.List;

/**
 * This class impelemnts the expander generating all the moves of a black queen.
 * 
 * @version 1.0.0 (Jul 6, 2024)
 * @since 1.0.0 (Jul 6, 2024)
 */
public final class BlackQueenExpander extends AbstractChessBoardStateExpander {

    private static final AbstractChessBoardStateExpander ROOK_EXPANDER = 
            new BlackRookExpander();
    
    private static final AbstractChessBoardStateExpander BISHOP_EXPANDER = 
            new BlackBishopExpander();
    
    @Override
    public void expand(final ChessBoardState state,
                       final Piece piece, 
                       final int file, 
                       final int rank, 
                       final List<ChessBoardState> children) {

        ROOK_EXPANDER.expand(state, 
                             piece,
                             file, 
                             rank, 
                             children);
        
        BISHOP_EXPANDER.expand(state,
                               piece, 
                               file, 
                               rank,
                               children);
    }
}
