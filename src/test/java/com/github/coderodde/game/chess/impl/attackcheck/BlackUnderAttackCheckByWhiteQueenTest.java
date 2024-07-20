package com.github.coderodde.game.chess.impl.attackcheck;

import com.github.coderodde.game.chess.ChessBoardState;
import com.github.coderodde.game.chess.Piece;
import static com.github.coderodde.game.chess.PieceColor.BLACK;
import static com.github.coderodde.game.chess.PieceColor.WHITE;
import static com.github.coderodde.game.chess.PieceType.BISHOP;
import static com.github.coderodde.game.chess.PieceType.PAWN;
import static com.github.coderodde.game.chess.PieceType.QUEEN;
import com.github.coderodde.game.chess.CellCoordinates;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.github.coderodde.game.chess.UnderAttackCheck;

public final class BlackUnderAttackCheckByWhiteQueenTest {
    
    private static final Piece whiteQueen = new Piece(WHITE, QUEEN);
    private static final Piece blackQueen = new Piece(BLACK, QUEEN);
    
    private static final Piece whitePawn = new Piece(WHITE, PAWN);
    private static final Piece blackPawn = new Piece(BLACK, PAWN);
    
    private static final UnderAttackCheck QUEEN_CHECK = 
            new BlackUnderAttackCheckByWhiteQueen();
    
    private final ChessBoardState state = new ChessBoardState();
    private static final CellCoordinates CELL_COORDS =
            UnderAttackCheck.ATTACKER_COORDINATES;
    
    @Before
    public void before() {
        state.clear();
        state.set(3, 3, blackPawn);
    }
    
    @Test
    public void whiteQueenNorth() {
        state.set(3, 1, whiteQueen);
        assertCheck();
        assertCellCoords(3, 1);
    }
    
    @Test
    public void whiteQueenSouth() {
        state.set(3, 7, whiteQueen);
        assertCheck();
        assertCellCoords(3, 7);
    }
    
    @Test
    public void whiteQueenWest() {
        state.set(0, 3, whiteQueen);
        assertCheck();
        assertCellCoords(0, 3);
    }
    
    @Test
    public void whiteQueenEast() {
        state.set(6, 3, whiteQueen);
        assertCheck();
        assertCellCoords(6, 3);
    }
    
    @Test
    public void whiteQueenNorthEast() {
        state.set(6, 0, whiteQueen);
        assertCheck();
        assertCellCoords(6, 0);
    }
    
    @Test
    public void whiteQueenNorthWest() {
        state.set(0, 0, whiteQueen);
        assertCheck();
        assertCellCoords(0, 0);
    }
    
    @Test
    public void whiteQueenSouthEast() {
        state.set(6, 6, whiteQueen);
        assertCheck();
        assertCellCoords(6, 6);
    }
    
    @Test
    public void whiteQueenSouthWest() {
        state.set(0, 6, whiteQueen);
        assertCheck();
        assertCellCoords(0, 6);
    }
    
    @Test
    public void blackQueenNorth() {
        state.set(3, 1, blackQueen);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blackQueenSouth() {
        state.set(3, 7, blackQueen);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blackQueenWest() {
        state.set(0, 3, blackQueen);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blackQueenEast() {
        state.set(6, 3, blackQueen);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blackQueenNorthEast() {
        state.set(6, 0, blackQueen);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blackQueenNorthWest() {
        state.set(0, 0, blackQueen);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blackQueenSouthEast() {
        state.set(6, 6, blackQueen);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void blackQueenSouthWest() {
        state.set(0, 6, blackQueen);
        assertNotCheck();
        assertNoCell();
    }
        
    @Test
    public void whitePawnNorth() {
        state.set(3, 1, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnSouth() {
        state.set(3, 7, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnWest() {
        state.set(0, 3, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnEast() {
        state.set(6, 3, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnNorthEast() {
        state.set(6, 0, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnNorthWest() {
        state.set(0, 0, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnSouthEast() {
        state.set(6, 6, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void whitePawnSouthWest() {
        state.set(0, 6, whitePawn);
        assertNotCheck();
        assertNoCell();
    }
    
    @Test
    public void threatenedInAllDirection() {
        state.set(3, 3, new Piece(WHITE, BISHOP));
        
        // Diagonal queens:
        state.set(0, 0, whiteQueen);
        state.set(6, 6, whiteQueen);
        state.set(1, 5, whiteQueen);
        state.set(5, 1, whiteQueen);
        
        // Straight queens:
        state.set(3, 5, whiteQueen);
        state.set(3, 0, whiteQueen);
        state.set(0, 3, whiteQueen);
        state.set(7, 3, whiteQueen);
        
        assertTrue(QUEEN_CHECK.check(state, 3, 3));
        
        System.out.println(WhiteUnderAttackCheck.ATTACKER_COORDINATES.file + " : " + WhiteUnderAttackCheck.ATTACKER_COORDINATES.rank);
    }
    
    @Test
    public void limitNorthWestFile() {
        state.set(2, 3, blackPawn);
        assertNotCheck(2, 3);
        assertNoCell();
    }
    
    @Test
    public void limitNorthWestRank() {
        state.set(3, 2, blackPawn);
        assertNotCheck(3, 2);
        assertNoCell();
    }
    
    @Test
    public void limitNorthWestBoth() {
        state.set(2, 2, blackPawn);
        assertNotCheck(2, 2);
        assertNoCell();
    }
    
    @Test
    public void limitNorthEastFile() {
        state.set(6, 3, blackPawn);
        assertNotCheck(6, 3);
        assertNoCell();
    }
    
    @Test
    public void limitNorthEastRank() {
        state.set(3, 6, blackQueen);
        assertNotCheck(3, 6);
        assertNoCell();
    }
    
    private void assertNotCheck(final int file, 
                                final int rank) {
        
        assertFalse(QUEEN_CHECK.check(state, file, rank));
    }
    
    private void assertCheck() {
        assertTrue(QUEEN_CHECK.check(state, 3, 3));
    }
    
    private void assertNotCheck() {
        assertFalse(QUEEN_CHECK.check(state, 3, 3));
    }
    
    private void assertCellCoords(final int file, final int rank) {
        assertEquals(file, CELL_COORDS.file);
        assertEquals(rank, CELL_COORDS.rank);
    }
    
    private void assertNoCell() {
        assertEquals(CellCoordinates.NO_ATTACK_FILE, CELL_COORDS.file);
    }
}
