package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.CellType;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.KNIGHT;
import java.util.List;

/**
 * This class implements an expander for generatinga all white knight moves.
 * 
 * @version 1.0.0 (Jun 30, 2024)
 * @since 1.0.0 (Jun 30, 2024)
 */
public final class WhiteKnightExapnder extends AbstractChessBoardStateExpander {

    @Override
    public void expand(final ChessBoardState state, 
                       final Piece piece, 
                       final int file, 
                       final int rank, 
                       final List<ChessBoardState> children) {
        
        tryGenerateNorthLeft (state, file, rank, children);
        tryGenerateNorthRight(state, file, rank, children);
        
        tryGenerateEastLeft (state, file, rank, children);
        
    }
    
    private void tryGenerateNorthLeft(final ChessBoardState state,
                                      final int file,
                                      final int rank,
                                      final List<ChessBoardState> children) {
        if (rank < 2) {
            return;
        }
        
        if (file == 0) {
            return;
        }
        
        if (state.getCellColor(file - 1, rank - 2) != CellType.WHITE) {
            // Once here, can move or capture:
            final ChessBoardState child = new ChessBoardState(state);
            
            child.clear(file, rank);
            child.set(file - 1, rank - 2, new Piece(WHITE, KNIGHT, this));
            
            children.add(child);
        }
    }
    
    private void tryGenerateNorthRight(final ChessBoardState state,
                                       final int file,
                                       final int rank,
                                       final List<ChessBoardState> children) {
        if (rank < 2) {
            return;
        }
        
        if (file == N - 1) {
            return;
        }
        
        if (state.getCellColor(file + 1, rank - 2) != CellType.WHITE) {
            // Once here, can move or capture:
            final ChessBoardState child = new ChessBoardState(state);
            
            child.clear(file, rank);
            child.set(file + 1, rank - 2, new Piece(WHITE, KNIGHT, this));
            
            children.add(child);
        }
    }
    
    private void tryGenerateEastLeft(final ChessBoardState state, 
                                     final int file, 
                                     final int rank, 
                                     final List<ChessBoardState> children) {
        if (rank > N - 3) {
            return;
        }
        
        if (file == 0) {
            return;
        }
        
        if (state.getCellColor(file - 1, rank + 2) != CellType.WHITE) {
            final ChessBoardState child = new ChessBoardState(state);
            
            child.clear(file, rank);
            child.set(file - 1, rank + 2, new Piece(WHITE, KNIGHT, this));
            
            children.add(child);
        }
    }
}
