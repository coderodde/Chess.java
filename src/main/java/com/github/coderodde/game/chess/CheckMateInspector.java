package com.github.coderodde.game.chess;

/**
 * This interface defines the API for checking whether a board state is in 
 * checkmate.
 * 
 * @version 1.0.0 (Jul 16, 2024)
 * @since 1.0.0 (Jul 16, 2024)
 */
public interface CheckMateInspector {
    
    /**
     * Checks whether the state is in checkmate.
     * 
     * @param state the target state to check.
     * 
     * @return {@code true} if and only if the input state is in checkmate.
     */
    public boolean isInCheckMate(final ChessBoardState state);
}
