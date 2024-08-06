package com.github.coderodde.game.chess.impl.engine;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.PlayerTurn;
import com.github.coderodde.game.chess.AbstractGameEngine;
import com.github.coderodde.game.chess.AbstractHeuristicFunction;
import com.github.coderodde.game.chess.ThreeFoldRepetionRuleDrawException;
import java.util.List;
import java.util.Map;

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
            final AbstractHeuristicFunction heuristicFunction) {
        
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
        
        Map<ChessBoardState, Integer> boardStateFrequencyMap = 
                heuristicFunction.getStateFrequencyMap();
        
        boardStateFrequencyMap.put(
                bestMoveState, 
                boardStateFrequencyMap.getOrDefault(
                        bestMoveState, 
                        0) + 1);
        
        if (boardStateFrequencyMap.get(bestMoveState) == 3) {
            // Once here, the state 'bestMoveState' was visited three times.
            // End the game with a draw due to three-fold repetition rule.
            throw new ThreeFoldRepetionRuleDrawException();
        }
        
        return bestMoveState;
    }
        
    private void alphaBetaPruningRootImpl(final ChessBoardState root,
                                         final int depth,
                                         double alpha,
                                         double beta,
                                         final PlayerTurn playerTurn) {
        
        if (playerTurn == PlayerTurn.BLACK) {
            // Black is the maximizing player!
            double value = MINIMUM_SCORE;
            double tentativeValue = MINIMUM_SCORE;
            
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
                
//                System.out.println("Black " + value);
                
//                if (value > beta) {
//                    break;
//                }
                
                if (tentativeValue < value) {
                    tentativeValue = value;
                    bestMoveState = new ChessBoardState(child);
                }
                
                alpha = Math.max(alpha, value);
            }
        } else {
            // Once here, 'playerTurn' is 'WHITE': a minimizing player!
            double value = MAXIMUM_SCORE;
            double tentativeValue = MAXIMUM_SCORE;
            
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

//                if (value < alpha) {
//                    break;
//                }
                
                if (tentativeValue > value) {
                    tentativeValue = value;
                    bestMoveState = new ChessBoardState(child);
                }
                
                beta = Math.min(beta, value);
            }
        }
    }
    
    private double alphaBetaPruningImpl(final ChessBoardState root,
                                        final int depth,
                                        double alpha,
                                        double beta,
                                        final PlayerTurn playerTurn) {
        
        if (playerTurn == PlayerTurn.BLACK) {
            if (WHITE_CHECK_MATE_INSPECTOR.isInCheckMate(root)) {
                return MAXIMUM_SCORE + depth;
            }
        } else {
            if (BLACK_CHECK_MATE_INSPECTOR.isInCheckMate(root)) {
//                BLACK_CHECK_MATE_INSPECTOR.isInCheckMate(root);
                return MINIMUM_SCORE - depth;
            }
        }
        
        if (depth == 0) {
            return heuristicFunction.evaluate(root, depth);
        }
//        if (depth == 0) {
//            return heuristicFunction.evaluate(root, depth);
//        }
        
        // TODO: Check for stalemate here?
        
        if (playerTurn == PlayerTurn.BLACK) {
            // The black player is the maximizing player:
            double value = MINIMUM_SCORE;
            
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
            double value = MAXIMUM_SCORE;
            
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
