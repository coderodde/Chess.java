package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.CellType;
import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.Piece;
import java.util.List;

/**
 * This class implements the expander generating all white queen moves.
 * 
 * @version 1.0.0 (Jul 2, 2024)
 * @since 1.0.0 (Jul 2, 2024)
 */
public final class WhiteQueenExpander extends AbstractChessBoardStateExpander {

    @Override
    public void expand(final ChessBoardState state,
                       final Piece piece, 
                       final int file, 
                       final int rank, 
                       final List<ChessBoardState> children) {
        
        final AbstractChessBoardStateExpander expander1 =
                new WhiteRookExpander();
     
        final AbstractChessBoardStateExpander expander2 = 
                new WhiteBishopExpander();
        
        expander1.expand(state, 
                         piece,
                         file, 
                         rank, 
                         children);
        
        expander2.expand(state,
                         piece, 
                         file, 
                         rank,
                         children);
        
//        // Try expand upwards:
//        tryGenerateNorth(state, 
//                         file,
//                         rank, 
//                         children);
//        
//        // Try expand downwards:
//        tryGenerateSouth(state,
//                         file,
//                         rank,
//                         children);
//        
//        // Try expand to the left:
//        tryGenerateWest(state,
//                        file,
//                        rank,
//                        children);
//        
//        // Try expand to the right:
//        tryGenerateEast(state,
//                        file,
//                        rank,
//                        children);
//        
//        // Generate children upwards to the left:
//        tryGenerateNorthWest(state,
//                             file,
//                             rank, 
//                             children);
//        
//        // Generate children upwards to the right:
//        tryGenerateNorthEast(state,
//                             file,
//                             rank,
//                             children);
//        
//        // Generate children downwards to the left:
//        tryGenerateSouthWest(state,
//                             file, 
//                             rank,
//                             children);
//        
//        // Generate children downwards to the right:
//        tryGenerateSouthEast(state,
//                             file, 
//                             rank,
//                             children);
    }

    /**
     * Generates all the white rook moves upwards from {@code (file, rank)}.
     * 
     * @param state    the current game state.
     * @param file     the starting file of the white rook.
     * @param rank     the starting rank of the white rook.
     * @param children the child node collection.
     */
    private void tryGenerateNorth(final ChessBoardState state,
                                  final int file,
                                  final int rank,
                                  final List<ChessBoardState> children) {
        
        for (int currentRank = rank - 1; currentRank >= 0; currentRank--) {
            
            final CellType cellType = state.getCellType(file, currentRank);
            
            if (cellType == CellType.BLACK) {
                // Can capture upwards:
                final ChessBoardState child = new ChessBoardState(state);
                
                child.set(file,
                          currentRank, 
                          child.get(file,
                                    rank));
                
                child.clear(file,
                            rank);
                
                children.add(child);
                return;
            }
            
            if (cellType == CellType.WHITE) {
                // A white piece blocks the current rook:
                return;
            }
            
            // Once here, just move the rook without capturing and continue
            // the search:
            final ChessBoardState child = new ChessBoardState(state);
            
            child.set(file,
                      currentRank,
                      child.get(file, 
                                rank));
            
            child.clear(file,
                        rank);
            
            children.add(child);
        }
    }
    
    /**
     * Generates all the white rook moves downwards from {@code (file, rank)}.
     * 
     * @param state    the current game state.
     * @param file     the starting file of the white rook.
     * @param rank     the starting rank of the white rook.
     * @param children the child node collection.
     */
    private void tryGenerateSouth(final ChessBoardState state,
                                  final int file,
                                  final int rank,
                                  final List<ChessBoardState> children) {
        
        for (int currentRank = rank + 1; currentRank < N; currentRank++) {
            
            final CellType cellType = state.getCellType(file, currentRank);
            
            if (cellType == CellType.BLACK) {
                // Can capture downwards:
                final ChessBoardState child = new ChessBoardState(state);
                
                child.set(file, 
                          currentRank, 
                          child.get(file,
                                    rank));
                
                child.clear(file,
                            rank);
                
                children.add(child);
                return;
            }
            
            if (cellType == CellType.WHITE) {
                // A white piece blocks the current rook:
                return;
            }
            
            // Once here, just move the rook without capturing and continue
            // the search:
            final ChessBoardState child = new ChessBoardState(state);
            
            child.set(file,
                      currentRank,
                      child.get(file, 
                                rank));
            
            child.clear(file,
                        rank);
            
            children.add(child);
        }
    }
    
    /**
     * Generates all the white rook moves to the left from {@code (file, rank)}.
     * 
     * @param state    the current game state.
     * @param file     the starting file of the white rook.
     * @param rank     the starting rank of the white rook.
     * @param children the child node collection.
     */
    private void tryGenerateWest(final ChessBoardState state, 
                                 final int file,
                                 final int rank,
                                 final List<ChessBoardState> children) {
        
        for (int currentFile = file - 1; currentFile >= 0; currentFile--) {
            
            final CellType cellType = state.getCellType(currentFile, rank);
            
            if (cellType == CellType.BLACK) {
                // Can capture to the left:
                final ChessBoardState child = new ChessBoardState(state);
                
                child.set(currentFile,
                          rank, 
                          child.get(file, 
                                    rank));
                
                child.clear(file, 
                            rank);
                
                children.add(child);
                return;
            }
            
            if (cellType == CellType.WHITE) {
                // A white pieced blocks the current rook:
                return;
            }
            // Once here, just move the rook without capturing and continue
            // the search:
            final ChessBoardState child = new ChessBoardState(state);
            
            child.set(currentFile,
                      rank,
                      child.get(file, 
                                rank));
            
            child.clear(file,
                        rank);
            
            children.add(child);
        }
    }
    
    /**
     * Generates all the white rook moves to the right from 
     * {@code (file, rank)}.
     * 
     * @param state    the current game state.
     * @param file     the starting file of the white rook.
     * @param rank     the starting rank of the white rook.
     * @param children the child node collection.
     */
    private void tryGenerateEast(final ChessBoardState state, 
                                 final int file,
                                 final int rank,
                                 final List<ChessBoardState> children) {
        
        for (int currentFile = file + 1; currentFile < N; currentFile++) {
            
            final CellType cellType = state.getCellType(currentFile, rank);
            
            if (cellType == CellType.BLACK) {
                // Can capture to the left:
                final ChessBoardState child = new ChessBoardState(state);
                
                child.set(currentFile,
                          rank, 
                          child.get(file, 
                                    rank));
                
                child.clear(file, 
                            rank);
                
                children.add(child);
                return;
            }
            
            if (cellType == CellType.WHITE) {
                // A white pieced blocks the current rook:
                return;
            }
            
            // Once here, just move the rook without capturing and continue
            // the search:
            final ChessBoardState child = new ChessBoardState(state);
            
            child.set(currentFile,
                      rank,
                      child.get(file, 
                                rank));
            
            child.clear(file,
                        rank);
            
            children.add(child);
        }
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
            
            if (cellType == CellType.BLACK) {
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
            
            if (cellType == CellType.WHITE) {
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
            
            if (cellType == CellType.BLACK) {
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
            
            if (cellType == CellType.WHITE) {
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
            
            if (cellType == CellType.BLACK) {
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
            
            if (cellType == CellType.WHITE) {
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
