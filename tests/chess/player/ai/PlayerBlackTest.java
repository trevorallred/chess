package chess.player.ai;

import junit.framework.Assert;

import org.junit.Test;

import chess.Board;
import chess.BoardBuilder;
import chess.pieces.Color;
import chess.player.Move;
import chess.player.Player;

public class PlayerBlackTest {
	@Test
	public void testRook_Kill() throws Exception {
		testBasic("Pa1,Kf1", "Ra8,Kf8", "a8", "a1");
	}

	private void testBasic(String white, String black, String from, String to) {
		Board board = BoardBuilder.build(white, black);
		Player player = new PlayerBlack();
		Move move = player.suggestMove(board, Color.Black);
		Assert.assertEquals(from, move.getFrom().toString());
		Assert.assertEquals(to, move.getTo().toString());
	}
}
