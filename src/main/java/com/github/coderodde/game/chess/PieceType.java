package com.github.coderodde.game.chess;

/**
 *
 * @version 1.0.0 (Jun 26, 2024)
 * @since 1.0.0 (Jun 26, 2024)
 */
public enum PieceType {
    PAWN  ((byte) 0b000001),
    BISHOP((byte) 0b000010),
    KNIGHT((byte) 0b000100),
    ROOK  ((byte) 0b001000),
    QUEEN ((byte) 0b010000),
    KING  ((byte) 0b100000);
    
    private PieceType(final byte pieceCodeBits) {
        this.pieceCodeBits = pieceCodeBits;
    }
    
    public byte getPieceTypeCodeBits() {
        return pieceCodeBits;
    }
    
    private final byte pieceCodeBits;
}
