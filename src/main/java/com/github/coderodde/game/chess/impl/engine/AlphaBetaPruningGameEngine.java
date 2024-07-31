package com.github.coderodde.game.chess.impl.engine;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.PlayerTurn;
import com.github.coderodde.game.chess.AbstractGameEngine;
import com.github.coderodde.game.chess.HeuristicFunction;
import com.github.coderodde.game.chess.ThreeFoldRepetionRuleDrawException;
import java.util.List;

/**
 * This class implements an Alpha-beta pruning game engine.
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
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ChessBoardState search(final ChessBoardState root, 
                                  final int depth, 
                                  final PlayerTurn playerTurn)
            
    throws ThreeFoldRepetionRuleDrawException {
        
        bestMoveState = null;
        
        alphaBetaPruningRootImpl(root, 
                                 depth,
                                 MINIMUM_SCORE,
                                 MAXIMUM_SCORE,
                                 playerTurn);
        
        playerMoveFrequencyMap.put(
                bestMoveState, 
                playerMoveFrequencyMap.getOrDefault(
                        bestMoveState, 
                        0) + 1);
        
        if (playerMoveFrequencyMap.get(bestMoveState) == 3) {
            // Once here, the state 'bestMoveState' was visited three times.
            // End the game with a draw due to three-fold repetition rule.
            throw new ThreeFoldRepetionRuleDrawException();
        }
        
        return bestMoveState;
    }
        
    private void alphaBetaPruningRootImpl(final ChessBoardState root,
                                         final int depth,
                                         int alpha,
                                         int beta,
                                         final PlayerTurn playerTurn) {
        
        if (playerTurn == PlayerTurn.BLACK) {
            // Black is the maximizing player!
            int value = MINIMUM_SCORE;
            int tentativeValue = MINIMUM_SCORE;
            
            final List<ChessBoardState> children = 
                    root.expand(PlayerTurn.BLACK);
            
            if (children.isEmpty()) {
                throw new IllegalStateException();
            }
            
            for (final ChessBoardState child : children) {
                value = Math.max(value, 
                                 alphaBetaPruningImpl(child, 
                                                      depth - 1, 
                                                      alpha, 
                                                      beta, 
                                                      PlayerTurn.WHITE));
                
                System.out.println("Black " + value);
                
                if (value > beta) {
                    break;
                }
                
                if (tentativeValue < value) {
                    tentativeValue = value;
                    bestMoveState = new ChessBoardState(child);
                }
                
                alpha = Math.max(alpha, value);
            }
        } else {
            // Once here, 'playerTurn' is 'WHITE': a minimizing player!
            int value = MAXIMUM_SCORE;
            int tentativeValue = MAXIMUM_SCORE;
            
            final List<ChessBoardState> children = 
                    root.expand(PlayerTurn.WHITE);
            
            if (children.isEmpty()) {
                throw new IllegalStateException();
            }
            
            for (final ChessBoardState child : children) {
                value = Math.min(value,
                                 alphaBetaPruningImpl(child,
                                                      depth - 1,
                                                      alpha,
                                                      beta,
                                                      PlayerTurn.BLACK));

                System.out.println("White " + value);
                if (value < alpha) {
                    break;
                }
                
                if (tentativeValue > value) {
                    tentativeValue = value;
                    bestMoveState = new ChessBoardState(child);
                }
                
                beta = Math.min(beta, value);
            }
        }
    }
    
    private int alphaBetaPruningImpl(final ChessBoardState root,
                                     final int depth,
                                     int alpha,
                                     int beta,
                                     final PlayerTurn playerTurn) {
        
        if (playerTurn == PlayerTurn.WHITE) {
            if (WHITE_CHECK_MATE_INSPECTOR.isInCheckMate(root)) {
                return MAXIMUM_SCORE + depth;
            }
        } else {
            if (BLACK_CHECK_MATE_INSPECTOR.isInCheckMate(root)) {
                return MINIMUM_SCORE - depth;
            }
        }
        
        if (depth == 0) {
            return heuristicFunction.evaluate(root, depth);
        }
        
        // TODO: Check for stalemate here?
        
        if (playerTurn == PlayerTurn.BLACK) {
            // The black player is the maximizing player:
            int value = MINIMUM_SCORE;
            
            final List<ChessBoardState> children = root.expand(playerTurn);
            
            if (children.isEmpty()) {
                // Once here, we have a stalemate:
                System.out.println("Stalemate in black player!");
                return MAXIMUM_SCORE - depth;
            }
            
            for (final ChessBoardState child : children) {
                value = Math.max(value,
                                 alphaBetaPruningImpl(
                                         child, 
                                         depth - 1, 
                                         alpha, 
                                         beta, 
                                         PlayerTurn.WHITE));
                
                if (value > beta) {
                    break;
                }
                
                alpha = Math.max(alpha, value);
            }
            
            return value;
            
        } else {
            // Here, 'playerTurn == PlayerTurn.WHITE', the minimizing player:
            int value = MAXIMUM_SCORE;
            
            final List<ChessBoardState> children = root.expand(playerTurn);
            
            if (children.isEmpty()) {
                System.out.println("yeahhhh");
                return MINIMUM_SCORE + depth;
            }
            
            for (final ChessBoardState child : children) {
                value = Math.min(value,
                                 alphaBetaPruningImpl(
                                         child, 
                                         depth - 1,
                                         alpha, 
                                         beta, 
                                         PlayerTurn.BLACK));
                
                if (value < alpha) {
                    break;
                }
                
                beta = Math.min(beta, value);
            }
            
            return value;
        }
    }
}
