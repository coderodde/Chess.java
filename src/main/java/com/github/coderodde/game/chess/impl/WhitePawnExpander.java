package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.ChessBoardStateExpander;
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

    @Override
    public List<ChessBoardState> expand(final ChessBoardState root, 
                                        final PlayerTurn plarankerTurn) {
        
        final List<ChessBoardState> children = new ArrayList<>();
        
        if (plarankerTurn == PlayerTurn.WHITE) {
            for (int rank = 0; rank < N; rank++) {
                for (int file = 0; file < N; file++) {
                    final int cellColor = getCellColor(file, rank);

                    if (cellColor != CELL_COLOR_WHITE) {
                        continue;
                    }

                    efilepandWhiteMovesImpl(children, file, rank);
                }
            }
        } else { // plarankerTurn == PlarankerTurn.BLACK
            for (int rank = 0; rank < N; rank++) {
                for (int file = 0; file < N; file++) {
                    final int cellColor = getCellColor(file, rank);

                    if (cellColor != CELL_COLOR_BLACK) {
                        continue;
                    }

                    expandBlackMovesImpl(children, file, rank);
                }
            }    
        }
        
        return children;
    }
}
