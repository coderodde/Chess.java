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

    private static final int INITIAL_WHITE_PAWN_RANK = 6;
    private static final int INITIAL_WHITE_PAWN_MOVE_1_RANK = 5;
    private static final int INITIAL_WHITE_PAWN_MOVE_2_RANK = 4;
    private static final int EN_PASSANT_SOURCE_RANK = 3;
    private static final int EN_PASSANT_TARGET_RANK = 2;
    private static final int PROMOTION_SOURCE_RANK = 1;
    private static final int PROMOTION_TARGET_RANK = 0;
    
    @Override
    public void expand(final ChessBoardState root, 
                       final Piece piece,
                       final List<ChessBoardState> children) {
        
        final int pieceFile = piece.getFile();
        final int pieceRank = piece.getRank();
     
        if (piece.getRank() == INITIAL_WHITE_PAWN_RANK 
                && root.get(piece.getFile(), 
                            INITIAL_WHITE_PAWN_MOVE_1_RANK) == null
                && root.get(piece.getFile(), 
                            INITIAL_WHITE_PAWN_MOVE_2_RANK) == null) {
            
            // Once here, we can move a white pawn two moves forward:
            final ChessBoardState child = new ChessBoardState(root);
            
            child.markWhitePawnInitialDoubleMove(pieceFile);
            
            child.set(pieceFile, INITIAL_WHITE_PAWN_RANK, null);
            child.set(pieceFile, INITIAL_WHITE_PAWN_MOVE_2_RANK, piece);
            children.add(child);
            
            tryBasicMoveForward(root, 
                                children, 
                                pieceFile, 
                                pieceRank, 
                                piece);
            return;
            
        } else if (piece.getRank() == EN_PASSANT_SOURCE_RANK) {
            
            if (pieceFile > 0) {
                // Try en passant to the left:
                tryEnPassantToLeft(root, piece, children);
            }
            
            if (pieceFile < N - 1) {
                // Try en passant to the right:
                tryEnPassantToRight(root, piece, children);
            }
            
            tryBasicMoveForward(root,
                                children, 
                                pieceFile, 
                                pieceRank, 
                                piece);
            return;
            
        } else if (pieceRank == PROMOTION_SOURCE_RANK) {
            if (pieceFile > 0 && 
                root.getCellColor(pieceFile - 1,
                                  PROMOTION_TARGET_RANK) == CellType.BLACK) {
                
                // Once here, can capture to the left and promote:
                for (final PieceType pieceType : PROMOTION_PIECE_TYPES) {
                    final Piece newPiece = 
                            new Piece(
                                    PieceColor.WHITE, 
                                    pieceType, 
                                    pieceFile - 1, 
                                    PROMOTION_TARGET_RANK,
                                    this);
                    
                    final ChessBoardState child = new ChessBoardState(root);
                    
                    child.set(pieceFile - 1, PROMOTION_TARGET_RANK, newPiece);
                    child.clear(pieceFile, PROMOTION_SOURCE_RANK);
                    children.add(child);
                }
            }
            
            if (pieceFile < N - 1 &&
                root.getCellColor(pieceFile + 1,
                                  PROMOTION_TARGET_RANK) == CellType.BLACK) {
                
                // Once here, can capture to the right and promote:
                for (final PieceType pieceType : PROMOTION_PIECE_TYPES) {
                    final Piece newPiece = 
                            new Piece(
                                    PieceColor.WHITE,
                                    pieceType,
                                    pieceFile + 1,
                                    PROMOTION_TARGET_RANK,
                                    this);
                    
                    final ChessBoardState child = new ChessBoardState(root);
                    
                    child.set(pieceFile + 1, PROMOTION_TARGET_RANK, newPiece);
                    child.clear(pieceFile, PROMOTION_SOURCE_RANK);
                    children.add(child);
                }
            }
            
            if (root.getCellColor(pieceFile, 
                                  PROMOTION_TARGET_RANK) == CellType.EMPTY) {
                
                // Once here, can move forward an promote:
                for (final PieceType pieceType : PROMOTION_PIECE_TYPES) {
                    final Piece newPiece = 
                            new Piece(
                                    PieceColor.WHITE,
                                    pieceType,
                                    pieceFile,
                                    PROMOTION_TARGET_RANK,
                                    this);
                    
                    final ChessBoardState child = new ChessBoardState(root);
                    
                    child.set(pieceFile, PROMOTION_TARGET_RANK, newPiece);
                    child.clear(pieceFile, PROMOTION_SOURCE_RANK);
                    children.add(child);
                }
            }
            
            return;
        }
        
        // Try move forward:
        tryBasicMoveForward(root, 
                            children, 
                            pieceFile, 
                            pieceRank, 
                            piece);
        
        // Try capture to left:
        if (pieceFile > 0 
                && pieceRank > 1 
                && root.getCellColor(pieceFile - 1, pieceRank - 1) 
                == CellType.BLACK) {
            
            final ChessBoardState child = new ChessBoardState(root);
            
            child.set(pieceFile, pieceRank, null);
            child.set(pieceFile - 1, pieceRank - 1, piece);
            
            children.add(child);
        }
        
        // Try capture to right:
        if (pieceFile < N - 1
                && pieceRank > 1
                && root.getCellColor(pieceFile + 1, pieceRank - 1)
                == CellType.BLACK) {
            
            final ChessBoardState child = new ChessBoardState(root);
            
            child.set(pieceFile, pieceRank, null);
            child.set(pieceFile + 1, pieceRank - 1, piece);
            
            children.add(child);
        }
    }
    
    private void tryBasicMoveForward(final ChessBoardState root,
                                     final List<ChessBoardState> children,
                                     final int pieceFile,
                                     final int pieceRank,
                                     final Piece piece) {
        
        if (pieceRank > 1 && 
            root.getCellColor(pieceFile, pieceRank - 1) == CellType.EMPTY) {
            
            final ChessBoardState child = new ChessBoardState(root);
            child.set(pieceFile, pieceRank - 1, piece);
            child.set(pieceFile, pieceRank, null);
            children.add(child);
        }
    }
    
    private void tryEnPassantToLeft(final ChessBoardState root,
                                    final Piece piece, 
                                    final List<ChessBoardState> children) {
        if (!root.getBlackIsPreviouslyDoubleMoved()[piece.getFile()]) {
            return;
        }
        
        final ChessBoardState child = new ChessBoardState(root);
        final int file = piece.getFile();
        
        child.clear(file, EN_PASSANT_SOURCE_RANK);
        child.clear(file - 1, EN_PASSANT_SOURCE_RANK);
        child.set(file - 1, EN_PASSANT_TARGET_RANK, piece);
        
        children.add(child);
    }
    
    private void tryEnPassantToRight(final ChessBoardState root,
                                     final Piece piece, 
                                     final List<ChessBoardState> children) {
        if (!root.getBlackIsPreviouslyDoubleMoved()[piece.getFile()]) {
            return;
        }
        
        final ChessBoardState child = new ChessBoardState(root);
        final int file = piece.getFile();
        
        child.clear(file, EN_PASSANT_SOURCE_RANK);
        child.clear(file + 1, EN_PASSANT_SOURCE_RANK);
        child.set(file + 1, EN_PASSANT_TARGET_RANK, piece);
        
        children.add(child);
    }
}
