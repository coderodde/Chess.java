package com.github.coderodde.game.chess;

/**
 * This interface defines the API for heuristic functions evaluating chess board
 * states.
 * 
 * @version 1.0.0 (Jul 6, 2024)
 * @since 1.0.0 (Jul 6, 2024)
 */
public interface HeuristicFunction {
   
    /**
     * Returns the evaluation of the input {@code state} at depth {@code depth}.
     * 
     * @param state the state to evaluate.
     * @param depth the depth of {@code state} in the search tree.
     * 
     * @return the evaluation of the input state.
     */
    public int evaluate(final ChessBoardState state, final int depth);
}
