package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 *
 * @version 1.0.0 (Jul 7, 2024)
 * @since 1.0.0 (Jul 7, 2024)
 */
public final class WhiteUnderAttackCheck implements UnderAttackCheck {

    private static final UnderAttackCheck WHITE_UNDER_ATTACK_BY_BLACK_QUEEN = 
            new WhiteUnderAttackCheckByBlackQueen();
    
    private static final UnderAttackCheck WHITE_UNDER_ATTACK_BY_BLACK_BISHOP = 
            new WhiteUnderAttackCheckByBlackBishop();
    
    @Override
    public boolean check(final ChessBoardState state, 
                         final int file,
                         final int rank) {
        
        return WHITE_UNDER_ATTACK_BY_BLACK_QUEEN.check(state, 
                                                       file, 
                                                       rank)
                ||
                WHITE_UNDER_ATTACK_BY_BLACK_BISHOP.check(state, 
                                                         file, 
                                                         rank);
    }
}
