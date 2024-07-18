package com.github.coderodde.game.chess.impl.engine;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.PlayerTurn;
import com.github.coderodde.game.chess.AbstractGameEngine;
import com.github.coderodde.game.chess.HeuristicFunction;

/**
 *
 * @version 1.0.0 (Jul 18, 2024)
 * @since 1.0.0 (Jul 18, 2024)
 */
public final class AlphaBetaPruningGameEngine extends AbstractGameEngine {

    /**
     * Constructs this alpha-beta pruning game engine.
     * 
     * @param heuristicFunction the heuristic function to use.
     */
    public AlphaBetaPruningGameEngine(
            final HeuristicFunction heuristicFunction) {
        
        super(heuristicFunction);
    }
    
    @Override
    public ChessBoardState search(final ChessBoardState root, 
                                  final int depth, 
                                  final PlayerTurn playerTurn) {
        
        
        
        throw new UnsupportedOperationException();
    }
}
