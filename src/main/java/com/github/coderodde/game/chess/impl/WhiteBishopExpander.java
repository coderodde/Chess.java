package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.CellType;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import java.util.List;

public class WhiteBishopExpander extends AbstractChessBoardStateExpander {

    @Override
    public void expand(final ChessBoardState state, 
                       final Piece piece,
                       final int file, 
                       final int rank, 
                       final List<ChessBoardState> children) {
     
        tryGenerateNorthWest(state,
                             file,
                             rank, 
                             children);
    }
    
    private void tryGenerateNorthWest(final ChessBoardState state,
                                      final int file,
                                      final int rank,
                                      final List<ChessBoardState> children) {
        
        for (int index = 1, endIndex = Math.min(file, rank); 
                 index <= endIndex;
                 index++) {
            
            final CellType cellType = state.getCellType(file - index,
                                                        rank - index);
            
            if (cellType == CellType.BLACK) {
                // Can capture:
                final ChessBoardState child = new ChessBoardState(state);
                
                child.set(file - index, 
                          rank - index, 
                          child.get(file,
                                    rank));
                
                child.clear(file,
                            rank);
                
                children.add(child);
                return;
            }
            
            if (cellType == CellType.WHITE) {
                // Cannot move further because of obstruction:
            }
            
            final ChessBoardState child = new ChessBoardState(state);
            
            child.set(file - index, 
                      rank - index,
                      child.get(file,
                                rank));
            
            child.clear(file, 
                        rank);
            
            children.add(child);
        }
    }
}
