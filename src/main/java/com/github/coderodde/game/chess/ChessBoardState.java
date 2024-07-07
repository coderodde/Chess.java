package com.github.coderodde.game.chess;

import com.github.coderodde.game.chess.impl.BlackBishopExpander;
import com.github.coderodde.game.chess.impl.BlackKingExpander;
import com.github.coderodde.game.chess.impl.BlackKnightExpander;
import com.github.coderodde.game.chess.impl.BlackPawnExpander;
import com.github.coderodde.game.chess.impl.BlackQueenExpander;
import com.github.coderodde.game.chess.impl.BlackRookExpander;
import com.github.coderodde.game.chess.impl.WhiteBishopExpander;
import com.github.coderodde.game.chess.impl.WhiteKingExpander;
import com.github.coderodde.game.chess.impl.WhiteKnightExpander;
import com.github.coderodde.game.chess.impl.WhitePawnExpander;
import com.github.coderodde.game.chess.impl.WhiteQueenExpander;
import com.github.coderodde.game.chess.impl.WhiteRookExpander;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class implements a chess board state.
 * 
 * @version 1.0.1 (Jun 26, 2024)
 * @since 1.0.0 (Jun 22, 2024)
 */
public final class ChessBoardState {
    
    public static final int N = 8;
    
    private static final AbstractChessBoardStateExpander WHITE_BISHOP_EXPANDER;
    private static final AbstractChessBoardStateExpander WHITE_KING_EXPANDER;
    private static final AbstractChessBoardStateExpander WHITE_KNIGHT_EXPANDER;
    private static final AbstractChessBoardStateExpander WHITE_PAWN_EXPANDER;
    private static final AbstractChessBoardStateExpander WHITE_QUEEN_EXPANDER;
    private static final AbstractChessBoardStateExpander WHITE_ROOK_EXPANDER;
    
    private static final AbstractChessBoardStateExpander BLACK_BISHOP_EXPANDER;
    private static final AbstractChessBoardStateExpander BLACK_KING_EXPANDER;
    private static final AbstractChessBoardStateExpander BLACK_KNIGHT_EXPANDER;
    private static final AbstractChessBoardStateExpander BLACK_PAWN_EXPANDER;
    private static final AbstractChessBoardStateExpander BLACK_QUEEN_EXPANDER;
    private static final AbstractChessBoardStateExpander BLACK_ROOK_EXPANDER;
    
    static {
        WHITE_BISHOP_EXPANDER = new WhiteBishopExpander();
        WHITE_KING_EXPANDER   = new WhiteKingExpander();
        WHITE_KNIGHT_EXPANDER = new WhiteKnightExpander();
        WHITE_PAWN_EXPANDER   = new WhitePawnExpander();
        WHITE_QUEEN_EXPANDER  = new WhiteQueenExpander();
        WHITE_ROOK_EXPANDER   = new WhiteRookExpander();
        
        BLACK_BISHOP_EXPANDER = new BlackBishopExpander();
        BLACK_KING_EXPANDER   = new BlackKingExpander();
        BLACK_KNIGHT_EXPANDER = new BlackKnightExpander();
        BLACK_PAWN_EXPANDER   = new BlackPawnExpander();
        BLACK_QUEEN_EXPANDER  = new BlackQueenExpander();
        BLACK_ROOK_EXPANDER   = new BlackRookExpander();
    }
    
    private Piece[][] state;
    private boolean[] whiteIsPreviouslyDoubleMoved = new boolean[N];
    private boolean[] blackIsPreviouslyDoubleMoved = new boolean[N];
    private byte enPassantFlags;
    
    public ChessBoardState() {
        state = new Piece[N][N];
        
        // Black pieces:
        state[0][0] = new Piece(PieceColor.BLACK, 
                                PieceType.ROOK, 
                                BLACK_ROOK_EXPANDER);
        
        state[0][7] = new Piece(PieceColor.BLACK, 
                                PieceType.ROOK, 
                                BLACK_ROOK_EXPANDER);
  
        state[0][1] = new Piece(PieceColor.BLACK, 
                                PieceType.KNIGHT,
                                BLACK_KNIGHT_EXPANDER);
        
        state[0][6] = new Piece(PieceColor.BLACK, 
                                PieceType.KNIGHT, 
                                BLACK_KNIGHT_EXPANDER);
        
        state[0][2] = new Piece(PieceColor.BLACK, 
                                PieceType.BISHOP, 
                                BLACK_BISHOP_EXPANDER);
        
        state[0][5] = new Piece(PieceColor.BLACK, 
                                PieceType.BISHOP, 
                                BLACK_BISHOP_EXPANDER);
  
        state[0][3] = new Piece(PieceColor.BLACK, 
                                PieceType.QUEEN, 
                                BLACK_QUEEN_EXPANDER);
        
        state[0][4] = new Piece(PieceColor.BLACK, 
                                PieceType.KING, 
                                BLACK_KING_EXPANDER);
        
        for (int file = 0; file < N; file++) {
            state[1][file] = new Piece(PieceColor.BLACK,
                                       PieceType.PAWN,
                                       BLACK_PAWN_EXPANDER);
        }
        
        // White pieces:
        state[7][0] = new Piece(PieceColor.WHITE, 
                                PieceType.ROOK, 
                                WHITE_ROOK_EXPANDER);
        
        state[7][7] = new Piece(PieceColor.WHITE, 
                                PieceType.ROOK, 
                                WHITE_ROOK_EXPANDER);
  
        state[7][1] = new Piece(PieceColor.WHITE, 
                                PieceType.KNIGHT, 
                                WHITE_KNIGHT_EXPANDER);
        
        state[7][6] = new Piece(PieceColor.WHITE, 
                                PieceType.KNIGHT, 
                                WHITE_KNIGHT_EXPANDER);
        
        state[7][2] = new Piece(PieceColor.WHITE, 
                                PieceType.BISHOP, 
                                WHITE_BISHOP_EXPANDER);
        
        state[7][5] = new Piece(PieceColor.WHITE, 
                                PieceType.BISHOP, 
                                WHITE_BISHOP_EXPANDER);
        
        state[7][3] = new Piece(PieceColor.WHITE, 
                                PieceType.QUEEN, 
                                WHITE_QUEEN_EXPANDER);
        
        state[7][4] = new Piece(PieceColor.WHITE, 
                                PieceType.KING, 
                                WHITE_KING_EXPANDER);
        
        for (int file = 0; file < N; file++) {
            state[6][file] = new Piece(PieceColor.WHITE,
                                       PieceType.PAWN,
                                       WHITE_PAWN_EXPANDER);
        }
    }
    
    public ChessBoardState(final ChessBoardState copy) {
        this.state = new Piece[N][N];
        
        for (int rank = 0; rank < N; rank++) {
            for (int file = 0; file < N; file++) {
                if (copy.state[rank][file] == null) {
                    continue;
                }
                
                this.state[rank][file] = new Piece(copy.state[rank][file]);
            }
        }
        
        // TODO: Just set?
        System.arraycopy(this.whiteIsPreviouslyDoubleMoved, 
                         0, 
                         copy.whiteIsPreviouslyDoubleMoved, 
                         0, 
                         N);
        
        System.arraycopy(this.blackIsPreviouslyDoubleMoved, 
                         0, 
                         copy.blackIsPreviouslyDoubleMoved, 
                         0, 
                         N);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ChessBoardState)) {
            return false;
        }
        
        final ChessBoardState other = (ChessBoardState) o;
        return Arrays.deepEquals(state, other.state);
    }
    
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }
    
    /**
     * Clears the entire board. Used in unit testing.
     */
    public void clear() {
        this.state = new Piece[N][N];
    }
    
    /**
     * Returns {@code true} iff the piece of color {@code pieceColor} is under
     * attack in position with file {@code file} and rank {@code rank}.
     * 
     * @param file       the file of the piece to test.
     * @param rank       the rank of the piece to test.
     * @param pieceColor the color of the piece to test.
     * 
     * @return true if the input piece is under attack. 
     */
    public boolean cellIsUnderAttack(final int file,
                                     final int rank,
                                     final PieceColor pieceColor) {
        if (pieceColor == PieceColor.WHITE) {
            return cellIsUnderAttackWhite(file, rank);
        } else {
            return cellIsUnderAttackBlack(file, rank);
        }
    }
    
    /**
     * Returns the piece value at rank {@code rank}, file {@code file}. Used in 
     * unit testing.
     * 
     * @param file the file of the requested piece.
     * @param rank the rank of the requested piece.
     * 
     * @return the piece.
     */
    public Piece get(final int file, final int rank) {
        return state[rank][file];
    }
    
    /**
     * Sets the piece {@code piece} at rank {@code rank}, file {@code file}. Used in 
     * unit testing.
     * 
     * @param file the file of the requested piece.
     * @param rank the rank of the requested piece.
     * @param piece the piece to set.
     */
    public void set(final int file, 
                    final int rank, 
                    final Piece piece) {
        state[rank][file] = piece;
    }
    
    /**
     * Clears the position at rank {@code rank} and file {@code file}. Used in 
     * unit testing.
     * 
     * @param file the file of the requested piece.
     * @param rank the rank of the requested piece.
     */
    public void clear(final int file, final int rank) {
        state[rank][file] = null;
    }
    
    /**
     * Returns the array of previous double moves flags. Used in unit testing.
     * 
     * @return the array of previous double moves flags for the white player. 
     */
    public boolean[] getWhiteIsPreviouslyDoubleMoved() {
        return whiteIsPreviouslyDoubleMoved;
    }
    
    /**
     * Returns the array of previous double moves flags. Used in unit testing.
     * 
     * @return the array of previous double moves flags for the black player. 
     */
    public boolean[] getBlackIsPreviouslyDoubleMoved() {
        return blackIsPreviouslyDoubleMoved;
    }
    
    /**
     * Returns a simple tefiletual representation of this state. Not verrank readable.
     * 
     * @return a tefiletual representation of this state.
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder =
                new StringBuilder((N + 3) * (N + 2));
        
        int rankNumber = 8;
        
        for (int rank = 0; rank < N; rank++) {
            for (int file = -1; file < N; file++) {
                if (file == -1) {
                    stringBuilder.append(rankNumber--).append(' ');
                } else {
                    final Piece piece = state[rank][file];
                    
                    stringBuilder.append(
                            (piece == null ? 
                                    ((file + rank) % 2 == 0 ? "." : "#") :
                                    piece));
                }
            }
            
            stringBuilder.append('\n');
        }
        
        stringBuilder.append("\n  abcdefgh");
        return stringBuilder.toString();
    }
    
    
    /**
     * Marks that the white pawn at file {@code file} made an initial double move.
     * Used for unit testing.
     * 
     * @param file the file number of the target white pawn. 
     */
    public void markWhitePawnInitialDoubleMove(final int file) {
        this.whiteIsPreviouslyDoubleMoved[file] = true;
    }
    
    /**
     * Marks that the black pawn at file {@code file} made an initial double move.
     * Used for unit testing.
     * 
     * @param file the file number of the target white pawn. 
     */
    public void markBlackPawnInitialDoubleMove(final int file) {
        this.blackIsPreviouslyDoubleMoved[file] = true;
    }
   
    public CellType getCellType(final int file, final int rank) {
        final Piece piece = state[rank][file];
        
        if (piece == null) {
            return CellType.EMPTY;
        }
        
        if ((piece.getPieceCodeBits() & Piece.WHITE_COLOR) != 0) {
            return CellType.WHITE;
        }
        
        if ((piece.getPieceCodeBits() & Piece.BLACK_COLOR) != 0) {
            return CellType.BLACK;
        }
        
        throw new IllegalStateException("Unknown cell color: " + piece);
    }
    
    public boolean isValidLocationForWhiteMove(final int file, final int rank) {
        if (file < 0 || file >= N || rank < 0 || rank >= N) {
            return false;
        }
        
        return getCellType(file, rank) != CellType.WHITE;
    }
    
    public boolean isValidLocationForBlackMove(final int file, final int rank) {
        if (file < 0 || file >= N || rank < 0 || rank >= N) {
            return false;
        }
        
        return getCellType(file, rank) != CellType.BLACK;
    }
  
    public List<ChessBoardState> expand(final PlayerTurn playerTurn) {
        
        final List<ChessBoardState> children = new ArrayList<>();
        
        if (null == playerTurn) {
            throw new NullPointerException("playerTurn is null.");
        } else switch (playerTurn) {
            case WHITE -> {
                for (int rank = 0; rank < N; rank++) {
                    for (int file = 0; file < N; file++) {
                        final CellType cellType = getCellType(file, rank);
                        
                        if (cellType == CellType.WHITE) {
                            children.addAll(
                                    state[rank]
                                         [file].expand(this, file, rank));
                        }
                    }
                }   
            }
                
            case BLACK -> {
                for (int rank = 0; rank < N; rank++) {
                    for (int file = 0; file < N; file++) {
                        final CellType cellType = getCellType(file, rank);
                        
                        if (cellType == CellType.BLACK) {
                            children.addAll(
                                    state[rank]
                                         [file].expand(this, file, rank));
                        }
                    }
                }
            }
            
            default -> throw new EnumConstantNotPresentException(
                        PlayerTurn.class,
                        playerTurn.name());
        }
        
        return children;
    }
    
    /**
     * Checks that white piece at file {@code file} and rank {@code rank} is 
     * under attack by a black piece.
     * 
     * @param file the file of the piece to check.
     * @param rank the rank of the piece to check.
     * 
     * @return {@code true} if and only if the white piece is under attack.
     */
    private boolean cellIsUnderAttackWhite(final int file, 
                                           final int rank) {
        
        if (cellIsUnderAttackWhiteQueen(file, rank)) {
            return true;
        }
        
        throw new UnsupportedOperationException();
    }
    
    /**
     * Checks that black piece at file {@code file} and rank {@code rank} is 
     * under attack by a black piece.
     * 
     * @param file the file of the piece to check.
     * @param rank the rank of the piece to check.
     * 
     * @return {@code true} if and only if the white piece is under attack.
     */
    private boolean cellIsUnderAttackBlack(final int file, 
                                           final int rank) {
        
        throw new UnsupportedOperationException();
    }
    
    /**
     * Checks that a white piece is under attack by a black queen.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean cellIsUnderAttackWhiteQueen(final int file, 
                                                final int rank) {
        
        if (cellIsUnderAttackWhiteQueenNorth(file, rank)) {
            return true;
        }
        
        if (cellIsUnderAttackWhiteQueenSouth(file, rank));
        
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black queen on top of the
     * piece.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean cellIsUnderAttackWhiteQueenNorth(final int file, 
                                                     final int rank) {
        for (int r = rank - 1; r >= 0; r--) {
            final Piece piece = get(file, r);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                return true;
            }
        }
        
        // Once here, there is no black queen above the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black queen below the
     * piece.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean cellIsUnderAttackWhiteQueenSouth(final int file, 
                                                     final int rank) {
        for (int r = rank + 1; r < N; r++) {
            final Piece piece = get(file, r);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                return false;
            }
            
            if (piece.getPieceType() == PieceType.QUEEN) {
                return true;
            }
        }
        
        // Once here, there is no black queen above the piece:
        return false;
    }
}
