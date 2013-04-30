package chess.player.ai;

import chess.pieces.Color;

@SuppressWarnings("serial")
public class KingIsInCheck extends Exception {
	public KingIsInCheck(Color color) {
		super("The " + color + "King can't move into or remain in Check");
	}
}
