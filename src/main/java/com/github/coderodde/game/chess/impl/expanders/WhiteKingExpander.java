package com.github.coderodde.game.chess.impl.expanders;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.CellType;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import java.util.List;

/**
 * This class implements an expander for generating all white king moves.
 * 
 * @version 1.0.0 (Jul 1, 2024)
 * @since 1.0.0 (Jul 1, 2024)
 */
public final class WhiteKingExpander extends AbstractChessBoardStateExpander {

    @Override
    public void expand(final ChessBoardState state,
                       final Piece piece,
                       final int file,
                       final int rank, 
                       final List<ChessBoardState> children) {
        
        if (file > 0) {
            // Once here, can move to the left:
            if (rank > 0 && state.getCellType(file - 1, 
                                              rank - 1) != CellType.WHITE) {
                
                // Once here, can move to left upwards:
                children.add(
                        move(state, 
                             file, 
                             rank, 
                             file - 1, 
                             rank - 1));
            }
            
            if (rank < N - 1 && state.getCellType(file - 1, 
                                                  rank + 1) != CellType.WHITE) {
                
                // Once here, can move to left downwards:
                children.add(
                        move(state, 
                             file, 
                             rank, 
                             file - 1, 
                             rank + 1));
            }
            
            // Move to the left:
            if (state.getCellType(file - 1, rank) != CellType.WHITE) {
                children.add(
                        move(state, 
                             file, 
                             rank, 
                             file - 1, 
                             rank));    
            }
        } 
        
        if (file < N - 1) {
            // Once here, can move to the right:
            if (rank > 0 && state.getCellType(file + 1, 
                                              rank - 1) != CellType.WHITE) {
                
                // Once here, can move to right upwards:
                children.add(
                        move(state, 
                             file, 
                             rank, 
                             file + 1, 
                             rank - 1));    
            }
            
            if (rank < N - 1 && state.getCellType(file + 1, 
                                                  rank + 1) != CellType.WHITE) {
                // Once here, can move to right downwards:
                children.add(
                        move(state, 
                             file, 
                             rank, 
                             file + 1, 
                             rank + 1));
            }
            
            // Move to the right:
            if (state.getCellType(file + 1, rank) != CellType.WHITE) {
                children.add(
                        move(state, 
                             file, 
                             rank, 
                             file + 1, 
                             rank));
            }
        }
        
        if (rank > 0 && state.getCellType(file, rank - 1) != CellType.WHITE) {
            // Move upwards:
            children.add(
                    move(state, 
                         file, 
                         rank, 
                         file, 
                         rank - 1));
        }
        
        if (rank < N - 1 && state.getCellType(file, 
                                              rank + 1) != CellType.WHITE) {
            // Move downwards:
            children.add(
                    move(state, 
                         file, 
                         rank, 
                         file, 
                         rank + 1));
        }
    }
    
    private static ChessBoardState move(final ChessBoardState state, 
                                        final int sourceFile,
                                        final int sourceRank,
                                        final int targetFile,
                                        final int targetRank) {
        
        final ChessBoardState moveState = new ChessBoardState(state);
        
        moveState.set(targetFile, 
                      targetRank, 
                      state.get(sourceFile, 
                                sourceRank));
        
        moveState.clear(sourceFile,
                        sourceRank);
        
        state.setWhiteKingFile(targetFile);
        state.setWhiteKingRank(targetRank);
        
        return moveState;
    }
}
