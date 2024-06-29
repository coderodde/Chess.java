package com.github.coderodde.game.chess;

import com.github.coderodde.game.chess.impl.WhitePawnExpander;
import java.util.List;

public class Chess {

    public static void main(String[] args) {
        ChessBoardState s = new ChessBoardState();
        AbstractChessBoardStateExpander expander = new WhitePawnExpander();
        Piece pawn1 = new Piece(PieceColor.WHITE,
                                PieceType.PAWN, 
                                expander);
        
        Piece pawn2 = new Piece(PieceColor.WHITE,
                                PieceType.PAWN,
                                expander);
        
        s.clear();
        s.set(2, 6, pawn1);
        s.set(4, 6, pawn2);
        
        final List<ChessBoardState> children = s.expand(PlayerTurn.WHITE);
        
        for (final ChessBoardState child : children) {
            System.out.println(child);
            System.out.println();
        }
    }
}
