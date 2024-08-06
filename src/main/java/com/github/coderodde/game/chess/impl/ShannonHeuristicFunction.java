package com.github.coderodde.game.chess.impl;

import com.github.coderodde.game.chess.ChessBoardState;
import static com.github.coderodde.game.chess.ChessBoardState.N;
import com.github.coderodde.game.chess.AbstractHeuristicFunction;
import com.github.coderodde.game.chess.Piece;
import com.github.coderodde.game.chess.PieceColor;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import com.github.coderodde.game.chess.impl.attackcheck.BlackUnderAttackCheck;
import com.github.coderodde.game.chess.impl.attackcheck.WhiteUnderAttackCheck;
import com.github.coderodde.game.chess.UnderAttackCheck;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements a default heuristic function. It takes into account 
 * presence of each piece and wether each piece is under attack by a player of
 * opposite color.
 * 
 * @version 1.0.0 (Jul 15, 2024)
 * @since 1.0.0 (Jul 15, 2024)
 */
public final class ShannonHeuristicFunction extends AbstractHeuristicFunction {

    private static final UnderAttackCheck WHITE_CHECK = 
            new WhiteUnderAttackCheck();

    private static final UnderAttackCheck BLACK_CHECK = 
            new BlackUnderAttackCheck();
    
    private final Map<ChessBoardState, Integer> stateFrequencyMap = 
            new HashMap<>();

    @Override
    public void clearStateFrequencyMap() {
        this.stateFrequencyMap.clear();
    }

    @Override
    public Map<ChessBoardState, Integer> getStateFrequencyMap() {
        return stateFrequencyMap;
    }
    
    @Override
    public double evaluate(final ChessBoardState state, final int depth) {
        double score = 0;
        
        for (int rank = 0; rank < N; rank++) {
            for (int file = 0; file < N; file++) {
                final Piece piece = state.get(file, rank);
                
                if (piece == null) {
                    continue;
                }
                
                if (piece.isWhite()) {
                    score -= piece.getPresenceScore();
                    
//                    if (WHITE_CHECK.check(state, file, rank)) {
//                        score += piece.getVulnerabilityScore();
//                    }
                } else {
                    score += piece.getPresenceScore();
                    
//                    if (BLACK_CHECK.check(state, file, rank)) {
//                        score -= piece.getVulnerabilityScore();
//                    }
                }
            }
        }
        
        return score - pawnMaterial(state) + mobility(state); 
    }
    
    private double pawnMaterial(final ChessBoardState state) {
        int score = 0;
        
        for (int file = 0; file < N; file++) {
            score += countBlockedPawnWhite(state, file);
            score -= countBlockedPawnBlack(state, file);
            
            score += countDoubledPawnsWhite(state, file);
            score -= countDoubledPawnsBlack(state, file);
            
            score += countIsolatedPawnsWhite(state, file);
            score -= countIsolatedPawnsBlack(state, file);
        }
        
        return 0.5 * score;
    }
    
    /**
     * Counts the number of blocked pawns on file {@code file} in the state
     * {@code state}.
     * 
     * @param state the state to investigate for the number of blocked pawns.
     * @param file  the target file to investigate.
     * 
     * @return the count of blocked pawns at file {@code file} multiplied by 
     *         0.5.
     */
    int countBlockedPawnWhite(final ChessBoardState state,
                              final int file) {
        
        int blockedPawnCount = 0;
        
        for (int rank = N - 1; rank >= 1; rank--) {
            final Piece currentPiece = state.get(file, rank);
            
            if (currentPiece == null) {
                continue;
            }
            
            if (currentPiece.getPieceType() != PAWN) {
                continue;
            }
            
            if (currentPiece.isBlack()) {
                // Not a white pawn in question:
                continue;
            }
            
            final Piece nextPiece = state.get(file, rank - 1);
            
            if (nextPiece == null) {
                continue;
            }
            
            blockedPawnCount++;
        }
        
        return blockedPawnCount;
    }
    
    int countBlockedPawnBlack(final ChessBoardState state,
                              final int file) {
        
        int blockedPawnCount = 0;
        
        for (int rank = 0; rank < N - 1; rank++) {
            final Piece currentPiece = state.get(file, rank);
            
            if (currentPiece == null) {
                continue;
            }
            
            if (currentPiece.getPieceType() != PAWN) {
                continue;
            }
            
            if (currentPiece.isWhite()) {
                // Not a white pawn in question:
                continue;
            }
            
            final Piece nextPiece = state.get(file, rank - 1);
            
            if (nextPiece == null) {
                continue;
            }
            
            blockedPawnCount++;
        }
        
        return blockedPawnCount;
    }
    
    int countDoubledPawnsWhite(final ChessBoardState state,
                               final int file) {
        int count = -1;
        
        for (int rank = 1; rank < N - 1; rank++) {
            
            final Piece piece = state.get(file, rank);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.getPieceType() != PAWN) {
                continue;
            }
            
            if (piece.isWhite()) {
                count++;
            }
        }
        
        return Math.max(0, count);
    }
    
    int countDoubledPawnsBlack(final ChessBoardState state,
                               final int file) {
        int count = -1;
        
        for (int rank = 1; rank < N - 1; rank++) {
            
            final Piece piece = state.get(file, rank);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.getPieceType() != PAWN) {
                continue;
            }
            
            if (piece.isBlack()) {
                count++;
            }
        }
        
        return Math.max(0, count);
    }
    
    double mobility(final ChessBoardState state) {
        int mobilityWhite = 0;
        int mobilityBlack = 0;
        
        for (int rank = 0; rank < N; rank++) {
            for (int file = 0; file < N; file++) {
                
                final Piece piece = state.get(file, rank);
                
                if (piece == null) {
                    continue;
                }
                
                if (piece.isWhite()) {
                    for (int rank2 = 0; rank2 < N; rank2++) {
                        for (int file2 = 0; file2 < N; file2++) {
                            if (BLACK_CHECK.check(state, file2, rank2)) {
                                mobilityWhite++;
                            }
                        }
                    }
                } else {
                    for (int rank2 = 0; rank2 < N; rank2++) {
                        for (int file2 = 0; file2 < N; file2++) {
                            if (WHITE_CHECK.check(state, file2, rank2)) {
                                mobilityBlack++;
                            }
                        }
                    }
                }
            }
        }
        
        return 0.1 * (mobilityBlack - mobilityWhite);       
    }
    
    private boolean[] getOpenFileFlags(final PieceColor color,
                                       final ChessBoardState state) {
        
        final boolean[] openFileFlags = new boolean[N];
        
        for (int file = 0; file < N; file++) {
            openFileFlags[file] = isFileOpen(color, state, file);
        }
        
        return openFileFlags;
    }
    
    private boolean isFileOpen(final PieceColor color, 
                               final ChessBoardState state,
                               final int file) {
        
        for (int rank = 0; rank < N; rank++) {
            final Piece piece = state.get(file, rank);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.getPieceColor() == color &&
                piece.getPieceType() == PAWN) {
                
                return false;
            }
        }
        
        return true;
    }
    
    private int countIsolatedPawnsImpl(final ChessBoardState state,
                                       final int file,
                                       final PieceColor pieceColor) {
        int count = 0;
        
        final boolean[] openFlags = getOpenFileFlags(pieceColor, state);
        
        if (file == 0) {
            for (int rank = 0; rank < N; rank++) {
                final Piece piece = state.get(0, rank);
                
                if (piece == null) {
                    continue;
                }
                
                if (piece.getPieceType() != PAWN) {
                    continue;
                }
                
                if (piece.getPieceColor() == pieceColor) {
                    if (openFlags[1]) {
                        count++;
                    }
                }
            }
        } else if (file == N - 1) {
            for (int rank = 0; rank < N; rank++) {
                final Piece piece = state.get(N - 1, rank);
                
                if (piece == null) {
                    continue;
                }
                
                if (piece.getPieceType() != PAWN) {
                    continue;
                }
                
                if (piece.getPieceColor() == pieceColor) {
                    if (openFlags[1]) {
                        count++;
                    }
                }
            }
        } else {
            for (int f = 1; f < N - 1; f++) {
                for (int rank = 0; rank < N; rank++) {
                    final Piece piece = state.get(N - 1, rank);

                    if (piece == null) {
                        continue;
                    }

                    if (piece.getPieceType() != PAWN) {
                        continue;
                    }

                    if (piece.getPieceColor() == pieceColor) {
                        if (openFlags[f - 1] && openFlags[f + 1]) {
                            count++;
                        }
                    }
                }
            }
        }
        
        return count;
    }
    
    int countIsolatedPawnsWhite(final ChessBoardState state, 
                                final int file) {
        
        return countIsolatedPawnsImpl(state,
                                      file, 
                                      WHITE);
    }
    
    int countIsolatedPawnsBlack(final ChessBoardState state, 
                                final int file) {
        
        return countIsolatedPawnsImpl(state, 
                                      file,
                                      BLACK);
    }
}
