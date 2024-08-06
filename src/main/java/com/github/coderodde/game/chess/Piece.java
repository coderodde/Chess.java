package com.github.coderodde.game.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class implements the data type for representing a piece: piece type,
 * color, expander and position.
 * 
 * @version 1.0.0 (Jun 27, 2024)
 * @since 1.0.0 (Jun 27, 2024)
 */
public final class Piece {
    
    public static final byte EMPTY = 0;
    
    public static final byte WHITE_COLOR = (byte) 0b01000000;
    public static final byte BLACK_COLOR = (byte) 0b10000000;
    
    public static final byte WHITE_PAWN = (byte) WHITE_COLOR | 0b00000001;
    public static final byte BLACK_PAWN = (byte) BLACK_COLOR | 0b00000001;
    
    public static final byte WHITE_BISHOP = (byte) WHITE_COLOR | 0b00000010;
    public static final byte BLACK_BISHOP = (byte) BLACK_COLOR | 0b00000010;
    
    public static final byte WHITE_KNIGHT = (byte) WHITE_COLOR | 0b00000100;
    public static final byte BLACK_KNIGHT = (byte) BLACK_COLOR | 0b00000100;
    
    public static final byte WHITE_ROOK = (byte) WHITE_COLOR | 0b00001000;
    public static final byte BLACK_ROOK = (byte) BLACK_COLOR | 0b00001000;
    
    public static final byte WHITE_QUEEN = (byte) WHITE_COLOR | 0b00010000;
    public static final byte BLACK_QUEEN = (byte) BLACK_COLOR | 0b00010000;
    
    public static final byte WHITE_KING = (byte) WHITE_COLOR | 0b00100000;
    public static final byte BLACK_KING = (byte) BLACK_COLOR | 0b00100000;
           
    private final PieceColor pieceColor;
    private final PieceType pieceType;
    private final AbstractChessBoardStateExpander expander;
    private double presenceScore;
    private double vulnerabilityScore;
    
    public Piece(final Piece other) {
        this(other.pieceColor,
             other.pieceType,
             other.expander);
    }
    
    /**
     * The minimal constructor for a chess piece. Defined for the sake of unit
     * testing.
     * 
     * @param pieceColor the color of the piece.
     * @param pieceType  the type of the piece.
     */
    public Piece(final PieceColor pieceColor,
                 final PieceType pieceType) {
        
        this(pieceColor, pieceType, null);
    }
    
    public Piece(final PieceColor pieceColor,
                 final PieceType pieceType,
                 final AbstractChessBoardStateExpander expander) {
        
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        this.expander = expander;
        
        switch (pieceType) {
            case PAWN -> {
                this.presenceScore = 1.0;
                this.vulnerabilityScore = 0.5;
            }
                
            case ROOK -> {
                this.presenceScore = 5.0;
                this.vulnerabilityScore = 2.5;
            }
                
            case BISHOP -> {
                this.presenceScore = 3.0;
                this.vulnerabilityScore = 1.5;
            }
                
            case KNIGHT -> {
                this.presenceScore = 3.0;
                this.vulnerabilityScore = 1.5;
            }
                
            case QUEEN -> {
                this.presenceScore = 9.0;
                this.vulnerabilityScore = 4.5;
            }
                
            case KING -> {
                this.presenceScore = 500.0;
                this.vulnerabilityScore = 250.0;
            }
                
            default -> {
                this.presenceScore = -1;
                this.vulnerabilityScore = -1;
                throw new IllegalStateException("Should not get here.");
            }
        }
    }
    
    public Piece(final Piece other,
                 final AbstractChessBoardStateExpander expander) {
        
        this.pieceColor = other.pieceColor;
        this.pieceType = other.pieceType;
        this.expander = expander;
    }
    
    public double getPresenceScore() {
        return presenceScore;
    }
    
    public double getVulnerabilityScore() {
        return vulnerabilityScore;
    }
    
    public AbstractChessBoardStateExpander getChessBoardStateExpander() {
        return expander;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Piece)) {
            return false;
        }
        
        final Piece other = (Piece) o;
        
        return this.pieceColor == other.pieceColor &&
               this.pieceType  == other.pieceType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        
        hash = 59 * hash 
                  + Objects.hashCode(pieceColor.getPieceColorCodeBits());
        
        hash = 59 * hash 
                  + Objects.hashCode(pieceType.getPieceTypeCodeBits());
        return hash;
    }
    
    public byte getPieceCodeBits() {
        return (byte)(pieceColor.getPieceColorCodeBits() | 
                      pieceType.getPieceTypeCodeBits());
    }
    
    public PieceColor getPieceColor() {
        return pieceColor;
    }
    
    public PieceType getPieceType() {
        return pieceType;
    }
    
    public boolean isWhite() {
        return (byte)(pieceColor.getPieceColorCodeBits() & WHITE_COLOR) != 0;
    }
    
    public boolean isBlack() {
        return (byte)(pieceColor.getPieceColorCodeBits() & BLACK_COLOR) != 0;
    }
    
    public List<ChessBoardState> expand(final ChessBoardState state,
                                        final int file, 
                                        final int rank) {
        
        final List<ChessBoardState> children = new ArrayList<>();
        
        if (expander == null) {
            final StackTraceElement[] stackTrace = 
                    Thread.currentThread().getStackTrace();
            
            for (final StackTraceElement s : stackTrace) {
                System.out.println(s);
            }
            
            System.exit(1);
        }
        
        expander.expand(state, 
                        this, 
                        file, 
                        rank, 
                        children);
        
        return children;
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
                
            case WHITE_PAWN   -> "P";
            case WHITE_KNIGHT -> "N";
            case WHITE_BISHOP -> "B";
            case WHITE_ROOK   -> "R";
            case WHITE_QUEEN  -> "Q";
            case WHITE_KING   -> "K";
                
            case BLACK_PAWN   -> "p";
            case BLACK_KNIGHT -> "n";
            case BLACK_BISHOP -> "b";
            case BLACK_ROOK   -> "r";
            case BLACK_QUEEN  -> "q";
            case BLACK_KING   -> "k";
                
            default -> throw new IllegalStateException("Should not get here.");
        };
    }
}
