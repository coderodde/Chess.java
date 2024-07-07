package com.github.coderodde.game.chess;

/**
 * 
 * @version 1.0.0 (Jul 7, 2024)
 * @since 1.0.0 (Jul 7, 2024)
 */
public interface UnderAttackCheck {
    
    public boolean check(final ChessBoardState state, 
                         final int file,
                         final int rank);
}
