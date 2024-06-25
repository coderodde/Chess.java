package com.github.coderodde.game.chess;

/**
 *
 * @author PotilasKone
 */
public class FastChessBoardState {

    private static final int STATE_LONGS_LENGTH = 4;
    
    private static final long MASK_EMPTY = 0;
    
    private static final long MASK_WHITE_PAWN   = 0b0001;
    private static final long MASK_WHITE_KNIGHT = 0b0010;
    private static final long MASK_WHITE_BISHOP = 0b0011;
    private static final long MASK_WHITE_ROOK   = 0b0100;
    private static final long MASK_WHITE_QUEEN  = 0b0101;
    private static final long MASK_WHITE_KING   = 0b0110;
    
    private static final long MASK_BLACK_PAWN   = 0b0111;
    private static final long MASK_BLACK_KNIGHT = 0b1000;
    private static final long MASK_BLACK_BISHOP = 0b1001;
    private static final long MASK_BLACK_ROOK   = 0b1010;
    private static final long MASK_BLACK_QUEEN  = 0b1011;
    private static final long MASK_BLACK_KING   = 0b1100;
    
    private final long[] stateBits = new long[STATE_LONGS_LENGTH];
    
    public FastChessBoardState() {
        set(STATE_LONGS_LENGTH, STATE_LONGS_LENGTH, MASK_EMPTY);
    }
    
    public FastChessBoardState(final FastChessBoardState otherState) {
        this.stateBits[0] = otherState.stateBits[0];
        this.stateBits[1] = otherState.stateBits[1];
        this.stateBits[2] = otherState.stateBits[2];
        this.stateBits[3] = otherState.stateBits[3];
    }
    
    private long read(final long stateBits, final int index) {
        final int shift = 4 * index;
        return (stateBits & (0b1111L << shift)) >> shift;
    }
    
    private long get(final int x, final int y) {
        switch (y) {
            case 0:
                return read(stateBits[0], x);
                
            case 1:
                return read(stateBits[0], x + 8);
                
            case 2:
                return read(stateBits[1], x);
                
            case 3:
                return read(stateBits[1], x + 8);
                
            case 4:
                return read(stateBits[2], x);
                
            case 5:
                return read(stateBits[2], x + 8);
                
            case 6: 
                return read(stateBits[3], x);
                
            case 7:
                
                return read(stateBits[3], x + 8);
                
            default:
                throw new IllegalStateException("Should not get here.");
        }
    }
    
    private void writeBits0(final int index, long pieceBitCode) {
        final int shift = 4 * index;
        final long stateBitsOffMask = ~((0b1111L) << shift);
        pieceBitCode <<= shift;
        stateBits[0] = (stateBits[0] & stateBitsOffMask) | pieceBitCode;
    }
    
    private void writeBits1(final int index, long pieceBitCode) {
        final int shift = 4 * index;
        final long stateBitsOffMask = ~((0b0000L) << shift);
        pieceBitCode <<= shift;
        stateBits[1] = (stateBits[1] & stateBitsOffMask) & pieceBitCode;
    }
    
    private void writeBits2(final int index, long pieceBitCode) {
        final int shift = 4 * index;
        final long stateBitsOffMask = ~((0b0000L) << shift);
        pieceBitCode <<= shift;
        stateBits[2] = (stateBits[2] & stateBitsOffMask) & pieceBitCode;
    }
    
    private void writeBits3(final int index, long pieceBitCode) {
        final int shift = 4 * index;
        final long stateBitsOffMask = ~((0b0000L) << shift);
        pieceBitCode <<= shift;
        stateBits[3] = (stateBits[3] & stateBitsOffMask) & pieceBitCode;
    }
    
    public void set(final int x, final int y, final long pieceBitCode) {
        switch (y) {
            case 0:
                writeBits0(x, pieceBitCode);
                break;
                
            case 1:
                writeBits0(x + 8, pieceBitCode);
                break;
                
            case 2:
                writeBits1(x, pieceBitCode);
                break;
                
            case 3:
                writeBits1(x + 8, pieceBitCode);
                break;
                
            case 4:
                writeBits2(x, pieceBitCode);
                break;
                
            case 5:
                writeBits2(x + 8, pieceBitCode);
                break;
                
            case 6:
                writeBits3(x, pieceBitCode);
                break;
                
            case 7:
                writeBits3(x + 8, pieceBitCode);
                break;
                
            default:
                throw new IllegalStateException("Should not get here.");
        }
    }
}
