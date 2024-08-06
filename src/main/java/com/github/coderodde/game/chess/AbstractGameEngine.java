package com.github.coderodde.game.chess;

import com.github.coderodde.game.chess.impl.BlackCheckMateInspector;
import com.github.coderodde.game.chess.impl.WhiteCheckMateInspector;
import java.util.Objects;

/**
 * This abstract class defines the API for making searches in the game tree via
 * a game engine.
 * 
 * @version 1.0.0 (Jul 18, 2024)
 * @since 1.0.0 (Jul 18, 2024)
 */
public abstract class AbstractGameEngine {
    
    // TODO: Put in the heuristic function?
    protected static final int MINIMUM_SCORE = -1000_000_000;
    protected static final int MAXIMUM_SCORE = +1000_000_000;
    
    /**
     * This field caches the best move state so far in this search.
     */
    protected ChessBoardState bestMoveState;
    
    /**
     * Holds the used heuristic function.
     */
    protected final AbstractHeuristicFunction heuristicFunction;
    
    /**
     * Holds the check mate inspector for the white player.
     */
    protected final CheckMateInspector WHITE_CHECK_MATE_INSPECTOR = 
            new WhiteCheckMateInspector();
    
    /**
     * Holds the check mate inspector for the black player.
     */
    protected final CheckMateInspector BLACK_CHECK_MATE_INSPECTOR = 
            new BlackCheckMateInspector();
    
    /**
     * Constructs an instance of this abstract class.
     * 
     * @param heuristicFunction the heuristic function.
     */
    public AbstractGameEngine(
            final AbstractHeuristicFunction heuristicFunction) {
        
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
     * @throws com.github.coderodde.game.chess.ThreeFoldRepetionRuleDrawException
     *         is thrown when the three-fold repetition rule is broken.
     */
    public abstract ChessBoardState search(final ChessBoardState root, 
                                           final int depth,
                                           final PlayerTurn playerTurn)
            throws ThreeFoldRepetionRuleDrawException;
    
    /**
     * Clears the state frequency map.
     */
    public void clearSearchState() {
        heuristicFunction.clearStateFrequencyMap();
    }
}
