package com.github.coderodde.game.chess.impl.engine;

import com.github.coderodde.game.chess.AbstractGameEngine;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.HeuristicFunction;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.List;

/**
 * This class implements the Negamax game engine.
 * 
 * @version 1.0.0 (Jul 18, 2024)
 * @since 1.0.0 (Jul 18, 2024)
 */
public final class NegamaxGameEngine extends AbstractGameEngine {

    private static final int WHITE_MINIMIZING_COLOR = -1;
    private static final int BLACK_MAXIMIZING_COLOR = +1;
    
    /**
     * Constructs this Negamax-based game engine.
     * 
     * @param heuristicFunction the heuristic function to use.
     */
    public NegamaxGameEngine(final HeuristicFunction heuristicFunction) {
        
        super(heuristicFunction);
    }
    
    @Override
    public ChessBoardState search(final ChessBoardState root, 
                                  final int depth,
                                  final PlayerTurn playerTurn) {
    
        bestMoveState = null;
        
        negamaxRootImpl(root, 
                        depth,
                        MINIMUM_SCORE,
                        MAXIMUM_SCORE,
                        playerTurnToColor(playerTurn));
        
        return bestMoveState;
    }
    
    private void negamaxRootImpl(final ChessBoardState root,
                                 final int depth,
                                 int alpha,
                                 int beta,
                                 final int color) {
        
        int value = MINIMUM_SCORE;
        
        final List<ChessBoardState> children = root.expand(colorToPlayerTurn(color));
        
        for (final ChessBoardState child : children) {
            final int score = -negamaxImpl(child,
                                           depth - 1,
                                           -beta,
                                           -alpha,
                                           -color);
            
            
        }
    }
    
    private int negamaxImpl(final ChessBoardState root,
                            final int depth,
                            int alpha,
                            int beta,
                            final int color) {
        if (depth == 0) {
            return color * heuristicFunction.evaluate(root, depth);
        }
        
        if (color == WHITE_MINIMIZING_COLOR) {
            if (root.isCheckMate(PlayerTurn.WHITE)) {
                // '- depth': Prefer winning for white player higher in the
                // tree:
                return MINIMUM_SCORE - depth;
            }
        } else if (root.isCheckMate(PlayerTurn.BLACK)) {
            // '+ depth': Prefer winning for black player higher in the
            // tree:
            return MAXIMUM_SCORE + depth;
        }
        
        final PlayerTurn currentPlayerTurn = 
                color == BLACK_MAXIMIZING_COLOR ?
                PlayerTurn.BLACK :
                PlayerTurn.WHITE;
        
        final List<ChessBoardState> children = 
                root.expand(currentPlayerTurn);
        
        if (children.isEmpty()) {
            if (color == BLACK_MAXIMIZING_COLOR) {
                return MAXIMUM_SCORE - depth;
            } else {
                return MINIMUM_SCORE + depth;
            }
        }
        
        int value = MINIMUM_SCORE;
        
        for (final ChessBoardState child : children) {
            value = Math.max(value,
                             -negamaxImpl(root,
                                          depth - 1,
                                          -beta,
                                          -alpha,
                                          -color));
            
            alpha = Math.max(alpha, value);
            
            if (alpha >= beta) {
                break;
            }
        }
        
        return value;
    }
    
    private static PlayerTurn colorToPlayerTurn(final int color) {
        return color == WHITE_MINIMIZING_COLOR ?
                PlayerTurn.WHITE :
                PlayerTurn.BLACK;
    }
    
    private static int playerTurnToColor(final PlayerTurn playerTurn) {
        return playerTurn == PlayerTurn.WHITE ?
                WHITE_MINIMIZING_COLOR :
                BLACK_MAXIMIZING_COLOR;
    }
}