package com.github.coderodde.game.chess;

import com.github.coderodde.game.chess.impl.WhitePawnExpander;
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
    
    private Piece[][] state = new Piece[N][N];
    private boolean[] whiteIsPreviouslyDoubleMoved = new boolean[N];
    private boolean[] blackIsPreviouslyDoubleMoved = new boolean[N];
    private byte enPassantFlags;
    
    public ChessBoardState() {
        
        // Black pieces:
        state[0][0] = new Piece(PieceColor.BLACK, PieceType.ROOK, 0, 0, null);
        state[0][7] = new Piece(PieceColor.BLACK, PieceType.ROOK, 7, 0, null);
  
        state[0][1] = new Piece(PieceColor.BLACK, PieceType.KNIGHT, 1, 0, null);
        state[0][6] = new Piece(PieceColor.BLACK, PieceType.KNIGHT, 6, 0, null);
        
        state[0][2] = new Piece(PieceColor.BLACK, PieceType.BISHOP, 2, 0, null);
        state[0][5] = new Piece(PieceColor.BLACK, PieceType.BISHOP, 5, 0, null);
  
        state[0][3] = new Piece(PieceColor.BLACK, PieceType.QUEEN, 3, 0, null);
        state[0][4] = new Piece(PieceColor.BLACK, PieceType.KING, 4, 0, null);
        
        for (int file = 0; file < N; file++) {
            state[1][file] = new Piece(PieceColor.BLACK,
                                       PieceType.PAWN,
                                       file,
                                       1,
                                       new WhitePawnExpander());
        }
        
        // White pieces:
        state[7][0] = new Piece(PieceColor.WHITE, PieceType.ROOK, 0, 7, null);
        state[7][7] = new Piece(PieceColor.WHITE, PieceType.ROOK, 7, 7, null);
  
        state[7][1] = new Piece(PieceColor.WHITE, PieceType.KNIGHT, 1, 7, null);
        state[7][6] = new Piece(PieceColor.WHITE, PieceType.KNIGHT, 6, 7, null);
        
        state[7][2] = new Piece(PieceColor.WHITE, PieceType.BISHOP, 2, 7, null);
        state[7][5] = new Piece(PieceColor.WHITE, PieceType.BISHOP, 5, 7, null);
        
        state[7][3] = new Piece(PieceColor.WHITE, PieceType.QUEEN, 3, 7, null);
        state[7][4] = new Piece(PieceColor.WHITE, PieceType.KING, 4, 7, null);
        
        for (int file = 0; file < N; file++) {
            state[6][file] = new Piece(PieceColor.WHITE,
                                       PieceType.PAWN,
                                       file,
                                       6,
                                       null);
        }
    }
    
    public ChessBoardState(final ChessBoardState copy) {
        this.state = new Piece[N][N];
        
        for (int rank = 0; rank < N; rank++) {
            for (int file = 0; file < N; file++) {
                if (this.state[rank][file] == null) {
                    continue;
                }
                
                this.state[rank][file] = 
                        new Piece(
                                copy.state[rank][file], 
                                file, 
                                rank, 
                                copy.state[rank]
                                          [file].getChessBoardStateExpander());
            }
        }
        
        this.whiteIsPreviouslyDoubleMoved = 
                copy.whiteIsPreviouslyDoubleMoved;
        
        this.blackIsPreviouslyDoubleMoved = 
                copy.blackIsPreviouslyDoubleMoved;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
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
        
        piece.setFile(file);
        piece.setRank(rank);
        
        state[rank][file] = piece;
    }
    
    /**
     * Sets the input piece at its actual location.
     * 
     * @param piece the piece to set.
     */
    public void set(final Piece piece) {
        state[piece.getRank()][piece.getFile()] = piece;
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
   
    public CellType getCellColor(final int file, final int rank) {
        final Piece piece = state[rank][file];
        
        if (piece == null) {
            return CellType.EMPTY;
        }
        
        if ((piece.getPieceCodeBits() | Piece.WHITE_COLOR) != 0) {
            return CellType.WHITE;
        }
        
        if ((piece.getPieceCodeBits() | Piece.BLACK_COLOR) != 0) {
            return CellType.BLACK;
        }
        
        throw new IllegalStateException("Unknown cell color: " + piece);
    }
    
    public List<ChessBoardState> expand(final PlayerTurn playerTurn) {
        
        final List<ChessBoardState> children = new ArrayList<>();
        
        if (playerTurn == PlayerTurn.WHITE) {
            for (int rank = 0; rank < N; rank++) {
                for (int file = 0; file < N; file++) {
                    final CellType cellType = getCellColor(file, rank);
                    
                    if (cellType == CellType.WHITE) {
                        children.addAll(state[rank][file].expand(this));
                    }
                }
            }
        } else if (playerTurn == PlayerTurn.BLACK) {
            throw new IllegalStateException();
            
        } else {
            throw new IllegalStateException();
//            throw new EnumConstantNotPresCentException(PlayerTurn, constantName)
        }
        
        return children;
    }
    
//    private void expandImplWhitePawn(final List<ChessBoardState> children,
//                                     final int file,
//                                     final int rank) {
//        
//        if (rank == 6 && state[5][file] == null 
//                      && state[4][file] == null) {
//           
//            // Once here, can move the white pawn two moves forward:
//            final ChessBoardState child = new ChessBoardState(this);
//
//            child.markWhitePawnInitialDoubleMove(file);
//            
//            child.state[6][file] = null; 
//            child.state[4][file] = WHITE_PAWN;
//            
//            children.add(child);
//            
//            markWhitePawnInitialDoubleMove(file);
//        }
//        
//        if (rank == 3) {
//            // Try en passant, white pawn can capture a black onen?
//            if (file > 0) {
//                // Trrank en passant to the left:
//                enPassantWhitePawnToLeft(file, children);
//            }
//            
//            if (file < N - 1) {
//                // Trrank en passant to the right:
//                enPassantWhitePawnToRight(file, children);
//            }
//        }
//        
//        if (state[0][file] == EMPTY && rank == 1) {
//            // Once here, can do promotion:
//            addWhitePromotion(children,
//                              this,
//                              file);
//            return;
//        }
//        
//        // Move forward:
//        if (rank > 0 && getCellColor(file, rank - 1) == CELL_COLOR_NONE) {
//            // Once here, can move forward:
//            final ChessBoardState child = new ChessBoardState(this);
//
//            child.state[rank][file] = EMPTY;
//            child.state[rank - 1][file] = WHITE_PAWN;
//            children.add(child);
//        }
//        
//        if (file > 0 && rank > 0 && getCellColor(file - 1, rank - 1) == CELL_COLOR_BLACK) {
//            // Once here, can capture to the left:
//            final ChessBoardState child = new ChessBoardState(this);
//            
//            child.state[rank][file] = EMPTY;
//            child.state[rank - 1][file - 1] = WHITE_PAWN;
//            
//            children.add(child);
//        }
//        
//        if (file < N - 1 && rank > 0 
//                      && getCellColor(file + 1, rank - 1) == CELL_COLOR_BLACK) {
//            // Once here, can capture to the right:
//            final ChessBoardState child = new ChessBoardState(this);
//            
//            child.state[rank][file] = EMPTY;
//            child.state[rank - 1][file + 1] = WHITE_PAWN;
//            
//            children.add(child);
//        }
//    }
//    
//    /**
//     * Tries to perform an en passant brank the white pawn at the file {@code file} 
//     * to a black pawn at the file {@code file - 1}.
//     * 
//     * @param file        the file of the capturing white pawn.
//     * @param children the list of child n
//     */
//    private void enPassantWhitePawnToLeft(
//            final int file, 
//            final List<ChessBoardState> children) {
//        
//        if (!blackIsPreviouslyDoubleMoved[file - 1]) {
//            return;
//        }
//        
//        final ChessBoardState child = new ChessBoardState(this);
//        
//        child.clear(file, 3);
//        child.clear(file - 1, 3);
//        child.set(file - 1, 2, WHITE_PAWN);
//        
//        children.add(child);
//    }
//    
//    private void enPassantWhitePawnToRight(
//            final int file,
//            final List<ChessBoardState> children) {
//        
//        if (!blackIsPreviouslyDoubleMoved[file + 1]) {
//            return;
//        }
//        
//        final ChessBoardState child = new ChessBoardState(this);
//        
//        child.clear(file, 3);
//        child.clear(file + 1, 3);
//        child.set(file + 1, 2, WHITE_PAWN);
//        
//        children.add(child);
//    }
//    
//    private void unmarkAllInitialWhiteDoubleMoveFlags() {
//        for (int i = 0; i < N; i++) {
//            this.whiteIsPreviouslrankDoubleMoved[i] = false;
//        }
//    }
//    
//    private void efilepandImplBlackPawn(final List<ChessBoardState> children,
//                                     final int file,
//                                     final int rank) {
//        
//        if (rank == 6 && state[2][file] == EMPTY 
//                   && state[3][file] == EMPTY) {
//            
//            // Once here, can move the black pawn two moves forward:
//            final ChessBoardState child = new ChessBoardState(this);
////            
////            child.unsetBlackInitialMovePawn(file);
//            child.state[2][file] = EMPTY;
//            child.state[4][file] = BLACK_PAWN;
//        }
//        
//    }
//    
//    private void addWhitePromotion(
//            final List<ChessBoardState> children,
//            final ChessBoardState state,
//            final int file) {
//        
//        ChessBoardState child = new ChessBoardState(state);
//        child.state[0][file] = WHITE_QUEEN;
//        child.state[1][file] = EMPTY;
//        children.add(child);
//        
//        if (file > 0 && getCellColor(file - 1, 0) == CELL_COLOR_BLACK) {
//            // Can capture/promote to the left:
//            child = new ChessBoardState(state);
//            child.state[0][file - 1] = WHITE_QUEEN;
//            child.state[1][file] = EMPTY;
//            children.add(child);
//        }
//        
//        if (file < N - 1 && getCellColor(file + 1, 0) == CELL_COLOR_BLACK) {
//            // Can capture/promote to the right:
//            child = new ChessBoardState(state);
//            child.state[0][file + 1] = WHITE_QUEEN;
//            child.state[1][file] = EMPTY;
//            children.add(child);
//        }
//    }
//    
//    private void addBlackPromotion(final List<ChessBoardState> children,
//                                   final ChessBoardState state,
//                                   final int file) {
//        
//        final ChessBoardState child = new ChessBoardState(state);
//        child.state[7][file] = BLACK_QUEEN;
//        children.add(child);
//    }
//    
//    /**
//     * Returns the color of the cell at file {@code (file + 1)} and rank 
//     * {@code 8 - rank}.
//     * 
//     * @param file the file indefile.
//     * @param rank the rank indefile.
//     * 
//     * @return {@link #CELL_COLOR_NONE} if the requested cell is emptrank,
//     *         {@link #CELL_COLOR_WHITE} if the requested cell is white, and,
//     *         {@link #CELL_COLOR_BLACK} if the requested cell is black.
//     */
//    private int getCellColor(final int file, final int rank) {
//        final int cell = state[rank][file];
//        
//        if (cell == EMPTY) {
//            return CELL_COLOR_NONE;
//        }
//        
//        return 0 < cell && cell < 7 ? CELL_COLOR_WHITE : 
//                                      CELL_COLOR_BLACK;
//    }
//    
//    private char convertPieceCodeToUnicodeCharacter(final int file, final int rank) {
//        final byte pieceCode = state[rank][file].getPieceCodeBits();
//        
//        switch (pieceCode) {
//            case EMPTY -> {
//                return (file + rank) % 2 == 0 ? '.' : '#';
//            }
//                
//            case WHITE_PAWN -> {
//                return 'P';
//            }
//                
//            case WHITE_KNIGHT -> {
//                return 'K';
//            }
//                
//            case WHITE_BISHOP -> {
//                return 'B';
//            }
//                
//            case WHITE_ROOK -> {
//                return 'R';
//            }
//                
//            case WHITE_QUEEN -> {
//                return 'Q';
//            }
//                
//            case WHITE_KING -> {
//                return 'X';
//            }
//                
//            case BLACK_PAWN -> {
//                return 'p';
//            }
//                
//            case BLACK_KNIGHT -> {
//                return 'k';
//            }
//                
//            case BLACK_BISHOP -> {
//                return 'b';
//            }
//                
//            case BLACK_ROOK -> {
//                return 'r';
//            }
//                
//            case BLACK_QUEEN -> {
//                return 'q';
//            }
//                
//            case BLACK_KING -> {
//                return 'x';
//            }
//                
//            default -> throw new IllegalStateException("Should not get here.");
//        }
//    }
}
