package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.HeuristicFunction;

/**
 * This class implements a naïve heuristic function. It only considers presence
 * of the pieces, the attacking opportunities and vulnerabilities.
 * 
 * @version 1.0.0 (Jul 6, 2024)
 * @since 1.0.0 (Jul 6, 2024)
 */
public final class NaiveHeuristicFunctionV1 implements HeuristicFunction {

    @Override
    public int evaluate(ChessBoardState state, int depth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
