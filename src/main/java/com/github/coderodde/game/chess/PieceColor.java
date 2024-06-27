package com.github.coderodde.game.chess;

/** 
 * This enumeration encodes the color of a piece.
 * 
 * @version 1.0.0 (Jun 27, 2024)
 * @since 1.0.0 (Jun 27, 2024)
 */
public enum PieceColor {
   
    WHITE((byte) 0b01000000),
    BLACK((byte) 0b10000000);
    
    private final byte colorCodeBits;
    
    private PieceColor(final byte colorCodeBits) {
        this.colorCodeBits = colorCodeBits;
    }
    
    public byte getPieceColorCodeBits() {
        return colorCodeBits;
    }
}
