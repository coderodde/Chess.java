package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
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
            if (rank > 0) {
                // Once here, can move to left upwards:
                final ChessBoardState child = new ChessBoardState(state);
                child.set(file - 1, rank - 1, state.get(file, rank));
                child.clear(file, rank);
                children.add(child);
            }
            
            if (rank < N - 1) {
                // Once here, can move to left downwards:
                final ChessBoardState child = new ChessBoardState(state);
                child.set(file - 1, rank + 1, state.get(file, rank));
                child.clear(file, rank);
                children.add(child);
            }
            
            // Move to the left:
            final ChessBoardState child = new ChessBoardState(state);
            child.set(file - 1, rank, state.get(file, rank));
            child.clear(file, rank);
            children.add(child);
        } 
        
        if (file < N - 1) {
            // Once here, can move to the right:
            if (rank > 0) {
                // Once here, can move to right upwards:
                final ChessBoardState child = new ChessBoardState(state);
                child.set(file + 1, rank - 1, state.get(file, rank));
                child.clear(file, rank);
                children.add(child);
            }
            
            if (rank < N - 1) {
                // Once here, can move to right downwards:
                final ChessBoardState child = new ChessBoardState(state);
                child.set(file + 1, rank + 1, state.get(file, rank));
                child.clear(file, rank);
                children.add(child);
            }
            
            // Move to the right:
            final ChessBoardState child = new ChessBoardState(state);
            child.set(file + 1, rank, state.get(file, rank));
            child.clear(file, rank);
            children.add(child);
        }
        
        if (rank > 0) {
            // Move upwards:
            final ChessBoardState child = new ChessBoardState(state);
            child.set(file, rank - 1, state.get(file, rank));
            child.clear(file, rank);
            children.add(child);
        }
        
        if (rank < N - 1) {
            // Move downwards:
            final ChessBoardState child = new ChessBoardState(state);
            child.set(file, rank + 1, state.get(file, rank));
            child.clear(file, rank);
            children.add(child);
        }
        
//        if (isValidLocation(file - 1, rank - 1)) {
//            final ChessBoardState child = new ChessBoardState(state);
//            child.set(file - 1, rank - 1, child.get(file, rank));
//            child.clear(file, rank);
//            children.add(child);
//        }
//        
//        if (isValidLocation(file, rank - 1)) {
//            final ChessBoardState child = new ChessBoardState(state);
//            child.set(file, rank - 1, child.get(file, rank));
//            child.clear(file, rank);
//            children.add(child);
//        }
//        
//        if (isValidLocation(file + 1, rank - 1)) {
//            final ChessBoardState child = new ChessBoardState(state);
//            child.set(file + 1, rank - 1, child.get(file, rank));
//            child.clear(file, rank);
//            children.add(child);
//        }
//        
//        if (isValidLocation(file + 1, rank)) {
//            final ChessBoardState child = new ChessBoardState(state);
//            child.set(file + 1, rank, child.get(file, rank));
//            child.clear(file, rank);
//            children.add(child);
//        }
//        
//        if (isValidLocation(file + 1, rank + 1)) {
//            final ChessBoardState child = new ChessBoardState(state);
//            child.set(file + 1, rank + 1, child.get(file, rank));
//            child.clear(file, rank);
//            children.add(child);
//        }
//        
//        if (isValidLocation(file, rank + 1)) {
//            final ChessBoardState child = new ChessBoardState(state);
//            child.set(file, rank + 1, child.get(file, rank));
//            child.clear(file, rank);
//            children.add(child);
//        }
//        
//        if (isValidLocation(file - 1, rank + 1)) {
//            final ChessBoardState child = new ChessBoardState(state);
//            child.set(file - 1, rank + 1, child.get(file, rank));
//            child.clear(file, rank);
//            children.add(child);
//        }
//        
//        if (isValidLocation(file - 1, rank)) {
//            final ChessBoardState child = new ChessBoardState(state);
//            child.set(file - 1, rank, child.get(file, rank));
//            child.clear(file, rank);
//            children.add(child);
//        }
    }
    
    private static boolean isValidLocation(final int file, final int rank) {
        return file >= 0 && file < N && rank >= 0 && rank < N;
    }
}
