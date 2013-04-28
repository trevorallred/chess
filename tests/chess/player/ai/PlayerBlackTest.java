package chess.player.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import chess.BoardBuilder;
import chess.pieces.Color;
import chess.player.Move;
import chess.player.Player;

public class PlayerBlackTest {

	private BoardBuilder builder;

	@Before
	public void setup() {
		builder = BoardBuilder.build();
	}

	@Test
	public void testNextPawnMove() throws Exception {
		builder.addWhite("Pd3").addBlack("Pd6");
		assertMove("d6", "d5", suggestBlackMove());
	}

	// @Test
	// public void testQueen_BadMove() throws Exception {
	// builder.addWhite("Ke1,Pd2").addBlack("Ke8,Qd8");
	// assertBadMove("d8", "d2", suggestBlackMove());
	// }

	@Test
	public void testKing_OutOfCheck() throws Exception {
		builder.addWhite("Ka8,Rb8,Rf6").addBlack("Kh8,Pg7");
		assertMove("h8", "h7", suggestBlackMove());
	}

	@Test
	public void testRook_Kill() throws Exception {
		builder.addWhite("Ka8,Rb8,Rf6").addBlack("Kh8,Pg7");
		assertMove("h8", "h7", suggestBlackMove());
	}

	private Move suggestBlackMove() {
		Player player = new PlayerBlack();
		return player.suggestMove(builder.getBoard(), Color.Black);
	}

	private static void assertBadMove(String expectedFrom, String expectedTo, Move actualMove) {
		String msg = actualMove.toString() + " is considered a bad move";
		assertFalse(msg, expectedFrom.equals(actualMove.getFrom().toString()));
		assertFalse(msg, expectedTo.equals(actualMove.getTo().toString()));
	}

	private static void assertMove(String expectedFrom, String expectedTo, Move actualMove) {
		assertEquals(expectedFrom, actualMove.getFrom().toString());
		assertEquals(expectedTo, actualMove.getTo().toString());
	}
}
