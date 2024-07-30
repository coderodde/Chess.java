package com.github.coderodde.game.chess.benchmark;

import com.github.coderodde.game.chess.AbstractChessBoardStateExpander;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.PieceColor;
import com.github.coderodde.game.chess.PieceType;
import com.github.coderodde.game.chess.PlayerTurn;
import com.github.coderodde.game.chess.impl.expanders.WhiteQueenExpander;
import com.github.coderodde.game.chess.impl.expanders.WhiteRookExpander;

/**
 * This class implements some benchmarking.
 * 
 * @version 1.0.0 (Jul 30, 2024)
 * @since 1.0.0 (Jul 30, 2024)
 */
public final class Benchmark {
    
    public static void main(final String[] args) {
        benchmarkInitialExpansion();
        benchmarkExpansionOnSparseBoard();
    }
    
    private static void benchmarkInitialExpansion() {
        final int ITERATIONS = 50_000;
        final long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < ITERATIONS; i++) {
            final ChessBoardState state = new ChessBoardState();
            state.expand(PlayerTurn.WHITE);
        }
        
        final long endTime = System.currentTimeMillis();
        
        System.out.printf("benchmarkInitialExpansion: " + 
                          "Making %d expansions in %d milliseconds.\n",
                          ITERATIONS,
                          endTime - startTime);
    }
    
    private static void benchmarkExpansionOnSparseBoard() {
        final AbstractChessBoardStateExpander queenExpander = 
                new WhiteQueenExpander();
        
        final AbstractChessBoardStateExpander rookExpander = 
                new WhiteRookExpander();
        
        final Piece whiteQueen = new Piece(PieceColor.WHITE, 
                                           PieceType.QUEEN, 
                                           queenExpander);
        
        final Piece whiteRook  = new Piece(PieceColor.WHITE, 
                                           PieceType.ROOK, 
                                           rookExpander);
        
        final int ITERATIONS = 50_000;
        final long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < ITERATIONS; i++) {
            final ChessBoardState state = new ChessBoardState();
            state.clear();
            state.set(3, 3, whiteQueen);
            state.set(2, 1, whiteRook);
            state.set(4, 5, whiteRook);
            state.expand(PlayerTurn.WHITE);
        }
        
        final long endTime = System.currentTimeMillis();
        
        System.out.printf("benchmarkExpansionOnSparseBoard: " + 
                          "Making %d expansions in %d milliseconds.\n",
                          ITERATIONS,
                          endTime - startTime);
    }
}
