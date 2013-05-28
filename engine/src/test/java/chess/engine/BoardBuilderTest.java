package chess.engine;

import static org.junit.Assert.assertEquals;

import chess.engine.Board;
import chess.engine.BoardBuilder;
import org.junit.Test;

public class BoardBuilderTest {
	@Test
	public void testStartNew() throws Exception {
		Board board = BoardBuilder.build().addAll().getBoard();
		assertEquals(32, board.getPieces().size());
	}
}
