package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.CellType;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import java.util.List;

public class WhiteBishopExpander extends AbstractChessBoardStateExpander {

    @Override
    public void expand(final ChessBoardState state, 
                       final Piece piece,
                       final int file, 
                       final int rank, 
                       final List<ChessBoardState> children) {
     
        // Generate children upwards to the left:
        tryGenerateNorthWest(state,
                             file,
                             rank, 
                             children);
        
        // Generate children upwards to the right:
        tryGenerateNorthEast(state,
                             file,
                             rank,
                             children);
        
        // Generate children downwards to the left:
        tryGenerateSouthWest(state,
                             file, 
                             rank,
                             children);
        
        // Generate children downwards to the right:
        tryGenerateSouthEast(state,
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
    
    private void tryGenerateNorthEast(final ChessBoardState state,
                                      final int file,
                                      final int rank,
                                      final List<ChessBoardState> children) {
        
        for (int index = 1, endIndex = Math.min(N - file - 1, rank); 
                 index <= endIndex;
                 index++) {
            
            final CellType cellType = state.getCellType(file + index,
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
    
    private void tryGenerateSouthWest(final ChessBoardState state,
                                      final int file,
                                      final int rank,
                                      final List<ChessBoardState> children) {
                                                                 
        for (int index = 1, endIndex = Math.min(file, N - 1 - rank); 
                 index <= endIndex;
                 index++) {
            
            final CellType cellType = state.getCellType(file - index,
                                                        rank + index);
            
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
    
    private void tryGenerateSouthEast(final ChessBoardState state,
                                      final int file,
                                      final int rank,
                                      final List<ChessBoardState> children) {
                                                                 
        for (int index = 1, endIndex = Math.min(N - 1 - file, N - 1 - rank); 
                 index <= endIndex;
                 index++) {
            
            final CellType cellType = state.getCellType(file + index,
                                                        rank + index);
            
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
