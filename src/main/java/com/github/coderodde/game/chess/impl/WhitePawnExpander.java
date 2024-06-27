package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.ChessBoardStateExpander;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.PlayerTurn;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements an expander for generating all white pawn moves.
 * 
 * @version 1.0.0 (Jun 26, 2024)
 * @since 1.0.0 (Jun 26, 2024)
 */
public final class WhitePawnExpander implements ChessBoardStateExpander {

    private static final int INITIAL_WHITE_PAWN_RANK = 6;
    private static final int INITIAL_WHITE_PAWN_MOVE_1_RANK = 5;
    private static final int INITIAL_WHITE_PAWN_MOVE_2_RANK = 4;
    private static final int EN_PASSANT_RANK = 3;
    
    @Override
    public List<ChessBoardState> expand(final ChessBoardState root, 
                                        final Piece piece) {
        
        final PlayerTurn playerTurn = piece.getPlayerTurn();
        final List<ChessBoardState> children = new ArrayList<>();
        final int pieceFile = piece.getFile();
     
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
        }
        
        if (piece.getRank() == EN_PASSANT_RANK) {
            if (pieceFile > 0) {
                // Try en passant to the left:
                tryEnPassantToLeft(pieceFile, children);
            }
            
            if (pieceFile < N - 1) {
                // Try en passant to the right:
                tryEnPassantToRight(pieceFile, children);
            }
        }
        
        return children;
    }
    
    private void tryEnPassantToLeft(final int pieceFile, 
                                    final List<ChessBoardState> children) {
        
    }
    
    private void tryEnPassantToRight(final int pieceFile, 
                                     final List<ChessBoardState> children) {
        
    }
}
