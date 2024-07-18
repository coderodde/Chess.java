package com.github.coderodde.game.chess;

import java.util.Objects;

/**
 * This abstract class defines the API for making searches in the game tree via
 * a game engine.
 * 
 * @version 1.0.0 (Jul 18, 2024)
 * @since 1.0.0 (Jul 18, 2024)
 */
public abstract class AbstractGameEngine {
    
    /**
     * Holds the used heuristic function.
     */
    protected final HeuristicFunction heuristicFunction;
    
    /**
     * Constructs an instance of this abstract class.
     * 
     * @param heuristicFunction the heuristic function.
     */
    public AbstractGameEngine(final HeuristicFunction heuristicFunction) {
        this.heuristicFunction = 
                Objects.requireNonNull(
                        heuristicFunction,
                        "The input heuristic function is null.");
    }
    
    /**
     * Returns the next state to move to.
     * 
     * @param root       the root state to start the search from.
     * @param depth      the search depth.
     * @param playerTurn the player turn enumeration flag.
     * 
     * @return the next best move.
     */
    public abstract ChessBoardState search(final ChessBoardState root, 
                                           final int depth,
                                           final PlayerTurn playerTurn);
}
