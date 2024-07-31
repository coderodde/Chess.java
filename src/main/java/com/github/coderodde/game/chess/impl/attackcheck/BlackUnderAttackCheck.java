package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.CellCoordinates;
import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.UnderAttackCheck;

/**
 * This class provides facilities to check whether a black piece is threatened 
 * by a white piece.
 * '
 * @version 1.0.0 (Jul 7, 2024)
 * @since 1.0.0 (Jul 7, 2024)
 */
public final class BlackUnderAttackCheck extends UnderAttackCheck {

    private static final UnderAttackCheck BLACK_UNDER_ATTACK_BY_BLACK_QUEEN = 
            new BlackUnderAttackCheckByWhiteQueen();
    
    private static final UnderAttackCheck BLACK_UNDER_ATTACK_BY_BLACK_ROOK = 
            new BlackUnderAttackCheckByWhiteRook();
    
    private static final UnderAttackCheck BLACK_UNDER_ATTACK_BY_BLACK_BISHOP = 
            new BlackUnderAttackCheckByWhiteBishop();
    
    private static final UnderAttackCheck BLACK_UNDER_ATTACK_BY_BLACK_KNIGHT = 
            new BlackUnderAttackCheckByWhiteKnight();
    
    private static final UnderAttackCheck BLACK_UNDER_ATTACK_BY_BLACK_PAWN = 
            new BlackUnderAttackCheckByWhitePawn();
    
    private static final UnderAttackCheck BLACK_UNDER_ATTACK_BY_BLACK_KING = 
            new BlackUnderAttackCheckByWhiteKing();
    
    @Override
    public boolean check(final ChessBoardState state, 
                         final int file,
                         final int rank, 
                         final CellCoordinates attackerCellCoordinates) {
        
        return BLACK_UNDER_ATTACK_BY_BLACK_QUEEN.check(state, 
                                                       file, 
                                                       rank,
                                                       attackerCellCoordinates)
                ||
                BLACK_UNDER_ATTACK_BY_BLACK_ROOK.check(state, 
                                                       file,
                                                       rank,
                                                       attackerCellCoordinates)
                ||
                BLACK_UNDER_ATTACK_BY_BLACK_BISHOP.check(state, 
                                                         file, 
                                                         rank)
                ||
                BLACK_UNDER_ATTACK_BY_BLACK_KNIGHT.check(state,
                                                         file,
                                                         rank)
                ||
                BLACK_UNDER_ATTACK_BY_BLACK_PAWN.check(state, 
                                                       file, 
                                                       rank)
                ||
                BLACK_UNDER_ATTACK_BY_BLACK_KING.check(state, 
                                                       file,
                                                       rank);
    }
}
