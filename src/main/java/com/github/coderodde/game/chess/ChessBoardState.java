package com.github.coderodde.game.chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class implements a chess board state.
 * 
 * @version 1.0.0 (Jun 22, 2024)
 * @since 1.0.0 (Jun 22, 2024)
 */
public final class ChessBoardState {
    
    private static final int N = 8;
    
    public static final byte EMPTY        = 0;
    public static final byte WHITE_PAWN   = 1;
    public static final byte WHITE_BISHOP = 2;
    public static final byte WHITE_KNIGHT = 3;
    public static final byte WHITE_ROOK   = 4;
    public static final byte WHITE_QUEEN  = 5;
    public static final byte WHITE_KING   = 6;
 
    public static final byte BLACK_PAWN   = 9;
    public static final byte BLACK_BISHOP = 10;
    public static final byte BLACK_KNIGHT = 11;
    public static final byte BLACK_ROOK   = 12;
    public static final byte BLACK_QUEEN  = 13;
    public static final byte BLACK_KING   = 14;
    
    private static final int CELL_COLOR_NONE = 0;
    private static final int CELL_COLOR_WHITE = 1;
    private static final int CELL_COLOR_BLACK = -1;
    
    private byte[][] state = new byte[N][N];
    private byte[] promotionCounters = new byte[8];
    private byte whitePawnInitialFlags = (byte) 0xff;
    private byte blackPawnInitialFlags = (byte) 0xff;
     
    private static final int PROMOTION_COUNTER_INDEX_WHITE_QUEEN  = 0;
    private static final int PROMOTION_COUNTER_INDEX_WHITE_ROOK   = 1;
    private static final int PROMOTION_COUNTER_INDEX_WHITE_BISHOP = 2;
    private static final int PROMOTION_COUNTER_INDEX_WHITE_KNIGHT = 3;
     
    private static final int PROMOTION_COUNTER_INDEX_BLACK_QUEEN  = 4;
    private static final int PROMOTION_COUNTER_INDEX_BLACK_ROOK   = 5;
    private static final int PROMOTION_COUNTER_INDEX_BLACK_BISHOP = 6;
    private static final int PROMOTION_COUNTER_INDEX_BLACK_KNIGHT = 7;
    
    public ChessBoardState() {
        
        // Black pieces:
        state[0][0] =
        state[0][7] = BLACK_ROOK;
  
        state[0][1] = 
        state[0][6] = BLACK_KNIGHT;
  
        state[0][2] = 
        state[0][5] = BLACK_BISHOP;
  
        state[0][3] = BLACK_QUEEN;
        state[0][4] = BLACK_KING;
        
        for (int x = 0; x < N; x++) {
            state[1][x] = BLACK_PAWN;
        }
        
        // White pieces:
        state[7][0] =
        state[7][7] = WHITE_ROOK;
  
        state[7][1] = 
        state[7][6] = WHITE_KNIGHT;
  
        state[7][2] = 
        state[7][5] = WHITE_BISHOP;
  
        state[7][3] = WHITE_QUEEN;
        state[7][4] = WHITE_KING;
        
        for (int x = 0; x < N; x++) {
            state[6][x] = WHITE_PAWN;
        }
    }
    
    public ChessBoardState(final ChessBoardState copy) {
        this.state = new byte[N][N];
        
        for (int y = 0; y < N; y++) {
            System.arraycopy(copy.state[y], 0, this.state[y], 0, N);
        }
        
        this.whitePawnInitialFlags = copy.whitePawnInitialFlags;
        this.blackPawnInitialFlags = copy.blackPawnInitialFlags;
        this.promotionCounters = Arrays.copyOf(copy.promotionCounters, 
                                               copy.promotionCounters.length);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (!getClass().equals(o.getClass())) {
            return false;
        }
        
        final ChessBoardState other = (ChessBoardState) o;
        
        return Arrays.deepEquals(state, other.state);
    }
    
    /**
     * Clears the entire board. Used in unit testing.
     */
    public void clear() {
        this.state = new byte[N][N];
    }
    
    /**
     * Returns the piece value at rank {@code y}, file {@code x}. Used in unit 
     * testing.
     * 
     * @param x the file of the requested piece.
     * @param y the rank of the requested piece.
     * 
     * @return the piece value.
     */
    public byte get(final int x, final int y) {
        return state[y][x];
    }
    
    /**
     * Sets the piece {@code piece} at rank {@code y}, file {@code x}. Used in 
     * unit testing.
     * 
     * @param x the file of the requested piece.
     * @param y the rank of the requested piece.
     * 
     * @param piece the value of the desired piece.
     */
    public void set(final int x, final int y, final byte piece) {
        state[y][x] = piece;
    }
    
    /**
     * Returns a simple textual representation of this state. Not very readable.
     * 
     * @return a textual representation of this state.
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder =
                new StringBuilder((N + 3) * (N + 2));
        
        int rankNumber = 8;
        
        for (int y = 0; y < N; y++) {
            for (int x = -1; x < N; x++) {
                if (x == -1) {
                    stringBuilder.append(rankNumber--).append(' ');
                } else {
                    stringBuilder.append(pieceToChar(state[y][x]));
                }
            }
            
            stringBuilder.append('\n');
        }
        
        stringBuilder.append("\n  abcdefgh");
        return stringBuilder.toString();
    }
    
    public List<ChessBoardState> expand(final PlayerTurn playerTurn) {
        
        final List<ChessBoardState> children = new ArrayList<>();
        
        if (playerTurn == PlayerTurn.WHITE) {
            for (int y = 0; y < N; y++) {
                for (int x = 0; x < N; x++) {
                    final int cellColor = getCellColor(x, y);

                    if (cellColor != CELL_COLOR_WHITE) {
                        continue;
                    }

                    expandImpl(children, x, y);
                }
            }
        } else { // playerTurn == PlayerTurn.BLACK
            for (int y = 0; y < N; y++) {
                for (int x = 0; x < N; x++) {
                    final int cellColor = getCellColor(x, y);

                    if (cellColor != CELL_COLOR_BLACK) {
                        continue;
                    }

                    expandImpl(children, x, y);
                }
            }    
        }
        
        return children;
    }
    
    private void expandImpl(final List<ChessBoardState> children,
                            final int x,
                            final int y) {
        
        final byte cell = state[y][x];
        
        switch (cell) {
            case WHITE_PAWN:
                expandImplWhitePawn(children, x, y);
                break;
                
            case BLACK_PAWN:
                expandImplBlackPawn(children, x, y);
                break;
                
            case WHITE_KING:
                break;
                
            case BLACK_KING:
                break;
                
            case WHITE_QUEEN:
                break;
                
            case BLACK_QUEEN:
                
            case WHITE_ROOK:
                break;
                
            case BLACK_ROOK:
                break;
                
            case WHITE_BISHOP:
                break;
                
            case BLACK_BISHOP:
                break;
                
            case WHITE_KNIGHT:
                break;
                
            case BLACK_KNIGHT:
                break;
                
            default:
                throw new IllegalStateException("Should not get here.");
        }
    }
    
    private void expandImplWhitePawn(final List<ChessBoardState> children,
                                     final int x,
                                     final int y) {
        
        if (y == 6 && checkWhiteInitialMovePawn(x)
                   && state[5][x] == EMPTY 
                   && state[4][x] == EMPTY) {
           
            // Once here, can move the white pawn two moves forward:
            final ChessBoardState child = new ChessBoardState(this);

            child.unsetWhiteInitialMovePawn(x);
            child.state[6][x] = EMPTY; 
            child.state[4][x] = WHITE_PAWN;
            
            children.add(child);
        }
        
        if (state[y - 1][x] == EMPTY) {
            // Once here, just move one step forward:
            final ChessBoardState child = new ChessBoardState(this);
            
            // Move
            child.state[y][x] = EMPTY;
            child.state[y - 1][x] = WHITE_PAWN;
            children.add(child);
            
            if (y == 1) {
                // Once here, can do promotion:
                addWhitePromotion(children,
                                  this,
                                  x);
            }
        }
        
        if (x > 0 
                && y > 0 
                && getCellColor(x - 1, y - 1) == CELL_COLOR_BLACK) {
            
            // Once here, can eat to the left board:
            final ChessBoardState child = new ChessBoardState(this);
            child.state[y][x] = EMPTY;
            child.state[y - 1][x - 1] = WHITE_PAWN;
            children.add(child);
        }
        
        if (x < N - 1 
                && y > 0
                && getCellColor(x + 1, y - 1) == CELL_COLOR_BLACK) {
            
            // Once here, can eat to the right board:
            final ChessBoardState child = new ChessBoardState(this);
            child.state[y][x] = EMPTY;
            child.state[y - 1][x + 1] = WHITE_PAWN;
            children.add(child);
        }
    }
    
    private void expandImplBlackPawn(final List<ChessBoardState> children,
                                     final int x,
                                     final int y) {
        
        if (y == 6 && checkBlackInitialMovePawn(x)
                   && state[2][x] == EMPTY 
                   && state[3][x] == EMPTY) {
            
            // Once here, can move the black pawn two moves forward:
            final ChessBoardState child = new ChessBoardState(this);
            
            child.unsetBlackInitialMovePawn(x);
            child.state[2][x] = EMPTY;
            child.state[4][x] = BLACK_PAWN;
        }
        
    }
    
    private void addWhitePromotion(
            final List<ChessBoardState> children,
            final ChessBoardState state,
            final int x) {
        
        final ChessBoardState child = new ChessBoardState(state);
        child.state[0][x] = WHITE_QUEEN;
        children.add(child);
    }
    
    private void addBlackPromotion(final List<ChessBoardState> children,
                                   final ChessBoardState state,
                                   final int x) {
        
        final ChessBoardState child = new ChessBoardState(state);
        child.state[7][x] = BLACK_QUEEN;
        children.add(child);
    }
    
    /**
     * Checks that the white pawn on (x + 1)st file can make two moves forward.
     * 
     * @param x the file index of the requested white pawn.
     * 
     * @return {@code true} iff the white pawn in question has not moved yet.
     */
    private boolean checkWhiteInitialMovePawn(final int x) {
        return (whitePawnInitialFlags & (1 << x)) != 0;
    }
    
    /**
     * Marks that the white pawn at file (x + 1) has moved at least once.
     * 
     * @param x the file index of the white pawn.
     */
    private void unsetWhiteInitialMovePawn(final int x) {
        whitePawnInitialFlags &= ~(1 << x);
    }
    
    /**
     * Checks that the black pawn on (x + 1)st file can make two moves forward.
     * 
     * @param x the file index of the requested black pawn.
     * 
     * @return {@code true} iff the black pawn in question has not moved yet.
     */
    private boolean checkBlackInitialMovePawn(final int x) {
        return (blackPawnInitialFlags & (1 << x)) != 0;
    }
    
    /**
     * Marks that the black pawn at file (x + 1) has moved at least once.
     * 
     * @param x the file index of the black pawn.
     */
    private void unsetBlackInitialMovePawn(final int x) {
        blackPawnInitialFlags &= ~(1 << x);
    }
    
    /**
     * Converts the piece code to its textual representation. Used in
     * {@link #toString()}.
     * 
     * @param piece the piece code.
     * 
     * @return textual representation of the input piece code.
     */
    private static char pieceToChar(final byte piece) {
        switch (piece) {
            case EMPTY -> {
                return '.';
            }
                
            case WHITE_PAWN -> {
                return 'P';
            }
                
            case BLACK_PAWN -> {
                return 'p';
            }
                
            case WHITE_ROOK -> {
                return 'R';
            }
                
            case BLACK_ROOK -> {
                return 'r';
            }
                
            case WHITE_BISHOP -> {
                return 'B';
            }
                
            case BLACK_BISHOP -> {
                return 'b';
            }
                
            case WHITE_KNIGHT -> {
                return 'K';
            }
                
            case BLACK_KNIGHT -> {
                return 'k';
            }
                
            case WHITE_QUEEN -> {
                return 'Q';
            }
                
            case BLACK_QUEEN -> {
                return 'q';
            }
                
            case WHITE_KING -> {
                return 'X';
            }
                
            case BLACK_KING -> {
                return 'x';
            }
                
            default -> throw new IllegalStateException();
        }
    }
    
    /**
     * Returns the color of the cell at file {@code (x + 1)} and rank 
     * {@code 8 - y}.
     * 
     * @param x tje file index.
     * @param y the rank index.
     * 
     * @return {@link #CELL_COLOR_NONE} if the requested cell is empty,
     *         {@link #CELL_COLOR_WHITE} if the requested cell is white, and,
     *         {@link #CELL_COLOR_BLACK} if the requested cell is black.
     */
    private int getCellColor(final int x, final int y) {
        final byte cell = state[y][x];
        
        if (cell == EMPTY) {
            return CELL_COLOR_NONE;
        }
        
        return 0 < cell && cell < 7 ? 
                    CELL_COLOR_WHITE : 
                    CELL_COLOR_BLACK;
    }
}
