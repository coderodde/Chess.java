package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.ChessBoardStateExpander;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements an expander for generating all white pawn moves.
 * 
 * @version 1.0.0 (Jun 26, 2024)
 * @since 1.0.0 (Jun 26, 2024)
 */
public final class WhitePawnExpander implements ChessBoardStateExpander {

    @Override
    public List<ChessBoardState> expand(final ChessBoardState root, 
                                        final Piece piece) {
        
        final PlayerTurn playerTurn = piece.getPlayerTurn();
        final List<ChessBoardState> children = new ArrayList<>();
     
        return children;
    }
}
