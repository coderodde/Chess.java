package com.github.coderodde.game.chess;

import java.util.List;

/**
 * This interface defines the API for node expanders for chess board states.
 * 
 * @version 1.0.0 (Jun 26, 2024)
 * @since 1.0.0 (Jun 26, 2024)
 */
public interface ChessBoardStateExpander {
   
    /**
     * Generates all child states of {@code root}.
     * 
     * @param state the game state root to expand.
     * @param piece the piece to move.
     * 
     * @return all children. 
     */
    public List<ChessBoardState> expand(final ChessBoardState state,
                                        final Piece piece);
}
