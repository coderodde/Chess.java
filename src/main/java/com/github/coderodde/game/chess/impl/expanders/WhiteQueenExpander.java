package com.github.coderodde.game.chess.impl.expanders;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import java.util.List;

/**
 * This class implements the expander generating all white queen moves.
 * 
 * @version 1.0.0 (Jul 2, 2024)
 * @since 1.0.0 (Jul 2, 2024)
 */
public final class WhiteQueenExpander extends AbstractChessBoardStateExpander {

    private static final AbstractChessBoardStateExpander ROOK_EXPANDER = 
            new WhiteRookExpander();
    
    private static final AbstractChessBoardStateExpander BISHOP_EXPANDER = 
            new WhiteBishopExpander();
    
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
