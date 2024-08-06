package com.github.coderodde.game.chess;

import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.KING;
import static com.github.coderodde.game.chess.PieceType.KNIGHT;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.QUEEN;
import static com.github.coderodde.game.chess.PieceType.ROOK;
import com.github.coderodde.game.chess.impl.BlackCheckMateInspector;
import com.github.coderodde.game.chess.impl.WhiteCheckMateInspector;
import com.github.coderodde.game.chess.impl.expanders.BlackBishopExpander;
import com.github.coderodde.game.chess.impl.expanders.BlackKingExpander;
import com.github.coderodde.game.chess.impl.expanders.BlackKnightExpander;
import com.github.coderodde.game.chess.impl.expanders.BlackPawnExpander;
import com.github.coderodde.game.chess.impl.expanders.BlackQueenExpander;
import com.github.coderodde.game.chess.impl.expanders.BlackRookExpander;
import com.github.coderodde.game.chess.impl.expanders.WhiteBishopExpander;
import com.github.coderodde.game.chess.impl.expanders.WhiteKingExpander;
import com.github.coderodde.game.chess.impl.expanders.WhiteKnightExpander;
import com.github.coderodde.game.chess.impl.expanders.WhitePawnExpander;
import com.github.coderodde.game.chess.impl.expanders.WhiteQueenExpander;
import com.github.coderodde.game.chess.impl.expanders.WhiteRookExpander;
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
    
    private static final CheckMateInspector WHITE_CHECK_MATE_INSPECTOR = 
            new WhiteCheckMateInspector();
    
    private static final CheckMateInspector BLACK_CHECK_MATE_INSPECTOR = 
            new BlackCheckMateInspector();
    
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
    private int whiteKingFile;
    private int whiteKingRank;
    private int blackKingFile;
    private int blackKingRank;
    
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
        
        blackKingFile = 4;
        blackKingRank = 0;
        
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
        
        whiteKingFile = 4;
        whiteKingRank = 7;
        
        for (int file = 0; file < N; file++) {
            state[6][file] = new Piece(PieceColor.WHITE,
                                       PieceType.PAWN,
                                       WHITE_PAWN_EXPANDER);
        }
    }
     
    public ChessBoardState(final String[] description) {
        this();
        clear();
        
        for (int rank = 0; rank < N; rank++) {
            final String rowDescription = description[rank];
            
            if (rowDescription.length() != N) {
                throw new IllegalArgumentException(
                        String.format(
                                "Bad state description \"%s\" at rank %d. ",
                                rowDescription, 
                                rank));
            }
            
            for (int file = 0; file < N; file++) {
                final char pieceCharacter = rowDescription.charAt(file);
                
                if (pieceCharacter == '.' || pieceCharacter == '#') {
                    continue;
                }
                
                switch (pieceCharacter) {
                    case 'p':
                        set(file, rank, new Piece(BLACK, 
                                                  PAWN, 
                                                  BLACK_PAWN_EXPANDER));
                        break;
                        
                    case 'n':
                        set(file, rank, new Piece(BLACK,
                                                  KNIGHT, 
                                                  BLACK_KNIGHT_EXPANDER));
                        break;
                        
                    case 'b':
                        set(file, rank, new Piece(BLACK, 
                                                  BISHOP, 
                                                  BLACK_BISHOP_EXPANDER));
                        break;
                        
                    case 'r':
                        set(file, rank, new Piece(BLACK,
                                                  ROOK,
                                                  BLACK_ROOK_EXPANDER));
                        break;
                        
                    case 'q':
                        set(file, rank, new Piece(BLACK,
                                                  QUEEN, 
                                                  BLACK_QUEEN_EXPANDER));
                        break;
                        
                    case 'k':
                        set(file, rank, new Piece(BLACK, 
                                                  KING, 
                                                  BLACK_KING_EXPANDER));
                        break;
                        
                    case 'P':
                        set(file, rank, new Piece(WHITE, 
                                                  PAWN, 
                                                  WHITE_PAWN_EXPANDER));
                        break;
                        
                    case 'N':
                        set(file, rank, new Piece(WHITE, 
                                                  KNIGHT, 
                                                  WHITE_KNIGHT_EXPANDER));
                        break;
                        
                    case 'B':
                        set(file, rank, new Piece(WHITE, 
                                                  BISHOP, 
                                                  WHITE_BISHOP_EXPANDER));
                        break;
                        
                    case 'R':
                        set(file, rank, new Piece(WHITE, 
                                                  ROOK, 
                                                  WHITE_ROOK_EXPANDER));
                        break;
                        
                    case 'Q':
                        set(file, rank, new Piece(WHITE,
                                                  QUEEN,
                                                  WHITE_QUEEN_EXPANDER));
                        break;
                        
                    case 'K':
                        set(file, rank, new Piece(WHITE,
                                                  KING,
                                                  WHITE_KING_EXPANDER));
                        break;
                        
                    default:
                        throw new IllegalStateException(
                                String.format(
                                        "Unknown piece character: %c.", 
                                        pieceCharacter));
                }
            }
        }
    }
    
    public ChessBoardState(final ChessBoardState copy) {
        this.state = new Piece[N][N];
        
        // TODO: Does this mess the logic?
        for (int rank = 0; rank < N; rank++) {
            System.arraycopy(copy.state[rank],
                             0,
                             this.state[rank], 
                             0, 
                             N);
        }
        
        whiteKingFile = copy.whiteKingFile;
        whiteKingRank = copy.whiteKingRank;
        blackKingFile = copy.blackKingFile;
        blackKingRank = copy.blackKingRank;
        
//        for (int rank = 0; rank < N; rank++) {
//            for (int file = 0; file < N; file++) {
//                if (copy.state[rank][file] == null) {
//                    continue;
//                }
//                
//                this.state[rank][file] = new Piece(copy.state[rank][file]);
//            }
//        }
        
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
    
    public boolean strongEquals(final ChessBoardState otherState) {
        if (!equals(otherState)) {
            return false;
        }
        
        if (!Arrays.equals(whiteIsPreviouslyDoubleMoved,
                           otherState.whiteIsPreviouslyDoubleMoved)) {
            return false;
        }
        
        if (!Arrays.equals(blackIsPreviouslyDoubleMoved, 
                           otherState.blackIsPreviouslyDoubleMoved)) {
            return false;
        }
        
        // TODO: (1) Check castling flags, (2) king coordinates and the (3) en 
        // passant flags.
        return true;
    }
    
    public void clearBlackInitialDoubleMoveFlags() {
        Arrays.fill(this.blackIsPreviouslyDoubleMoved, false);
    }
    
    public void clearWhiteInitialDoubleMoveFlags() {
        Arrays.fill(this.whiteIsPreviouslyDoubleMoved, false);
    }
    
    public int getWhiteKingFile() {
        return whiteKingFile;
    }
    
    public int getWhiteKingRank() {
        return whiteKingRank;
    }
    
    public int getBlackKingFile() {
        return blackKingFile;
    }
    
    public int getBlackKingRank() {
        return blackKingRank;
    }
    
    public void setWhiteKingFile(final int whiteKingFile) {
        this.whiteKingFile = whiteKingFile;
    }
    
    public void setWhiteKingRank(final int whiteKingRank) {
        this.whiteKingRank = whiteKingRank;
    }
    
    public void setBlackKingFile(final int blackKingFile) {
        this.blackKingFile = blackKingFile;
    }
    
    public void setBlackKingRank(final int blackKingRank) {
        this.blackKingRank = blackKingRank;
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
    
    public void move(final int sourceFile,
                     final int sourceRank,
                     final int targetFile,
                     final int targetRank) {
        
        final Piece piece = get(sourceFile, 
                                sourceRank);
        
        set(targetFile,
            targetRank,
            piece);
        
        clear(sourceFile,
              sourceRank);
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
        
        if (playerTurn == PlayerTurn.WHITE) {
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
            
            return children;
            
        } else {
            // Once here, we must have 'playerTurn == BLACK':
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
            
            return children;
        }
    }
    
    public boolean isCheckMate(final PlayerTurn playerTurn) {
        if (playerTurn == PlayerTurn.WHITE) {
            return WHITE_CHECK_MATE_INSPECTOR.isInCheckMate(this);
        } else {
            return BLACK_CHECK_MATE_INSPECTOR.isInCheckMate(this);
        }
    }
    
    private boolean isCheckMateWhite() {
        return isCheckMate(PlayerTurn.WHITE);
    }
    
    private boolean isCheckMateBlack() {
        return isCheckMate(PlayerTurn.BLACK);
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
        
//        if (cellIsUnderAttackWhiteQueen(file, rank)) {
//            return true;
//        }
//        
//        if (cellIsUnderAttackWhiteRook(file, rank)) {
//            return true;
//        }
        
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
    
//    /**
//     * Checks that a white piece is under attack by a black queen.
//     * 
//     * @param file the file of the piece to test for.
//     * @param rank the rank of the piece to test for.
//     * 
//     * @return {@code true} if and only if the white piece is under attack. 
//     */
//    private boolean cellIsUnderAttackWhiteQueen(final int file, 
//                                                final int rank) {
//        
//        if (cellIsUnderAttackWhiteQueenNorth(file, rank)) {
//            return true;
//        }
//        
//        if (cellIsUnderAttackWhiteQueenSouth(file, rank)) {
//            return true;
//        }
//        
//        if (cellIsUnderAttackWhiteQueenWest(file, rank)) {
//            return true;
//        }
//        
//        if (cellIsUnderAttackWhiteQueenEast(file, rank)) {
//            return true;
//        }
//        
//        if (cellIsUnderAttackWhiteQueenNorthEast(file, rank)) {
//            return true;
//        }
//        
//        if (cellIsUnderAttackWhiteQueenNorthWest(file, rank)) {
//            return true;
//        }
//        
//        if (cellIsUnderAttackWhiteQueenSouthEast(file, rank)) {
//            return true;
//        }
//        
//        if (cellIsUnderAttackWhiteQueenSouthWest(file, rank)) {
//            return true;
//        }
//        
//        return false;
//    }
    
    /**
     * Checks that a white piece is under attack by a black rook.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean cellIsUnderAttackWhiteRook(final int file, 
                                               final int rank) {
        
        if (cellIsUnderAttackWhiteRookNorth(file, rank)) {
            return true;
        }
        
        if (cellIsUnderAttackWhiteRookSouth(file, rank)) {
            return true;
        }
        
        if (cellIsUnderAttackWhiteRookWest(file, rank)) {
            return true;
        }
        
        if (cellIsUnderAttackWhiteRookEast(file, rank)) {
            return true;
        }
        
        return false;
    }
    
    
    /**
     * Checks that a white piece is under attack by a black rook above.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean cellIsUnderAttackWhiteRookNorth(final int file,
                                                   final int rank) {
        for (int r = rank - 1; r >= 0; r--) {
            
            final Piece piece = get(file, r);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                return false;
            }
            
            if (piece.getPieceType() == PieceType.ROOK) {
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black rook below.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean cellIsUnderAttackWhiteRookSouth(final int file,
                                                    final int rank) {
        for (int r = rank + 1; r < N; r++) {
            
            final Piece piece = get(file, r);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                return false;
            }
            
            if (piece.getPieceType() == PieceType.ROOK) {
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black rook on the left.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean cellIsUnderAttackWhiteRookWest(final int file,
                                                   final int rank) {
        for (int f = file - 1; f >= 0; f--) {
            
            final Piece piece = get(f, rank);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                return false;
            }
            
            if (piece.getPieceType() == PieceType.ROOK) {
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
    
    /**
     * Checks that a white piece is under attack by a black rook on the left.
     * 
     * @param file the file of the piece to test for.
     * @param rank the rank of the piece to test for.
     * 
     * @return {@code true} if and only if the white piece is under attack. 
     */
    private boolean cellIsUnderAttackWhiteRookEast(final int file,
                                                   final int rank) {
        for (int f = file + 1; f < N; f++) {
            
            final Piece piece = get(f, rank);
            
            if (piece == null) {
                continue;
            }
            
            if (piece.isWhite()) {
                return false;
            }
            
            if (piece.getPieceType() == PieceType.ROOK) {
                return true;
            }
        }
        
        // Once here, there is no black queen below the piece:
        return false;
    }
}
