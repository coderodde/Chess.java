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
     * This field holds the coordinates of the attacking piece. If the piece is
     * not threatened, {@code ATTACKER_COORDINATES.file} is set to 
     * {@link com.github.coderodde.game.chess.CellCoordinates#NO_ATTACK_FILE}.
     */
    public static final CellCoordinates ATTACKER_COORDINATES = 
            new CellCoordinates(CellCoordinates.NO_ATTACK_FILE, 0);
    
    /**
     * Returns {@code true} if and only if a piece at file {@code file} and rank
     * {@code rank} in state {@code state} is under attack.
     * 
     * @param state the state to check.
     * @param file  the file of the piece to check.
     * @param rank  the rank of the piece to check.
     * 
     * @return {@code true} if and only if the specified piece is under attack.
     */
    public abstract boolean check(final ChessBoardState state, 
                                  final int file,
                                  final int rank);
}
