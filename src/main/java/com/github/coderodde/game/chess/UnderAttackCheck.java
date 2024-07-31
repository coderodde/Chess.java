package com.github.coderodde.game.chess;

/**
 * This abstract class defines the API for checking whether a piece is under 
 * attack.
 * 
 * @version 1.0.1 (Jul 20, 2024)
 * @since 1.0.0 (Jul 7, 2024)
 */
public abstract class UnderAttackCheck {
    
    /**
     * Returns {@code true} if and only if a piece at file {@code file} and rank
     * {@code rank} in state {@code state} is under attack.
     * 
     * @param state                   the state to check.
     * @param file                    the file of the piece to check.
     * @param rank                    the rank of the piece to check.
     * @param attackerCellCoordinates the attacker cell coordinates.
     * 
     * @return {@code true} if and only if the specified piece is under attack.
     */
    public abstract boolean check(
            final ChessBoardState state, 
            final int file,
            final int rank,
            final CellCoordinates attackerCellCoordinates);
}
