package chess;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import chess.pieces.Color;
import chess.pieces.Piece;
import chess.pieces.PieceMover;

public class BoardTest {

	@Test
	public void testStartNew() throws Exception {
		Board board = BoardBuilder.build().addAll().getBoard();

		assertEquals(32, board.getPieces().size());
	}

	@Test
	public void testPossibleWhiteMoves_AfterStart() throws Exception {
		Board board = BoardBuilder.build().addAll().getBoard();

		System.out.println(BoardPrinter.print(board));
		
		PieceMover mover = new PieceMover(board);
		Map<Piece, Set<Location>> nextMoves = mover.getNextMoves(Color.White);

		for (Piece piece : nextMoves.keySet()) {
			System.out.println(piece.toString() + piece.getLocation().toString());
			for (Location toLocation : nextMoves.get(piece)) {
				System.out.println("     " + toLocation);
			}
		}
		assertEquals(10, nextMoves.size());
	}
}
