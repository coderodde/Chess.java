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
        
        tryGenerateSouthLeft (state, file, rank, children);
        tryGenerateSouthRight(state, file, rank, children);
        
        tryGenerateWestUp  (state, file, rank, children);
        tryGenerateWestDown(state, file, rank, children);
        
        tryGenerateEastUp  (state, file, rank, children);
        tryGenerateEastDown(state, file, rank, children);
    }
    
    /**
     * Attempts to move a white knight at {@code (file, rank)} two moves 
     * upwards and one move to the left.
     * 
     * @param state    the starting state.
     * @param file     the file of the white knight to move.
     * @param rank     the rank of the white knight to move.
     * @param children the list of child states.
     */
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
    
    /**
     * Attempts to move a white knight at {@code (file, rank)} two moves 
     * upwards and one move to the right.
     * 
     * @param state    the starting state.
     * @param file     the file of the white knight to move.
     * @param rank     the rank of the white knight to move.
     * @param children the list of child states.
     */
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
    
    /**
     * Attempts to move a white knight at {@code (file, rank)} two moves 
     * downwards and one move to the left.
     * 
     * @param state    the starting state.
     * @param file     the file of the white knight to move.
     * @param rank     the rank of the white knight to move.
     * @param children the list of child states.
     */
    private void tryGenerateSouthLeft(final ChessBoardState state, 
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
    
    /**
     * Attempts to move a white knight at {@code (file, rank)} two moves 
     * downwards and one move to the right.
     * 
     * @param state    the starting state.
     * @param file     the file of the white knight to move.
     * @param rank     the rank of the white knight to move.
     * @param children the list of child states.
     */
    private void tryGenerateSouthRight(final ChessBoardState state, 
                                       final int file, 
                                       final int rank, 
                                       final List<ChessBoardState> children) {
        if (rank > N - 3) {
            return;
        }
        
        if (file == N - 1) {
            return;
        }
        
        if (state.getCellColor(file + 1, rank + 2) != CellType.WHITE) {
            final ChessBoardState child = new ChessBoardState(state);
            
            child.clear(file, rank);
            child.set(file + 1, rank + 2, new Piece(WHITE, KNIGHT, this));
            
            children.add(child);
        }
    }
    
    /**
     * Attempts to move a white knight at {@code (file, rank)} two moves to the 
     * left and one move upwords.
     * 
     * @param state    the starting state.
     * @param file     the file of the white knight to move.
     * @param rank     the rank of the white knight to move.
     * @param children the list of child states.
     */
    private void tryGenerateWestUp(final ChessBoardState state, 
                                   final int file, 
                                   final int rank, 
                                   final List<ChessBoardState> children) {
        if (file < 2) {
            return;
        }
        
        if (rank == 0) {
            return;
        }
        
        if (state.getCellColor(file - 2, rank - 1) != CellType.WHITE) {
            final ChessBoardState child = new ChessBoardState(state);
            
            child.clear(file, rank);
            child.set(file - 2, rank - 1, new Piece(WHITE, KNIGHT, this));
            
            children.add(child);
        }
    }
    
    /**
     * Attempts to move a white knight at {@code (file, rank)} two moves to the 
     * left and one move downwards.
     * 
     * @param state    the starting state.
     * @param file     the file of the white knight to move.
     * @param rank     the rank of the white knight to move.
     * @param children the list of child states.
     */
    private void tryGenerateWestDown(final ChessBoardState state, 
                                     final int file, 
                                     final int rank, 
                                     final List<ChessBoardState> children) {
        if (file < 2) {
            return;
        }
        
        if (rank == N - 1) {
            return;
        }
        
        if (state.getCellColor(file - 2, rank + 1) != CellType.WHITE) {
            final ChessBoardState child = new ChessBoardState(state);
            
            child.clear(file, rank);
            child.set(file - 2, rank + 1, new Piece(WHITE, KNIGHT, this));
            
            children.add(child);
        }
    }
    
    /**
     * Attempts to move a white knight at {@code (file, rank)} two moves to the 
     * right and one move upwards.
     * 
     * @param state    the starting state.
     * @param file     the file of the white knight to move.
     * @param rank     the rank of the white knight to move.
     * @param children the list of child states.
     */
    private void tryGenerateEastUp(final ChessBoardState state,
                                   final int file, 
                                   final int rank, 
                                   final List<ChessBoardState> children) {
        if (file > N - 3) {
            return;
        }
        
        if (rank == 0) {
            return;
        }
        
        if (state.getCellColor(file + 2, rank - 1) != CellType.WHITE) {
            final ChessBoardState child = new ChessBoardState(state);
            
            child.clear(file, rank);
            child.set(file + 2, rank - 1, new Piece(WHITE, KNIGHT, this));
            
            children.add(child);
        }
    }
    
    /**
     * Attempts to move a white knight at {@code (file, rank)} two moves to the 
     * right and one move downwards.
     * 
     * @param state    the starting state.
     * @param file     the file of the white knight to move.
     * @param rank     the rank of the white knight to move.
     * @param children the list of child states.
     */
    private void tryGenerateEastDown(final ChessBoardState state, 
                                     final int file, 
                                     final int rank, 
                                     final List<ChessBoardState> children) {
        if (file > N - 3) {
            return;
        }
        
        if (rank == N - 1) {
            return;
        }
        
        if (state.getCellColor(file + 2, rank + 1) != CellType.WHITE) {
            final ChessBoardState child = new ChessBoardState(state);
            
            child.clear(file, rank);
            child.set(file + 2, rank + 1, new Piece(WHITE, KNIGHT, this));
            
            children.add(child);
        }
    }
}
