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
    
    private static final byte EMPTY        = 0;
    private static final byte WHITE_PAWN   = 1;
    private static final byte WHITE_BISHOP = 2;
    private static final byte WHITE_KNIGHT = 3;
    private static final byte WHITE_ROOK   = 4;
    private static final byte WHITE_QUEEN  = 5;
    private static final byte WHITE_KING   = 6;
    
    private static final byte BLACK_PAWN   = 9;
    private static final byte BLACK_BISHOP = 10;
    private static final byte BLACK_KNIGHT = 11;
    private static final byte BLACK_ROOK   = 12;
    private static final byte BLACK_QUEEN  = 13;
    private static final byte BLACK_KING   = 14;
    
    private static final int CELL_COLOR_NONE = 0;
    private static final int CELL_COLOR_WHITE = 1;
    private static final int CELL_COLOR_BLACK = -1;
    
    private byte[][] state = new byte[N][N];
    private byte[] promotionCounters = new byte[8];
    private byte pawnInitialFlags = (byte) 0xff;
     
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
        
        this.pawnInitialFlags = copy.pawnInitialFlags;
        this.promotionCounters = Arrays.copyOf(copy.promotionCounters, copy.promotionCounters.length);
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
    
    public List<ChessBoardState> expand() {
        final List<ChessBoardState> children = new ArrayList<>();
        
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                final byte cell = state[y][x];
                
                if (cell == EMPTY) {
                    continue;
                }
                
                expandImpl(children, x, y, cell);
            }
        }
        
        return children;
    }
    
    private void expandImpl(final List<ChessBoardState> children,
                            final int x,
                            final int y,
                            final byte cell) {
        switch (cell) {
            case WHITE_PAWN:
                expandImplWhitePawn(children, x, y);
                break;
                
            default:
                throw new IllegalStateException("Should not get here.");
        }
    }
    
    private void expandImplWhitePawn(final List<ChessBoardState> children,
                                     final int x,
                                     final int y) {
        
        final boolean pawnHasInitialMove = checkInitialMovePawn(x);
        
        if (pawnHasInitialMove && state[y - 2][x] == EMPTY &&
                                  state[y - 1][x] == EMPTY) {
           
            // Once here, can move the white pawn two moves forward:
            unsetInitialMovePawn(x);

            final ChessBoardState child = new ChessBoardState(this);

            child.state[y][x] = EMPTY; 
            child.state[y - 2][x] = WHITE_PAWN;
            
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
                if (canPromoteWhite()) {
                    child.state[y - 1][x] = EMPTY;
                    
                    addWhitePromotions(children,
                                       child,
                                       x);
                }
            }
        }
        
        if (x > 0 && y > 0 && getCellColor(x - 1, y - 1) == CELL_COLOR_BLACK) {
            // Once here, can eat to the left board:
            final ChessBoardState child = new ChessBoardState(this);
            child.state[y][x] = EMPTY;
            child.state[y - 1][x - 1] = WHITE_PAWN;
            children.add(child);
        }
        
        if (x < N - 1 && y > 0 
                      && getCellColor(x + 1, y - 1) == CELL_COLOR_BLACK) {
            // Once here, can eat to the right board:
            final ChessBoardState child = new ChessBoardState(this);
            child.state[y][x] = EMPTY;
            child.state[y - 1][x + 1] = WHITE_PAWN;
            children.add(child);
        }
    }
    
    private void addWhitePromotions(
            final List<ChessBoardState> children,
            final ChessBoardState child,
            final int x) {
        
        for (int index  = PROMOTION_COUNTER_INDEX_WHITE_QUEEN; 
                 index <= PROMOTION_COUNTER_INDEX_WHITE_KNIGHT;
                 index++) {
            
            byte promotionCount = child.promotionCounters[index]; 
            
            if (promotionCount == 0) {
                continue;
            }
            
            final ChessBoardState nextChild = new ChessBoardState(child);
            
            nextChild.state[0][x] = convertPromotionIndexToPieceCode(index);
                    
            child.promotionCounters[index]--;
            
            children.add(nextChild);
        }
    }
    
    private static byte convertPromotionIndexToPieceCode(final int index) {
        switch (index) {
            
            case PROMOTION_COUNTER_INDEX_WHITE_QUEEN :
                return WHITE_QUEEN;
                
            case PROMOTION_COUNTER_INDEX_WHITE_ROOK  :
                return WHITE_ROOK;
                
            case PROMOTION_COUNTER_INDEX_WHITE_BISHOP:
                return WHITE_BISHOP;
                
            case PROMOTION_COUNTER_INDEX_WHITE_KNIGHT:
                return WHITE_KNIGHT;
                
            case PROMOTION_COUNTER_INDEX_BLACK_QUEEN :
                return BLACK_QUEEN;
                
            case PROMOTION_COUNTER_INDEX_BLACK_ROOK  :
                return BLACK_ROOK;
                
            case PROMOTION_COUNTER_INDEX_BLACK_BISHOP:
                return BLACK_BISHOP;
                
            case PROMOTION_COUNTER_INDEX_BLACK_KNIGHT:
                return BLACK_KNIGHT;
                
            default: 
                throw new IllegalStateException();
        }
    }
    
    private boolean canPromoteWhite() {
        if (promotionCounters[PROMOTION_COUNTER_INDEX_WHITE_BISHOP] != 0) {
            return true;
        }
        
        if (promotionCounters[PROMOTION_COUNTER_INDEX_WHITE_KNIGHT] != 0) {
            return true;
        }
        
        if (promotionCounters[PROMOTION_COUNTER_INDEX_WHITE_ROOK] != 0) {
            return true;
        }
        
        if (promotionCounters[PROMOTION_COUNTER_INDEX_WHITE_QUEEN] != 0) {
            return true;
        }
        
        return false;
    }
    
    private boolean canPromoteBlack() {
        if (promotionCounters[PROMOTION_COUNTER_INDEX_BLACK_BISHOP] != 0) {
            return true;
        }
        
        if (promotionCounters[PROMOTION_COUNTER_INDEX_BLACK_KNIGHT] != 0) {
            return true;
        }
        
        if (promotionCounters[PROMOTION_COUNTER_INDEX_BLACK_ROOK] != 0) {
            return true;
        }
        
        if (promotionCounters[PROMOTION_COUNTER_INDEX_BLACK_QUEEN] != 0) {
            return true;
        }
        
        return false;
    }
    
    private boolean checkInitialMovePawn(final int x) {
        return (pawnInitialFlags & (1 << x)) != 0;
    }
    
    private void unsetInitialMovePawn(final int x) {
        pawnInitialFlags &= ~(1 << x);
    }
    
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
