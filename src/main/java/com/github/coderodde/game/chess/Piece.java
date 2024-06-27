package com.github.coderodde.game.chess;

import static com.github.coderodde.game.chess.ChessBoardState.BLACK_BISHOP;
import static com.github.coderodde.game.chess.ChessBoardState.BLACK_KING;
import static com.github.coderodde.game.chess.ChessBoardState.BLACK_KNIGHT;
import static com.github.coderodde.game.chess.ChessBoardState.BLACK_PAWN;
import static com.github.coderodde.game.chess.ChessBoardState.BLACK_QUEEN;
import static com.github.coderodde.game.chess.ChessBoardState.BLACK_ROOK;
import static com.github.coderodde.game.chess.ChessBoardState.EMPTY;
import static com.github.coderodde.game.chess.ChessBoardState.WHITE_BISHOP;
import static com.github.coderodde.game.chess.ChessBoardState.WHITE_KING;
import static com.github.coderodde.game.chess.ChessBoardState.WHITE_KNIGHT;
import static com.github.coderodde.game.chess.ChessBoardState.WHITE_PAWN;
import static com.github.coderodde.game.chess.ChessBoardState.WHITE_QUEEN;
import static com.github.coderodde.game.chess.ChessBoardState.WHITE_ROOK;
import java.util.List;

/**
 * This class implements the data type for representing a piece: piece type,
 * color, expander and position.
 * 
 * @version 1.0.0 (Jun 27, 2024)
 * @since 1.0.0 (Jun 27, 2024)
 */
public final class Piece {
    
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
        final byte pieceCode = this.getPieceCodeBits();
        
        return switch (pieceCode) {
            case EMPTY -> (file + rank) 
                return (file + rank) % 2 == 0 ? '.' : '#';
            }
                
            case WHITE_PAWN -> {
                return 'P';
            }
                
            case WHITE_KNIGHT -> {
                return 'K';
            }
                
            case WHITE_BISHOP -> {
                return 'B';
            }
                
            case WHITE_ROOK -> {
                return 'R';
            }
                
            case WHITE_QUEEN -> {
                return 'Q';
            }
                
            case WHITE_KING -> {
                return 'X';
            }
                
            case BLACK_PAWN -> {
                return 'p';
            }
                
            case BLACK_KNIGHT -> {
                return 'k';
            }
                
            case BLACK_BISHOP -> {
                return 'b';
            }
                
            case BLACK_ROOK -> {
                return 'r';
            }
                
            case BLACK_QUEEN -> {
                return 'q';
            }
                
            case BLACK_KING -> {
                return 'x';
            }
                
            default -> throw new IllegalStateException("Should not get here.");
        }
    }
}
