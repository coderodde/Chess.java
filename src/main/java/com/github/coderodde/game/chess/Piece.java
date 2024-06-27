package com.github.coderodde.game.chess;

import java.util.List;

/**
 * This class implements the data type for representing a piece: piece type,
 * color, expander and position.
 * 
 * @version 1.0.0 (Jun 27, 2024)
 * @since 1.0.0 (Jun 27, 2024)
 */
public final class Piece {
    
    private static final byte EMPTY = 0;
    
    public static final byte WHITE_PAWN = (byte) 0b01000000 | 0b00000001;
    private static final byte BLACK_PAWN = (byte) 0b10000000 | 0b00000001;
    
    private static final byte WHITE_BISHOP = (byte) 0b01000000 | 0b00000010;
    private static final byte BLACK_BISHOP = (byte) 0b10000000 | 0b00000010;
    
    private static final byte WHITE_KNIGHT = (byte) 0b01000000 | 0b00000100;
    private static final byte BLACK_KNIGHT = (byte) 0b10000000 | 0b00000100;
    
    private static final byte WHITE_ROOK = (byte) 0b01000000 | 0b00001000;
    private static final byte BLACK_ROOK = (byte) 0b10000000 | 0b00001000;
    
    private static final byte WHITE_QUEEN = (byte) 0b01000000 | 0b00010000;
    private static final byte BLACK_QUEEN = (byte) 0b10000000 | 0b00010000;
    
    private static final byte WHITE_KING = (byte) 0b01000000 | 0b00100000;
    private static final byte BLACK_KING = (byte) 0b10000000 | 0b00100000;
           
    private final PieceColor pieceColor;
    private final PieceType pieceType;
    private int rank;
    private int file;
    private final ChessBoardStateExpander expander;
    
    public Piece(final PieceColor pieceColor,
                 final PieceType pieceType,
                 final int rank,
                 final int file,
                 final ChessBoardStateExpander expander) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        this.rank = rank;
        this.file = file;
        this.expander = expander;
    }
    
    public Piece(final Piece other,
                 final int file, 
                 final int rank, 
                 final ChessBoardStateExpander expander) {
        
        this.pieceColor = other.pieceColor;
        this.pieceType = other.pieceType;
        this.rank = rank;
        this.file = file;
        this.expander = expander;
    }
    
    public int getFile() {
        return file;
    }
    
    public int getRank() {
        return rank;
    }
    
    public ChessBoardStateExpander getChessBoardStateExpander() {
        return expander;
    }
    
    public void moveTo(final int file, final int rank) {
        this.file = file;
        this.rank = rank;
    }
    
    public byte getPieceCodeBits() {
        return (byte)(pieceColor.getPieceColorCodeBits() | 
                      pieceType.getPieceTypeCodeBits());
    }
    
    public List<ChessBoardState> expand(final ChessBoardState state) {
        return expander.expand(state, this);
    }
    
    public PlayerTurn getPlayerTurn() {
        if ((byte)(pieceColor.getPieceColorCodeBits() & 
             PieceColor.WHITE.getPieceColorCodeBits()) != 0) {
            
            return PlayerTurn.WHITE;
        }
        
        if ((byte)(pieceColor.getPieceColorCodeBits() &
             PieceColor.BLACK.getPieceColorCodeBits()) != 0) {
            
            return PlayerTurn.BLACK;
        }
        
        throw new IllegalStateException("Should not get here.");
    }
    
    @Override
    public String toString() {
        final byte pieceCode = getPieceCodeBits();
        
        return switch (pieceCode) {
            case EMPTY        -> (file + rank) % 2 == 0 ? "." : "#";
                
            case WHITE_PAWN   -> "P";
            case WHITE_KNIGHT -> "K";
            case WHITE_BISHOP -> "B";
            case WHITE_ROOK   -> "R";
            case WHITE_QUEEN  -> "Q";
            case WHITE_KING   -> "X";
                
            case BLACK_PAWN   -> "p";
            case BLACK_KNIGHT -> "k";
            case BLACK_BISHOP -> "b";
            case BLACK_ROOK   -> "r";
            case BLACK_QUEEN  -> "q";
            case BLACK_KING   -> "x";
                
            default -> throw new IllegalStateException("Should not get here.");
        };
    }
}
