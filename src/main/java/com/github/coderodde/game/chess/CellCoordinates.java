package com.github.coderodde.game.chess;

/**
 * This class implements a simple structure for holding chess board cell 
 * coordinates.
 * 
 * @version 1.0.0 (Jul 20, 2024)
 * @since 1.0.0 (Jul 20, 2024)
 */
public final class CellCoordinates {
   
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

    public CellCoordinates(final int file, final int rank) {
        this.file = file;
        this.rank = rank;
    }

    @Override
    public boolean equals(final Object o) {
        final CellCoordinates other = (CellCoordinates) o;
        return other.file == file &&
               other.rank == rank;
    } 
}
