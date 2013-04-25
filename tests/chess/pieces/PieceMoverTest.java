package chess.pieces;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import chess.Board;
import chess.BoardBuilder;
import chess.BoardPrinter;
import chess.Location;

public class PieceMoverTest {
	@Test
	public void testBishop_Best() throws Exception {
		testPiece("Bc4", "", "c4", 11);
		testPiece("Bc4,Bb5", "", "c4", 9);
	}

	@Test
	public void testBishop_Kill() throws Exception {
		testPiece("Bc4", "Pb3", "c4", 10);
	}

	@Test
	public void testBishop_Best2() throws Exception {
		testPiece("Pb2", "", "b2", 2);
	}

	private void testPiece(String white, String black, String location, int expectedMoves) {
		Board board = BoardBuilder.build(white, black);
		System.out.println(white + " vs " + black);
		System.out.println(BoardPrinter.print(board));
		Piece piece = board.getPieceAtLocation(new Location(location));
		Set<Location> nextMoves = piece.getNextMoves(board);
		Assert.assertEquals(expectedMoves, nextMoves.size());
	}

}
