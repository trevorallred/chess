package chess.player;

import chess.Board;

public interface Player {
	Move suggestMove(Board board);
}
