package chess;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoardBuilderTest {
	@Test
	public void testStartNew() throws Exception {
		Board board = BoardBuilder.build().addAll().getBoard();
		assertEquals(32, board.getPieces().size());
	}
}
