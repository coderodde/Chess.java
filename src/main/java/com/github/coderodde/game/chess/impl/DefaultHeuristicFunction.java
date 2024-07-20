package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.HeuristicFunction;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.impl.attackcheck.BlackUnderAttackCheck;
import com.github.coderodde.game.chess.impl.attackcheck.WhiteUnderAttackCheck;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 * This class implements a default heuristic function. It takes into account 
 * presence of each piece and wether each piece is under attack by a player of
 * opposite color.
 * 
 * @version 1.0.0 (Jul 15, 2024)
 * @since 1.0.0 (Jul 15, 2024)
 */
public final class DefaultHeuristicFunction implements HeuristicFunction {

    private static final UnderAttackCheck WHITE_CHECK = 
            new WhiteUnderAttackCheck();

    private static final UnderAttackCheck BLACK_CHECK = 
            new BlackUnderAttackCheck();
    
    @Override
    public int evaluate(final ChessBoardState state, final int depth) {
        int score = 0;
        
        for (int rank = 0; rank < N; rank++) {
            for (int file = 0; file < N; file++) {
                final Piece piece = state.get(file, rank);
                
                if (piece == null) {
                    continue;
                }
                
                if (piece.isWhite()) {
                    score -= piece.getPresenceScore();
                    
                    if (WHITE_CHECK.check(state, file, rank)) {
                        score += piece.getVulnerabilityScore();
                    }
                } else {
                    score += piece.getPresenceScore();
                    
                    if (BLACK_CHECK.check(state, file, rank)) {
                        score -= piece.getVulnerabilityScore();
                    }
                }
            }
        }
        
        return score;
    }
}
