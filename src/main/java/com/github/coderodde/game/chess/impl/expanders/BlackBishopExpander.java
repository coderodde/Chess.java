package com.github.coderodde.game.chess.impl.expanders;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.CellType;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import java.util.List;

public class BlackBishopExpander extends AbstractChessBoardStateExpander {

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
    
    /**
     * Tries to generate the next moves while moving upwards to the left.
     * 
     * @param state    the initial state.
     * @param file     the file of the starting position.
     * @param rank     the rank of the starting position.
     * @param children the child move container.
     */
    private void tryGenerateNorthWest(final ChessBoardState state,
                                      final int file,
                                      final int rank,
                                      final List<ChessBoardState> children) {
        
        for (int index = 1, endIndex = Math.min(file, rank); 
                 index <= endIndex;
                 index++) {
            
            final CellType cellType = state.getCellType(file - index,
                                                        rank - index);
            
            if (cellType == CellType.WHITE) {
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
            
            if (cellType == CellType.BLACK) {
                // Cannot move further because of obstruction:
                return;
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
    
    /**
     * Tries to generate the next moves while moving upwards to the right.
     * 
     * @param state    the initial state.
     * @param file     the file of the starting position.
     * @param rank     the rank of the starting position.
     * @param children the child move container.
     */
    private void tryGenerateNorthEast(final ChessBoardState state,
                                      final int file,
                                      final int rank,
                                      final List<ChessBoardState> children) {
        
        for (int index = 1, endIndex = Math.min(N - file - 1, rank); 
                 index <= endIndex;
                 index++) {
            
            final CellType cellType = state.getCellType(file + index,
                                                        rank - index);
            
            if (cellType == CellType.WHITE) {
                // Can capture:
                final ChessBoardState child = new ChessBoardState(state);
                
                child.set(file + index, 
                          rank - index, 
                          child.get(file,
                                    rank));
                
                child.clear(file,
                            rank);
                
                children.add(child);
                return;
            }
            
            if (cellType == CellType.BLACK) {
                // Cannot move further because of obstruction:
                return;
            }
            
            final ChessBoardState child = new ChessBoardState(state);
            
            child.set(file + index, 
                      rank - index,
                      child.get(file,
                                rank));
            
            child.clear(file, 
                        rank);
            
            children.add(child);
        }
    }
    
    /**
     * Tries to generate the next moves while moving downwards to the left.
     * 
     * @param state    the initial state.
     * @param file     the file of the starting position.
     * @param rank     the rank of the starting position.
     * @param children the child move container.
     */
    private void tryGenerateSouthWest(final ChessBoardState state,
                                      final int file,
                                      final int rank,
                                      final List<ChessBoardState> children) {
                                                                 
        for (int index = 1, endIndex = Math.min(file, N - 1 - rank); 
                 index <= endIndex;
                 index++) {
            
            final CellType cellType = state.getCellType(file - index,
                                                        rank + index);
            
            if (cellType == CellType.WHITE) {
                // Can capture:
                final ChessBoardState child = new ChessBoardState(state);
                
                child.set(file - index, 
                          rank + index, 
                          child.get(file,
                                    rank));
                
                child.clear(file,
                            rank);
                
                children.add(child);
                return;
            }
            
            if (cellType == CellType.BLACK) {
                // Cannot move further because of obstruction:
                return;
            }
            
            final ChessBoardState child = new ChessBoardState(state);
            
            child.set(file - index, 
                      rank + index,
                      child.get(file,
                                rank));
            
            child.clear(file, 
                        rank);
            
            children.add(child);
        }
    }
    
    /**
     * Tries to generate the next moves while moving upwards to the right.
     * 
     * @param state    the initial state.
     * @param file     the file of the starting position.
     * @param rank     the rank of the starting position.
     * @param children the child move container.
     */
    private void tryGenerateSouthEast(final ChessBoardState state,
                                      final int file,
                                      final int rank,
                                      final List<ChessBoardState> children) {
                                                                 
        for (int index = 1, endIndex = Math.min(N - 1 - file, N - 1 - rank); 
                 index <= endIndex;
                 index++) {
            
            final CellType cellType = state.getCellType(file + index,
                                                        rank + index);
            
            if (cellType == CellType.WHITE) {
                // Can capture:
                final ChessBoardState child = new ChessBoardState(state);
                
                child.set(file + index, 
                          rank + index, 
                          child.get(file,
                                    rank));
                
                child.clear(file,
                            rank);
                
                children.add(child);
                return;
            }
            
            if (cellType == CellType.BLACK) {
                // Cannot move further because of obstruction:
                return;
            }
            
            final ChessBoardState child = new ChessBoardState(state);
            
            child.set(file + index, 
                      rank + index,
                      child.get(file,
                                rank));
            
            child.clear(file, 
                        rank);
            
            children.add(child);
        }
    }
}
