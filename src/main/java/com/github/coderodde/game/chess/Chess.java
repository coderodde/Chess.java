package com.github.coderodde.game.chess;

import static com.github.coderodde.game.chess.ChessBoardState.WHITE_PAWN;
import java.util.List;

public class Chess {

    public static void main(String[] args) {
        ChessBoardState s = new ChessBoardState();
        s.clear();
        
        s.set(2, 6, WHITE_PAWN);
        s.set(4, 6, WHITE_PAWN);
        
        final List<ChessBoardState> children = s.expand(PlayerTurn.WHITE);
        
        for (final ChessBoardState child : children) {
            System.out.println(child);
            System.out.println();
        }
    }
}
