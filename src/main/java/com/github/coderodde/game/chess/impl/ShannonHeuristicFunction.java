package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.AbstractHeuristicFunction;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.impl.attackcheck.BlackUnderAttackCheck;
import com.github.coderodde.game.chess.impl.attackcheck.WhiteUnderAttackCheck;
import com.github.coderodde.game.chess.UnderAttackCheck;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements a default heuristic function. It takes into account 
 * presence of each piece and wether each piece is under attack by a player of
 * opposite color.
 * 
 * @version 1.0.0 (Jul 15, 2024)
 * @since 1.0.0 (Jul 15, 2024)
 */
public final class ShannonHeuristicFunction extends AbstractHeuristicFunction {

    private static final UnderAttackCheck WHITE_CHECK = 
            new WhiteUnderAttackCheck();

    private static final UnderAttackCheck BLACK_CHECK = 
            new BlackUnderAttackCheck();
    
    private final Map<ChessBoardState, Integer> stateFrequencyMap = 
            new HashMap<>();
    
    @Override
    public double evaluate(final ChessBoardState state, final int depth) {
        
        if (stateFrequencyMap.containsKey(state) && 
            stateFrequencyMap.get(state) == 2) {
            
            System.out.println("Avoiding TFRR breakage...");
            // Try to postpone the breakage of the three-fold repetion rule as 
            // long as possible:
            return 0.0;
        }
        
        double score = 0;
        
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
        
        return score + pawnMaterial(state) + mobility(state); 
    }
    
    private double pawnMaterial(final ChessBoardState state) {
        throw new UnsupportedOperationException();
    }
    
    private double mobility(final ChessBoardState state) {
        
        for (int rank = 0; rank < N; rank++) {
            for (int file = 0; file < N; file++) {
                
                final Piece piece = state.get(file, rank);
                
                
            }
        }
        
        
        
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearStateFrequencyMap() {
        this.stateFrequencyMap.clear();
    }

    @Override
    public Map<ChessBoardState, Integer> getStateFrequencyMap() {
        return stateFrequencyMap;
    }
}
