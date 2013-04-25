package chess;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import chess.pieces.Piece;

public class BoardTest {

	@Test
	public void testStartNew() throws Exception {
		Board board = BoardBuilder.startNew();

		assertEquals(32, board.getPieces().size());
	}

	@Test
	public void testPossibleWhiteMoves_AfterStart() throws Exception {
		Board board = BoardBuilder.startNew();

		System.out.println(BoardPrinter.print(board));

		Map<Piece, Set<Location>> nextMoves = board.getNextMoves();

		for (Piece piece : nextMoves.keySet()) {
			System.out.println(piece.toString() + piece.getLocation().toString());
			for (Location toLocation : nextMoves.get(piece)) {
				System.out.println("     " + toLocation);
			}
		}
		assertEquals(14, nextMoves.size());
	}
}
