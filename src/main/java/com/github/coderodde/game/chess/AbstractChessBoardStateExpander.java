package com.github.coderodde.game.chess;

import java.util.List;

/**
 * This interface defines the API for node expanders for chess board states.
 * 
 * @version 1.0.0 (Jun 26, 2024)
 * @since 1.0.0 (Jun 26, 2024)
 */
public abstract class AbstractChessBoardStateExpander {
    
    protected static final PieceType[] PROMOTION_PIECE_TYPES = {
        PieceType.QUEEN,
        PieceType.BISHOP,
        PieceType.KNIGHT,
        PieceType.ROOK,
    };
   
    /**
     * Generates all child states of {@code root}.
     * 
     * @param state    the game state root to expand.
     * @param piece    the piece to move.
     * @param children the list of child states generated so far.
     */
    public abstract void expand(final ChessBoardState state,
                                final Piece piece,
                                final List<ChessBoardState> children);
}
