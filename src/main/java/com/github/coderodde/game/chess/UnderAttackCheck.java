package com.github.coderodde.game.chess;

/**
 * 
 * @version 1.0.0 (Jul 7, 2024)
 * @since 1.0.0 (Jul 7, 2024)
 */
public interface UnderAttackCheck {
    
    /**
     * Holds the coordinates of the attacking piece.
     */
    public static final class CellCoordinates {
        
        /**
         * Specifies that a piece is not under attack.
         */
        public static final int NO_ATTACK_FILE = -1;
        
        /**
         * The file of the attacking piece.
         */
        public int file;
        
        /**
         * The rank of the attacking piece.
         */
        public int rank;
    }
    
    public static final CellCoordinates ATTACKER_COORDINATES = 
            new CellCoordinates();
    
    public boolean check(final ChessBoardState state, 
                         final int file,
                         final int rank);
}
