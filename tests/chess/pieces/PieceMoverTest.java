package chess.pieces;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import chess.Board;
import chess.BoardBuilder;
import chess.BoardPrinter;
import chess.Location;
import chess.player.Move;

public class PieceMoverTest {
	@Test
	public void testBishop_Best() throws Exception {
		testPiece("Bc4", "", "c4", 11);
		testPiece("Bc4,Bb5", "", "c4", 9);
	}

	@Test
	public void testBishop_CanKill() throws Exception {
		testPiece("Bc4", "Pb3", "c4", 10);
	}

	@Test
	public void testBishop_Best2() throws Exception {
		testPiece("Pb2", "", "b2", 2);
	}

	@Test
	public void testRook_Kill() throws Exception {
		String from = "Ra8";
		String to = "Pa1";
		Board board = BoardBuilder.build(to, from);
		System.out.println(BoardPrinter.print(board));
		PieceMover mover = new PieceMover(board);
		Move move = new Move(new Location("a8"), new Location("a1"));
		mover.move(move);
		System.out.println(BoardPrinter.print(board));
		Assert.assertEquals(1, board.getPieces().size());
	}

	private void testPiece(String white, String black, String location, int expectedMoves) {
		Board board = BoardBuilder.build(white, black);
		System.out.println(white + " vs " + black);
		System.out.println(BoardPrinter.print(board));
		Piece piece = board.getPieceAtLocation(new Location(location));
		PieceMover mover = new PieceMover(board);
		Set<Location> nextMoves = mover.getNextMoves(piece);
		Assert.assertEquals(expectedMoves, nextMoves.size());
	}

}
