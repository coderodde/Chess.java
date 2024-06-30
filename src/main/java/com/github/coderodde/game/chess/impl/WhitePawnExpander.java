package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.CellType;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.PieceColor;
import com.github.coderodde.game.chess.PieceType;
import java.util.List;

/**
 * This class implements an expander for generating all white pawn moves.
 * 
 * @version 1.0.0 (Jun 26, 2024)
 * @since 1.0.0 (Jun 26, 2024)
 */
public final class WhitePawnExpander extends AbstractChessBoardStateExpander {

    public static final int INITIAL_WHITE_PAWN_RANK = 6;
    public static final int INITIAL_WHITE_PAWN_MOVE_1_RANK = 5;
    public static final int INITIAL_WHITE_PAWN_MOVE_2_RANK = 4;
    public static final int EN_PASSANT_SOURCE_RANK = 3;
    public static final int EN_PASSANT_TARGET_RANK = 2;
    public static final int PROMOTION_SOURCE_RANK = 1;
    public static final int PROMOTION_TARGET_RANK = 0;
    
    @Override
    public void expand(final ChessBoardState root, 
                       final Piece piece,
                       final int file,
                       final int rank,
                       final List<ChessBoardState> children) {
        
        if (rank == INITIAL_WHITE_PAWN_RANK 
                && root.get(file, INITIAL_WHITE_PAWN_MOVE_1_RANK) == null
                && root.get(file, INITIAL_WHITE_PAWN_MOVE_2_RANK) == null) {
            
            // Once here, we can move a white pawn two moves forward:
            final ChessBoardState child = new ChessBoardState(root);
            
            child.markWhitePawnInitialDoubleMove(file);
            
            child.clear(file, INITIAL_WHITE_PAWN_RANK);
            child.set(file, INITIAL_WHITE_PAWN_MOVE_2_RANK, piece);
            children.add(child);
            
            tryBasicMoveForward(root, 
                                children, 
                                file, 
                                rank, 
                                piece);
            return;
            
        } else if (rank == EN_PASSANT_SOURCE_RANK) {
            
            if (file > 0) {
                // Try en passant to the left:
                tryEnPassantToLeft(root, 
                                   piece, 
                                   file, 
                                   children);
            }
            
            if (file < N - 1) {
                // Try en passant to the right:
                tryEnPassantToRight(root,
                                    piece, 
                                    file,
                                    children);
            }
            
            tryBasicMoveForward(root,
                                children, 
                                file, 
                                rank, 
                                piece);
            return;
            
        } else if (rank == PROMOTION_SOURCE_RANK) {
            if (file > 0 && 
                root.getCellColor(file - 1,
                                  PROMOTION_TARGET_RANK) == CellType.BLACK) {
                
                // Once here, can capture to the left and promote:
                for (final PieceType pieceType : PROMOTION_PIECE_TYPES) {
                    final Piece newPiece = 
                            new Piece(
                                    PieceColor.WHITE,
                                    pieceType,
                                    this);
                    
                    final ChessBoardState child = new ChessBoardState(root);
                    
                    child.set(file - 1, PROMOTION_TARGET_RANK, newPiece);
                    child.clear(file, PROMOTION_SOURCE_RANK);
                    children.add(child);
                }
            }
            
            if (file < N - 1 &&
                root.getCellColor(file + 1,
                                  PROMOTION_TARGET_RANK) == CellType.BLACK) {
                
                // Once here, can capture to the right and promote:
                for (final PieceType pieceType : PROMOTION_PIECE_TYPES) {
                    final Piece newPiece = 
                            new Piece(
                                    PieceColor.WHITE,
                                    pieceType,
                                    this);
                    
                    final ChessBoardState child = new ChessBoardState(root);
                    
                    child.set(file + 1, PROMOTION_TARGET_RANK, newPiece);
                    child.clear(file, PROMOTION_SOURCE_RANK);
                    children.add(child);
                }
            }
            
            if (root.getCellColor(file, 
                                  PROMOTION_TARGET_RANK) == CellType.EMPTY) {
                
                // Once here, can move forward an promote:
                for (final PieceType pieceType : PROMOTION_PIECE_TYPES) {
                    final Piece newPiece = 
                            new Piece(
                                    PieceColor.WHITE,
                                    pieceType,
                                    this);
                    
                    final ChessBoardState child = new ChessBoardState(root);
                    
                    child.set(file, PROMOTION_TARGET_RANK, newPiece);
                    child.clear(file, PROMOTION_SOURCE_RANK);
                    children.add(child);
                }
            }
            
            return;
        }
        
        // Try move forward:
        tryBasicMoveForward(root, 
                            children, 
                            file, 
                            rank, 
                            piece);
        
        // Try capture to left:
        if (file > 0 
                && rank > 1 
                && root.getCellColor(file - 1, rank - 1) 
                == CellType.BLACK) {
            
            final ChessBoardState child = new ChessBoardState(root);
            
            child.set(file, rank, null);
            child.set(file - 1, rank - 1, piece);
            
            children.add(child);
        }
        
        // Try capture to right:
        if (file < N - 1
                && rank > 1
                && root.getCellColor(file + 1, rank - 1)
                == CellType.BLACK) {
            
            final ChessBoardState child = new ChessBoardState(root);
            
            child.set(file, rank, null);
            child.set(file + 1, rank - 1, piece);
            
            children.add(child);
        }
    }
    
    private void tryBasicMoveForward(final ChessBoardState root,
                                     final List<ChessBoardState> children,
                                     final int file,
                                     final int rank,
                                     final Piece piece) {
        if (rank > 1 && 
            root.getCellColor(file, rank - 1) == CellType.EMPTY) {
            
            final ChessBoardState child = new ChessBoardState(root);
            child.set(file, rank - 1, piece);
            child.set(file, rank, null);
            children.add(child);
        }
    }
    
    private void tryEnPassantToLeft(final ChessBoardState root,
                                    final Piece piece, 
                                    final int file,
                                    final List<ChessBoardState> children) {
        
        if (!root.getBlackIsPreviouslyDoubleMoved()[file - 1]) {
            return;
        }
        
        final ChessBoardState child = new ChessBoardState(root);
        
        child.clear(file, EN_PASSANT_SOURCE_RANK);
        child.clear(file - 1, EN_PASSANT_SOURCE_RANK);
        child.set(file - 1, EN_PASSANT_TARGET_RANK, piece);
        
        children.add(child);
    }
    
    private void tryEnPassantToRight(final ChessBoardState root,
                                     final Piece piece, 
                                     final int file,
                                     final List<ChessBoardState> children) {
        if (!root.getBlackIsPreviouslyDoubleMoved()[file + 1]) {
            return;
        }
        
        final ChessBoardState child = new ChessBoardState(root);
        
        child.clear(file, EN_PASSANT_SOURCE_RANK);
        child.clear(file + 1, EN_PASSANT_SOURCE_RANK);
        child.set(file + 1, EN_PASSANT_TARGET_RANK, piece);
        
        children.add(child);
    }
}
